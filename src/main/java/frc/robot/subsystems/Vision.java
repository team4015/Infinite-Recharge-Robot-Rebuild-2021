package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.lang.Math;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class Vision extends SubsystemBase
{
    private Solenoid ringlight; // Ringlight is connected to PCM using a solenoid channel
    private Thread cameraFeed;
    private boolean runFeed;
    private double height;
    private double width;
    private Point targetCenter;
    private Point reticle;
    private boolean turnLeft;
    private boolean turnRight;
    private double targetDistance;
    private double calculatedShooterSpeed;

    public Vision()
    {
        ringlight = new Solenoid(Constants.RING_LIGHT);
        cameraFeed = new Thread(() -> getCameraFeed());
        cameraFeed.start();

        height = 0;
        width = 0;
        targetCenter = new Point();
        reticle = new Point();
        turnLeft = false;
        turnRight = false;
        targetDistance = 0;
        calculatedShooterSpeed = 0;
    }

    public void toggleVision()
    {
        if (isEnabled())
        {
            stop();
        }
        else
        {
            start();
        }
    }

    private void getCameraFeed()
    {
        CameraServer.getInstance().startAutomaticCapture("Unprocessed", 0);

        CvSink videoInput = CameraServer.getInstance().getVideo();
        CvSource videoOutput = CameraServer.getInstance().putVideo("Processed", 640, 480);

        Mat unprocessedPixels = new Mat();

        while (true)
        {
            if (videoInput.grabFrame(unprocessedPixels) == 0)
            {
                continue;
            }

            videoOutput.putFrame(processFrames(unprocessedPixels));
        }
    }

    private Mat processFrames(Mat image)
    {
        if (!runFeed)
        {
            return image; // If ringlight is not on, unaltered frames will be sent back to driver station
        }

        List<MatOfPoint> contours = new ArrayList<>();
        Point [] endPoints = new Point[2]; // This will store the two points used to make a targeting box
        
        Core.inRange(image, Constants.VISION_THRESHOLD, Constants.WHITE, image); // This will filter out all colours outside of the range between two colours
        Imgproc.dilate(image, image, new Mat(3, 3, 0)); // This will help remove imperfections and noise from the image

        Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE); // This will find the contours of your image but will not draw them

        endPoints = getBoxPoints(image);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2BGR); // Converts grayscale picture back to colour

        acquireTarget(image, endPoints, contours); // This will take care of targeting

        return image; // Sends back fully processed image
    }

    private Point [] getBoxPoints(Mat image)
    {
        Rect rectangle = Imgproc.boundingRect(image); // This determines a rectangle around the target but does not draw it
        Point [] endPoints = new Point[2];
        double [] centerPoints = {0, 0};

        endPoints[0] = rectangle.tl(); // Top left point of rectangle
        endPoints[1] = rectangle.br(); // Top right point of rectangle

        height = rectangle.height;
        width = rectangle.width;

        centerPoints[0] = endPoints[1].x - (width / 2);
        centerPoints[1] = endPoints[1].y - (height / 2);

        targetCenter.set(centerPoints); // Set rectangle center

        centerPoints[0] = image.width() / 2;
        centerPoints[1] = image.height() / 2;

        reticle.set(centerPoints); // Set reticle point

        return endPoints;
    }

    private void drawMisalignedMasking(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.RED);
        Imgproc.circle(image, reticle, 3, Constants.RED, -1);
        Imgproc.drawContours(image, contours, -1, Constants.WHITE, -1);
    }

    private void drawAlignedMasking(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.GREEN);
        Imgproc.circle(image, reticle, 3, Constants.GREEN, -1);
        Imgproc.drawContours(image, contours, -1, Constants.GREEN, -1);
    }

    private void acquireTarget(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        targetDistance = ((reticle.y - targetCenter.y) * -Constants.PIXEL_TO_METRES_RATIO) + Constants.TARGET_CENTER_DISTANCE; // Negative to counteract Mat pixel ordering (top to bottom), 162cm is when reticle is centered with targetCenter, 2 represents 2cm per pixel
        calculatedShooterSpeed = (0.1633 * Math.pow(targetDistance, 3)) - (0.5561 * Math.pow(targetDistance, 2)) + (0.6154 * targetDistance) + 0.4097;


        if ((reticle.x - Constants.AIM_BOT_ACCURACY) < targetCenter.x && targetCenter.x < (reticle.x + Constants.AIM_BOT_ACCURACY))
        {
            turnRight = false;
            turnLeft = false;
            
            drawAlignedMasking(image, endPoints, contours);
        }
        else if ((reticle.x - Constants.AIM_BOT_ACCURACY) < targetCenter.x)
        {
            turnRight = true;
            turnLeft = false;

            drawMisalignedMasking(image, endPoints, contours);
        }
        else if (targetCenter.x < (reticle.x + Constants.AIM_BOT_ACCURACY))
        {
            turnLeft = true;
            turnRight = false;

            drawMisalignedMasking(image, endPoints, contours);
        }
    }

    public void start()
    {
        ringlight.set(true);
        runFeed = true;
    }

    public void stop()
    {
        ringlight.set(false);
        runFeed = false;
    }

    public boolean isEnabled()
    {
        return runFeed;
    }

    public boolean turnLeft()
    {
        return turnLeft;
    }

    public boolean turnRight()
    {
        return turnRight;
    }

    public double getRequiredShooterSpeed()
    {
        return calculatedShooterSpeed;
    }
}

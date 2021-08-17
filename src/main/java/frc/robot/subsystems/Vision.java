package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
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
    private Solenoid ringlight;
    private Thread cameraFeed;
    private boolean runFeed;
    private boolean horizontallyAligned;
    private double height;
    private double width;
    private Point targetCenter;
    private Point reticle;
    private boolean turnLeft;
    private boolean turnRight;

    public Vision()
    {
        ringlight = new Solenoid(Constants.RING_LIGHT);
        horizontallyAligned = false;
        cameraFeed = new Thread(() -> getCameraFeed());
        cameraFeed.start();

        height = 0;
        width = 0;
        targetCenter = new Point();
        reticle = new Point();
        turnLeft = false;
        turnRight = false;
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
            return image;
        }

        List<MatOfPoint> contours = new ArrayList<>();
        Point [] endPoints = new Point[2];
        
        Core.inRange(image, Constants.VISION_THRESHOLD, Constants.WHITE, image);
        Imgproc.dilate(image, image, new Mat(3, 3, 0));

        Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        endPoints = getBoxPoints(image);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2BGR);

        drawTargetBox(image, endPoints, contours);

        return image;
    }

    private Point [] getBoxPoints(Mat image)
    {
        Rect rectangle = Imgproc.boundingRect(image);
        Point [] endPoints = new Point[2];
        double [] centerPoints = {0, 0};

        endPoints[0] = rectangle.tl();
        endPoints[1] = rectangle.br();

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

    private void drawTargetBox(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        if ((reticle.x - 5) < targetCenter.x && targetCenter.x < (reticle.x + 5))
        {
            horizontallyAligned = true;
            turnRight = false;
            turnLeft = false;
            
            Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.GREEN);
            Imgproc.circle(image, reticle, 3, Constants.GREEN, -1);
            Imgproc.drawContours(image, contours, -1, Constants.GREEN, -1);
        }
        else if ((reticle.x - 5) < targetCenter.x)
        {
            horizontallyAligned = false;
            turnRight = true;
            turnLeft = false;

            Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.RED);
            Imgproc.circle(image, reticle, 3, Constants.RED, -1);
            Imgproc.drawContours(image, contours, -1, Constants.WHITE, -1);
        }
        else if (targetCenter.x < (reticle.x + 5))
        {
            horizontallyAligned = false;
            turnLeft = true;
            turnRight = false;

            Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.RED);
            Imgproc.circle(image, reticle, 3, Constants.RED, -1);
            Imgproc.drawContours(image, contours, -1, Constants.WHITE, -1);
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

    public boolean getVisionEnabled()
    {
        return runFeed;
    }

    public boolean getHorizontallyAligned()
    {
        return horizontallyAligned;
    }

    public boolean turnLeft()
    {
        return turnLeft;
    }

    public boolean turnRight()
    {
        return turnRight;
    }
}

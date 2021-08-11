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
    private double height;
    private double width;
    private Point targetCenter;

    public Vision()
    {
        ringlight = new Solenoid(Constants.RING_LIGHT);
        cameraFeed = new Thread(() -> getCameraFeed());
        cameraFeed.start();

        height = 0;
        width = 0;
        targetCenter = new Point();
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

        Imgproc.drawContours(image, contours, -1, Constants.WHITE, -1);
        drawTargetBox(image, endPoints);

        return image;
    }

    private Point [] getBoxPoints(Mat image)
    {
        Rect rectangle = Imgproc.boundingRect(image);
        Point [] endPoints = new Point[2];
        double [] rectangleCenter = {0, 0};

        endPoints[0] = rectangle.tl();
        endPoints[1] = rectangle.br();

        height = rectangle.height;
        width = rectangle.width;

        rectangleCenter[0] = endPoints[1].x - (width / 2);
        rectangleCenter[1] = endPoints[1].y - (height / 2);

        targetCenter.set(rectangleCenter);

        return endPoints;
    }

    private void drawTargetBox(Mat image, Point [] endPoints)
    {
        if (((image.width() / 2) - 5) < targetCenter.x && targetCenter.x < ((image.width() / 2) + 5))
        {
            Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.GREEN);
            Imgproc.circle(image, targetCenter, 3, Constants.GREEN, -1);
        }
        else
        {
            Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.RED);
            Imgproc.circle(image, targetCenter, 3, Constants.RED, -1);
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
}

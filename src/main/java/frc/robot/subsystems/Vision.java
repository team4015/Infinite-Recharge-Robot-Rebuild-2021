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
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

public class Vision extends SubsystemBase
{
    private Solenoid ringlight;
    Thread cameraFeed;
    private boolean runFeed;

    public Vision()
    {
        ringlight = new Solenoid(Constants.RING_LIGHT);
        cameraFeed = new Thread(() -> getCameraFeed());
        cameraFeed.start();
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
        
        Core.inRange(image, new Scalar(40, 0, 175), new Scalar(255, 255, 255), image);
        Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

        endPoints = getBoxPoints(image);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2BGR);
        Imgproc.rectangle(image, endPoints[0], endPoints[1], new Scalar(0, 0, 255));
        
        Imgproc.drawContours(image, contours, -1, new Scalar(0, 255, 0), -1);

        return image;
    }

    private Point [] getBoxPoints(Mat image)
    {
        Rect rectangle = Imgproc.boundingRect(image);
        Point [] endPoints = new Point[2];

        endPoints[0] = rectangle.tl();
        endPoints[1] = rectangle.br();

        return endPoints;
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

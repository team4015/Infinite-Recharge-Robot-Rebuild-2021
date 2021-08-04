package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
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

        System.out.println("hi");

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

    private Mat processFrames(Mat unprocessedPixels)
    {
        if (!runFeed)
        {
            return unprocessedPixels;
        }

        Mat processedPixels = new Mat();

        Imgproc.cvtColor(unprocessedPixels, processedPixels, Imgproc.COLOR_BGR2HSV);
        Core.inRange(processedPixels, new Scalar(40, 0, 175), new Scalar(255, 255, 255), processedPixels);

        return processedPixels;
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

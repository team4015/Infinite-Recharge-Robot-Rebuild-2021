/* ==================================================
Authour: Shane Pinto
Description: Vision.java handles the running of the 
camera feeds, image processing, target acquirement,
course adjustment calculations, and shooter speed
calculations. It is also given control over the 
ringlight.
================================================== */

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
    private Thread cameraFeed; // The camera feed must be a thread according to wpilib documentation
    private boolean runFeed; // Will be used to tell the processing feed to run or stop
    private double height;
    private double width;
    private Point targetCenter;
    private Point reticle;
    private boolean turnLeft; // Will be used to tell the AimBot that the robot must turn left to align with the target
    private boolean turnRight; // Will be used to tell the AimBot that the robot must turn right to align with the target
    private double targetDistance;
    private double calculatedShooterSpeed; // The shooter speed that will be needed to hit the target at the current distance

    public Vision()
    {
        ringlight = new Solenoid(Constants.RING_LIGHT);
        cameraFeed = new Thread(() -> getCameraFeed()); // Camera feed will run when this thread is spawned
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

    /* =====================================
    toggleVision() switches the vision
    subsystem on and off.
    ===================================== */
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

    /* =====================================
    getCameraFeed() will spawn two different
    feeds. One will be for unprocessed frames.
    The other feed will have the ability to
    send it's frames to other methods for
    image processing and target acquirement.
    It will be returned as what the robot
    sees the video feed as.
    ===================================== */
    private void getCameraFeed()
    {
        CameraServer.getInstance().startAutomaticCapture("Unprocessed", 0);

        CvSink videoInput = CameraServer.getInstance().getVideo(); // Sends frames from the camera to a sink
        CvSource videoOutput = CameraServer.getInstance().putVideo("Processed", 640, 480); // Makes a new source for frames to be sent from

        Mat unprocessedPixels = new Mat(); // A Mat() is a matrix of pixels. It will store each pixel of the captured picture

        while (true)
        {
            if (videoInput.grabFrame(unprocessedPixels) == 0)
            {
                continue;
            }

            videoOutput.putFrame(processFrames(unprocessedPixels)); // Send processed feed to driver station
        }
    }

    /* =====================================
    processFrames() will handle taking 
    unprocessed frames and isolating colours
    inside a specified range. This can be
    used to identify targets made with
    retroreflective tape as they will often
    glow white to the camera.
    ===================================== */
    private Mat processFrames(Mat image)
    {
        if (!runFeed)
        {
            return image; // If ringlight is not on or vision is not running, unaltered frames will be sent back to driver station
        }

        List<MatOfPoint> contours = new ArrayList<>();
        Point [] endPoints = new Point[2]; // This will store the two points used to make a targeting box
        
        Core.inRange(image, Constants.VISION_THRESHOLD, Constants.WHITE, image); // This will filter out all colours outside of the range between two colours
        Imgproc.dilate(image, image, new Mat(3, 3, 0)); // This will help remove imperfections and noise from the image

        Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE); // This will find the contours of your image but will not draw them

        endPoints = getBoxPoints(image);
        Imgproc.cvtColor(image, image, Imgproc.COLOR_GRAY2BGR); // Converts grayscale picture back to colour

        acquireTarget(image, endPoints, contours); // This will handle targeting

        return image; // Sends back fully processed image
    }

    /* =====================================
    getBoxPoints() will handle drawing a 
    rectangle around a possible target. It
    will also find the center point of the 
    bounding box rectangle and the center
    point of the frame.
    ===================================== */
    private Point [] getBoxPoints(Mat image)
    {
        Rect rectangle = Imgproc.boundingRect(image); // This determines a rectangle around the target but does not draw it
        Point [] endPoints = new Point[2];
        double [] centerPoints = {0, 0};

        endPoints[0] = rectangle.tl(); // Top left point of rectangle
        endPoints[1] = rectangle.br(); // Top right point of rectangle

        height = rectangle.height;
        width = rectangle.width;

        // Finds center of rectangle
        centerPoints[0] = endPoints[1].x - (width / 2);
        centerPoints[1] = endPoints[1].y - (height / 2);

        targetCenter.set(centerPoints); // Set rectangle center

        // Finds center of image
        centerPoints[0] = image.width() / 2;
        centerPoints[1] = image.height() / 2;

        reticle.set(centerPoints); // Set reticle point

        return endPoints;
    }

    /* =====================================
    acquireTarget() will handle the 
    targeting of the supposed target and the
    calculations needed to align the robot
    with the target. It contains a rangefinder
    equation that will calculate the robot's
    horizontal distance from the target. It
    also contains a cubic function that 
    gives the correct shooter speed needed
    to hit the target at a specific distance.
    ===================================== */
    private void acquireTarget(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        // This equation takes the reticle's distance from the center of the target in pixels. It then mltiplies it by a ratio that relates change in vertical pixels to change in horizontal distance in meters. The ratio is negative because the Mat() orders pixels from 0 up starting from the top. The product is then added to the measured horziontal distance in meters when the reticle is exactly on the target center. The resulting number is the horizontal distance from the target.
        targetDistance = ((reticle.y - targetCenter.y) * -Constants.PIXEL_TO_METRES_RATIO) + Constants.TARGET_CENTER_DISTANCE;

        // This cubic equation is an interpolation of emperically determined data. Shooter speed was related with horizontal distance to yield this polynomial equation. While projectile motion can also be used, other physics forces at play often make it hard to get a fully accurate equation.
        calculatedShooterSpeed = (0.1633 * Math.pow(targetDistance, 3)) - (0.5561 * Math.pow(targetDistance, 2)) + (0.6154 * targetDistance) + 0.4097;

        if ((reticle.x - Constants.AIM_BOT_ACCURACY) < targetCenter.x && targetCenter.x < (reticle.x + Constants.AIM_BOT_ACCURACY)) // Check if target is aligned with robot
        {
            turnRight = false;
            turnLeft = false;
            
            drawAlignedMasking(image, endPoints, contours);
        }
        else if ((reticle.x - Constants.AIM_BOT_ACCURACY) < targetCenter.x) // Check if target is too far right
        {
            turnRight = true;
            turnLeft = false;

            drawMisalignedMasking(image, endPoints, contours);
        }
        else if (targetCenter.x < (reticle.x + Constants.AIM_BOT_ACCURACY)) // Check if target is too far left
        {
            turnLeft = true;
            turnRight = false;

            drawMisalignedMasking(image, endPoints, contours);
        }
    }

    /* =====================================
    drawMisalignedMasking() will draw the
    target colours as red if the robot
    is not yet aligned with the target.
    ===================================== */
    private void drawMisalignedMasking(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.RED);
        Imgproc.circle(image, reticle, 3, Constants.RED, -1);
        Imgproc.drawContours(image, contours, -1, Constants.WHITE, -1);
    }

    /* =====================================
    drawAlignedMasking() will draw the
    target colours as green if the robot
    is aligned with the target.
    ===================================== */
    private void drawAlignedMasking(Mat image, Point [] endPoints, List<MatOfPoint> contours)
    {
        Imgproc.rectangle(image, endPoints[0], endPoints[1], Constants.GREEN);
        Imgproc.circle(image, reticle, 3, Constants.GREEN, -1);
        Imgproc.drawContours(image, contours, -1, Constants.GREEN, -1);
    }

    /* =====================================
    start() will turn on the ringlight and
    will start image processing.
    ===================================== */
    public void start()
    {
        ringlight.set(true);
        runFeed = true;
    }

     /* =====================================
    stop() will turn off the ringlight and
    will stop image processing.
    ===================================== */
    public void stop()
    {
        ringlight.set(false);
        runFeed = false;
    }

     /* =====================================
    isEnabled() returns a boolean indicating
    if image processing is running or not.
    ===================================== */
    public boolean isEnabled()
    {
        return runFeed;
    }

     /* =====================================
    turnLeft() returns true if the robot
    must turn left to align with the target.
    ===================================== */
    public boolean turnLeft()
    {
        return turnLeft;
    }

    /* =====================================
    turnRight() returns true if the robot
    must turn right to align with the target.
    ===================================== */
    public boolean turnRight()
    {
        return turnRight;
    }

    /* =====================================
    getRequiredShooterSpeed() returns the
    calculated shooter speed according to
    the robot's distance from the target.
    ===================================== */
    public double getRequiredShooterSpeed()
    {
        return calculatedShooterSpeed;
    }
}

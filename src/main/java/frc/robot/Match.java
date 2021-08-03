package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

import com.fasterxml.jackson.databind.cfg.ContextAttributes.Impl;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import frc.robot.commands.teleop.*;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Match extends TimedRobot
{
  private Command m_autonomousCommand;
  private Command teleop;
  private Robot robot;

  private void getCameraFeeds()
  {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("Unprocessed", 0);
    
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

  private Mat processFrames(Mat unprocessedPixels)
  {
    Mat processedPixels = new Mat();

    Imgproc.cvtColor(unprocessedPixels, processedPixels, Imgproc.COLOR_BGR2HSV);
    Core.inRange(processedPixels, new Scalar(40, 0, 175), new Scalar(255, 255, 255), processedPixels);

    return processedPixels;
  }

  @Override
  public void robotInit()
  {
    robot = new Robot();
    teleop = new Teleop(robot);

    Thread cameraFeed = new Thread(() -> getCameraFeeds());
    cameraFeed.start();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
    // Runs the Scheduler.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    //}
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit()
  {
    // if (m_autonomousCommand != null)
    // {
    //   m_autonomousCommand.cancel();
    // }

    if (teleop != null)
    {
        teleop.schedule();
    }

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic()
  {
    
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}

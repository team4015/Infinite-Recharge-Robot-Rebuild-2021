package frc.robot.commands.teleop.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class TeleopVision extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  private boolean visionEnabled;

  public TeleopVision(Robot robot)
  {
    this.robot = robot;
    visionEnabled = false;
    addRequirements(robot.vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.vision.stop(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (robot.driver.getSteerButtonPressed(Constants.TOGGLE_VISION))
    {
      visionEnabled = !visionEnabled;
    }

    if (visionEnabled)
    {
      robot.vision.start();
    }
    else
    {
      robot.vision.stop(false);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.vision.stop(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

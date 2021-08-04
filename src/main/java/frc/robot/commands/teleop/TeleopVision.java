package frc.robot.commands.teleop;

import frc.robot.subsystems.Vision;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.controls.Driver;
import frc.robot.Robot;

public class TeleopVision extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Vision vision;
  private boolean on = false;

  public TeleopVision(Robot robot)
  {
    vision = robot.vision;
    addRequirements(vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    vision.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (Driver.getSteerButtonPressed(Constants.TOGGLE_RINGLIGHT))
    {
      on = !on;
    }

    if (on)
    {
      vision.start();
    }
    else
    {
      vision.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    vision.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

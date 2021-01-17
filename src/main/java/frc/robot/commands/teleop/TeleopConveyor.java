package frc.robot.commands.teleop;

import frc.robot.subsystems.Conveyor;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.controls.Driver;
import frc.robot.Robot;

public class TeleopConveyor extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Conveyor conveyor;

  public TeleopConveyor(Robot robot)
  {
    conveyor = robot.conveyor;
    addRequirements(conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    conveyor.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (Driver.getThrottleButton(Constants.FEED_CONVEYOR))
    {
        conveyor.feed();
    }
    else if (Driver.getThrottleButton(Constants.REVERSE_CONVEYOR))
    {
        conveyor.reverse();
    }
    else
    {
        conveyor.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    conveyor.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

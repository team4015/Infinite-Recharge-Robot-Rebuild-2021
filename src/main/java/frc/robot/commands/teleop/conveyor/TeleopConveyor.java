package frc.robot.commands.teleop.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class TeleopConveyor extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public TeleopConveyor(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.conveyor);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.conveyor.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (robot.driver.getThrottleButton(Constants.FEED_CONVEYOR))
    {
      robot.conveyor.feed(robot.driver.getSteerButton(Constants.CHARGE_SHOOTER));
    }
    else if (robot.driver.getThrottleButton(Constants.REVERSE_CONVEYOR))
    {
      robot.conveyor.reverse();
    }
    else if (robot.driver.getSteerButton(Constants.SPIN_INTAKE))
    {
      robot.conveyor.standby(robot.driver.getSteerButton(Constants.CHARGE_SHOOTER));
    }
    else
    {
      robot.conveyor.stop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.conveyor.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

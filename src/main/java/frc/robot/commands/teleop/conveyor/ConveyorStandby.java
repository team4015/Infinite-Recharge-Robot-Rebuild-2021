package frc.robot.commands.teleop.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class ConveyorStandby extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public ConveyorStandby(Robot robot)
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
    robot.conveyor.standby(robot.driver.getSteerButton(Constants.CHARGE_SHOOTER));
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

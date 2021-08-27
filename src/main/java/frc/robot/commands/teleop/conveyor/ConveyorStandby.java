/* ==================================================
Authour: Shane Pinto
Description: ConveyorStandby.java is a command that
will tell the conveyor subsystem to wait for a ball
to enter the robot, then move it along the storage
bay to make room for the next ball.
================================================== */

package frc.robot.commands.teleop.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
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

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    robot.conveyor.standby(robot.driver.getChargeShooterButton());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

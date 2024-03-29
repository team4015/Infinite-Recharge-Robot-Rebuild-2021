/* ==================================================
Authour: Shane Pinto
Description: ConveyorFeed.java is a command that
will tell the conveyor subsystem to spin the conveyor
to intake the balls.
================================================== */

package frc.robot.commands.teleop.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ConveyorFeed extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public ConveyorFeed(Robot robot)
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
    robot.conveyor.feed(robot.driver.getChargeShooterButton());
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
    return true;
  }
}

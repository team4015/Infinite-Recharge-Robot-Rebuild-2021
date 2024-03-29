/* ==================================================
Authour: Shane Pinto
Description: ConveyorReverse.java is a command that
will tell the conveyor subsystem to reverse the 
conveyor to outake the balls.
================================================== */

package frc.robot.commands.teleop.conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ConveyorReverse extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public ConveyorReverse(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.conveyor, robot.intake);
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
      robot.conveyor.reverse();
      robot.intake.reverse(); // Intake must also be reverse so that balls can exit the robot
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

/* ==================================================
Authour: Shane Pinto
Description: ShooterSpin.java is a command that tells
the shooter subsystem to spin the shooter.
================================================== */

package frc.robot.commands.teleop.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ShooterSpin extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  
  public ShooterSpin(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.shooter);
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
    robot.shooter.spin();
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

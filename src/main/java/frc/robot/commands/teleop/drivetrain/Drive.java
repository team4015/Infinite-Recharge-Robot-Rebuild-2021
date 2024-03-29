/* ==================================================
Authour: Shane Pinto
Description: Drive.java is a command that will send
the joystick readings to the drivetrain subsystem
in order to write a speed to the motors so that the
robot can move according to joystick input.
================================================== */

package frc.robot.commands.teleop.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Drive extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public Drive(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.drivetrain.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    robot.drivetrain.move(robot.driver.getThrottle(), robot.driver.getSteer());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.drivetrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

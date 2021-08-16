package frc.robot.commands.teleop.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class TeleopShooter extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public TeleopShooter(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.shooter.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (robot.driver.getSteerButton(Constants.CHARGE_SHOOTER))
    {
      robot.shooter.spin();
    }
    else
    {
      robot.shooter.stop();
    } 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.shooter.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return true;
  }
}

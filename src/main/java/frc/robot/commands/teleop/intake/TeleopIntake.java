package frc.robot.commands.teleop.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class TeleopIntake extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public TeleopIntake(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.intake.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (robot.driver.getSteerButton(Constants.SPIN_INTAKE))
    {
      robot.intake.spin();
    }
    else if (robot.driver.getSteerButton(Constants.REVERSE_INTAKE) || robot.driver.getThrottleButton(Constants.REVERSE_CONVEYOR))
    {
      robot.intake.reverse();
    }
    else
    {
      robot.intake.stop();
    }

    if (robot.driver.getSteerButton(Constants.DEPLOY_INTAKE))
    {
      robot.intake.deploy();
    }
    else if (robot.driver.getSteerButton(Constants.RETRACT_INTAKE))
    {
      robot.intake.retract();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

package frc.robot.commands.teleop;

import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.controls.Driver;
import frc.robot.Robot;

public class TeleopIntake extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Intake intake;

  public TeleopIntake(Robot robot)
  {
    intake = robot.intake;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    intake.stop();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (Driver.getSteerButton(Constants.SPIN_INTAKE))
    {
      intake.spin();
    }
    else if (Driver.getSteerButton(Constants.REVERSE_INTAKE) || Driver.getThrottleButton(Constants.REVERSE_CONVEYOR))
    {
      intake.reverse();
    }
    else
    {
      intake.stop();
    }

    if (Driver.getSteerButton(Constants.DEPLOY_INTAKE))
    {
      intake.deploy();
    }
    else if (Driver.getSteerButton(Constants.RETRACT_INTAKE))
    {
      intake.retract();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

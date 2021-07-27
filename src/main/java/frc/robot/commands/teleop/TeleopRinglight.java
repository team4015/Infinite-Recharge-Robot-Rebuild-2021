package frc.robot.commands.teleop;

import frc.robot.subsystems.Ringlight;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.controls.Driver;
import frc.robot.Robot;

public class TeleopRinglight extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Ringlight ringlight;
  private boolean on = false;

  public TeleopRinglight(Robot robot)
  {
    ringlight = robot.ringlight;
    addRequirements(ringlight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    ringlight.off();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    if (Driver.getSteerButtonPressed(Constants.TOGGLE_RINGLIGHT))
    {
      on = !on;
    }

    if (on)
    {
      ringlight.on();
    }
    else
    {
      ringlight.off();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    ringlight.off();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}

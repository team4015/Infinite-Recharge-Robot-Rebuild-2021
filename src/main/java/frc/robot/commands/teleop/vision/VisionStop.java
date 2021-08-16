package frc.robot.commands.teleop.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class VisionStop extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  private boolean visionEnabled;

  public VisionStop(Robot robot)
  {
    this.robot = robot;
    visionEnabled = false;
    addRequirements(robot.vision);
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
    robot.vision.stop(false);
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

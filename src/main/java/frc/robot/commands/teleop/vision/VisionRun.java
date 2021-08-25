package frc.robot.commands.teleop.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class VisionRun extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;

  public VisionRun(Robot robot)
  {
    this.robot = robot;
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
    robot.vision.toggleVision();
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

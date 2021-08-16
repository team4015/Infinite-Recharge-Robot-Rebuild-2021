package frc.robot.commands.teleop.vision;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class VisionStart extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private Robot robot;
  private boolean visionEnabled;

  public VisionStart(Robot robot)
  {
    this.robot = robot;
    addRequirements(robot.vision);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.vision.stop(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    robot.vision.start();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.vision.stop(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return true;
  }
}

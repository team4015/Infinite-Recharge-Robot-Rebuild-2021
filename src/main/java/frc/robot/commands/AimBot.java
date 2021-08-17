package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;

public class AimBot extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private Robot robot;

    public AimBot(Robot robot)
    {
        this.robot = robot;
        addRequirements(robot.drivetrain, robot.vision);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        robot.vision.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute()
    {
        if (robot.vision.turnLeft())
        {
            robot.drivetrain.move(0, -0.125);
        }
        else if (robot.vision.turnRight())
        {
            robot.drivetrain.move(0, 0.125);
        }
        else if (!robot.vision.turnLeft() && !robot.vision.turnRight())
        {
            robot.drivetrain.stop();
        }
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
        if (robot.vision.getHorizontallyAligned() || !robot.driver.getSteerButton(Constants.RUN_AIMBOT))
        {
            robot.drivetrain.stop();
            robot.vision.stop();
            return true;
        }

        return false;
    }
}

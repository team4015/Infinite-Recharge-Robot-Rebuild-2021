/* ==================================================
Authour: Shane Pinto
Description: AimBot.java is an autonomous command
that uses the vision processing abilities of the 
vision subsystem to assist with targeting. This 
command will take over robot control, align the robot
with a target, and select the necessary shooter 
speed required to hit the target at the robot's
current distance from the target.
================================================== */

package frc.robot.commands.auto;

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
        addRequirements(robot.drivetrain, robot.vision, robot.shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()
    {
        robot.vision.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    /* =====================================
    execute() will use the vision, shooter,
    and drivetrain subsystems to align the
    robot with the target, and then set
    the needed shooter speed to hit the
    target at the current distance.
    ===================================== */
    @Override
    public void execute()
    {
        robot.shooter.setSpeed(robot.vision.getRequiredShooterSpeed());
        
        if (robot.vision.turnLeft())
        {
            robot.drivetrain.move(0, Constants.AUTO_LEFT_TURN_SPEED);
        }
        else if (robot.vision.turnRight())
        {
            robot.drivetrain.move(0, Constants.AUTO_RIGHT_TURN_SPEED);
        }
        else
        {
            robot.drivetrain.stop();
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted)
    {
        robot.drivetrain.stop();
        robot.vision.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished()
    {
        return false; // AimBot will continue running until it's trigger button is released
    }
}

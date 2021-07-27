package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.teleop.*;
import frc.robot.Robot;

public class Teleop extends ParallelCommandGroup
{
    public Teleop(Robot robot)
    {
        addCommands
        (
            new TeleopDrivetrain(robot), 
            new TeleopIntake(robot),
            new TeleopConveyor(robot),
            new TeleopShooter(robot),
            new TeleopRinglight(robot)
        );
    }
}

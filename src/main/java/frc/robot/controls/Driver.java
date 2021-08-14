package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.commands.AimBot;
import frc.robot.subsystems.Drivetrain;

import java.lang.Math;

public class Driver
{
    public static Joystick throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
    public static Joystick steer = new Joystick(Constants.STEER_JOYSTICK);
    private static Drivetrain drivetrain;
    private static boolean on = false;

    public Driver(Drivetrain drivetrain)
    {
        this.drivetrain = drivetrain;
    }

    public static double getThrottle()
    {
        double throttleValue = 0;
        
        throttleValue = -throttle.getY() * Constants.MAX_THROTTLE_SPEED;

        if (Math.abs(throttleValue) < Constants.DEADZONE)
        {
            return 0;
        }

        return throttleValue;
    }

    public static double getSteer()
    {
        double steerValue = 0;

        steerValue = steer.getX() * Constants.MAX_STEER_SPEED;

        if (Math.abs(steerValue) < Constants.DEADZONE)
        {
            return 0;
        }

        return steerValue;
    }

    public static boolean getThrottleButton(int button)
    {
        return throttle.getRawButton(button);
    }

    public static boolean getSteerButton(int button)
    {
        return steer.getRawButton(button);
    }

    public static boolean getSteerButtonPressed(int button)
    {
        return steer.getRawButtonPressed(button);
    }

    public static void toggleAimBot()
    {
        if (steer.getRawButtonPressed(Constants.TOGGLE_VISION))
        {
            on = !on;
        }

        if (on)
        {
            
            CommandScheduler.getInstance().schedule(new AimBot(drivetrain));
        }
    }
}

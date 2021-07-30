package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;
import java.lang.Math;

public class Driver
{
    public static Joystick throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
    public static Joystick steer = new Joystick(Constants.STEER_JOYSTICK);

    private static double throttleVal = 0;
    private static double steerVal = 0;

    public static double getThrottle()
    {
        throttleVal = -throttle.getY();

        if (Math.abs(throttleVal) < Constants.DEADZONE)
        {
            throttleVal = 0;
        }

        return throttleVal;
    }

    public static double getSteer()
    {
        steerVal = steer.getX();

        if (Math.abs(steerVal) < Constants.DEADZONE)
        {
            steerVal = 0;
        }

        return steerVal;
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
}

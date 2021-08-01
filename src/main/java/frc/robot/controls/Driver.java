package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;
import java.lang.Math;

public class Driver
{
    public static Joystick throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
    public static Joystick steer = new Joystick(Constants.STEER_JOYSTICK);

    public static double getThrottle()
    {
        double throttleValue = 0;
        
        throttleValue = -throttle.getY();

        if (Math.abs(throttleValue) < Constants.DEADZONE)
        {
            return 0;
        }

        return throttleValue;
    }

    public static double getSteer()
    {
        double steerValue = 0;

        steerValue = steer.getX();

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
}

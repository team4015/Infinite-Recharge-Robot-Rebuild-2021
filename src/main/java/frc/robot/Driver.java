package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Driver
{
    private static Joystick throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
    private static Joystick steer = new Joystick(Constants.STEER_JOYSTICK);

    public static double getThrottle()
    {
        return -throttle.getY();
    }

    public static double getSteer()
    {
        return steer.getX();
    }

    public static boolean getThrottleButton(int button)
    {
        return throttle.getRawButton(button);
    }

    public static boolean getSteerButton(int button)
    {
        return steer.getRawButton(button);
    }
}

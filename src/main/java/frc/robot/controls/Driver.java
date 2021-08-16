package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.commands.AimBot;
import frc.robot.Robot;

import java.lang.Math;

public class Driver
{
    private Joystick throttle;
    private Joystick steer;
    private Robot robot;
    private Command aimBot;

    public Driver(Robot robot)
    {
        this.robot = robot;
        throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
        steer = new Joystick(Constants.STEER_JOYSTICK);
    }

    public double getThrottle()
    {
        double throttleValue = 0;
        
        throttleValue = -throttle.getY() * Constants.MAX_THROTTLE_SPEED;

        if (Math.abs(throttleValue) < Constants.DEADZONE)
        {
            return 0;
        }

        return throttleValue;
    }

    public double getSteer()
    {
        double steerValue = 0;

        steerValue = steer.getX() * Constants.MAX_STEER_SPEED;

        if (Math.abs(steerValue) < Constants.DEADZONE)
        {
            return 0;
        }

        return steerValue;
    }

    public boolean getThrottleButton(int button)
    {
        return throttle.getRawButton(button);
    }

    public boolean getSteerButton(int button)
    {
        return steer.getRawButton(button);
    }

    public boolean getSteerButtonPressed(int button)
    {
        return steer.getRawButtonPressed(button);
    }

    public void toggleAimBot(Command teleop)
    {   
        if (getSteerButton(7) && robot.vision.getVisionEnabled())
        {
            aimBot = new AimBot(robot, teleop);

            if (CommandScheduler.getInstance().isScheduled(aimBot))
            {
                return;
            }

            CommandScheduler.getInstance().schedule(aimBot);
        }
        else
        {
            return;
        }
    }
}

package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants;
import frc.robot.commands.AimBot;
import frc.robot.Robot;
import frc.robot.commands.teleop.intake.*;
import frc.robot.commands.teleop.conveyor.*;
import frc.robot.commands.teleop.shooter.*;
import frc.robot.commands.teleop.vision.*;

import java.lang.Math;

public class Driver
{
    private Joystick throttle;
    private Joystick steer;
    private Robot robot;
    private Command aimBot;

    private JoystickButton intakeSpin;
    private JoystickButton intakeReverse;
    private JoystickButton intakeDeploy;
    private JoystickButton intakeRetract;
    private JoystickButton conveyorFeed;
    private JoystickButton conveyorReverse;
    private JoystickButton shooterSpin;
    private JoystickButton visionStart;
    private JoystickButton visionStop;

    public Driver(Robot robot)
    {
        this.robot = robot;
        throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
        steer = new Joystick(Constants.STEER_JOYSTICK);

        // COMMAND BINDINGS
        intakeSpin = new JoystickButton(steer, Constants.SPIN_INTAKE);
        intakeReverse = new JoystickButton(steer, Constants.REVERSE_INTAKE);
        intakeDeploy = new JoystickButton(steer, Constants.DEPLOY_INTAKE);
        intakeRetract = new JoystickButton(steer, Constants.RETRACT_INTAKE);
        conveyorFeed = new JoystickButton(throttle, Constants.FEED_CONVEYOR);
        conveyorReverse = new JoystickButton(throttle, Constants.REVERSE_CONVEYOR);
        shooterSpin = new JoystickButton(steer, Constants.CHARGE_SHOOTER);
        visionStart = new JoystickButton(throttle, Constants.TOGGLE_VISION);
        visionStop = new JoystickButton(throttle, Constants.TOGGLE_VISION);

        intakeSpin.whileHeld(new IntakeSpin(robot));
        intakeReverse.whileHeld(new IntakeReverse(robot));
        intakeDeploy.whenPressed(new IntakeDeploy(robot));
        intakeRetract.whenPressed(new IntakeRetract(robot));
        conveyorFeed.whileHeld(new ConveyorFeed(robot));
        conveyorReverse.whileHeld(new ConveyorReverse(robot));
        shooterSpin.whileHeld(new ShooterSpin(robot));
        visionStart.whenPressed(new VisionStart(robot));
        visionStop.whenReleased(new VisionStop(robot));
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

    public void toggleAimBot()
    {   
        // if (getSteerButton(7) && robot.vision.getVisionEnabled())
        // {
        //     aimBot = new AimBot(robot);

        //     if (CommandScheduler.getInstance().isScheduled(aimBot))
        //     {
        //         return;
        //     }

        //     CommandScheduler.getInstance().schedule(aimBot);
        // }
        // else
        {
            return;
        }
    }
}

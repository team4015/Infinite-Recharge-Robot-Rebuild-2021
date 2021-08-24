package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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

    private JoystickButton intakeSpin;
    private JoystickButton intakeReverse;
    private JoystickButton intakeDeploy;
    private JoystickButton intakeRetract;
    private JoystickButton conveyorFeed;
    private JoystickButton conveyorReverse;
    private JoystickButton shooterSpin;
    private JoystickButton visionRun;
    private JoystickButton aimBot;

    public Driver(Robot robot)
    {
        throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
        steer = new Joystick(Constants.STEER_JOYSTICK);

        // COMMAND BINDINGS
        intakeSpin = new JoystickButton(steer, Constants.SPIN_INTAKE);
        intakeReverse = new JoystickButton(steer, Constants.REVERSE_INTAKE);
        intakeDeploy = new JoystickButton(steer, Constants.DEPLOY_INTAKE);
        intakeRetract = new JoystickButton(steer, Constants.RETRACT_INTAKE);
        conveyorFeed = new JoystickButton(throttle, Constants.FEED_CONVEYOR);
        conveyorReverse = new JoystickButton(throttle, Constants.REVERSE_CONVEYOR);
        shooterSpin = new JoystickButton(throttle, Constants.CHARGE_SHOOTER);
        visionRun = new JoystickButton(steer, Constants.TOGGLE_VISION);
        aimBot = new JoystickButton(steer, Constants.RUN_AIMBOT);

        intakeSpin.whileHeld(new IntakeSpin(robot));
        intakeReverse.whileHeld(new IntakeReverse(robot));
        intakeDeploy.whenPressed(new IntakeDeploy(robot));
        intakeRetract.whenPressed(new IntakeRetract(robot));
        conveyorFeed.whileHeld(new ConveyorFeed(robot));
        conveyorReverse.whileHeld(new ConveyorReverse(robot));
        shooterSpin.whileHeld(new ShooterSpin(robot));
        visionRun.whenPressed(new VisionRun(robot));
        aimBot.whenHeld(new AimBot(robot));
    }

    public double getSlider()
    {
        return (-throttle.getThrottle() + 1) / 2;
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
}

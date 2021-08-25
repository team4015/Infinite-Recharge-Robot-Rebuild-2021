package frc.robot.controls;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.commands.auto.AimBot;
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

    // BUTTON BINDINGS
    public static final int START_COMPRESSOR = 7;
    public static final int STOP_COMPRESSOR = 8;
    public static final int DEPLOY_INTAKE = 6;
    public static final int RETRACT_INTAKE = 4;
    public static final int SPIN_INTAKE = 1;
    public static final int REVERSE_INTAKE = 3;
    public static final int FEED_CONVEYOR = 1;
    public static final int REVERSE_CONVEYOR = 3;
    public static final int CHARGE_SHOOTER = 5;
    public static final int TOGGLE_VISION = 5;
    public static final int RUN_AIMBOT = 2;

    public Driver(Robot robot)
    {
        throttle = new Joystick(Constants.THROTTLE_JOYSTICK);
        steer = new Joystick(Constants.STEER_JOYSTICK);

        // COMMAND BINDINGS
        intakeSpin = new JoystickButton(steer, SPIN_INTAKE);
        intakeReverse = new JoystickButton(steer, REVERSE_INTAKE);
        intakeDeploy = new JoystickButton(steer, DEPLOY_INTAKE);
        intakeRetract = new JoystickButton(steer, RETRACT_INTAKE);
        conveyorFeed = new JoystickButton(throttle, FEED_CONVEYOR);
        conveyorReverse = new JoystickButton(throttle, REVERSE_CONVEYOR);
        shooterSpin = new JoystickButton(throttle, CHARGE_SHOOTER);
        visionRun = new JoystickButton(steer, TOGGLE_VISION);
        aimBot = new JoystickButton(steer, RUN_AIMBOT);

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

    public double getThrottleSlider()
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

    private boolean getThrottleButton(int button)
    {
        return throttle.getRawButton(button);
    }

    private boolean getSteerButton(int button)
    {
        return steer.getRawButton(button);
    }

    public boolean getChargeShooterButton()
    {
        return getThrottleButton(CHARGE_SHOOTER);
    }
}

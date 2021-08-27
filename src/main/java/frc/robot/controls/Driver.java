/* ==================================================
Authour: Shane Pinto
Description: Driver.java contains most of the code
that handles button binding, joystick reading, and
command binding.
================================================== */

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
    // JOYSTICK CONTROLLERS
    private Joystick throttle;
    private Joystick steer;

    // BUTTONS
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

        // JOYSTICK BINDINGS
        // These instantiations will bind the JoystickButtons to a physical button on the joystick
        intakeSpin = new JoystickButton(steer, SPIN_INTAKE);
        intakeReverse = new JoystickButton(steer, REVERSE_INTAKE);
        intakeDeploy = new JoystickButton(steer, DEPLOY_INTAKE);
        intakeRetract = new JoystickButton(steer, RETRACT_INTAKE);
        conveyorFeed = new JoystickButton(throttle, FEED_CONVEYOR);
        conveyorReverse = new JoystickButton(throttle, REVERSE_CONVEYOR);
        shooterSpin = new JoystickButton(throttle, CHARGE_SHOOTER);
        visionRun = new JoystickButton(steer, TOGGLE_VISION);
        aimBot = new JoystickButton(steer, RUN_AIMBOT);

        // COMMAND BINDINGS
        // These function calls will tell the buttons which commands to call when they are in a certain state
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

    /* =====================================
    getThrottleSlider() returns the current
    value of the slider on the throttle
    joystick. It will also set it to a value
    between 0 and 1.
    ===================================== */
    public double getThrottleSlider()
    {
        return (-throttle.getThrottle() + 1) / 2;
    }

    /* =====================================
    getThrottle() will return the current
    value of thhe Y-Axis of the throttle
    joystick. It will also handle adjusting
    the reading according to the deadzone,
    and according to the max throttle speed.
    ===================================== */
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

    /* =====================================
    getSteer() will return the current
    value of thhe X-Axis of the steer
    joystick. It will also handle adjusting
    the reading according to the deadzone,
    and according to the max steer speed.
    ===================================== */
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

    /* =====================================
    getThrottleButton() will return the 
    value of the button passed to the
    function on the throttle joystick.
    ===================================== */
    private boolean getThrottleButton(int button)
    {
        return throttle.getRawButton(button);
    }

    /* =====================================
    getSteerButton() will return the 
    value of the button passed to the
    function on the steer joystick.
    ===================================== */
    private boolean getSteerButton(int button)
    {
        return steer.getRawButton(button);
    }

    /* =====================================
    getChargeShooterButton() will return the
    current value of the shooter charging 
    button on the throttle joystick.
    ===================================== */
    public boolean getChargeShooterButton()
    {
        return getThrottleButton(CHARGE_SHOOTER);
    }
}

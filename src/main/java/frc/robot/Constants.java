package frc.robot;

import org.opencv.core.Scalar;

public final class Constants
{
    // HARDWARE PORTS
    public static final int LEFT_MOTOR_FRONT = 3;
    public static final int LEFT_MOTOR_REAR = 5;
    public static final int RIGHT_MOTOR_FRONT = 4;
    public static final int RIGHT_MOTOR_REAR = 8;

    public static final int INTAKE_CAN_SPARK = 8; 
    public static final int CONVEYOR_SPARK = 9;
    public static final int SHOOTER_VICTOR = 2;

    // CONTROLLER PORTS
    public static final int THROTTLE_JOYSTICK = 0;
    public static final int STEER_JOYSTICK = 1;

    // PISTONS
    public static final int SHOOTER_PISTON_DEPLOY = 2;
    public static final int SHOOTER_PISTON_RETRACT = 3;
    public static final int INTAKE_PISTON_DEPLOY = 7;
    public static final int INTAKE_PISTON_RETRACT = 6;
    public static final int CLIMBER_PISON_DEPLOY = 1;
    public static final int CLIMBER_PISON_RETRACT = 0;

    // LIMIT SWITCHES
    public static final int INTAKE_SWITCH = 0;
    public static final int CONVEYOR_SWITCH = 1;
    public static final int SHOOTER_SWITCH = 2;

    // RING LIGHT
    public static final int RING_LIGHT = 5;

    // SETTINGS
    public static final double DEADZONE = 0.15;
    public static final double DEFAULT_SHOOTER_SPEED = 0.71;
    public static final double MAX_THROTTLE_SPEED = 0.30;
    public static final double MAX_STEER_SPEED = 0.2;
    public static final int CONVEYOR_TIMEOUT = 2;
    public static final double AUTO_LEFT_TURN_SPEED = -0.11;
    public static final double AUTO_RIGHT_TURN_SPEED = 0.15;
    public static final double CONVEYOR_SPEED = 0.75;
    public static final double CONVEYOR_REVERSE_SPEED = -0.5;

    // COLOURS
    public static final Scalar GREEN = new Scalar(0, 255, 0);
    public static final Scalar RED = new Scalar(0, 0, 255);
    public static final Scalar WHITE = new Scalar(255, 255, 255);
    public static final Scalar VISION_THRESHOLD = new Scalar(40, 0, 175);

    // TARGETING CONSTANTS
    public static final double AIM_BOT_ACCURACY = 5;
    public static final double PIXEL_TO_METRES_RATIO = 0.01615;
    public static final double TARGET_CENTER_DISTANCE = 1.52;
}
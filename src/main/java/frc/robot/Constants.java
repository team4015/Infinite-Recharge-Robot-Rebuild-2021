package frc.robot;

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

    // JOYSTICK KEY MAPPING
    public static final int THROTTLE_JOYSTICK = 0;
    public static final int STEER_JOYSTICK = 1;

    public static final int START_COMPRESSOR = 7;
    public static final int STOP_COMPRESSOR = 8;
    public static final int DEPLOY_INTAKE = 6;
    public static final int RETRACT_INTAKE = 4;
    public static final int SPIN_INTAKE = 1;
    public static final int REVERSE_INTAKE = 3;
    public static final int FEED_CONVEYOR = 1;
    public static final int REVERSE_CONVEYOR = 3;
    public static final int CHARGE_SHOOTER = 2;

    public static final int TOGGLE_RINGLIGHT = 5;

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
    public static final double SHOOTER_SPEED = 0.55;
    public static final double MAX_THROTTLE_SPEED = 0.75;
    public static final double MAX_STEER_SPEED = 0.75;
}
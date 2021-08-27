/* ==================================================
Authour: Shane Pinto
Description: Constants.java contains most of the 
constant values used throughout the robot. This 
includes settings, hardware ports, and other values.
================================================== */

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

    // CONTROLLER COMPUTER PORTS
    public static final int THROTTLE_JOYSTICK = 0;
    public static final int STEER_JOYSTICK = 1;

    // PISTON SOLENOID PCM PORTS
    public static final int SHOOTER_PISTON_DEPLOY = 2;
    public static final int SHOOTER_PISTON_RETRACT = 3;
    public static final int INTAKE_PISTON_DEPLOY = 7;
    public static final int INTAKE_PISTON_RETRACT = 6;
    public static final int CLIMBER_PISON_DEPLOY = 1;
    public static final int CLIMBER_PISON_RETRACT = 0;

    // LIMIT SWITCH PORTS
    public static final int INTAKE_SWITCH = 0;
    public static final int CONVEYOR_SWITCH = 1;
    public static final int SHOOTER_SWITCH = 2;

    // RING LIGHT PORT
    public static final int RING_LIGHT = 5;

    // SETTINGS
    public static final double DEADZONE = 0.15; // Deadzone applied to joysticks to aid in adjusting sensitivity
    public static final double MAX_THROTTLE_SPEED = 0.30;
    public static final double MAX_STEER_SPEED = 0.2;
    public static final int CONVEYOR_TIMEOUT = 2; // Time in seconds before conveyor belt stops spinning on standby
    public static final double AUTO_LEFT_TURN_SPEED = -0.11;
    public static final double AUTO_RIGHT_TURN_SPEED = 0.15;
    public static final double CONVEYOR_SPEED = 0.75;
    public static final double CONVEYOR_REVERSE_SPEED = -0.5;
    public static final double INTAKE_SPEED = -0.25;
    public static final double INTAKE_REVERSE_SPEED = 0.25;

    // COLOURS
    public static final Scalar GREEN = new Scalar(0, 255, 0);
    public static final Scalar RED = new Scalar(0, 0, 255);
    public static final Scalar WHITE = new Scalar(255, 255, 255);
    public static final Scalar VISION_THRESHOLD = new Scalar(40, 0, 175);

    // TARGETING CONSTANTS
    public static final double AIM_BOT_ACCURACY = 5; // How many pixels off from the center of the target
    public static final double PIXEL_TO_METRES_RATIO = 0.01615; // How many meters one pixel represents
    public static final double TARGET_CENTER_DISTANCE = 1.52; // Distance from target when reticle is centered on target center
}

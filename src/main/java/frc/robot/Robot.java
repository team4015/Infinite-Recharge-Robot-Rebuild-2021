/* ==================================================
Authour: Shane Pinto
Description: Robot.java is a class that contains all
the necessary things to make a robot instance. This 
class will make instances of each subsystem, 
initialize a startup function for each subsystem, 
and then set a default command for each subsystem to 
use. It will also instantiate Driver.java to allow 
driver input via joysticks to control the robot.
================================================== */

package frc.robot;

import frc.robot.subsystems.*;
import frc.robot.controls.Driver;
import edu.wpi.first.wpilibj.Compressor;
import frc.robot.commands.teleop.drivetrain.*;
import frc.robot.commands.teleop.intake.*;
import frc.robot.commands.teleop.conveyor.*;
import frc.robot.commands.teleop.shooter.*;

public class Robot
{
  // SUBSYSTEMS
  public Drivetrain drivetrain;
  public Intake intake;
  public Compressor compressor;
  public Conveyor conveyor;
  public Shooter shooter;
  public Vision vision;

  // CONTROLS
  public Driver driver;

  public Robot()
  {
    // SUBSYSTEM INSTANTIATION
    drivetrain = new Drivetrain();
    intake = new Intake();
    compressor = new Compressor();
    conveyor = new Conveyor();
    shooter = new Shooter();
    vision = new Vision();

    driver = new Driver(this);

    setDefaultCommands();
    init();
  }

  /* =====================================
  init() will tell each subsystem to 
  perform a starting function. Often this
  is to just stop all motors and set the
  robot to a starting position.
  ===================================== */
  private void init()
  {
    drivetrain.stop();
    intake.stop();
    intake.retract();
    conveyor.stop();
    shooter.stop();
    compressor.setClosedLoopControl(false);
    vision.stop();
  }

  /* =====================================
  setDefaultCommands() will tell each 
  subsystem which command should be run
  when no other command is being scheduled.
  ===================================== */
  private void setDefaultCommands()
  {
    drivetrain.setDefaultCommand(new Drive(this));
    intake.setDefaultCommand(new IntakeStop(this));
    conveyor.setDefaultCommand(new ConveyorStandby(this));
    shooter.setDefaultCommand(new ShooterStop(this));
  }
}

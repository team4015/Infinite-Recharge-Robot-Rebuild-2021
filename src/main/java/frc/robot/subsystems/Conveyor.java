/* ==================================================
Authour: Shane Pinto
Description: Conveyor.java contains the functions
and objects needed to control the conveyor subsystem.
It also contains code to help prevent jams inside the
ball storage bay.
================================================== */

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class Conveyor extends SubsystemBase
{
  private PWMSparkMax conveyorMotor; // The conveyor motor is wired using PWM
  private DigitalInput intakeSwitch;
  private DigitalInput conveyorSwitch;
  private DigitalInput shooterSwitch;
  private Timer timer;

  public Conveyor()
  {
    timer = new Timer(); // This timer will be used to timeout the conveyor to prevent jams
    conveyorMotor = new PWMSparkMax(Constants.CONVEYOR_SPARK); // The conveyor belt is run by one brushless motor controlled by one SparkMax motor controller
    intakeSwitch = new DigitalInput(Constants.INTAKE_SWITCH);
    conveyorSwitch = new DigitalInput(Constants.CONVEYOR_SWITCH);
    shooterSwitch = new DigitalInput(Constants.SHOOTER_SWITCH);
  }
  
  public boolean getIntakeSwitch()
  {
    return intakeSwitch.get();
  }

  public boolean getConveyorSwitch()
  {
    return conveyorSwitch.get();
  }

  public boolean getShooterSwitch()
  {
    return shooterSwitch.get();
  }

  /* =====================================
  standby() allows the conveyor belt to
  be on a standby mode. The belt will not
  spin or move unless a new ball is given
  to it. This function also has a built in
  check that will prevent the loading of
  new balls if the ball bay is full. 
  ===================================== */
  public void standby(boolean shooterRunning)
  { 
    if (!shooterSwitch.get() && intakeSwitch.get()) // This check makes sure that the shooter switch is not pressed, indicating that the ball bay has more room
    {
      feed(shooterRunning);

      timer.reset();
      timer.start();
      while (!conveyorSwitch.get() && timer.get() < Constants.CONVEYOR_TIMEOUT); // This check will stop the conveyor from running after a specificed amount of time to prevent the code from getting stuck in the while loop

      timer.reset();
      timer.start();
      while (conveyorSwitch.get() && timer.get() < Constants.CONVEYOR_TIMEOUT); // This check will stop the conveyor from running after a specificed amount of time to prevent the code from getting stuck in the while loop
      
      timer.stop();
    }

    stop();
  }

  /* =====================================
  feed() spins the conveyor forward to 
  intake balls into the bay.
  ===================================== */
  public void feed(boolean shooterRunning)
  {
    if (!shooterSwitch.get() || shooterRunning)
    {
      conveyorMotor.set(Constants.CONVEYOR_SPEED);
    }
    else
    {
      stop();
    }
  }

  /* =====================================
  stop() will stop the conveyor from
  spinning.
  ===================================== */
  public void stop()
  {
    conveyorMotor.set(0);
  }

  /* =====================================
  reverse() will reverse the conveyor's
  spin in order to outake balls.
  ===================================== */
  public void reverse()
  {
    conveyorMotor.set(Constants.CONVEYOR_REVERSE_SPEED);
  }
}

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMSparkMax;

import com.fasterxml.jackson.databind.deser.std.NumberDeserializers.DoubleDeserializer;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class Conveyor extends SubsystemBase
{
  private PWMSparkMax conveyorMotor;
  private DigitalInput intakeSwitch;
  private DigitalInput conveyorSwitch;
  private DigitalInput shooterSwitch;
  private Timer timer;

  public Conveyor()
  {
    timer = new Timer();
    conveyorMotor = new PWMSparkMax(Constants.CONVEYOR_SPARK);
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

  public void standby(boolean shooterRunning)
  { 
    if (!shooterSwitch.get() && intakeSwitch.get())
    {
      feed(shooterRunning);

      timer.reset();
      timer.start();
      while (!conveyorSwitch.get() && timer.get() < Constants.CONVEYOR_TIMEOUT);

      timer.reset();
      timer.start();
      while (conveyorSwitch.get() && timer.get() < Constants.CONVEYOR_TIMEOUT);
      
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

  public void stop()
  {
    conveyorMotor.set(0);
  }

  public void reverse()
  {
    conveyorMotor.set(Constants.CONVEYOR_REVERSE_SPEED);
  }
}

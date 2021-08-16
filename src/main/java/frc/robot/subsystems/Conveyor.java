package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMSparkMax;
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

  public void feed(boolean shooterRunning)
  {
    if (!shooterSwitch.get() || shooterRunning)
    {
      conveyorMotor.set(0.75);
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
    conveyorMotor.set(-0.5);
  }

  @Override
  public void periodic()
  {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic()
  {
    // This method will be called once per scheduler run during simulation
  }
}

package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMSparkMax;
import edu.wpi.first.wpilibj.DigitalInput;

public class Conveyor extends SubsystemBase
{
  private PWMSparkMax conveyorMotor;
  private DigitalInput intakeSwitch;
  private DigitalInput conveyorSwitch;

  public Conveyor()
  {
    conveyorMotor = new PWMSparkMax(Constants.CONVEYOR_SPARK);
    intakeSwitch = new DigitalInput(Constants.INTAKE_SWITCH);
    conveyorSwitch = new DigitalInput(Constants.CONVEYOR_SWITCH);
  }

  public void standby()
  {
    if (intakeSwitch.get())
    {
      feed();
      while (!conveyorSwitch.get());
      while (conveyorSwitch.get());
    }

    stop();
  }

  public void feed()
  {
    conveyorMotor.set(0.75);
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
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

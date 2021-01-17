package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PWMSparkMax;

public class Conveyor extends SubsystemBase
{
  private PWMSparkMax conveyorMotor;

  public Conveyor()
  {
    conveyorMotor = new PWMSparkMax(Constants.CONVEYOR_SPARK);
  }

  public void feed()
  {
    conveyorMotor.set(0.5);
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

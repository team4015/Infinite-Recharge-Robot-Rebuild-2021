package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake extends SubsystemBase
{
  private CANSparkMax intakeMotor;
  private DoubleSolenoid intakePiston;

  public Intake()
  {
    intakeMotor = new CANSparkMax(Constants.INTAKE_CAN_SPARK, MotorType.kBrushless); // Motor is Brushless
    intakePiston = new DoubleSolenoid(Constants.INTAKE_PISTON_DEPLOY, Constants.INTAKE_PISTON_RETRACT);
  }

  public void deploy()
  {
    intakePiston.set(Value.kForward);
  }

  public void retract()
  {
    intakePiston.set(Value.kReverse);
  }

  public void spin()
  {
    intakeMotor.set(0.25);
  }

  public void stop()
  {
    intakeMotor.set(0);
  }

  public void reverse()
  {
    intakeMotor.set(-0.25);
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
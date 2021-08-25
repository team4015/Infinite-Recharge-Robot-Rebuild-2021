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
  private boolean deployed;

  public Intake()
  {
    intakeMotor = new CANSparkMax(Constants.INTAKE_CAN_SPARK, MotorType.kBrushless);   // Motor is brushless
    intakePiston = new DoubleSolenoid(Constants.INTAKE_PISTON_DEPLOY, Constants.INTAKE_PISTON_RETRACT);
    deployed = false;
  }

  public void deploy()
  {
    intakePiston.set(Value.kForward);
    deployed = true;
  }

  public void retract()
  {
    intakePiston.set(Value.kReverse);
    deployed = false;
  }

  public void spin()
  {
    if (deployed)
    {
      intakeMotor.set(-0.25);
    }
  }

  public void stop()
  {
    intakeMotor.set(0);
  }

  public void reverse()
  {
    if (deployed)
    {
      intakeMotor.set(0.25);
    }
  }
}

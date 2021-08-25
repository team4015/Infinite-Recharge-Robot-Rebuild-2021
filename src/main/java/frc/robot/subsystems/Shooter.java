package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Victor;

public class Shooter extends SubsystemBase
{
  private Victor motor;
  private double speed;

  public Shooter()
  {
    motor = new Victor(Constants.SHOOTER_VICTOR);
    speed = 0.6;
  }

  public void spin()
  {        
    motor.set(-speed);
  }

  public void stop()
  {
    motor.set(0);
  }

  public void setSpeed(double speed)
  {
    this.speed = speed;

    if (speed > 1)
    {
      speed = 1; // Prevents motor controllers from writing a value higher than 1 to the motors
    }

    if (speed < 0)
    {
      speed = 0; // Prevents shooter from spinning backwards
    }
  }
}

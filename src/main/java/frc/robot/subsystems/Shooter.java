/* ==================================================
Authour: Shane Pinto
Description: Shooter.java contains the functions and
objects needed to control the shooter subsystem.
================================================== */

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
    motor = new Victor(Constants.SHOOTER_VICTOR); // The shooter is controlled by 2 motors controlled by the same motor controller
    speed = 0.6; // Default speed
  }

  /* =====================================
  spin() sets the motors to spin and 
  charge up the shooter to the set speed.
  ===================================== */
  public void spin()
  {        
    motor.set(-speed);
  }

  /* =====================================
  stop() will stop the shooter from 
  spinning.
  ===================================== */
  public void stop()
  {
    motor.set(0);
  }

  /* =====================================
  setSpeed() will take a given speed
  value between -1 and 1 and while write
  it to the motors. This also allows the 
  vision subsystem to tell the shooter
  what speed to spin at.
  ===================================== */
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

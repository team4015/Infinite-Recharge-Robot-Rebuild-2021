package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Constants;

public class Ringlight extends SubsystemBase
{
    public Solenoid ringlight;

    public Ringlight()
    {
        ringlight = new Solenoid(Constants.RING_LIGHT);
    }

    public void on()
    {
        ringlight.set(true);
    }

    public void off()
    {
        ringlight.set(false);
    }
}

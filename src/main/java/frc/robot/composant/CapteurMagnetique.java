package frc.robot.composant;

import edu.wpi.first.wpilibj.DigitalInput;

public class CapteurMagnetique extends DigitalInput{


    public CapteurMagnetique(int port)
    {
        super(port);
    }
    
    public boolean estActive()
    {
        return !this.get();
    }
}

package frc.robot.composant;

import edu.wpi.first.wpilibj.DigitalInput;

public class CapteurMagnetique extends DigitalInput{


    public CapteurMagnetique(int port)
    {
        super(port);
    }
    
    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean estActive()
    {
        return this.get();
    }
}

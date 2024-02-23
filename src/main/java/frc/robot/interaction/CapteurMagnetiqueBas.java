package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalInput;

public class CapteurMagnetiqueBas {
    private static DigitalInput input;


    public CapteurMagnetiqueBas()
    {
        // Initalisation du capteur de luminosité
        input = new DigitalInput(1);
    }
    
    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean estActive()
    {
        return input.get();
    }
}

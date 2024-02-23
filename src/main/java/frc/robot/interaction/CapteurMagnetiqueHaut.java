package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalInput;

public class CapteurMagnetiqueHaut {
    private static DigitalInput input;


    public CapteurMagnetiqueHaut()
    {
        // Initalisation du capteur de luminosité
        input = new DigitalInput(2);
    }
    
    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean estActive()
    {
        return input.get();
    }
}

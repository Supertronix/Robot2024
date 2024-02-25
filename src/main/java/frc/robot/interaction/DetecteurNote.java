package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalInput;

public class DetecteurNote {
    private static DigitalInput input;


    public DetecteurNote()
    {
        // Initalisation du capteur de luminosité
        input = new DigitalInput(0);
    }
    
    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean getLuminosite()
    {
        return input.get();
    }

    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean detecteNote()
    {
        return input.get();
    }

}

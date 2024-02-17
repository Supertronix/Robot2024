package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalInput;

public class CapteurLuminosite {
    private static DigitalInput input;
    public CapteurLuminosite()
    {
        // Initalisation du capteur de luminosité
        input = new DigitalInput(0);
    }
    
    /** 
     * @return boolean
     */
    public boolean getLuminosite()
    {
        return input.get();
    }
}

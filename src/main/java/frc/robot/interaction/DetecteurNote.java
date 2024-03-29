package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Materiel;

public class DetecteurNote implements Materiel.DetecteurNote {
    private static DigitalInput input;

    public DetecteurNote()
    {
        // Initalisation du capteur de luminosité
        input = new DigitalInput(ID_PORT_DIO);
    }

    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean detecteNote()
    {
        return input.get();
    }

}

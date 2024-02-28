package frc.robot.interaction;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Materiel;

public class DetecteurChaine implements Materiel.DetecteurChaine {
    private static DigitalInput input;
    private static final int ID_PORT_DIO = 3;

    public DetecteurChaine()
    {
        // Initalisation du capteur de luminosité
        input = new DigitalInput(ID_PORT_DIO);
    }

    /** 
     * @return True si la luminosité est faible, false sinon
     */
    public boolean detecteChaine()
    {
        return input.get();
    }

}

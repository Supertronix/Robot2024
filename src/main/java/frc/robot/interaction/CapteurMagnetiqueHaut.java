package frc.robot.interaction;

import frc.robot.Materiel;
import frc.robot.composant.CapteurMagnetique;

public class CapteurMagnetiqueHaut extends CapteurMagnetique {

    public CapteurMagnetiqueHaut()
    {
        super(Materiel.Lanceur.Extension.PORT_CAPTEUR_MAGNETIQUE_RETRACTE);
    }    
}

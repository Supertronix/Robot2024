package frc.robot.interaction;

import frc.robot.Materiel;
import frc.robot.composant.CapteurMagnetique;

public class CapteurMagnetiqueBas extends CapteurMagnetique{
    public CapteurMagnetiqueBas()
    {
        super(Materiel.Lanceur.Extension.PORT_CAPTEUR_MAGNETIQUE_DEPLOYE);
    }
}

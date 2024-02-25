package frc.robot.interaction;

import frc.robot.Materiel;
import frc.robot.composant.CapteurMagnetique;

public class DetecteurConvoyeurHaut implements Materiel.ConvoyeurHaut {

    protected CapteurMagnetique capteurMagnetiqueOuverture;
    protected CapteurMagnetique capteurMagnetiqueRetractage;

    public DetecteurConvoyeurHaut()
    {
        this.capteurMagnetiqueOuverture = new CapteurMagnetique(PORT_CAPTEUR_MAGNETIQUE_OUVERTURE);
        this.capteurMagnetiqueRetractage = new CapteurMagnetique(PORT_CAPTEUR_MAGNETIQUE_RETRACTAGE);
    }

    public boolean estOuvert()
    {
        return this.capteurMagnetiqueOuverture.estActive();
    }
    public boolean estRetracte()
    {
        return this.capteurMagnetiqueRetractage.estActive();
    }
}

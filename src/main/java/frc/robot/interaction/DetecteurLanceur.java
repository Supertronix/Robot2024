package frc.robot.interaction;

import frc.robot.Materiel;
import frc.robot.composant.CapteurMagnetique;

public class DetecteurLanceur implements Materiel.Lanceur.Extension {

    protected CapteurMagnetique capteurMagnetiqueOuverture;
    protected CapteurMagnetique capteurMagnetiqueFermeture;

    public DetecteurLanceur()
    {
        this.capteurMagnetiqueOuverture = new CapteurMagnetique(PORT_CAPTEUR_MAGNETIQUE_OUVERTURE);
        this.capteurMagnetiqueFermeture = new CapteurMagnetique(PORT_CAPTEUR_MAGNETIQUE_FERMETURE);
    }

    public boolean estOuvert()
    {
        return this.capteurMagnetiqueOuverture.estActif();
    }
    public boolean estFerme()
    {
        return this.capteurMagnetiqueFermeture.estActif();
    }
}

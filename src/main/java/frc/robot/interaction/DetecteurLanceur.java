package frc.robot.interaction;

import frc.robot.Materiel;
import frc.robot.composant.CapteurMagnetique;

public class DetecteurLanceur implements Materiel.Lanceur.Extension {

    protected CapteurMagnetique capteurMagnetiqueDeploiement;
    protected CapteurMagnetique capteurMagnetiqueRetraction;

    public DetecteurLanceur()
    {
        this.capteurMagnetiqueDeploiement = new CapteurMagnetique(PORT_CAPTEUR_MAGNETIQUE_OUVERTURE);
        this.capteurMagnetiqueRetraction = new CapteurMagnetique(PORT_CAPTEUR_MAGNETIQUE_FERMETURE);
    }

    public boolean estOuvert()
    {
        return this.capteurMagnetiqueDeploiement.estActif();
    }
    public boolean estFerme()
    {
        return this.capteurMagnetiqueRetraction.estActif();
    }
}

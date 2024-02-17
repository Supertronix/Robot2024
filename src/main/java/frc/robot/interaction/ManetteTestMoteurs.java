package frc.robot.interaction;

import edu.wpi.first.wpilibj.XboxController;

public class ManetteTestMoteurs {
    private static XboxController manette = null;

    public enum BOUTON {
        A, B, X, Y, START, RETOUR, GACHETTE_GAUCHE, GACHETTE_DROITE
    }

    protected ManetteTestMoteurs() // pour design pattern singleton
    {
        // test manette xbox
        manette = new XboxController(0);
    }

    // ******************* GETTERS ******************* //

    public boolean getABouton() {
        return manette.getAButton();
    }

    public boolean getBBouton() {
        return manette.getBButton();
    }

    public boolean getXBouton() {
        return manette.getXButton();
    }

    public boolean getBoutonY() {
        return manette.getYButton();
    }

    public boolean getBoutonStart() {
        return manette.getStartButton();
    }

    public boolean getBoutonRetour() {
        return manette.getBackButton();
    }

    public boolean getGachetteLeft() {
        return manette.getLeftBumper();
    }

    public boolean getGachetteRight() {
        return manette.getRightBumper();
    }

    public double getAxeGachetteGauche() {
        return manette.getLeftTriggerAxis();
    }

    public double getAxeGachetteDroite() {
        return manette.getRightTriggerAxis();
    }

    // ******************* METHODES ******************* //

    /**
     * Gère l'activation on/off d'un moteur à l'aide d'un bouton de la manette
     * @param bouton  Le bouton de la manette
     * @param moteurID L'ID du moteur à contrôler
     */
    public void mapperBoutonMoteur(BOUTON bouton, int moteurID) {


    }

    public void executerActions() {

    }
}

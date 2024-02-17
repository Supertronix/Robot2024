package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

public class Bras implements Materiel.Winch {
    protected boolean actif;

    protected MoteurTalon moteur;

    public Bras() {
        actif = false;
        moteur = new MoteurTalon(ID_WINCH);
    }

     public void descendre() {
        this.activer(true);
    }
     public void monter() {
        this.activer(false);
    }
     public void descendreSelonVitesse(double vitesse) {
        this.activerSelonVitesse(true,vitesse);
    }
     public void monterSelonVitesse(double vitesse) {
        this.activerSelonVitesse(false,vitesse);
    }

     // Démarre les moteurs avec la vitesse par défaut
     public void activer(boolean descendre) {
        moteur.setInverted(descendre);
        moteur.set(TalonSRXControlMode.PercentOutput, VITESSE_WINCH);
        actif = true;
    }

    /** 
     * Démarre les moteurs du lanceur avec la vitesse passée en paramètre
     * @param vitesse La vitesse du lanceur entre 0.00 et 1.00
     */
    public void activerSelonVitesse(boolean descendre, double vitesse) {
        moteur.setInverted(descendre);
        moteur.set(TalonSRXControlMode.PercentOutput, vitesse);
        actif = true;
    }

    // Arrête les moteurs du lanceur
    public void desactiver() {
        moteur.set(TalonSRXControlMode.PercentOutput, 0);
        actif = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.actif;
    }
}

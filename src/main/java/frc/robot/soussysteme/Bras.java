package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Cinematique;
import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

public class Bras implements Materiel.Bras, Cinematique.Bras {
    protected boolean actif;
    protected MoteurTalon moteur;

    // TODO peut-etre enum
    public static boolean ACTION_MONTER = false;
    public static boolean ACTION_DESCENDRE = true;

    public Bras() {
        actif = false;
        moteur = new MoteurTalon(ID_TREUIL);
    }

     public void descendre() {
        this.activer(ACTION_DESCENDRE);
    }
     public void monter() {
        this.activer(ACTION_MONTER);
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
        moteur.set(TalonSRXControlMode.PercentOutput, VITESSE_TREUIL);
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

package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

public class Bras implements Materiel.Winch {
    protected boolean toggleOnOff;

    protected MoteurTalon moteur;

    public Bras() {
        toggleOnOff = false;

        moteur = new MoteurTalon(ID_WINCH);
    }

     // Démarre les moteurs avec la vitesse par défaut
     public void demarrerMoteur() {
        moteur.set(TalonSRXControlMode.PercentOutput, VITESSE_WINCH);
        toggleOnOff = true;
    }

    /** 
     * Démarre les moteurs du lanceur avec la vitesse passée en paramètre
     * @param vitesse La vitesse du lanceur entre 0.00 et 1.00
     */
    public void demarrerMoteur(double vitesse) {
        moteur.set(TalonSRXControlMode.PercentOutput, vitesse);
        toggleOnOff = true;
    }

    // Arrête les moteurs du lanceur
    public void arreterMoteur() {
        moteur.set(TalonSRXControlMode.PercentOutput, 0);
        toggleOnOff = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean moteurOn() {
        return this.toggleOnOff;
    }
}

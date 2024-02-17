package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Intake extends SousSysteme implements Materiel.Intake
{
    protected boolean actif; // toggle

    protected MoteurTalon moteur;

    public Intake() {
        moteur = new MoteurTalon(ID_TALON_INTAKE);
        moteur.setInverted(true);
    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void activer() {
        moteur.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
        actif = true;
    }
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void activerSelonVitesse(double vitesse) {
        moteur.set(TalonSRXControlMode.PercentOutput, vitesse);
        actif = true;
    }

    // Arrêter les moteurs du convoyeur
    public void desactiver() {
        moteur.set(TalonSRXControlMode.PercentOutput, 0);
        actif = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.actif;
    }
}

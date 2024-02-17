package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Intake extends SousSysteme implements Materiel.Intake
{
    protected boolean toggleOnOff;

    protected MoteurTalon moteurTalon;

    public Intake() {
        moteurTalon = new MoteurTalon(ID_TALON_INTAKE);
        moteurTalon.setInverted(true);
    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void demarrerMoteur() {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
        toggleOnOff = true;
    }
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void demarrerMoteur(double vitesse) {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, vitesse);
        toggleOnOff = true;
    }

    // Arrêter les moteurs du convoyeur
    public void arreterMoteur() {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, 0);
        toggleOnOff = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean moteurOn() {
        return this.toggleOnOff;
    }
}

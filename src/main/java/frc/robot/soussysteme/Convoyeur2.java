package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Convoyeur2 extends SousSysteme implements Materiel.Convoyeur2
{
    protected MoteurTalon moteurTalon;

    public Convoyeur2() {
        moteurTalon = new MoteurTalon(ID_TALON_CONVOYEUR_MAITRE);
        moteurTalon.setInverted(true);
    }

    public void setVitesse() {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
    }

    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void setVitesse(double vitesse) {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, vitesse);
    }
}

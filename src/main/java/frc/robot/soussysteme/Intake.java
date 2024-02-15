package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Intake extends SousSysteme implements Materiel.Intake
{
    protected MoteurTalon moteurTalon;

    public Intake() {
        moteurTalon = new MoteurTalon(ID_TALON_INTAKE);
        moteurTalon.setInverted(true);
        moteurTalon.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
    }

    public void setVitesse(double vitesse)
    {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, vitesse);
    }
}

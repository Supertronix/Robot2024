package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// Le deuxième convoyeur qui transporte la note du convoyeur 1 à l'intake
// Fonctionne avec 2 talons SRX
public class ConvoyeurHaut extends SousSysteme implements Materiel.Convoyeur2 {
    protected boolean toggleOnOff;

    protected MoteurTalon moteurTalonMaitre;
    protected MoteurTalon moteurTalonEsclave;

    public ConvoyeurHaut() {
        moteurTalonMaitre = new MoteurTalon(ID_TALON_CONVOYEUR_MAITRE);
        moteurTalonEsclave = new MoteurTalon(ID_TALON_CONVOYEUR_ESCLAVE);

        moteurTalonMaitre.setInverted(true);
        moteurTalonEsclave.setInverted(false);
        moteurTalonEsclave.follow(moteurTalonMaitre);

    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void demarrerMoteur() {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_CONVOYEUR);
        toggleOnOff = true;
    }
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void demarrerMoteur(double vitesse) {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, vitesse);
        toggleOnOff = true;
    }

    // Arrêter les moteurs du convoyeur
    public void arreterMoteur() {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, 0);
        toggleOnOff = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean moteurOn() {
        return this.toggleOnOff;
    }
}

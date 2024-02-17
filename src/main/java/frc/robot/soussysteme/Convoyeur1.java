package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// Le premier convoyeur qui transporte la note du convoyeur 1 au convoyeur 2
// Fonctionne avec 2 talons SRX
public class Convoyeur1 extends SousSysteme implements Materiel.Convoyeur1{
    protected MoteurTalon moteurTalonMaitre;
    protected MoteurTalon moteurTalonEsclave;

    public Convoyeur1() {
        moteurTalonMaitre = new MoteurTalon(ID_TALON_CONVOYEUR_MAITRE);
        moteurTalonEsclave = new MoteurTalon(ID_TALON_CONVOYEUR_ESCLAVE);

        moteurTalonEsclave.setInverted(true);
        moteurTalonEsclave.follow(moteurTalonMaitre);
    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void setVitesse() {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_CONVOYEUR);
    }
    
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void setVitesse(double vitesse) {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, vitesse);
    }
}

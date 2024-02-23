package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Intake extends SousSysteme implements Materiel.Intake
{
    protected boolean estActif; // toggle

    protected MoteurTalon moteur;
    protected Solenoid mouvementBouche;
    public static int MOUVEMENT_BOUCHE = 2;

    public Intake() {
        //this.compresseur = new Compressor(21, PneumaticsModuleType.CTREPCM); - singleton
        this.mouvementBouche = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_BOUCHE);
        moteur = new MoteurTalon(ID_TALON_INTAKE);
        moteur.setInverted(true);
    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void activer() {
        moteur.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
        estActif = true;
    }

    public void activer(int vitesse) {
        moteur.set(TalonSRXControlMode.PercentOutput, vitesse);
        estActif = true;
    }
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void activerSelonVitesse(double vitesse) {
        moteur.set(TalonSRXControlMode.PercentOutput, vitesse);
        estActif = true;
    }

    // Arrêter les moteurs du convoyeur
    public void desactiver() {
        moteur.set(TalonSRXControlMode.PercentOutput, 0);
        estActif = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.estActif;
    }
}

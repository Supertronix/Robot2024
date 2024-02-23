package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Avaleur extends SousSysteme implements Materiel.Avaleur
{
    protected boolean estActif; // toggle

    protected MoteurTalon moteurIntake;
    protected Solenoid mouvementBouche;

    public Avaleur() {
        //this.compresseur = new Compressor(21, PneumaticsModuleType.CTREPCM); - singleton
        this.mouvementBouche = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_BOUCHE);
        moteurIntake = new MoteurTalon(ID_TALON_INTAKE);
        moteurIntake.setInverted(true);
    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void activer() {
        moteurIntake.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
        mouvementBouche.set(true);
        estActif = true;
    }

    public void activer(int vitesse) {
        moteurIntake.set(TalonSRXControlMode.PercentOutput, vitesse);
        mouvementBouche.set(true);
        estActif = true;
    }
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void activerSelonVitesse(double vitesse) {
        moteurIntake.set(TalonSRXControlMode.PercentOutput, vitesse);
        estActif = true;
    }

    // Arrêter les moteurs du convoyeur
    public void desactiver() {
        moteurIntake.set(TalonSRXControlMode.PercentOutput, 0);
        estActif = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.estActif;
    }
}

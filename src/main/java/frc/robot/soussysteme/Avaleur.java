package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Cinematique;
import frc.robot.Materiel;
import frc.robot.composant.Compresseur;
import frc.robot.composant.MoteurTalon;

// L'intake qui permet de faire rentrer les notes, fonctionne avec un Talon SRX
public class Avaleur extends SousSysteme implements Materiel.Avaleur, Cinematique.Avaleur
{
    protected boolean estActif; // toggle

    protected MoteurTalon moteurIntake;
    protected Solenoid mouvementBouche;
    protected Compresseur compresseur;

    public Avaleur() {
        this.compresseur = Compresseur.getInstance();
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
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void activer(double vitesse) {
        moteurIntake.set(TalonSRXControlMode.PercentOutput, vitesse);
        mouvementBouche.set(true);
        estActif = true;
    }

    // Arrêter les moteurs du convoyeur
    public void desactiver() {
        moteurIntake.set(TalonSRXControlMode.PercentOutput, 0);
        this.mouvementBouche.set(false);
        estActif = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.estActif;
    }
}

package frc.robot.soussysteme;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Materiel;
import frc.robot.composant.MoteurSparkMax;

// Le lanceur du robot, composé de 2 moteurs SparkMAX
public class Lanceur implements Materiel.Lanceur {
    protected boolean actif;

    protected MoteurSparkMax moteurMaitre;
    protected MoteurSparkMax moteurEsclave;
    public RelativeEncoder encodeurMaitre;
    public RelativeEncoder encodeurEsclave;

    public Lanceur() {
        actif = false;

        moteurMaitre = new MoteurSparkMax(ID_LANCEUR_MAITRE);
        moteurEsclave = new MoteurSparkMax(ID_LANCEUR_ESCLAVE);
        this.moteurMaitre.setIdleMode(IdleMode.kCoast);
        this.moteurEsclave.setIdleMode(IdleMode.kCoast);

        encodeurMaitre = moteurMaitre.getEncoder();
        encodeurEsclave = moteurEsclave.getEncoder();
        SmartDashboard.putNumber("RPM Lanceur Maitre", encodeurMaitre.getVelocity());
        SmartDashboard.putNumber("RPM Lanceur Esclave", encodeurEsclave.getVelocity());

        moteurEsclave.follow(moteurMaitre, true);
    }

    // Démarre les moteurs avec la vitesse par défaut
    public void activer() {
        moteurMaitre.set(VITESSE_LANCEUR);
        actif = true;
    }

    /** 
     * Démarre les moteurs du lanceur avec la vitesse passée en paramètre
     * @param vitesse La vitesse du lanceur entre 0.00 et 1.00
     */
    public void activer(double vitesse) {
        moteurMaitre.set(vitesse);
        actif = true;
    }

    // Arrête les moteurs du lanceur
    public void desactiver() {
        moteurMaitre.set(0);
        actif = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.actif;
    }
}

package frc.robot.interaction;

import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commande.CommandeAvalerAutomatique;
import frc.robot.commande.CommandeLancerHaut;

public class ShuffleBoard {
    RelativeEncoder encodeurMaitreLanceur;
    RelativeEncoder encodeurEsclaveLanceur;
    CapteurLuminosite capteurLuminosite;
    public ShuffleBoard() {
        System.out.println("new ShuffleBoard()");
    }

    public void initialiser() {
        encodeurMaitreLanceur = Robot.getInstance().lanceur.encodeurMaitre;
        encodeurEsclaveLanceur = Robot.getInstance().lanceur.encodeurEsclave;
        capteurLuminosite = Robot.getInstance().capteurLuminosite;

        SmartDashboard.putNumber("RPM Lanceur Maitre", 0);
        SmartDashboard.putNumber("RPM Lanceur Esclave", 0);
        SmartDashboard.putBoolean("Note chargee", false);
        SmartDashboard.putBoolean("Alliance Rouge", true);

        SmartDashboard.putData("Commande Avaler", new CommandeAvalerAutomatique());
        SmartDashboard.putData("Commande Lancer Haut", new CommandeLancerHaut());
    }

    public void mettreAJour() {
        SmartDashboard.putNumber("RPM Lanceur Maitre", encodeurMaitreLanceur.getVelocity());
        SmartDashboard.putNumber("RPM Lanceur Esclave", encodeurEsclaveLanceur.getVelocity());
        SmartDashboard.putBoolean("Note chargee", capteurLuminosite.getLuminosite());
        SmartDashboard.putBoolean("Alliance Rouge", Robot.getInstance().getEstAllianceRouge());
    }

    /**
     * Changer l'alliance du robot manuellement verrouillera le changement d'alliance automatique
     */
    public void changerEquipe() {
        Robot.getInstance().setVerrouChangementAlliance(false);
        Robot.getInstance().setAlliance(!Robot.getInstance().getEstAllianceRouge());
        Robot.getInstance().setVerrouChangementAlliance(true);
    }
}

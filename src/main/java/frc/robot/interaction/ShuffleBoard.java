package frc.robot.interaction;

import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commande.robot.CommandeAvalerAutomatiquement;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.robot.CommandeRecracher;

public class ShuffleBoard {
    RelativeEncoder encodeurMaitreLanceur;
    RelativeEncoder encodeurEsclaveLanceur;
    DetecteurNote capteurLuminosite;
    public final Field2d arene = new Field2d();
    int periode;
    public ShuffleBoard() {
        System.out.println("new ShuffleBoard()");
    }

    public void initialiser() {
        encodeurMaitreLanceur = Robot.getInstance().lanceur.encodeurMaitre;
        encodeurEsclaveLanceur = Robot.getInstance().lanceur.encodeurEsclave;
        capteurLuminosite = Robot.getInstance().detecteurNote;

        SmartDashboard.putNumber("RPM Lanceur Maitre", 0);
        SmartDashboard.putNumber("RPM Lanceur Esclave", 0);
        SmartDashboard.putBoolean("Note chargee", false);
        SmartDashboard.putBoolean("Alliance Rouge", true);
        //SmartDashboard.putData("Arène", arene);

        SmartDashboard.putData("Recracher", new CommandeRecracher());
    }

    public void mettreAJour() {
        SmartDashboard.putNumber("RPM Lanceur Maitre", encodeurMaitreLanceur.getVelocity());
        SmartDashboard.putNumber("RPM Lanceur Esclave", encodeurEsclaveLanceur.getVelocity());
        SmartDashboard.putBoolean("Note chargee", capteurLuminosite.detecteNote());
        SmartDashboard.putBoolean("Alliance Rouge", Alliance.getInstance().getAllianceRouge());
        SmartDashboard.putNumber("angleRobot", Robot.getInstance().cameraLimelight.getBotpose()[5]);
        SmartDashboard.putNumber("angleTag", Robot.getInstance().cameraLimelight.getTagPositionRelatifRobot()[5]);
    }

    /**
     * Changer l'alliance du robot manuellement verrouillera le changement d'alliance automatique
     */
    /*public void changerEquipe() {
        
        Alliance.getInstance().setVerrouChangementAlliance(false);
        Alliance.getInstance().setAlliance(!Alliance.getInstance().getAllianceRouge());
        Alliance.getInstance().setVerrouChangementAlliance(true);
    }*/
}

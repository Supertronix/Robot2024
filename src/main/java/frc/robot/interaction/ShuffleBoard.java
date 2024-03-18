package frc.robot.interaction;

import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commande.robot.*;

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

        //SmartDashboard.putNumber("RPM Lanceur Maitre", 0);
        //SmartDashboard.putNumber("RPM Lanceur Esclave", 0);
        SmartDashboard.putBoolean("Note chargee", false);
        SmartDashboard.putBoolean("Alliance Rouge", true);
        //SmartDashboard.putData("Arène", arene);

        SmartDashboard.putData("Recracher", new CommandeRecracher());

        SmartDashboard.putData("ClignoterLeds", new CommandeClignoterLeds());
        SmartDashboard.putData("AllumerLeds", new CommandeAllumerLeds());
        SmartDashboard.putData("EteindreLeds", new CommandeEteindreLeds());
        SmartDashboard.putData("ResetLeds", new CommandeResetLeds());
    }

    public void mettreAJour() {
        //SmartDashboard.putNumber("RPM Lanceur Maitre", encodeurMaitreLanceur.getVelocity());
        //SmartDashboard.putNumber("RPM Lanceur Esclave", encodeurEsclaveLanceur.getVelocity());
        SmartDashboard.putBoolean("Note chargee", capteurLuminosite.detecteNote());
        SmartDashboard.putBoolean("Alliance Rouge", Alliance.getInstance().getAllianceRouge());
        //SmartDashboard.putNumber("angleRobot", Robot.getInstance().cameraLimelight.getBotpose()[5]);
        //SmartDashboard.putNumber("angleTag", Robot.getInstance().cameraLimelight.getTagPositionRelatifRobot()[5]);
    }
}

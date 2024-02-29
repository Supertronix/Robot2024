package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.classique.CommandeAvancer;
import frc.robot.interaction.Alliance;
import frc.robot.interaction.CameraLimelight;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;

public class AvancerDevantTag extends Command implements AprilTags {
    public static int DUREE = 8000;
    protected LimiteurDuree detecteurDuree;
    protected Robot robot;
    protected CommandeAllerA commandeAllerA;
    protected CameraLimelight cameraLimelight;
    public AvancerDevantTag() {
        System.out.println("TrajetAuto()");
        detecteurDuree = new LimiteurDuree(DUREE);
        robot = Robot.getInstance();
        cameraLimelight = robot.cameraLimelight;
        cameraLimelight.activerTargeting();
    }

    @Override
    public void initialize() {
        System.out.println("TrajetAuto.initialize()");
        double[] position = cameraLimelight.getBotpose();
        double x = position[0];
        double y = position[1];
        double angle = position[2];
        System.out.println("TrajetAuto.initialize() x: " + x + " y: " + y + " angle: " + angle);

        Vecteur3 positionCible = new Vecteur3(0, 0, 0);
        double angleCible = 0;
        if (Alliance.getInstance().getAllianceRouge()) {
            System.out.println("Avancer Speaker Rouge");
            //positionCible = new Vecteur3(x, y + 1, SpeakerRouge.ANGLE);
            new CommandeAvancer(20).schedule();
            angleCible = angle;
            commandeAllerA = new CommandeAllerA(positionCible, angleCible);
        } else {
            System.out.println("Avancer Speaker Bleu");
            positionCible = new Vecteur3(x, y - 1, SpeakerRouge.ANGLE);
            angleCible = angle;
            commandeAllerA = new CommandeAllerA(positionCible, angleCible);
        }
        //commandeAllerA.schedule();
    }

    @Override
    public boolean isFinished() {
        // Vérifie si la commandeAllerA est terminée
        if (commandeAllerA.isFinished()) {
            return true;
        }
        if (detecteurDuree.estTropLongue()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        commandeAllerA.end(interrupted);
        System.out.println("TrajetAuto.end()");
    }
}

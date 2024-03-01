package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.interaction.Alliance;
import frc.robot.interaction.CameraLimelight;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;

public class CommandeViserTag extends Command implements AprilTags {
    public static int DUREE = 20000;
    protected LimiteurDuree detecteurDuree;
    protected Robot robot;
    protected CommandeAllerA commandeAllerA;
    protected CameraLimelight cameraLimelight;
    protected boolean estFinie = false;
    public CommandeViserTag() {
        System.out.println("ViserTag()");
        detecteurDuree = new LimiteurDuree(DUREE);
        robot = Robot.getInstance();
        cameraLimelight = robot.cameraLimelight;
        cameraLimelight.activerTargeting();
    }

    @Override
    public void initialize() {
        System.out.println("ViserTag.initialize()");
        double[] position = cameraLimelight.getBotpose();
        double x = position[0];
        double y = position[1];
        double angleRobot = position[2];
        if (x == 0 || y == 0) {
            this.estFinie = true;
            return;
        }
        System.out.println("VisterTag.initialize() x: " + x + " y: " + y + " angle: " + angleRobot);

        double[] posTag = cameraLimelight.getTagPositionRelatifRobot();
        double angleVersCible = posTag[5];
        angleVersCible -= angleRobot;

        Vecteur3 positionCible = new Vecteur3(0, 0, 0);

        if (Alliance.getInstance().getAllianceRouge()) {
            System.out.println("Avancer Speaker Rouge");
            positionCible = new Vecteur3(x, y, 0);
            commandeAllerA = new CommandeAllerA(positionCible, angleVersCible);
        } else {
            System.out.println("Avancer Speaker Bleu");
            positionCible = new Vecteur3(x, y, 0);
            commandeAllerA = new CommandeAllerA(positionCible, angleVersCible);
        }
        commandeAllerA.schedule();
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
        if (estFinie) {
            return true;
        }
        return false;
    }
}

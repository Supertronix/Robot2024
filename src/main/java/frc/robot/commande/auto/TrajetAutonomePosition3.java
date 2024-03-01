package frc.robot.commande.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commande.robot.CommandeAvalerAutomatiquement;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.classique.CommandeAvancer;
import frc.robot.commande.terrain.classique.CommandeTourner;
import frc.robot.interaction.Alliance;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;

public class TrajetAutonomePosition3 extends SequentialCommandGroup implements AprilTags {

    public TrajetAutonomePosition3(){
        boolean estRouge = Alliance.getInstance().getAllianceRouge();
        int angle = estRouge ? 45 : -45;
        double positionX = estRouge ? SpeakerRouge.POSITIONS[2].x : SpeakerBleu.POSITIONS[2].x;
        double positionY = estRouge ? SpeakerRouge.POSITIONS[2].y : SpeakerBleu.POSITIONS[2].y;
        double positionAngle = estRouge ? SpeakerRouge.POSITIONS[2].z : SpeakerBleu.POSITIONS[2].z;
        Vecteur3 position = new Vecteur3(positionX, positionY, 0);

        addCommands(
                new CommandeTourner(angle),
                new CommandeLancerSpeaker(),
                new WaitCommand(1),
                new CommandeTourner(-angle),
                new CommandeAvancer(40).alongWith(new CommandeAvalerAutomatiquement()),
                new CommandeAllerA(position, positionAngle),
                new CommandeLancerSpeaker()
        );
        //      new CommandeLancerSpeaker().andThen(new WaitCommand(1).andThen(new CommandeAvalerAutomatiquement().alongWith(new CommandeAvancer(20)).andThen(new CommandeAvancer(-5))).andThen(new CommandeLancerSpeaker())).schedule();
    }

}

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
        int angleDebut = estRouge ? 180 : 0;
        double decalage = estRouge ? 0.82 : -0.82;
        double positionX = estRouge ? SpeakerRouge.POSITIONS[2].x : SpeakerBleu.POSITIONS[2].x;
        double positionY = estRouge ? SpeakerRouge.POSITIONS[2].y : SpeakerBleu.POSITIONS[2].y;
        double positionAngle = estRouge ? SpeakerRouge.POSITIONS[0].z : SpeakerBleu.POSITIONS[0].z;
        Vecteur3 position = new Vecteur3(positionX, positionY, 0);
        Vecteur3 positionDebut = new Vecteur3(positionX+decalage, positionY, 0);

        addCommands(
                new WaitCommand(0.5),
                new CommandeLancerSpeaker()
                //new CommandeAvancer(60, 2500),
                //new CommandeTourner(45)
        );
        //      new CommandeLancerSpeaker().andThen(new WaitCommand(1).andThen(new CommandeAvalerAutomatiquement().alongWith(new CommandeAvancer(20)).andThen(new CommandeAvancer(-5))).andThen(new CommandeLancerSpeaker())).schedule();
    }

}

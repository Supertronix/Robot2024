package frc.robot.commande.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commande.robot.CommandeAvalerAutomatiquement;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.classique.CommandeAvancer;
import frc.robot.commande.terrain.classique.CommandeTourner;
import frc.robot.commande.terrain.duree.CommandeTournerDroiteSelonDuree;
import frc.robot.interaction.Alliance;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;

public class TrajetAutonomePosition1 extends SequentialCommandGroup implements AprilTags {

    public TrajetAutonomePosition1(){
        boolean estRouge = Alliance.getInstance().getAllianceRouge();
        //int angle = estRouge ? -30 : 30;
        int angleDebut = estRouge ? 180 : 0;
        double decalage = estRouge ? 0.82 : -0.82;
        double positionX = estRouge ? SpeakerRouge.POSITIONS[0].x : SpeakerBleu.POSITIONS[0].x;
        double positionY = estRouge ? SpeakerRouge.POSITIONS[0].y : SpeakerBleu.POSITIONS[0].y;
        double positionAngle = estRouge ? SpeakerRouge.POSITIONS[0].z : SpeakerBleu.POSITIONS[0].z;
        Vecteur3 position = new Vecteur3(positionX, positionY, 0);
        Vecteur3 positionDebut = new Vecteur3(positionX+decalage, positionY, 0);

        addCommands(
            new CommandeLancerSpeaker(),
            new CommandeAvancer(60, 2500),
            new CommandeTourner(-45)
        );
        //      new CommandeLancerSpeaker().andThen(new WaitCommand(1).andThen(new CommandeAvalerAutomatiquement().alongWith(new CommandeAvancer(20)).andThen(new CommandeAvancer(-5))).andThen(new CommandeLancerSpeaker())).schedule();
    }
    
}

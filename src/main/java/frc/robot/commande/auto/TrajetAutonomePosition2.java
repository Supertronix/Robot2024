package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commande.robot.CommandeAvalerAutomatiquement;
import frc.robot.commande.robot.CommandeAvalerTeleop;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.classique.CommandeAvancer;

public class TrajetAutonomePosition2 extends SequentialCommandGroup {
    
    public TrajetAutonomePosition2(){
        // addCommands(
        //     new CommandeDeplacer(500, 0.3, 0, 0),
        //     new CommandeDeplacer(500, 0, 0.3, 0),
        //     new CommandeDeplacer(500, 0, 0, 0.3)
        // );a
        //addCommands(
        //    new CommandeTasserDroite(30)
        //);

        addCommands(
            new CommandeAvancer(15, 1200),
            new CommandeLancerSpeaker(),
            new CommandeAvalerAutomatiquement().raceWith(new CommandeAvancer(45, 3000)),
            new TrajetNoteDansSpeaker(),
            new CommandeAvancer(60, 3000)
        );
        //      new CommandeLancerSpeaker().andThen(new WaitCommand(1).andThen(new CommandeAvalerAutomatiquement().alongWith(new CommandeAvancer(20)).andThen(new CommandeAvancer(-5))).andThen(new CommandeLancerSpeaker())).schedule();
    }
    
}

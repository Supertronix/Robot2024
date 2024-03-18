package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commande.robot.*;
import frc.robot.commande.terrain.classique.CommandeAvancer;

public class TrajetAutonomePosition2 extends SequentialCommandGroup {
    
    public TrajetAutonomePosition2(){
        addCommands(
            new CommandeAvancer(15, 1100),
            new CommandeLancerSpeaker(),
            new CommandeAvalerAutomatiquement().raceWith(new CommandeAvancer(45, 3000)),
            new CommandeAllumerLeds(),
            new TrajetNoteDansSpeaker(),
            new CommandeEteindreLeds(),
            new CommandeAvancer(60, 3000)
        );
        //      new CommandeLancerSpeaker().andThen(new WaitCommand(1).andThen(new CommandeAvalerAutomatiquement().alongWith(new CommandeAvancer(20)).andThen(new CommandeAvancer(-5))).andThen(new CommandeLancerSpeaker())).schedule();
    }
    
}

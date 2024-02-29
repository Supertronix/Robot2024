package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.terrain.classique.CommandeAvancer;
import frc.robot.commande.terrain.classique.CommandeDeplacer;
import frc.robot.commande.terrain.classique.CommandeTourner;

public class TrajetTest extends SequentialCommandGroup {
    
    public TrajetTest(){
        // addCommands(
        //     new CommandeDeplacer(500, 0.3, 0, 0),
        //     new CommandeDeplacer(500, 0, 0.3, 0),
        //     new CommandeDeplacer(500, 0, 0, 0.3)
        // );a
        addCommands(
            //new CommandeAvancer(-30)
            new CommandeTourner(180)
        );
    }
    
}

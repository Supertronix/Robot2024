package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.CommandeLancerSpeaker;

public class TrajetAutonome extends SequentialCommandGroup {
    
    public TrajetAutonome(){
        // addCommands(
        //     new CommandeDeplacer(500, 0.3, 0, 0),
        //     new CommandeDeplacer(500, 0, 0.3, 0),
        //     new CommandeDeplacer(500, 0, 0, 0.3)
        // );a
        //addCommands(
        //    new CommandeTasserDroite(30)
        //);

        addCommands(
            new AvancerDevantTag()
            //new ViserTag(),
            //new CommandeLancerSpeaker()
        );
    }
    
}

package frc.robot.commande.auto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commande.robot.CommandeAvalerAutomatiquement;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.terrain.classique.CommandeAvancer;

public class TrajetAutonomePosition1 extends SequentialCommandGroup {

    public TrajetAutonomePosition1(){

        addCommands(
                //new CommandeLancerSpeaker(),
            //new WaitCommand(1),
                //new Commande
        );
        //      new CommandeLancerSpeaker().andThen(new WaitCommand(1).andThen(new CommandeAvalerAutomatiquement().alongWith(new CommandeAvancer(20)).andThen(new CommandeAvancer(-5))).andThen(new CommandeLancerSpeaker())).schedule();
    }
    
}

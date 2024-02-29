package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class CommandeLanceurOuvrirEtAllonger extends SequentialCommandGroup {
    
    public CommandeLanceurOuvrirEtAllonger()
    {
        System.out.println("new CommandeLanceurOuvrirEtAllonger()");
        this.addCommands(
            new CommandeLanceurOuvrir(), 
            new CommandeLanceurAllonger()
        );
    }

}

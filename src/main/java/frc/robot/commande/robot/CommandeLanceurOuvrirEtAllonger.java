package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandeLanceurOuvrirEtAllonger extends Command {
    
    public CommandeLanceurOuvrirEtAllonger()
    {
        //System.out.println("new CommandeLanceurOuvrirEtAllonger()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurOuvrirEtAllonger.initialize()");
        new CommandeLanceurOuvrir().andThen(new CommandeLanceurAllonger()).schedule();
    }
}

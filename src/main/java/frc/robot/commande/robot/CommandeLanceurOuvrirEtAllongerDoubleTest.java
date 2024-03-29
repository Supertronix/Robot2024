package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandeLanceurOuvrirEtAllongerDoubleTest extends Command {
    
    CommandeLanceurOuvrir commandeOuvrir = new CommandeLanceurOuvrir();
    CommandeLanceurAllonger commandeAllonger = new CommandeLanceurAllonger();

    enum COMMANDE_EN_COURS{OUVRIR,ALLONGER};
    COMMANDE_EN_COURS commandeEnCours = null;

    public CommandeLanceurOuvrirEtAllongerDoubleTest()
    {
        //System.out.println("new CommandeLanceurOuvrirEtAllongerDoubleTest()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurOuvrirEtAllongerDoubleTest.initialize()");
        commandeOuvrir.schedule();
        commandeEnCours = COMMANDE_EN_COURS.OUVRIR;
    }

    @Override
    public void execute() {
        if(this.commandeOuvrir.isFinished())
        {
            if(this.commandeEnCours == COMMANDE_EN_COURS.OUVRIR)
            {
                commandeAllonger.schedule();
                this.commandeEnCours = COMMANDE_EN_COURS.ALLONGER;
                System.out.println("Commande allonger simulee");
            }
        }
    }

    @Override
    public boolean isFinished() 
    {
        if(this.commandeOuvrir.estAnormale()) System.out.println("Premiere commande anormale");
        if(this.commandeOuvrir.estAnormale()) return true;
        if(this.commandeEnCours == COMMANDE_EN_COURS.ALLONGER)
            if(this.commandeAllonger.isFinished()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurOuvrirEtAllongerDoubleTest.end()");
    }
}

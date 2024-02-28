package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandeLanceurRetracterEtFermerDoubleTest extends Command {
    
    CommandeLanceurFermer commandeFermer = new CommandeLanceurFermer();
    CommandeLanceurRetracter commandeRetracter = new CommandeLanceurRetracter();

    enum COMMANDE_EN_COURS{FERMER,RETRACTER};
    COMMANDE_EN_COURS commandeEnCours = null;

    public CommandeLanceurRetracterEtFermerDoubleTest()
    {
        //System.out.println("new CommandeLanceurRetracterEtFermerDoubleTest()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurRetracterEtFermerDoubleTest.initialize()");
        commandeRetracter.schedule();
        commandeEnCours = COMMANDE_EN_COURS.RETRACTER;
    }

    @Override
    public void execute() {
        if(this.commandeRetracter.isFinished())
        {
            if(this.commandeEnCours == COMMANDE_EN_COURS.RETRACTER)
            {
                commandeFermer.schedule();
                this.commandeEnCours = COMMANDE_EN_COURS.FERMER;
                System.out.println("Commande fermer simulee");
            }
        }
    }

    @Override
    public boolean isFinished() 
    {
        if(this.commandeRetracter.estAnormale()) System.out.println("Premiere commande anormale");
        if(this.commandeRetracter.estAnormale()) return true;
        if(this.commandeEnCours == COMMANDE_EN_COURS.FERMER)
            if(this.commandeRetracter.isFinished()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurRetracterEtFermerDoubleTest.end()");
    }
}

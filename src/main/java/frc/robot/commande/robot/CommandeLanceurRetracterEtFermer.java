package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandeLanceurRetracterEtFermer extends Command {
    
    public CommandeLanceurRetracterEtFermer()
    {
        //System.out.println("new CommandeRetracterEtFermer()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurRetracterEtFermer.initialize()");
        new CommandeLanceurRetracter().andThen(new CommandeLanceurFermer()).schedule();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() 
    {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurRetracterEtFermer.end()");
    }
}

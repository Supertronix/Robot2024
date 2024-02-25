package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.ConvoyeurHaut;

public class CommandeLanceurRetracter extends Command {
    protected ConvoyeurHaut convoyeurHaut;
    //protected Manette manette;

    public CommandeLanceurRetracter()
    {
        System.out.println("new CommandeLanceurRetracter()");
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        addRequirements(this.convoyeurHaut);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurRetracter.initialize()");
        convoyeurHaut.retracter();
    }

    @Override
    public void execute() {
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurRetracter.end()");
    }
}

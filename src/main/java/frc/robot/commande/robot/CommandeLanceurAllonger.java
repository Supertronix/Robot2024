package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.ConvoyeurHaut;

public class CommandeLanceurAllonger extends Command {
    protected ConvoyeurHaut convoyeurHaut;
    //protected Manette manette;

    public CommandeLanceurAllonger()
    {
        System.out.println("new CommandeLanceurAllonger()");
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        addRequirements(this.convoyeurHaut);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurAllonger.initialize()");
        convoyeurHaut.allonger();
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
        System.out.println("CommandeLanceurAllonger.end()");
    }
}

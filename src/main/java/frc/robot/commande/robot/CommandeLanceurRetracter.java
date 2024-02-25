package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.soussysteme.ConvoyeurHaut;

public class CommandeLanceurRetracter extends Command {
    protected ConvoyeurHaut convoyeurHaut;
    protected static final int DUREE = 1000;
    protected LimiteurDuree detecteurDuree;
    //protected Manette manette;

    public CommandeLanceurRetracter()
    {
        System.out.println("new CommandeLanceurRetracter()");
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        addRequirements(this.convoyeurHaut);
        this.detecteurDuree = new LimiteurDuree(DUREE);
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

    public boolean anormale = false;
    public boolean estAnormale()
    {
        return this.anormale;
    }
    @Override
    public boolean isFinished() 
    {
        if(convoyeurHaut.estRetracte()) return true;
        if(this.detecteurDuree.estTropLongue()) 
        {
            this.anormale = true;
            return true;
        }
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurRetracter.end()");
    }
}

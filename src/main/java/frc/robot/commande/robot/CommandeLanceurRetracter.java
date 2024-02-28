package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.soussysteme.ConvoyeurHaut;

public class CommandeLanceurRetracter extends Command {
    protected ConvoyeurHaut convoyeurHaut;
    protected static final int DUREE = 10000;
    protected LimiteurDuree detecteurDuree;
    //protected Manette manette;

    public CommandeLanceurRetracter()
    {
        //System.out.println("new CommandeLanceurRetracter()");
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        addRequirements(this.convoyeurHaut);
        this.detecteurDuree = new LimiteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurRetracter.initialize()");
        convoyeurHaut.retracter();
        this.detecteurDuree.mesurer();
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
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurRetracter.end()");
    }

    @Override
    public SequentialCommandGroup andThen(Command... next) {
        if (anormale)
            return super.andThen(new WaitCommand(0));
        return super.andThen(next);
    }
}

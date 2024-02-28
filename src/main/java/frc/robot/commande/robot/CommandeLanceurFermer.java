package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.soussysteme.ConvoyeurHaut;

public class CommandeLanceurFermer extends Command {
    protected ConvoyeurHaut convoyeurHaut;
    //protected Manette manette;
    protected LimiteurDuree detecteurDuree;
    protected static final int DUREE = 10000;

    public CommandeLanceurFermer()
    {
        System.out.println("new CommandeLanceurFermer()");
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        addRequirements(this.convoyeurHaut);
        this.detecteurDuree = new LimiteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurFermer.initialize()");
        convoyeurHaut.fermer();
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        this.detecteurDuree.mesurer();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {

        if(!convoyeurHaut.estOuvert()) return true;
        if(this.detecteurDuree.estTropLongue()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurFermer.end()");
    }
}

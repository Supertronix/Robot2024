package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.soussysteme.ConvoyeurHaut;


public class CommandeLanceurOuvrir extends Command {
    protected ConvoyeurHaut convoyeurHaut;
    protected static final int DUREE = 1000;
    protected LimiteurDuree detecteurDuree;

    //protected Manette manette;

    public CommandeLanceurOuvrir()
    {
        System.out.println("new CommandeLanceurOuvrir()");
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        addRequirements(this.convoyeurHaut);
        this.detecteurDuree = new LimiteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurOuvrir.initialize()");
        convoyeurHaut.ouvrir();
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        System.out.println("Capteur deploye " + convoyeurHaut.estOuvert() );
        System.out.println("Capteur retracte " + convoyeurHaut.estRetracte() );
        this.detecteurDuree.mesurer();
    }

    /** 
     * @return boolean
     */

    public boolean anormale = false;
    public boolean estAnormale()
    {
        return this.anormale;
    }
    @Override
    public boolean isFinished() 
    {
        if(convoyeurHaut.estOuvert()) return true;
        if(this.detecteurDuree.estTropLongue()) 
        {
            this.anormale = true;
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurOuvrir.end()");
    }
}

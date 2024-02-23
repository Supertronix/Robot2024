package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.mesure.DetecteurDuree;
import frc.robot.soussysteme.LanceurExtension;

public class CommandeLanceurOuvrir extends Command {
    protected LanceurExtension lanceurExtension;
    protected static final int DUREE = 10000;
    protected DetecteurDuree detecteurDuree;

    //protected Manette manette;

    public CommandeLanceurOuvrir()
    {
        System.out.println("new CommandeLanceurOuvrir()");
        this.lanceurExtension = Robot.getInstance().lanceurExtension;
        addRequirements(this.lanceurExtension);
        this.detecteurDuree = new DetecteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurOuvrir.initialize()");
        lanceurExtension.ouvrir();
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        System.out.println("Capteur deploye " + lanceurExtension.estOuvert() );
        System.out.println("Capteur retracte " + lanceurExtension.estFerme() );
        this.detecteurDuree.mesurer();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if(lanceurExtension.estOuvert()) return true;
        if(this.detecteurDuree.estTropLongue()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurOuvrir.end()");
    }
}

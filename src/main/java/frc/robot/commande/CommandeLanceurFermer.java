package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.mesure.DetecteurDuree;
import frc.robot.soussysteme.LanceurExtension;

public class CommandeLanceurFermer extends Command {
    protected LanceurExtension lancerurExtension;
    //protected Manette manette;
    protected DetecteurDuree detecteurDuree;
    protected static final int DUREE = 10000;

    public CommandeLanceurFermer()
    {
        System.out.println("new CommandeLanceurFermer()");
        this.lancerurExtension = Robot.getInstance().lanceurExtension;
        addRequirements(this.lancerurExtension);
        this.detecteurDuree = new DetecteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurFermer.initialize()");
        lancerurExtension.fermer();
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        System.out.println("Capteur deploye " + lancerurExtension.estOuvert() );
        System.out.println("Capteur retracte " + lancerurExtension.estFerme() );
        this.detecteurDuree.mesurer();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if(lancerurExtension.estFerme()) return true;
        if(this.detecteurDuree.estTropLongue()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurFermer.end()");
    }
}

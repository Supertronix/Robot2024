package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Lanceur;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerHaut extends Command {

    protected int DUREE = 2000;

    protected LimiteurDuree detecteurDuree;
    protected ConvoyeurHaut convoyeurHaut;
    protected Lanceur lanceur;

    public CommandeLancerHaut()
    {
        System.out.println("new CommandeLancerHaut()");
        convoyeurHaut = Robot.getInstance().convoyeurHaut;
        lanceur = Robot.getInstance().lanceur;
        detecteurDuree = new LimiteurDuree(DUREE);

        addRequirements(convoyeurHaut);
        addRequirements(lanceur);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLancerHaut.initialize()");
        this.detecteurDuree.initialiser();
        lanceur.activer(1);
    }
    @Override
    public void execute() {
        detecteurDuree.mesurer();
        if (detecteurDuree.getDuree() > 800)
            convoyeurHaut.activer(1);
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        return detecteurDuree.estTropLongue();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLancerHaut.end()");
        convoyeurHaut.desactiver();
        lanceur.desactiver();
    }
}

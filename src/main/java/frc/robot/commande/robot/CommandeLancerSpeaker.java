package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Lanceur;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerSpeaker extends Command {

    public static int DUREE = 1000;
    public static int DELAIS_ENTRE_CONVOYEUR_ET_LANCEUR = 100;

    protected LimiteurDuree detecteurDuree;
    protected ConvoyeurHaut convoyeurHaut;
    protected Lanceur lanceur;

    public CommandeLancerSpeaker()
    {
        //System.out.println("new CommandeLancerSpeaker()");
        convoyeurHaut = Robot.getInstance().convoyeurHaut;
        lanceur = Robot.getInstance().lanceur;
        detecteurDuree = new LimiteurDuree(DUREE);

        addRequirements(convoyeurHaut);
        addRequirements(lanceur);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLancerSpeaker.initialize()");
        this.detecteurDuree.initialiser();
        lanceur.activer(1);
    }

    @Override
    public void execute() {
        detecteurDuree.mesurer();
        if (detecteurDuree.getDureePreMesuree() > DELAIS_ENTRE_CONVOYEUR_ET_LANCEUR)
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
        System.out.println("CommandeLancerSpeaker.end()");
        convoyeurHaut.desactiver();
        lanceur.desactiver();
    }
}

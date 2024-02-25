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

    public static int DUREE = 3000;
    public static int DELAIS_ENTRE_CONVOYEUR_ET_LANCEUR = 800;

    protected LimiteurDuree detecteurDuree;
    protected ConvoyeurHaut convoyeurHaut;
    protected Lanceur lanceur;

    public CommandeLancerSpeaker()
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
        if (detecteurDuree.getDuree() > DELAIS_ENTRE_CONVOYEUR_ET_LANCEUR)
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

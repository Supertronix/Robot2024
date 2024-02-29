package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Lanceur;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerAmplificateur extends Command {

    public static int DUREE = 1000;

    protected LimiteurDuree detecteurDuree;
    protected ConvoyeurHaut convoyeurHaut;

    public CommandeLancerAmplificateur()
    {
        System.out.println("new CommandeLancerAmplificateur()");
        convoyeurHaut = Robot.getInstance().convoyeurHaut;
        detecteurDuree = new LimiteurDuree(DUREE);

        addRequirements(convoyeurHaut);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLancerAmplificateur.initialize()");
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        detecteurDuree.mesurer();
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
        System.out.println("CommandeLancerAmplificateur.end()");
        convoyeurHaut.desactiver();
    }
}

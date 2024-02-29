package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.DetecteurNote;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.soussysteme.ConvoyeurHaut;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerAmplificateur extends Command {

    protected static int DUREE = 3000;

    protected boolean finie = false;

    protected LimiteurDuree detecteurDuree;
    protected DetecteurNote capteurLuminosite;

    protected ConvoyeurHaut convoyeurHaut;

    public CommandeLancerAmplificateur()
    {
        //System.out.println("new CommandeLancerAmplificateur()");     
        convoyeurHaut = Robot.getInstance().convoyeurHaut;
        detecteurDuree = new LimiteurDuree(DUREE);
        addRequirements(convoyeurHaut);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLancerAmplificateur.initialize()");
        convoyeurHaut.activer();
    }
    @Override
    public void execute() {
        
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if (detecteurDuree.estTropLongue())
        {
            System.out.println("CommandeLancerAmplificateur.isFinished() detecteurDuree.estTropLongue()");
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLancerAmplificateur.end()");
        convoyeurHaut.desactiver();
    }

}

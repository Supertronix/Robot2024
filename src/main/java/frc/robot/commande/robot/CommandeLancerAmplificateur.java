package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.interaction.DetecteurNote;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerAmplificateur extends Command {

    protected static int DUREE = 5000;

    protected boolean finie = false;
    protected LimiteurDuree detecteurDuree;
    protected DetecteurNote capteurLuminosite;

    public CommandeLancerAmplificateur()
    {
        System.out.println("new CommandeLancerBas()");        
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLancerBas.initialize()");
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
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLancerBas.end()");
    }

}

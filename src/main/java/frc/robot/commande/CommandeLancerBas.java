package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerBas extends Command {

    protected static int DUREE = 5000;

    protected boolean finie = false;
    protected LimiteurDuree detecteurDuree;
    protected CapteurLuminosite capteurLuminosite;

    public CommandeLancerBas()
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

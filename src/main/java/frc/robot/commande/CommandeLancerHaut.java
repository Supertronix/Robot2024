package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.soussysteme.Intake;
import frc.robot.soussysteme.ConvoyeurBas;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.DetecteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeLancerHaut extends Command {

    protected static int DUREE = 5000;

    protected boolean finie = false;
    protected DetecteurDuree detecteurDuree;
    protected CapteurLuminosite capteurLuminosite;

    public CommandeLancerHaut()
    {
    }
       
    @Override
    public void initialize() 
    {
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
}

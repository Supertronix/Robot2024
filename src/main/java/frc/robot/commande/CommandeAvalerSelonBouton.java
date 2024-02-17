package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.Intake;
import frc.robot.soussysteme.ConvoyeurBas;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.DetecteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvalerSelonBouton extends CommandeAvaler {


    protected Manette manette = null;

    public CommandeAvalerSelonBouton()
    {
        System.out.println("new CommandeAvalerSelonBouton()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvalerSelonBouton.initialize()");
        super.initialize();
        this.manette = RobotControleur.ActionManette.getInstance();
    }
    @Override
    public void execute() {
        System.out.println("CommandeAvalerSelonBouton.execute()");
        super.execute();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if(super.isFinished() == true) return true;
        // test du bouton de manette  return false
        return true;
    }
}

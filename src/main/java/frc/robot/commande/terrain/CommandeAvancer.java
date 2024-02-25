package frc.robot.commande.terrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvancer extends Command {

    protected Roues roues = null;
    protected boolean finie = false;
    protected LimiteurDuree detecteur;
    protected double pas;

    public CommandeAvancer(int pas)
    {
        System.out.println("new CommandeAvancer()");
        this.pas = pas;
        //this.roues = Robot.getInstance().roues;
        //this.addRequirements(this.roues);
        //this.detecteur = new DetecteurDuree(Cinematique.Machoire.TEMPS_MAXIMUM_OUVRIR);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvancer.initialize()");
        this.roues = Robot.getInstance().roues;
        this.roues.avancer(pas);
        //this.detecteur.initialiser();
        //this.finie = false;
    }
    @Override
    public void execute() {
        System.out.println("CommandeAvancer.execute()");
        //this.detecteur.mesurer();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        //if(this.machoire.estOuverte() || this.detecteur.estTropLongue())
        return true;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvancer.end()");
    }
}

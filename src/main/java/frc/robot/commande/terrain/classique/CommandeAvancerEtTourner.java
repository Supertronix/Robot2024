package frc.robot.commande.terrain.classique;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeAvancerEtTourner extends Command {

    private static final int TEMPS_MAXIMUM = 3000;

    protected Roues roues = null;
    protected boolean finie = false;
    protected LimiteurDuree detecteur;
    protected double angle; // angle en degre ???

    public CommandeAvancerEtTourner(double angle)
    {
        //System.out.println("new CommandeAvancerEtTourner()");
        this.angle = angle;
        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvancerEtTourner.initialize()");
        this.roues = Robot.getInstance().roues;
        //this.roues.avancer(pas);
        this.detecteur.initialiser();
        this.finie = false;
    }
    @Override
    public void execute() {
        System.out.println("CommandeTourner.execute()");
        this.detecteur.mesurer();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        //if(this.estArrivee() || this.detecteur.estTropLongue())
        return true;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvancerEtTourner.end()");
    }
}

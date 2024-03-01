package frc.robot.commande.terrain.duree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeAvancerSelonDuree extends Command {

    protected Roues roues = null;
    protected LimiteurDuree detecteur;

    protected double temps = 0;
    protected double vitesse = 0;

    /**
     * @param temps temps en milisecondes
     * @param vitesse de deplacement
     */
    public CommandeAvancerSelonDuree(double temps, double vitesse)
    {
        //System.out.println("new CommandeAvancerSelonDuree()");
        this.temps = temps;
        this.detecteur = new LimiteurDuree(temps);
        this.vitesse = vitesse;

        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
    }
       
    public void initialize() 
    {
        System.out.println("duree.CommandeAvancer.initialize()");
        this.roues = Robot.getInstance().roues;
        // this.roues.avancer(10);
        this.detecteur.initialiser();
		//pid.setSetpoint(LecteurAccelerometre.getInstance().accelerometre.getRawGyroZ);
		//	pid.enable();
        
    }

    public void execute() {
        this.detecteur.mesurer();
        this.roues.avancer(vitesse);
    }
    
    public boolean isFinished() 
    {
        return this.detecteur.estTropLongue();
    }

    public void end(boolean interrupted) {
        System.out.println("CommandeAvancerSelonDuree.end()");
        this.roues.conduireAvecAxes(0, 0, 0);
    }
}

package frc.robot.commande.terrain.duree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeReculerSelonDuree extends Command {

    protected Roues roues = null;
    protected LimiteurDuree detecteur;

    protected double temps = 0;
    protected double vitesse = 0;

    /**
     * 
     * @param tempsMs temps en milisecondes
     * @param vitesse de deplacement
     */
    public CommandeReculerSelonDuree(double temps, double vitesse)
    {
        //System.out.println("new CommandeReculerSelonDuree()");
        this.temps = temps;
        this.detecteur = new LimiteurDuree(temps);
        this.vitesse = vitesse;

        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
    }
       
    public void initialize() 
    {
        System.out.println("CommandeReculerSelonDuree.initialize()");
        this.roues = Robot.getInstance().roues;
        this.detecteur.initialiser();        
    }

    public void execute() {
        this.detecteur.mesurer();
        this.roues.avancer(-vitesse);
    }
    
    public boolean isFinished() 
    {
        return this.detecteur.estTropLongue();
    }

    public void end(boolean interrupted) {
        System.out.println("CommandeReculerSelonDuree.end()");
        this.roues.conduireAvecAxes(0, 0, 0);
    }
}

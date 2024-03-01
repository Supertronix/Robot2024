package frc.robot.commande.terrain.duree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeTournerDroiteSelonDuree extends Command {

    protected Roues roues = null;
    protected LimiteurDuree detecteur;

    protected double temps = 0;
    protected double vitesse = 0;

    /**
     * Cette classe tasse a droite avec un nombre positif et a gauche avec un nombre negatif
     * @param temps temps en milisecondes
     * @param vitesse de deplacement
     */
    public CommandeTournerDroiteSelonDuree(double temps, double vitesse)
    {
        //System.out.println("new CommandeTournerDroiteSelonDuree()");
        this.temps = temps;
        this.detecteur = new LimiteurDuree(temps);
        this.vitesse = vitesse;

        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
    }
       
    public void initialize() 
    {
        System.out.println("CommandeTournerSelonDuree.initialize()");
        this.roues = Robot.getInstance().roues;
        this.detecteur.initialiser();        
    }

    public void execute() {
        this.detecteur.mesurer();
        this.roues.tournerDroite(vitesse);
    }
    
    public boolean isFinished() 
    {
        return this.detecteur.estTropLongue();
    }

    public void end(boolean interrupted) {
        System.out.println("CommandeTournerDroiteSelonDuree.end()");
        this.roues.conduireAvecAxes(0, 0, 0);
    }
}

package frc.robot.commande.terrain.duree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeDiagonaleSelonDuree extends Command {

    protected Roues roues = null;
    protected LimiteurDuree detecteur;

    protected double temps = 0;
    protected double vitesseAvant = 0;
    protected double vitesseDroite = 0;

    /**
     * @param temps temps en milisecondes
     * @param vitesse de deplacement
     */
    public CommandeDiagonaleSelonDuree(double temps, double vitesseAvant, double vitesseDroite)
    {
        //System.out.println("new CommandeAvancerSelonDuree()");
        this.temps = temps;
        this.detecteur = new LimiteurDuree(temps);
        this.vitesseAvant = vitesseAvant;
        this.vitesseDroite = vitesseDroite;

        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
    }
       
    public void initialize() 
    {
        System.out.println("CommandeDiagonaleSelonDuree.initialize()");
        this.roues = Robot.getInstance().roues;
        this.detecteur.initialiser();
    }

    public void execute() {
        this.detecteur.mesurer();
        this.roues.conduireAvecAxes(vitesseAvant, vitesseDroite, 0);
    }
    
    public boolean isFinished() 
    {
        return this.detecteur.estTropLongue();
    }

    public void end(boolean interrupted) {
        System.out.println("CommandeDiagonaleSelonDuree.end()");
        this.roues.conduireAvecAxes(0, 0, 0);
    }
}

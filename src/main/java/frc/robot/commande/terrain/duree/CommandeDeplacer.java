package frc.robot.commande.terrain.duree;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeDeplacer extends Command {

    protected Roues roues = null;
    protected LimiteurDuree detecteur;

    protected double vitesseAvantArriere = 0;
    protected double vitesseDroiteGauche = 0;
    protected double vitesseRotationDroiteGauche = 0;

    /**
     * 
     * @param temps temps en milisecondes
     * @param vitesseAvantArriere vitesse de deplacement, 1 pour avancer vers l'avant a la vitesse maximale, -1 pour reculer vers l'arriere a la vitesse maximale
     * @param vitesseDroiteGauche vitesse de deplacement, 1 pour se deplacer ver la droite a la vitesse maximale, -1 pour se deplacer vers la gauche a la vitesse maximale
     * @param vitesseRotationDroiteGauche vitesse de deplacement, 1 pour tourner a droite a la vitesse maximale, -1 pour reculer tourner a gauche a la vitesse maximale
     */
    public CommandeDeplacer(double tempsMs, double vitesseAvantArriere, double vitesseDroiteGauche, double vitesseRotationDroiteGauche)
    {
        //System.out.println("new CommandeDeplacer()");
        this.detecteur = new LimiteurDuree(tempsMs);
        this.vitesseAvantArriere = vitesseAvantArriere;
        this.vitesseDroiteGauche = vitesseDroiteGauche;
        this.vitesseRotationDroiteGauche = vitesseRotationDroiteGauche;

        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
    }
       
    public void initialize() 
    {
        System.out.println("CommandeAvancer.initialize()");
        this.roues = Robot.getInstance().roues;
        // this.roues.avancer(10);
        this.detecteur.initialiser();
		//pid.setSetpoint(LecteurAccelerometre.getInstance().accelerometre.getRawGyroZ);
		//	pid.enable();
        
    }

    public void execute() {
        this.detecteur.mesurer();
        this.roues.conduireAvecAxes(vitesseAvantArriere, vitesseDroiteGauche, vitesseRotationDroiteGauche);
    }
    
    public boolean isFinished() 
    {
        return this.detecteur.estTropLongue();
    }

    public void end(boolean interrupted) {
        System.out.println("CommandeAvancer.end()");
        this.roues.conduireAvecAxes(0, 0, 0);
    }
}

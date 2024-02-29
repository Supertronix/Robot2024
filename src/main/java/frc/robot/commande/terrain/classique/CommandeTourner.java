package frc.robot.commande.terrain.classique;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.LecteurAccelerometre;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeTourner extends Command {

    private static final int TEMPS_MAXIMUM = 3000;

    protected Roues roues;
    protected boolean finie;
    protected LimiteurDuree detecteur;
    protected LecteurAccelerometre gyroscope;

    protected PIDController pid;

    protected double angleCible; // angle en degre ???
    protected double angleActuel;

    public CommandeTourner(double angleCible)
    {
        //System.out.println("new CommandeTourner()");
        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);
        this.gyroscope = LecteurAccelerometre.getInstance();
        this.pid = new PIDController(0.001, 0.00, 0.00);

        this.angleCible = angleCible;
        this.angleActuel = gyroscope.getYaw();
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeTourner.initialize()");
        this.roues = Robot.getInstance().roues;
        //this.roues.avancer(pas);
        this.detecteur.initialiser();
        this.finie = false;
        pid.reset();
    }

    @Override
    public void execute() {
        //System.out.println("CommandeTourner.execute()");
        this.angleActuel = gyroscope.getYaw();
        double vitesseRotation = pid.calculate(this.angleActuel, this.angleCible);

        if (this.angleActuel > this.angleCible)
            this.roues.tournerGauche(vitesseRotation);
        else
            this.roues.tournerDroite(vitesseRotation);

        System.out.println("Yaw gyro: " + gyroscope.getYaw() + " - Yaw target: " + this.angleCible);
        this.detecteur.mesurer();
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        boolean seuilAngleAtteint = Math.abs(this.angleActuel - this.angleCible) < 5.0;

        if (seuilAngleAtteint)
            return true;

        if(this.detecteur.estTropLongue())
            return true;
        
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvancer.end()");
        this.roues.arreter();
    }
}

package frc.robot.commande.terrain.classique;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.LecteurAccelerometre;
import frc.robot.interaction.ShuffleBoard;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeTourner extends Command {

    protected static final int TEMPS_MAXIMUM = 10000; // En millisecondes
    protected static final double SEUIL_ANGLE = 2.5; // En degrés

    protected Roues roues;
    protected boolean finie;
    protected LimiteurDuree detecteur;
    protected LecteurAccelerometre gyroscope;

    protected PIDController pid;
    protected ProfiledPIDController pidTest;

    protected double angleActuel; // Angle actuel du robot
    protected double angleCible; // Angle en degré, relatif à l'angle initial

    public CommandeTourner(double angleCible)
    {
        //System.out.println("new CommandeTourner()");
        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);
        this.gyroscope = LecteurAccelerometre.getInstance();
        this.pid = new PIDController(0.004, 0.00025, 0.00);
        this.pidTest = new ProfiledPIDController(0.1, 0, 0, new TrapezoidProfile.Constraints(10, 5));
        SmartDashboard.putData("PID Rotation", this.pid);

        this.angleActuel = gyroscope.getYaw();

        this.angleCible = this.angleActuel + angleCible;
        // On remappe l'angle entre -180/180 degrés
        if (this.angleCible > 180.0)
            this.angleCible -= 360.0;
        else if (this.angleCible < -180.0)
            this.angleCible += 360.0;
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
        pidTest.reset(gyroscope.getYaw());
        this.pidTest.enableContinuousInput(0, 360);
    }

    @Override
    public void execute() {
        this.angleActuel = gyroscope.getYaw();
        double differenceAngle = this.angleActuel - this.angleCible;
        double differenceAngleAbsolue = Math.abs(differenceAngle);
        double vitesseRotation = pid.calculate(0, differenceAngleAbsolue);

        double vitesseRotationTest = pidTest.calculate(gyroscope.getYaw(), angleCible);
        this.roues.tournerDroite(vitesseRotation);

        // Pour déterminer si gauche/droite plus rapide pour atteindre la cible
        differenceAngle = (differenceAngle + 180) % 360 - 180;
        if (differenceAngle > 0.0)
            this.roues.tournerGauche(vitesseRotation);
        else
            this.roues.tournerDroite(vitesseRotation);

        this.detecteur.mesurer();

        SmartDashboard.putNumber("Vitesse rotation PID", vitesseRotation);
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        boolean seuilAngleAtteint = Math.abs(this.angleActuel - this.angleCible) < SEUIL_ANGLE;

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

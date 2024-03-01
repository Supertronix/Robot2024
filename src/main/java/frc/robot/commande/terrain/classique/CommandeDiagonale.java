package frc.robot.commande.terrain.classique;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.Odometrie;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeDiagonale extends Command {

    private static final int TEMPS_MAXIMUM = 3000;

    protected Roues roues = null;
    protected boolean finie = false;
    protected LimiteurDuree detecteur;
    protected double targetEncodeurPosition;
    protected Odometrie odometrie;
    protected Pose2d nouvellePosition;

    protected PIDController pid;
    protected RelativeEncoder encodeurAvantDroit;

    protected double distance;
    protected double angle;

    public CommandeDiagonale(double distance, double angle)
    {
        //System.out.println("new CommandeDiagonale()");
        this.distance = distance;
        this.angle = angle;

        this.roues = Robot.getInstance().roues;
        this.odometrie = Odometrie.getInstance();
        
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);
        // this.pid = new PIDController(0.015, 0.0000001, 0); // distance 100
        this.pid = new PIDController(0.015, 0.0000001, 0);
        this.encodeurAvantDroit = this.roues.encodeurAvantDroit;
        this.targetEncodeurPosition = this.roues.encodeurAvantDroit.getPosition() + distance;

        
    }
       
    public void initialize() 
    {
        System.out.println("CommandeDiagonale.initialize()");
        this.roues = Robot.getInstance().roues;
        this.detecteur.initialiser();
        this.finie = false;
        pid.reset();
        
    }

    double positionTemporaire = 0;
    double positionCalculee = 0;    
    public void execute() {
        this.detecteur.mesurer();
        this.odometrie.actualiser();

        if(this.angle > 0 && this.angle < 90)
        {
            this.roues.roueAvantGauche.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
            this.roues.roueArriereDroite.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));

            this.positionTemporaire = encodeurAvantDroit.getPosition();
            if(this.angle > 45)
            {
                double positionCalculee = this.positionTemporaire * (1-(this.angle / 45.0) );
                this.roues.roueAvantDroite.set(pid.calculate(positionCalculee, 1));
                this.roues.roueArriereGauche.set(pid.calculate(positionCalculee, 1));
            }
            else
            {   
                // algo est generique ok - enlever ce petit if-else
                double positionCalculee = this.positionTemporaire * (1-(this.angle / 45.0) );
                this.roues.roueAvantDroite.set(pid.calculate(positionCalculee, 1));
                this.roues.roueArriereGauche.set(pid.calculate(positionCalculee, 1));                
            }
        }
        if((this.angle < 0 && this.angle > -90) || (this.angle > 270 && this.angle < 360))
        {
            this.roues.roueAvantDroite.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
            this.roues.roueArriereGauche.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));

            this.roues.roueAvantGauche.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
            this.roues.roueArriereDroite.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
        }
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if (this.detecteur.estTropLongue()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeDiagonale.end()");
    }
}

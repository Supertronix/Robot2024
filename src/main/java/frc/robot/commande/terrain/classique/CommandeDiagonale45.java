package frc.robot.commande.terrain.classique;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.Odometrie;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeDiagonale45 extends Command {

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
    protected boolean coteDroit;

    public CommandeDiagonale45(double distance, boolean coteDroit)
    {
        //System.out.println("new CommandeDiagonale45()");
        this.coteDroit = coteDroit;
        this.distance = distance;
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
        System.out.println("CommandeDiagonale45.initialize()");
        this.roues = Robot.getInstance().roues;
        this.detecteur.initialiser();
        this.finie = false;
        pid.reset();
        
    }

    public void execute() {
        this.detecteur.mesurer();
        this.odometrie.actualiser();
        
        if(this.coteDroit)
        {
            this.roues.roueAvantGauche.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
            this.roues.roueArriereDroite.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
        }
        else
        {
            this.roues.roueAvantDroite.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
            this.roues.roueArriereGauche.set(pid.calculate(encodeurAvantDroit.getPosition(), 1));
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
        System.out.println("CommandeDiagonale45.end()");
    }
}

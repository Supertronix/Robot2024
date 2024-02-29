package frc.robot.commande.terrain.classique;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.Odometrie;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeTasserDroite extends Command {

    protected static final int TEMPS_MAXIMUM = 3000;
    protected static final int SEUIL_ENCODEUR = 1;

    protected Roues roues = null;
    protected boolean finie = false;
    protected LimiteurDuree detecteur;
    protected double targetEncodeurPosition;
    Odometrie odometrie;
    Pose2d nouvellePosition;

    protected PIDController pid;
    protected RelativeEncoder encodeurAvantDroit;

    public CommandeTasserDroite(double distance)
    {
        this.roues = Robot.getInstance().roues;
        this.odometrie = Odometrie.getInstance();
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);

        this.pid = new PIDController(0.005, 0.00, 0);
        this.encodeurAvantDroit = this.roues.encodeurAvantDroit;
        this.targetEncodeurPosition = this.roues.encodeurAvantDroit.getPosition() + distance;        
    }
       
    public void initialize() 
    {
        System.out.println("CommandeTasserDroite.initialize()");
        this.roues = Robot.getInstance().roues;
        this.detecteur.initialiser();
        this.finie = false;
        pid.reset();
    }

    public void execute() {
        System.out.println("CommandeTasserDroite.execute()");
        this.detecteur.mesurer();
        this.odometrie.actualiser();
        this.roues.tasserDroite(pid.calculate(this.encodeurAvantDroit.getPosition(), this.targetEncodeurPosition));
        System.out.println(encodeurAvantDroit.getPosition());
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        boolean seuilEncodeurAtteint = Math.abs(this.targetEncodeurPosition - this.encodeurAvantDroit.getPosition()) < SEUIL_ENCODEUR;

        if (seuilEncodeurAtteint)
            return true;

        if (this.detecteur.estTropLongue())
            return true;
        
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeTasserDroite.end()");
    }
}

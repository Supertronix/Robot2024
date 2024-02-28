package frc.robot.commande.terrain.classique;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.LecteurAccelerometre;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeAvancer extends Command {

    private static final int TEMPS_MAXIMUM = 3000;

    protected Roues roues = null;
    protected boolean finie = false;
    protected LimiteurDuree detecteur;
    protected double centimetres;

    protected PIDController pid;
    protected RelativeEncoder unEncodeur;

    public CommandeAvancer(double centimetres)
    {
        //System.out.println("new CommandeAvancer()");
        this.centimetres = centimetres;
        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);
        this.pid = new PIDController(0,0,0);
        this.unEncodeur = this.roues.encodeurAvantDroit;
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvancer.initialize()");
        this.roues = Robot.getInstance().roues;
        this.roues.avancer(10);
        this.detecteur.initialiser();
        this.finie = false;
        pid.reset();
		//pid.setSetpoint(LecteurAccelerometre.getInstance().accelerometre.getRawGyroZ);
		//	pid.enable();
        
    }
    @Override
    public void execute() {
        System.out.println("CommandeAvancer.execute()");
        this.detecteur.mesurer();
        this.roues.roueAvantGauche.set(pid.calculate(unEncodeur.getPosition(), 1));
        this.roues.roueAvantDroite.set(pid.calculate(unEncodeur.getPosition(), 1));
        this.roues.roueArriereGauche.set(pid.calculate(unEncodeur.getPosition(), 1));
        this.roues.roueArriereDroite.set(pid.calculate(unEncodeur.getPosition(), 1));

    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        //if(this.estArrivee() || this.detecteur.estTropLongue())
        return true;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvancer.end()");
    }
}

package frc.robot.commande.terrain.classique;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeAvancerNombreDePas extends Command {

    private static final int TEMPS_MAXIMUM = 3000;
    protected Roues roues = null;
    protected boolean finie = false;
    protected LimiteurDuree detecteur;
    protected double pas;

    public CommandeAvancerNombreDePas(int pas)
    {
        System.out.println("new CommandeAvancerNombreDePas()");
        this.pas = pas;
        this.roues = Robot.getInstance().roues;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(TEMPS_MAXIMUM);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvancerNombreDePas.initialize()");
        this.roues = Robot.getInstance().roues;
        this.roues.avancer(pas);
        this.detecteur.initialiser();
        this.finie = false;
    }
    @Override
    public void execute() {
        System.out.println("CommandeAvancerNombreDePas.execute()");
        this.detecteur.mesurer();
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        //if(this.estArrive() || this.detecteur.estTropLongue())
        return true;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvancerNombreDePas.end()");
    }
}

package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.mesure.DetecteurDuree;
import frc.robot.soussysteme.LanceurExtension;

public class CommandeLanceurDeployer extends Command {
    protected LanceurExtension lanceurAngle;
    protected static final int DUREE = 10000;
    protected DetecteurDuree detecteurDuree;

    //protected Manette manette;

    public CommandeLanceurDeployer()
    {
        System.out.println("new CommandeLanceurAngleAllonger()");
        this.lanceurAngle = Robot.getInstance().lanceurExtension;
        addRequirements(this.lanceurAngle);
        this.detecteurDuree = new DetecteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurAngleAllonger initialize()");
        lanceurAngle.deployer();
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        System.out.println("Capteur deploye " + lanceurAngle.estDeploye() );
        System.out.println("Capteur retracte " + lanceurAngle.estRetracte() );
        this.detecteurDuree.mesurer();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if(lanceurAngle.estDeploye()) return true;
        if(this.detecteurDuree.estTropLongue()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurAngleAllonger.end()");
    }
}

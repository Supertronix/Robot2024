package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.Manette;
import frc.robot.mesure.DetecteurDuree;
import frc.robot.soussysteme.Bras;
import frc.robot.soussysteme.LanceurAngle;

public class CommandeLanceurRetracter extends Command {
    protected LanceurAngle lanceurAngle;
    //protected Manette manette;
    protected DetecteurDuree detecteurDuree;
    protected static final int DUREE = 10000;

    public CommandeLanceurRetracter()
    {
        System.out.println("new CommandeLanceurAngleRaccourcir()");
        this.lanceurAngle = Robot.getInstance().lanceurAngle;
        addRequirements(this.lanceurAngle);
        this.detecteurDuree = new DetecteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurAngleRaccourcir initialize()");
        lanceurAngle.retracter();
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
        if(lanceurAngle.estRetracte()) return true;
        if(this.detecteurDuree.estTropLongue()) return true;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurAngleRaccourcir.end()");
    }
}

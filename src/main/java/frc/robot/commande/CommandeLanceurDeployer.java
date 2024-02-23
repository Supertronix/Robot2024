package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.Bras;
import frc.robot.soussysteme.LanceurAngle;

public class CommandeLanceurDeployer extends Command {
    protected LanceurAngle lanceurAngle;
    
    //protected Manette manette;

    public CommandeLanceurDeployer()
    {
        System.out.println("new CommandeLanceurAngleAllonger()");
        this.lanceurAngle = Robot.getInstance().lanceurAngle;
        addRequirements(this.lanceurAngle);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurAngleAllonger initialize()");
        lanceurAngle.deployer();
    }

    @Override
    public void execute() {
        System.out.println("Capteur deploye " + lanceurAngle.estDeploye() );
        System.out.println("Capteur retracte " + lanceurAngle.estRetracte() );
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if(lanceurAngle.estDeploye()) return true;
        // t
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurAngleAllonger.end()");
    }
}

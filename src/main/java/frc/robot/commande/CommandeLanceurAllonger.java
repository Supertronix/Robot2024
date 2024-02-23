package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.LanceurExtension;

public class CommandeLanceurAllonger extends Command {
    protected LanceurExtension lanceurExtension;
    //protected Manette manette;

    public CommandeLanceurAllonger()
    {
        System.out.println("new CommandeLanceurAllonger()");
        this.lanceurExtension = Robot.getInstance().lanceurExtension;
        addRequirements(this.lanceurExtension);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeLanceurAllonger.initialize()");
        lanceurExtension.allonger();
    }

    @Override
    public void execute() {
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLanceurAllonger.end()");
    }
}

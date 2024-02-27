package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.Robot;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.soussysteme.ConvoyeurHaut;

public class CommandeLancerAmpli extends Command {
    
    public static int DUREE = 1000;
    
    protected LimiteurDuree detecteurDuree;
    protected ConvoyeurHaut convoyeurHaut;

    public CommandeLancerAmpli() {
        System.out.println("new CommandeLancerAmpli()");
        convoyeurHaut = Robot.getInstance().convoyeurHaut;
        detecteurDuree = new LimiteurDuree(DUREE);

        addRequirements(convoyeurHaut);
    }

    @Override
    public void initialize() 
    {
        System.out.println("CommandeLancerAmpli.initialize()");
        this.detecteurDuree.initialiser();
        convoyeurHaut.activer();
    }

    @Override
    public void execute() {
        detecteurDuree.mesurer();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        return detecteurDuree.estTropLongue();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeLancerAmpli.end()");
        convoyeurHaut.desactiver();
    }
}

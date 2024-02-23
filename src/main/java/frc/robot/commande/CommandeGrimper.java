package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.Bras;

public class CommandeGrimper extends Command { // CommandeBrasDescendre
    protected Bras bras;
    protected Manette manette;

    public CommandeGrimper()
    {
        System.out.println("new CommandeGrimper()");
        this.bras = Robot.getInstance().bras;
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeGrimper initialize()");
        bras.activer(true);
        //this.manette = RobotControleur.ActionManette.getInstance();
    }

    @Override
    public void execute() {}

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        //if (this.manette.getBoutonMaintenu(Materiel.Manette.BOUTON_RETOUR))
        //    return false;

        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeGrimper.end()");
        this.bras.desactiver();
    }
}

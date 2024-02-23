package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.Bras;

public class CommandeGrimpageRedescendre extends Command {
    protected Bras bras;
    protected Manette manette;

    public CommandeGrimpageRedescendre() // CommandeBrasMonter
    {
        System.out.println("new CommandeGrimpageRedescendre()");
        this.bras = Robot.getInstance().bras;
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeGrimpageRedescendre.initialize()");
        bras.activer(false);
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
        //if (this.manette.getBoutonMaintenu(Materiel.Manette.BOUTON_DEMARRER))
        //    return false;

        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeGrimpageRedescendre.end()");
        this.bras.desactiver();
    }
}

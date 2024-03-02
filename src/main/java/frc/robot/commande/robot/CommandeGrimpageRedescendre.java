package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.soussysteme.Bras;
//import frc.robot.RobotControleur;
//import frc.robot.interaction.Manette;

public class CommandeGrimpageRedescendre extends Command {
    protected Bras bras;
    //protected Manette manette;

    public CommandeGrimpageRedescendre() // CommandeBrasMonter
    {
        //System.out.println("new CommandeGrimpageRedescendre()");
        this.bras = Robot.getInstance().bras;
        this.addRequirements(bras);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeGrimpageRedescendre.initialize()");
        bras.activer(Bras.ACTION_MONTER);
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

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeGrimpageRedescendre.end()");
        this.bras.desactiver();
    }
}

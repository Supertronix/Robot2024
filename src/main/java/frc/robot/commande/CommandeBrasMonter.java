package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.Bras;

public class CommandeBrasMonter extends Command {
    protected Bras bras;
    protected Manette manette;

    public CommandeBrasMonter()
    {
        System.out.println("new CommandeBrasMonter()");
        this.bras = Robot.getInstance().bras;
        this.manette = RobotControleur.ActionManette.getInstance();
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeBrasMonter initialize()");
        bras.activer(false);
    }

    @Override
    public void execute() {}

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if (this.manette.getBoutonMaintenu(Materiel.Manette.BOUTON_DEMARRER))
            return false;

        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeBrasMonter.end()");
        this.bras.desactiver();
    }
}

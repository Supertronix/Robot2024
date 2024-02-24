package frc.robot.commande;

import frc.robot.Materiel;
import frc.robot.RobotControleur;
import frc.robot.interaction.Manette;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvalerTeleop extends CommandeAvaler {
    protected RobotControleur.ActionManette manette;

    public CommandeAvalerTeleop()
    {
        super();
        System.out.println("new CommandeAvalerTeleop()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvalerTeleop.initialize()");
        super.initialize();
        this.manette = RobotControleur.ActionManette.getInstance();
    }
    @Override
    public void execute() {
        //System.out.println("CommandeAvalerTeleop.execute()");
        super.execute();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if (this.capteurLuminosite.getLuminosite())
            return true;

        //if (this.manette.getBoutonMaintenu(Materiel.Manette.BOUTON_A))
        //    return false;

        return true;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvalerTeleop.end()");
    }
}
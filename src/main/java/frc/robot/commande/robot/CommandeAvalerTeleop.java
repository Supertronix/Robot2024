package frc.robot.commande.robot;

import frc.robot.RobotControleur;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvalerTeleop extends CommandeAvaler {
    protected RobotControleur.ManetteAction manette;

    public CommandeAvalerTeleop()
    {
        super();
        //System.out.println("new CommandeAvalerTeleop()");
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvalerTeleop.initialize()");
        super.initialize();
        //this.manette = RobotControleur.ActionManette.getInstance();
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
        //if(super.isFinished()) return true; // detecte la note
        if(this.detecteurNote.detecteNote()) return true;

        // condition geree par le whileTrue d'attachement de la commande
        //if (this.manette.getBoutonMaintenu(Materiel.Manette.BOUTON_A))
        //    return false;

        return false;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvalerTeleop.end()");
        super.end(interrupted);
    }
}
package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.Intake;
import frc.robot.soussysteme.ConvoyeurBas;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.DetecteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvalerAutonomous extends CommandeAvaler {
    protected static final int DUREE = 10000;
    protected DetecteurDuree detecteurDuree;

    public CommandeAvalerAutonomous()
    {
        super();
        System.out.println("new CommandeAvalerAutonomous()");
        this.detecteurDuree = new DetecteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvalerAutonomous.initialize()");
        super.initialize();
        
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("CommandeAvalerAutonomous.execute()");
        this.detecteurDuree.mesurer();
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if (this.detecteurDuree.estTropLongue())
            return true;

        if (this.capteurLuminosite.getLuminosite())
            return true;

        return false;
    }
}

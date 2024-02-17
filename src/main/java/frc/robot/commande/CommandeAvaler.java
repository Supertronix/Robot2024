package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.soussysteme.Intake;
import frc.robot.soussysteme.ConvoyeurBas;
import frc.robot.soussysteme.ConvoyeurHaut;
import frc.robot.mesure.DetecteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvaler extends Command {

    protected static int DUREE = 5000;

    protected boolean finie = false;
    protected DetecteurDuree detecteurDuree;
    protected CapteurLuminosite capteurLuminosite;

    protected Intake intake = null;
    protected ConvoyeurBas convoyeurBas = null;
    protected ConvoyeurHaut convoyeurHaut = null;
    //protected double pas;

    public CommandeAvaler()
    {
        System.out.println("new CommandeAvaler()");
        this.addRequirements(this.intake);
        this.addRequirements(this.convoyeurBas);
        this.addRequirements(this.convoyeurHaut);
        this.detecteurDuree = new DetecteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvancer.initialize()");
        this.intake = Robot.getInstance().intake;
        this.convoyeurBas = Robot.getInstance().convoyeurBas;
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        this.capteurLuminosite = Robot.getInstance().capteurLuminosite;

        this.finie = false;
        this.detecteurDuree.initialiser();
        
        this.intake.activer();
        this.convoyeurBas.activer();
        this.convoyeurHaut.activer();
    }
    @Override
    public void execute() {
        System.out.println("CommandeAvancer.execute()");
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

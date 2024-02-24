package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.soussysteme.Avaleur;
import frc.robot.soussysteme.ConvoyeurBas;
import frc.robot.soussysteme.ConvoyeurHaut;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public abstract class CommandeAvaler extends Command {
    protected Avaleur intake;
    protected ConvoyeurBas convoyeurBas;
    protected ConvoyeurHaut convoyeurHaut;
    protected CapteurLuminosite capteurLuminosite;

    public CommandeAvaler()
    {
        System.out.println("new CommandeAvaler()");

        this.intake = Robot.getInstance().intake;
        this.convoyeurBas = Robot.getInstance().convoyeurBas;
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        this.capteurLuminosite = Robot.getInstance().capteurLuminosite;

        this.addRequirements(this.intake);
        this.addRequirements(this.convoyeurBas);
        this.addRequirements(this.convoyeurHaut);
    }
    
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvaler.initialize()");
        
        this.intake.activer(1);
        this.convoyeurBas.activer(1);
        this.convoyeurHaut.activer(0.2);
    }

    @Override
    public void execute() {}
    
    /** 
     * @return boolean
     */
    @Override
    public abstract boolean isFinished();

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvaler.end()");
        this.intake.desactiver();
        this.convoyeurBas.desactiver();
        this.convoyeurHaut.desactiver();
    }
}

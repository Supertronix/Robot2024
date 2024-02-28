package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.DetecteurNote;
import frc.robot.soussysteme.Avaleur;
import frc.robot.soussysteme.ConvoyeurBas;
import frc.robot.soussysteme.ConvoyeurHaut;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public abstract class CommandeAvaler extends Command {
    protected Avaleur avaleur;
    protected ConvoyeurBas convoyeurBas;
    protected ConvoyeurHaut convoyeurHaut;
    protected DetecteurNote detecteurNote;

    public CommandeAvaler()
    {
        //System.out.println("new CommandeAvaler()");

        this.avaleur = Robot.getInstance().avaleur;
        this.convoyeurBas = Robot.getInstance().convoyeurBas;
        this.convoyeurHaut = Robot.getInstance().convoyeurHaut;
        this.detecteurNote = Robot.getInstance().detecteurNote;

        this.addRequirements(this.avaleur);
        this.addRequirements(this.convoyeurBas);
        this.addRequirements(this.convoyeurHaut);
    }
    
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvaler.initialize()");
        
        this.avaleur.activer(1);
        this.convoyeurBas.activer(1);
        this.convoyeurHaut.activer(0.2);

        if (Robot.getInstance().estVoyant()) {
            Robot.getInstance().cameraConducteur.estAvaleurActif = true;
        }
    }

    @Override
    public void execute() {}
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished(){
        return false;
    };

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvaler.end()");
        this.avaleur.desactiver();
        this.convoyeurBas.desactiver();
        this.convoyeurHaut.desactiver();

        if (Robot.getInstance().estVoyant()) {
            Robot.getInstance().cameraConducteur.estAvaleurActif = false;
        }
    }
}

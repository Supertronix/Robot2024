package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.DetecteurChaine;
import frc.robot.soussysteme.Bras;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeMaintenirChaine extends Command {
    protected Bras bras;
    protected DetecteurChaine detecteurChaine;
    protected boolean annuler = false;

    public CommandeMaintenirChaine()
    {
        System.out.println("new CommandeAvaler()");

        this.bras = Robot.getInstance().bras;

        this.addRequirements(this.bras);
    }
    
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvaler.initialize()");
        
        if (!detecteurChaine.detecteChaine()) {
            this.annuler = true;
        }
    }

    @Override
    public void execute() {
        if (!detecteurChaine.detecteChaine()) {
            bras.activerSelonVitesse(Bras.ACTION_DESCENDRE, 1);
        } else {

            bras.activerSelonVitesse(Bras.ACTION_DESCENDRE, 0.001); // A TESTER
        }
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished(){
        return this.annuler;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvaler.end()");
        this.bras.desactiver();
    }
}

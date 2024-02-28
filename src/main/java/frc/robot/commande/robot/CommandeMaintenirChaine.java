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
        //System.out.println("new CommandeMaintenirChaine()");

        this.detecteurChaine = Robot.getInstance().detecteurChaine;
        this.bras = Robot.getInstance().bras;

        this.addRequirements(this.bras);
    }
    
    @Override
    public void initialize() 
    {
        System.out.println("CommandeMaintenirChaine.initialize()");
        
        //if (!detecteurChaine.detecteChaine()) {
        //    System.out.println("Limite switch non active, annulation");
        //    this.annuler = true;
        //}
    }

    @Override
    public void execute() {
        if (!detecteurChaine.detecteChaine()) {
            bras.activerSelonVitesse(Bras.ACTION_MONTER, 1);
            System.out.println("Correction de la hauteur");
        } else {
            bras.activerSelonVitesse(Bras.ACTION_MONTER, 0.001); // A TESTER
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
        System.out.println("CommandeMaintenirChaine.end()");
        this.bras.desactiver();
    }
}

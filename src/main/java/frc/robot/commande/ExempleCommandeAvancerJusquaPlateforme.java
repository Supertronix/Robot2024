package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.LecteurAccelerometre;
import frc.robot.soussysteme.RouesMecanumSynchro;
//import frc.robot.mesure.DetecteurDuree;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class ExempleCommandeAvancerJusquaPlateforme extends Command {

    protected RouesMecanumSynchro roues = null;
    protected LecteurAccelerometre lecteurEquilibre = null;
    //protected boolean finie = false;
    //protected DetecteurDuree detecteur;

    public ExempleCommandeAvancerJusquaPlateforme()
    {
        System.out.println("new CommandeAvancerJusquaPlateforme()");
        this.lecteurEquilibre = LecteurAccelerometre.getInstance();
        //this.roues = Robot.getInstance().roues;
        //this.addRequirements(this.roues);
        //this.detecteur = new DetecteurDuree(Cinematique.Machoire.TEMPS_MAXIMUM_OUVRIR);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvancerJusquaPlateforme.initialize()");
        this.roues = (RouesMecanumSynchro)Robot.getInstance().roues;
        //this.detecteur.initialiser();
        //this.finie = false;
    }
    @Override
    public void execute() {
        //System.out.println("CommandeAvancerJusquaPlateforme.execute()");
        //this.detecteur.mesurer();

        this.roues.reinitialiser();
        if(!lecteurEquilibre.depasseSeuilPente())
        {
            this.roues.avancerAvecVitesse(0.15);
        }
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        if(lecteurEquilibre.depasseSeuilPente()) 
        { 
            System.out.println("CommandeAvancerJusquaPlateforme.isFinished() == true");
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvancerJusquaPlateforme.end()");
    }

}

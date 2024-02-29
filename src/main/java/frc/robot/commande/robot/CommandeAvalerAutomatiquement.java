package frc.robot.commande.robot;

import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvalerAutomatiquement extends CommandeAvaler {
    protected static final int DUREE = 4000;
    protected LimiteurDuree detecteurDuree;

    public CommandeAvalerAutomatiquement()
    {
        super();
        //System.out.println("new CommandeAvalerAutomatiquement()");
        this.detecteurDuree = new LimiteurDuree(DUREE);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAvalerAutomatiquement.initialize()");
        super.initialize();
        
        this.detecteurDuree.initialiser();
    }

    @Override
    public void execute() {
        super.execute();
        //System.out.println("CommandeAvalerAutomatiquement.execute()");
        this.detecteurDuree.mesurer();
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        //if(super.isFinished()) return true; // detecte la note
        if(this.detecteurNote.detecteNote()) return true;

        if (this.detecteurDuree.estTropLongue())
            return true;

        return false;
    }
    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAvalerAutomatiquement.end()");
        super.end(interrupted);
    }
}

package frc.robot.commande;

import edu.wpi.first.wpilibj2.command.Command;
//import frc.robot.Cinematique;
import frc.robot.mesure.LimiteurDuree;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeDormir extends Command {

    protected LimiteurDuree detecteur;
    protected int delais;

    public CommandeDormir(int delais)
    {
        //System.out.println("new CommandeDormir()");
        this.delais = delais;
        this.detecteur = new LimiteurDuree(delais);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeDormir.initialize()");
        this.detecteur.initialiser();
    }
    @Override
    public void execute() {
        //System.out.println("CommandeDormir.execute()");
        this.detecteur.mesurer();
    }

    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        return this.detecteur.estTropLongue();
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeDormir.end()");
    }
}

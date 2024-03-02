package frc.robot.commande.terrain.classique;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandeReculer extends Command {

    protected double centimetres;

    public CommandeReculer(double centimetres)
    {
        //System.out.println("new CommandeReculer()");
        this.centimetres = centimetres;
    }
       
    @Override
    public void initialize() 
    {
        new CommandeAvancer(-centimetres, 3000).schedule();
    }
    @Override
    public void execute() {
        System.out.println("CommandeReculer.execute()");
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        return true;
    }
    @Override
    public void end(boolean interrupted) {
    }
}

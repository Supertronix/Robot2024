package frc.robot.commande.terrain;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotControleur;
import frc.robot.RobotControleur.ActionManette;
import frc.robot.interaction.Manette;
import frc.robot.soussysteme.RouesMecanum;

public class CommandeInverserRoues extends Command {
    public RouesMecanum roues;

    public CommandeInverserRoues() {
        //System.out.println("new CommandeInverserRoues()");
        this.roues = Robot.getInstance().roues;
    }

    @Override
    public void initialize() {
        System.out.println("CommandeInverserRoues().initialize()");
    }

    @Override
    public void execute() {
    } 

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeInverserRoues.end()");
    }
}

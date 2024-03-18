package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.CameraLimelight;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeEteindreLeds extends Command {
    protected CameraLimelight limelight;

    public CommandeEteindreLeds()
    {
        limelight = Robot.getInstance().cameraLimelight;
    }
    
    @Override
    public void initialize() 
    {
        System.out.println("CommandeClignoterLeds.initialize()");
        limelight.setModeLeds(1);
    }

    @Override
    public void execute() {
    }
    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished(){
        return true;
    };

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeClignoterLeds.end()");
    }
}

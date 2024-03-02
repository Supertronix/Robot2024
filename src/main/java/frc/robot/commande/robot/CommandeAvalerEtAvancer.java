package frc.robot.commande.robot;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import frc.robot.mesure.LimiteurDuree;

//import frc.robot.Cinematique;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// https://docs.wpilib.org/en/stable/docs/software/commandbased/commands.html
public class CommandeAvalerEtAvancer extends ParallelRaceGroup {
    protected static final int DUREE = 4000;
    protected LimiteurDuree detecteurDuree;

    public CommandeAvalerEtAvancer() {
        //System.out.println("new CommandeAvalerAutomatiquement()");
        this.detecteurDuree = new LimiteurDuree(DUREE);
    }
}

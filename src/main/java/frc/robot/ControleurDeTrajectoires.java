package frc.robot;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;

public class ControleurDeTrajectoires {

    private HolonomicDriveController driveController;
    private PIDController xControleur;
    private PIDController yControleur;
    private ProfiledPIDController angleControleur;
    public ControleurDeTrajectoires() {
        driveController = new HolonomicDriveController(xControleur, yControleur, angleControleur);
        // c'est un test, en réalité, les trajectoires seront défini en commandes
        // cette classe sera supprimée
    }
}

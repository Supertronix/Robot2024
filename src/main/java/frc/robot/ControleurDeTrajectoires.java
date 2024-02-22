package frc.robot;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;

public class ControleurDeTrajectoires {

    private HolonomicDriveController m_driveController;
    private PIDController xControleur;
    private PIDController yControleur;
    private ProfiledPIDController angleControleur;
    public ControleurDeTrajectoires() {
        m_driveController = new HolonomicDriveController(xControleur, yControleur, angleControleur);
    }
}

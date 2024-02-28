package frc.robot.commande.terrain;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.RobotControleur.ActionManette;
import frc.robot.interaction.CameraLimelight;
import frc.robot.interaction.Manette;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.RouesMecanum;

public class CommandeAllerASelonTriplePID extends Command {

    protected static final double SEUIL_DISTANCE = 0.25 * 0.25;
    protected static final double SEUIL_ANGLE = 2.0;
    protected static final int DUREE_TIMEOUT = 10000;

    // PID axe X
    protected static double x_kP = 0.2;
    protected static double x_kI = 0.00;
    protected static double x_kD = 0.00;

    // PID axe Y
    protected static double y_kP = 0.2;
    protected static double y_kI = 0.00;
    protected static double y_kD = 0.00;

    // PID Angle
    protected static double ang_kP = 0.1;
    protected static double ang_kI = 0.00;
    protected static double ang_kD = 0.00;
    protected static double angMaxVitesse = 999.00; // m/s
    protected static double angMaxAcceleration = 999.00; // m2/s

    // Pour le calcul de déplacement des roues mecanum
    protected static double longueurDuCentre = 0.635;
    protected static double largeurDuCentre = 0.508;

    // Hardware
    protected RouesMecanum roues;
    protected CameraLimelight limelight;
    protected LimiteurDuree detecteur;
    protected Manette manette;

    // Contrôle PID
    protected PIDController xControleur;
    protected PIDController yControleur;
    protected ProfiledPIDController angleControleur;
    protected HolonomicDriveController driveControleur;
    protected MecanumDriveKinematics kinematics;

    // Etat de la commande
    protected double distance;
    protected boolean distanceAtteinte;
    protected boolean seuilAngleAtteint;
    protected Vecteur3 cible;
    protected double angleCible;
    protected double[] donneesPosition;

    public CommandeAllerASelonTriplePID(Vecteur3 cible, double angleCible)
    {
        //System.out.println("new CommandeAllerASelonTriplePID()");

        this.roues = (RouesMecanum) Robot.getInstance().roues;
        this.limelight = Robot.getInstance().cameraLimelight;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(DUREE_TIMEOUT);

        this.xControleur = new PIDController(x_kP, x_kI, x_kD);
        this.yControleur = new PIDController(y_kP, y_kI, y_kD);
        this.angleControleur = new ProfiledPIDController(ang_kP, ang_kI, ang_kD,
                new TrapezoidProfile.Constraints(6.28, 3.14));
        this.cible = cible;
        this.angleCible = angleCible;
    }

    @Override
    public void initialize()
    {
        System.out.println("CommandeAllerASelonTriplePID.initialize()");
        this.detecteur.initialiser();

        this.distanceAtteinte = false;
        this.seuilAngleAtteint = false;
    }

    @Override
    public void execute() {
        this.detecteur.mesurer();

        donneesPosition = this.limelight.getBotpose();

        if (donneesPosition[0] == 0 || donneesPosition[1] == 0)
            return;

        var pidOutputX = xControleur.calculate(donneesPosition[0], cible.x);        
        var pidOutputY = -yControleur.calculate(donneesPosition[1], cible.y);
        var pidOutputRot = -angleControleur.calculate(donneesPosition[5], angleCible);

        SmartDashboard.putNumber("Erreur Angle",donneesPosition[5] - angleCible);

        roues.conduireToutesDirections(
            pidOutputY - pidOutputX - pidOutputRot,
            pidOutputY + pidOutputX + pidOutputRot,
            pidOutputY + pidOutputX - pidOutputRot,
            pidOutputY - pidOutputX + pidOutputRot
        );

    }


    /**
     * @return boolean
     */
    @Override
    public boolean isFinished()
    {
        if (this.detecteur.estTropLongue()) {
            //System.out.println("CommandeAllerA.isFinished() detecteur.estTropLongue()");
            return true;
        }
        // Pas de données de tag valide cette frame
        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return false;
        
        // Cible atteinte
        if (this.distanceAtteinte && this.seuilAngleAtteint) {
            //System.out.println("CommandeAllerA.isFinished() distance < 0.5");
            return true;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAllerASelonTriplePID.end()");
        this.roues.arreter();
    }
}

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

public class CommandeAllerA extends Command {

    protected static final double SEUIL_DISTANCE = 0.50 * 0.50;
    protected static final double SEUIL_ANGLE = 5.0;
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
    protected static double ang_kP = 0.25;
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

    public CommandeAllerA(Vecteur3 cible, double angleCible)
    {
        System.out.println("new CommandeAllerA()");

        this.roues = (RouesMecanum) Robot.getInstance().roues;
        this.limelight = Robot.getInstance().cameraLimelight;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(DUREE_TIMEOUT);
        this.manette = ActionManette.getInstance();

        this.xControleur = new PIDController(x_kP, x_kI, x_kD);
        this.yControleur = new PIDController(y_kP, y_kI, y_kD);
        this.angleControleur = new ProfiledPIDController(ang_kP, ang_kI, ang_kD,
                new TrapezoidProfile.Constraints(angMaxVitesse, angMaxAcceleration));

        this.driveControleur = new HolonomicDriveController(this.xControleur, this.yControleur, this.angleControleur);
        
        this.cible = cible;
        this.angleCible = angleCible;

        SmartDashboard.putData("PID x", this.xControleur);
        SmartDashboard.putData("PID y", this.yControleur);
        SmartDashboard.putData("PID angle", this.angleControleur);
    }

    @Override
    public void initialize()
    {
        System.out.println("CommandeAllerA.initialize()");
        this.detecteur.initialiser();

        this.distanceAtteinte = false;
        this.seuilAngleAtteint = false;

        this.kinematics = new MecanumDriveKinematics(
            new Translation2d(-largeurDuCentre, longueurDuCentre),
            new Translation2d(largeurDuCentre, longueurDuCentre),
            new Translation2d(-largeurDuCentre, -longueurDuCentre),
            new Translation2d(largeurDuCentre, -longueurDuCentre)
        );
    }

    @Override
    public void execute() {
        this.detecteur.mesurer();

        double[] donneesPosition = this.limelight.getBotpose();

        if (donneesPosition[0] == 0 || donneesPosition[1] == 0)
            return;

        Pose2d position = new Pose2d(donneesPosition[0], donneesPosition[1], Rotation2d.fromDegrees(donneesPosition[5]));
        Pose2d cible = new Pose2d(this.cible.x + 1, this.cible.y, Rotation2d.fromDegrees(this.angleCible));

        // Calcul d'inverse kinematics pour déterminer les vitesses de roues
        ChassisSpeeds vitesseAjustee = this.driveControleur.calculate(position, cible, 0.2, Rotation2d.fromDegrees(this.angleCible));
        MecanumDriveWheelSpeeds vitesseRoues = this.kinematics.toWheelSpeeds(vitesseAjustee, new Translation2d(0, 0));

        // Déplacement x,y
        if (!this.distanceAtteinte) {
            //System.out.println("x diff: " + (donneesPosition[0] - this.cible.x));
            this.distance = Math.pow(donneesPosition[0] - this.cible.x, 2) + Math.pow(donneesPosition[1] - this.cible.y, 2);
            //System.out.println("Distance: " + distance);
            this.distanceAtteinte = this.distance < SEUIL_DISTANCE;

            //System.out.println("PID MOVEMENT RUNNING");
            this.roues.conduireToutesDirections(
                vitesseRoues.frontLeftMetersPerSecond, 
                vitesseRoues.frontRightMetersPerSecond,
                vitesseRoues.rearLeftMetersPerSecond,
                vitesseRoues.rearRightMetersPerSecond);
        }
        // Rotation du robot
        else if (!this.seuilAngleAtteint) {
            //System.out.println("PID ANGLE RUNNING");
            double differenceAngle = this.angleCible - donneesPosition[5];
            differenceAngle = (differenceAngle + 180) % 360 - 180;
            //System.out.println("Difference: " + differenceAngle + " - Position: " + donneesPosition[5]);
            this.seuilAngleAtteint = Math.abs(differenceAngle) < SEUIL_ANGLE;
            //System.out.println(Math.abs((donneesCible[5] - donneesPosition[5])));

            if (differenceAngle > 0.0) {
                System.out.println("TOURNE GAUCHE");
                this.roues.tournerGauche(Math.abs(vitesseAjustee.omegaRadiansPerSecond));
            }
            else {
                System.out.println("TOURNE DROITE");
                this.roues.tournerDroite(Math.abs(vitesseAjustee.omegaRadiansPerSecond));
            }
            System.out.println(vitesseAjustee.omegaRadiansPerSecond);
        }

        SmartDashboard.putNumber("vitesseAjustee.vxMetersPerSecond", vitesseAjustee.vxMetersPerSecond);
        SmartDashboard.putNumber("vitesseAjustee.vyMetersPerSecond", vitesseAjustee.vyMetersPerSecond);
        SmartDashboard.putNumber("vitesseAjustee.omegaDegreesPerSecond", vitesseAjustee.omegaRadiansPerSecond * 180 / Math.PI);
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

        double[] donneesPosition = this.limelight.getBotpose();

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
        System.out.println("CommandeAllerA.end()");
        this.roues.arreter();
    }
}

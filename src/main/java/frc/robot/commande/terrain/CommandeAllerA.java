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
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.RobotControleur.ActionManette;
import frc.robot.interaction.CameraLimelight;
import frc.robot.interaction.Manette;
import frc.robot.interaction.Odometrie;
import frc.robot.interaction.ShuffleBoard;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;
import frc.robot.soussysteme.RouesMecanum;

public class CommandeAllerA extends Command implements Materiel.Roues, AprilTags {

    protected Robot robot;
    protected ShuffleBoard shuffleBoard;
    protected static final double SEUIL_DISTANCE = 0.25 * 0.25;
    protected static final double SEUIL_ANGLE = 1.0;
    protected static final int DUREE_TIMEOUT = 10000;

    // PID axe X
    protected static double x_kP = 1;
    protected static double x_kI = 0.00;
    protected static double x_kD = 0.00;

    // PID axe Y
    protected static double y_kP = 1;
    protected static double y_kI = 0.00;
    protected static double y_kD = 0.00;

    // PID Angle
    protected static double ang_kP = 1;
    protected static double ang_kI = 0.00;
    protected static double ang_kD = 0.00;
    protected static double angMaxVitesse = 2.00; // m/s
    protected static double angMaxAcceleration = 2.00; // m2/s

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


    /* GPASLETEMPS
    public CommandeAllerA(AprilTags.Position position)
    {
        switch (position) {
            case SpeakerRouge:
                CommandeAllerA(getPositionProche(SpeakerRouge.POSITIONS);
        }
    }
     */

    public CommandeAllerA(Vecteur3 cible, double angleCible)
    {
        //System.out.println("new CommandeAllerA()");

        this.robot = Robot.getInstance();
        this.roues = (RouesMecanum) this.robot.roues;
        this.limelight = this.robot.cameraLimelight;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(DUREE_TIMEOUT);
        this.manette = ActionManette.getInstance();
        
        this.cible = cible;
        this.angleCible = angleCible;
    }

    @Override
    public void initialize()
    {
        System.out.println("CommandeAllerA.initialize()");

        this.detecteur.initialiser();
        this.distanceAtteinte = false;
        this.seuilAngleAtteint = false;

        this.xControleur = new PIDController(x_kP, x_kI, x_kD);
        this.yControleur = new PIDController(y_kP, y_kI, y_kD);
        this.angleControleur = new ProfiledPIDController(ang_kP, ang_kI, ang_kD,
                new TrapezoidProfile.Constraints(angMaxVitesse, angMaxAcceleration));

        this.angleControleur.enableContinuousInput(0, 360); // 0, Math.PI * 2 Radians ?
        //this.angleControleur.enableContinuousInput(0, Math.PI * 2);

        SmartDashboard.putData("PID x", this.xControleur);
        SmartDashboard.putData("PID y", this.yControleur);
        SmartDashboard.putData("PID angle", this.angleControleur);

        this.driveControleur = new HolonomicDriveController(this.xControleur, this.yControleur, this.angleControleur);
        Pose2d tolerance = new Pose2d(0.1, 0.1, Rotation2d.fromDegrees(5));
        this.driveControleur.setTolerance(tolerance);
        this.driveControleur.setEnabled(false);

        this.kinematics = Odometrie.getInstance().getCinematique();
        this.donneesPosition = new double[6];
    }

    @Override
    public void execute() {
        this.detecteur.mesurer();

        double[] donneesPositionInit = this.limelight.getBotpose();

        //System.out.println("x = " + donneesPosition[0] + " y = " + donneesPosition[1] + " angle = " + donneesPosition[5]);
        if (donneesPositionInit[0] == 0 || donneesPositionInit[1] == 0)
            return;
        donneesPosition[0] = donneesPositionInit[0];
        donneesPosition[1] = donneesPositionInit[1];
        donneesPosition[5] = donneesPositionInit[5];

        Pose2d position = new Pose2d(donneesPosition[0], donneesPosition[1], Rotation2d.fromDegrees(donneesPosition[5]));
        Pose2d cible = new Pose2d(this.cible.x, this.cible.y, Rotation2d.fromDegrees(this.angleCible));

        // Calcul d'inverse kinematics pour déterminer les vitesses de roues
        ChassisSpeeds vitesseAjustee = this.driveControleur.calculate(position, cible, 1, Rotation2d.fromDegrees(this.angleCible));
        // TODO : réactiver le PID
        MecanumDriveWheelSpeeds vitesseRoues = this.kinematics.toWheelSpeeds(vitesseAjustee);
        // TODO :
        vitesseRoues.desaturate(1.0); // On va le mettre en pourcentage de moteur

        this.roues.conduireToutesDirections(
                vitesseRoues.frontLeftMetersPerSecond,
                vitesseRoues.frontRightMetersPerSecond,
                vitesseRoues.rearLeftMetersPerSecond,
                vitesseRoues.rearRightMetersPerSecond);

        SmartDashboard.putNumber("Vitesse X (m/s)", vitesseAjustee.vxMetersPerSecond);
        SmartDashboard.putNumber("Vitesse Y (m/s)", vitesseAjustee.vyMetersPerSecond);
        SmartDashboard.putNumber("Vitesse angulaire (deg/s)", vitesseAjustee.omegaRadiansPerSecond * 180 / Math.PI);
        //arene.setRobotPose(position);
    }


    /**
     * @return boolean
     */
    @Override
    public boolean isFinished()
    {
        if (this.detecteur.estTropLongue()) {
            System.out.println("CommandeAllerA.isFinished() detecteur.estTropLongue()");
            return true;
        }

        double[] donneesPosition = this.limelight.getBotpose();

        // Pas de données de tag valide cette frame
        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return false;
        
        // Cible atteinte
        if (this.driveControleur.atReference()) {
            System.out.println("CommandeAllerA.isFinished() d < 0.2 : " + this.distance + " - a < 2 : " + (this.angleCible - donneesPosition[5]));
            return true;
        }

        //System.out.println("Distance: " + this.distance + " - Angle: " + (this.angleCible - donneesPosition[5]));

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("CommandeAllerA.end()");
        this.roues.arreter();
    }

    public Vecteur3 getPositionProche(Vecteur3[] positions, Vecteur3 positionActuelle)
    {
        Vecteur3 positionProche = positions[0];
        positionProche.y = 0; // J'ai juste utilisé y pour stocker l'angle
        double distanceMin = positionActuelle.distanceCarree(positions[0]);
        for (Vecteur3 position : positions) {
            double distance = positionActuelle.distanceCarree(position);
            if (distance < distanceMin) {
                distanceMin = distance;
                positionProche = position;
            }
        }
        return positionProche;
    }
}

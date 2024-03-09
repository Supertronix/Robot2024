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
import frc.robot.RobotControleur.ManetteAction;
import frc.robot.composant.Compresseur;
import frc.robot.interaction.CameraLimelight;
import frc.robot.interaction.Manette;
import frc.robot.interaction.Odometrie;
import frc.robot.interaction.ShuffleBoard;
import frc.robot.mesure.Chronometre;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;
import frc.robot.soussysteme.RouesMecanum;

public class CommandeAllerA extends Command implements Materiel.Roues, AprilTags {

    protected Robot robot;
    protected ShuffleBoard shuffleBoard;
    protected static final double SEUIL_DISTANCE = 0.25 * 0.25;
    protected static final double SEUIL_ANGLE = 10.0;
    protected static final int DUREE_TIMEOUT = 4000;
    protected static final int DUREE_TAG_PERDU = 2000;

    // PID axe X
    protected static double x_kP = 1;
    protected static double x_kI = 0.6;
    protected static double x_kD = 0.5;

    // PID axe Y
    protected static double y_kP = 1.15;
    protected static double y_kI = 0.6;
    protected static double y_kD = 0.3;

    // PID Angle
    protected static double ang_kP = 5;
    protected static double ang_kI = 0.1;
    protected static double ang_kD = 0.5;
    protected static double angMaxVitesse = 10.00; // m/s
    protected static double angMaxAcceleration = 5.00; // m2/s

    // Hardware
    protected RouesMecanum roues;
    protected CameraLimelight limelight;
    protected LimiteurDuree detecteur;
    protected Chronometre chronometre;
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


    public CommandeAllerA(Vecteur3 cible, double angleCible)
    {
        System.out.println("new CommandeAllerA(" + cible + ", " + angleCible + ")");

        this.robot = Robot.getInstance();
        Compresseur.getInstance().desactiver();
        this.roues = (RouesMecanum) this.robot.roues;
        this.limelight = this.robot.cameraLimelight;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(DUREE_TIMEOUT);
        //this.chronometre = new Chronometre();
        this.manette = ManetteAction.getInstance();
        
        this.cible = cible;
        this.angleCible = angleCible;
    }

    @Override
    public void initialize()
    {
        System.out.println("CommandeAllerA.initialize()");
        //this.chronometre.initialiser();

        this.distanceAtteinte = false;
        this.seuilAngleAtteint = false;

        this.xControleur = new PIDController(x_kP, x_kI, x_kD);
        this.yControleur = new PIDController(y_kP, y_kI, y_kD);
        this.angleControleur = new ProfiledPIDController(ang_kP, ang_kI, ang_kD,
                new TrapezoidProfile.Constraints(angMaxVitesse, angMaxAcceleration));

        this.angleControleur.enableContinuousInput(0, 360); // 0, Math.PI * 2 Radians ?
        //this.angleControleur.enableContinuousInput(0, Math.PI * 2);

        //SmartDashboard.putData("PID x", this.xControleur);
        //SmartDashboard.putData("PID y", this.yControleur);
        //SmartDashboard.putData("PID angle", this.angleControleur);

        this.driveControleur = new HolonomicDriveController(this.xControleur, this.yControleur, this.angleControleur);
        Pose2d tolerance = new Pose2d(0.13, 0.40, Rotation2d.fromDegrees(SEUIL_ANGLE));
        this.driveControleur.setTolerance(tolerance);
        this.driveControleur.setEnabled(true);

        //this.kinematics = Odometrie.getInstance().getCinematique();
        this.kinematics = new MecanumDriveKinematics(    // Trop de temps pour initialiser Odometrie
                new Translation2d(-LARGEUR_DU_CENTRE, LONGUEUR_DU_CENTRE),
                new Translation2d(LARGEUR_DU_CENTRE, LONGUEUR_DU_CENTRE),
                new Translation2d(-LARGEUR_DU_CENTRE, -LONGUEUR_DU_CENTRE),
                new Translation2d(LARGEUR_DU_CENTRE, -LONGUEUR_DU_CENTRE)
        );
        this.donneesPosition = new double[6];
    }

    @Override
    public void execute() {
        this.detecteur.mesurer();

        double[] donneesPositionInit = this.limelight.getBotpose();

        //System.out.println("x = " + donneesPosition[0] + " y = " + donneesPosition[1] + " angle = " + donneesPosition[5]);
        if (donneesPositionInit[0] == 0 || donneesPositionInit[1] == 0)
            return;

        //this.chronometre.initialiser();

        donneesPosition[0] = donneesPositionInit[0];
        donneesPosition[1] = donneesPositionInit[1];
        donneesPosition[5] = donneesPositionInit[5];

        Pose2d position = new Pose2d(donneesPosition[0], donneesPosition[1], Rotation2d.fromDegrees(donneesPosition[5]));
        Pose2d cible = new Pose2d(this.cible.x, this.cible.y, Rotation2d.fromDegrees(this.angleCible));
        Vecteur3 dist = new Vecteur3(donneesPosition[0], donneesPosition[1], 0);
        this.distance = dist.distanceCarree(this.cible);

        // Calcul d'inverse kinematics pour déterminer les vitesses de roues
        ChassisSpeeds vitesseAjustee = this.driveControleur.calculate(position, cible, 1, Rotation2d.fromDegrees(this.angleCible));
        MecanumDriveWheelSpeeds vitesseRoues = this.kinematics.toWheelSpeeds(vitesseAjustee);
        vitesseRoues.desaturate(1); // On va le mettre en pourcentage de moteur

        this.roues.conduireToutesDirections(
                vitesseRoues.frontLeftMetersPerSecond,
                vitesseRoues.frontRightMetersPerSecond,
                vitesseRoues.rearLeftMetersPerSecond,
                vitesseRoues.rearRightMetersPerSecond);

        //SmartDashboard.putNumber("Vitesse X (m/s)", vitesseAjustee.vxMetersPerSecond);
        //SmartDashboard.putNumber("Vitesse Y (m/s)", vitesseAjustee.vyMetersPerSecond);
        //SmartDashboard.putNumber("Vitesse angulaire (deg/s)", vitesseAjustee.omegaRadiansPerSecond * 180 / Math.PI);
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

        //if (DUREE_TAG_PERDU < this.chronometre.getDureePreMesuree()) {
        //    return true;
        //}

        double[] donneesPosition = this.limelight.getBotpose();

        // Pas de données de tag valide cette frame
        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return false;
        
        // Cible atteinte
        //System.out.println("Distance : " + this.distance + " - Angle : " + (this.angleCible - donneesPosition[5]));
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
        Compresseur.getInstance().activer();
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

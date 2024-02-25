package frc.robot.commande;

import edu.wpi.first.math.controller.HolonomicDriveController;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.interaction.CameraLimelight;
import frc.robot.interaction.ShuffleBoard;
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.RouesMecanum;

public class CommandeAllerA extends Command {
    // PID axe X
    protected static double x_kP = 0.05;
    protected static double x_kI = 0.00;
    protected static double x_kD = 0.00;

    // PID axe Y
    protected static double y_kP = 0.05;
    protected static double y_kI = 0.00;
    protected static double y_kD = 0.00;

    // PID Angle
    protected static double ang_kP = 0.05;
    protected static double ang_kI = 0.00;
    protected static double ang_kD = 0.00;
    protected static double angMaxVitesse = 999.00; // m/s
    protected static double angMaxAcceleration = 999.00; // m2/s

    // Pour le calcul de déplacement des roues mecanum
    protected static double longueurDuCentre = 0.38;
    protected static double largeurDuCentre = 0.43;

    protected RouesMecanum roues;
    protected LimiteurDuree detecteur;

    protected PIDController xControleur;
    protected PIDController yControleur;
    protected ProfiledPIDController angleControleur;
    protected HolonomicDriveController driveControleur;
    protected CameraLimelight limelight;

    protected MecanumDriveKinematics kinematics;

    public CommandeAllerA(Vecteur3 cible, double angle)
    {
        System.out.println("new CommandeAllerA()");

        this.roues = (RouesMecanum) Robot.getInstance().roues;
        this.limelight = Robot.getInstance().cameraLimelight;
        this.addRequirements(this.roues);
        this.detecteur = new LimiteurDuree(5000);

        this.xControleur = new PIDController(x_kP, x_kI, x_kD);
        this.yControleur = new PIDController(y_kP, y_kI, y_kD);
        this.angleControleur = new ProfiledPIDController(ang_kP, ang_kI, ang_kD,
                new TrapezoidProfile.Constraints(angMaxVitesse, angMaxAcceleration));

        this.driveControleur = new HolonomicDriveController(xControleur, yControleur, angleControleur);

        SmartDashboard.putData("PID x", xControleur);
        SmartDashboard.putData("PID y", yControleur);
        SmartDashboard.putData("PID angle", angleControleur);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAllerA.initialize()");
        this.detecteur.initialiser();
        kinematics = new MecanumDriveKinematics(new Translation2d(-largeurDuCentre, longueurDuCentre), new Translation2d(largeurDuCentre, longueurDuCentre), new Translation2d(-largeurDuCentre, -longueurDuCentre), new Translation2d(largeurDuCentre, -longueurDuCentre));
    }

    @Override
    public void execute() {
        //System.out.println("CommandeAllerA.execute()"); // commenter les logs d'execute en version finale

        double[] donneesPosition = limelight.getBotpose();
        double[] donneesCible = limelight.getTagPositionRelatifRobot();

        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return;

        Pose2d position = new Pose2d(donneesPosition[0], donneesPosition[1], Rotation2d.fromDegrees(donneesPosition[5]));
        Pose2d cible = new Pose2d(6.89, 1.42, Rotation2d.fromDegrees(donneesCible[5]));

        ChassisSpeeds vitesseAjustee = driveControleur.calculate(position, cible, 0.12, Rotation2d.fromDegrees(donneesCible[5]));
        MecanumDriveWheelSpeeds vitesseRoues = kinematics.toWheelSpeeds(vitesseAjustee, new Translation2d(0, 0));

        double distance = Math.pow(donneesPosition[0] - 6.89, 2) + Math.pow(donneesPosition[1] - 1.42, 2);
        
        if (distance < Math.pow(0.5, 2)) {
            if ((donneesCible[5] - donneesPosition[5]) > 0.0)
                roues.tournerGauche(vitesseAjustee.omegaRadiansPerSecond);
            else
                roues.tournerDroite(vitesseAjustee.omegaRadiansPerSecond);
        }
        else {
            roues.conduireToutesDirections(vitesseRoues.frontLeftMetersPerSecond, vitesseRoues.frontRightMetersPerSecond, vitesseRoues.rearLeftMetersPerSecond, vitesseRoues.rearRightMetersPerSecond);
        }
        
        /*
        if (vitesseAjustee.omegaRadiansPerSecond > 180.0)
            roues.tournerGauche(0.1);
        else
            roues.tournerDroite(0.1);*/

        

        SmartDashboard.putNumber("vitesseAjustee.vxMetersPerSecond", vitesseAjustee.vxMetersPerSecond);
        SmartDashboard.putNumber("vitesseAjustee.vyMetersPerSecond", vitesseAjustee.vyMetersPerSecond);
        SmartDashboard.putNumber("vitesseAjustee.omegaDegreesPerSecond", vitesseAjustee.omegaRadiansPerSecond*180/Math.PI);
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        double[] donneesPosition = limelight.getBotpose();
        double[] donneesCible = limelight.getTagPositionRelatifRobot();

        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return false;

        System.out.println("CommandeAllerA.isFinished() donneesPosition " + donneesPosition[0] + " " + donneesPosition[1]);
        
        double distance = Math.pow(donneesPosition[0] - 6.89, 2) + Math.pow(donneesPosition[1] - 1.42, 2);
        boolean seuilAngleAtteint = Math.abs((donneesCible[5] - donneesPosition[5])) < 5.0;
        System.out.println("CommandeAllerA.isFinished() distance " + distance);

        if (distance < Math.pow(0.5, 2) && seuilAngleAtteint) {
            System.out.println("CommandeAllerA.isFinished() distance < 0.5");
            return true;
        }

        if (this.detecteur.estTropLongue()) {
            System.out.println("CommandeAllerA.isFinished() detecteur.estTropLongue()");
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

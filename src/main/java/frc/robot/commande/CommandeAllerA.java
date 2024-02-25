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

    protected RouesMecanum roues;
    protected LimiteurDuree detecteur;

    protected PIDController xControleur;
    protected PIDController yControleur;
    protected ProfiledPIDController angleControleur;
    protected HolonomicDriveController driveControleur;
    protected CameraLimelight limelight;

    public CommandeAllerA(Vecteur3 cible, double angle)
    {
        System.out.println("new CommandeAllerA()");

        this.roues = (RouesMecanum) Robot.getInstance().roues;
        this.limelight = Robot.getInstance().cameraLimelight;

        this.addRequirements(this.roues);

        this.detecteur = new LimiteurDuree(5000);

        this.xControleur = new PIDController(0.1, 0, 0);
        this.yControleur = new PIDController(0.1, 0, 0);
        this.angleControleur = new ProfiledPIDController(0, 0, 0,
                new TrapezoidProfile.Constraints(6.28, 3.14));
        this.driveControleur = new HolonomicDriveController(xControleur, yControleur, angleControleur);
    }
       
    @Override
    public void initialize() 
    {
        System.out.println("CommandeAllerA.initialize()");
        this.detecteur.initialiser();
    }
    @Override
    public void execute() {
        //System.out.println("CommandeAllerA.execute()"); // commenter les logs d'execute en version finale

        double[] donneesPosition = limelight.getBotpose();
        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return;

        Pose2d position = new Pose2d(donneesPosition[0], donneesPosition[1], Rotation2d.fromRotations(donneesPosition[6]));
        Pose2d cible = new Pose2d(6.89, 1.42, Rotation2d.fromRotations(donneesPosition[6]));

        ChassisSpeeds vitesseAjustee = driveControleur.calculate(position, cible, 0.2, Rotation2d.fromRotations(0));
        SmartDashboard.putNumber("vitesseAjustee.vxMetersPerSecond", vitesseAjustee.vxMetersPerSecond);
        SmartDashboard.putNumber("vitesseAjustee.vyMetersPerSecond", vitesseAjustee.vyMetersPerSecond);
        SmartDashboard.putNumber("vitesseAjustee.omegaRadiansPerSecond", vitesseAjustee.omegaRadiansPerSecond);
        double longueurDuCentre = 0.38; // 0.635
        double largeurDuCentre = 0.43; // 0.508
        MecanumDriveKinematics kinematics = new MecanumDriveKinematics(new Translation2d(-largeurDuCentre, longueurDuCentre), new Translation2d(largeurDuCentre, longueurDuCentre), new Translation2d(-largeurDuCentre, -longueurDuCentre), new Translation2d(largeurDuCentre, -longueurDuCentre));
        MecanumDriveWheelSpeeds vitesseRoues = kinematics.toWheelSpeeds(vitesseAjustee, new Translation2d(0, 0));
        roues.conduireToutesDirections(vitesseRoues.frontLeftMetersPerSecond, vitesseRoues.frontRightMetersPerSecond, vitesseRoues.rearLeftMetersPerSecond, vitesseRoues.rearRightMetersPerSecond);
    }

    
    /** 
     * @return boolean
     */
    @Override
    public boolean isFinished() 
    {
        double[] donneesPosition = limelight.getBotpose();
        if (donneesPosition[0] == 0 && donneesPosition[1] == 0)
            return false;
        System.out.println("CommandeAllerA.isFinished() donneesPosition " + donneesPosition[0] + " " + donneesPosition[1]);
        double distance = Math.pow(donneesPosition[0] - 6.89, 2) + Math.pow(donneesPosition[1] - 1.42, 2);
        System.out.println("CommandeAllerA.isFinished() distance " + distance);
        if (distance < Math.pow(0.5, 2)) {
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

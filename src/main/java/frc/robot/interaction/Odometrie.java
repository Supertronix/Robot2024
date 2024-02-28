package frc.robot.interaction;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.soussysteme.Roues;

public class Odometrie implements Materiel.Roues{

    protected static Odometrie instance = null;
    public static Odometrie getInstance()
    {
        if(null == instance)
        {
            instance = new Odometrie();
        }
        return instance;
    }

    protected MecanumDriveKinematics cinematique;
    protected MecanumDriveOdometry odometrieMecanum;
    protected LecteurAccelerometre accelerometre;
    protected MecanumDriveWheelPositions positionsMecanum;
    protected Roues roues;
    protected Pose2d positionDepartMatch;

    public Odometrie()
    {
        this.roues = Robot.getInstance().roues;
        this.positionDepartMatch = new Pose2d(5.0, 13.5, new Rotation2d());

        this.cinematique = new MecanumDriveKinematics(
            new Translation2d(-LARGEUR_DU_CENTRE, LONGUEUR_DU_CENTRE),
            new Translation2d(LARGEUR_DU_CENTRE, LONGUEUR_DU_CENTRE),
            new Translation2d(-LARGEUR_DU_CENTRE, -LONGUEUR_DU_CENTRE),
            new Translation2d(LARGEUR_DU_CENTRE, -LONGUEUR_DU_CENTRE)
        );

        this.accelerometre = LecteurAccelerometre.getInstance();

       positionsMecanum = new MecanumDriveWheelPositions( 
            roues.encodeurAvantGauche.getPosition(),
            roues.encodeurAvantDroit.getPosition(),
            roues.encodeurArriereGauche.getPosition(),
            roues.encodeurArriereDroit.getPosition()
      );

        odometrieMecanum = 
        new MecanumDriveOdometry(cinematique, accelerometre.getRotation2D(), positionsMecanum, this.positionDepartMatch);
    }

    public MecanumDriveKinematics getCinematique() {
        return cinematique;
    }



}

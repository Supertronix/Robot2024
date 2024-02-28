package frc.robot.interaction;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import frc.robot.Materiel;
import frc.robot.Robot;
import frc.robot.mesure.Chronometre;
import frc.robot.soussysteme.Roues;

public class Odometrie implements Materiel.Roues{

    public double FACTEUR_GLISSEMENT_PAR_SECONDE = 0.01;
    private Pose2d positionSurLeField;
    protected MecanumDriveOdometry odometrieMecanum;
    protected Chronometre chronometre;
    protected double incertitude;

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
    protected LecteurAccelerometre accelerometre;
    protected MecanumDriveWheelPositions positionsMecanum;
    protected Roues roues;
    protected Pose2d positionDepartMatch;

    public Odometrie()
    {
        this.roues = Robot.getInstance().roues;
        this.chronometre = new Chronometre();
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

    public void actualiser()
    {
        this.positionSurLeField = odometrieMecanum.getPoseMeters();
    }

    public void setPositionSelonVision(Pose2d position)
    {
        this.positionSurLeField = position;
    }

    private void annulerIncertitude()
    {
        this.chronometre.desactiver();
        this.chronometre.initialiser();
        this.incertitude = 0;
    }

    // j'approxime l'incertitude comme une fraction du temps
    // todo update plus tard avec fraction de distance rellement parcourue
    public double getIncertitude()
    {
        return (this.chronometre.getDuree()/1000) * FACTEUR_GLISSEMENT_PAR_SECONDE;
    }

    public Pose2d getPosition()
    {
        return this.positionSurLeField;
    }
}

package frc.robot.soussysteme;

// Doc https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/math/geometry/Rotation2d.html
// import edu.wpi.first.math.geometry.Rotation2d;
// Doc https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/drive/MecanumDrive.html    
import edu.wpi.first.wpilibj.drive.MecanumDrive;

public class RouesMecanum extends Roues {

    protected MecanumDrive mecanum;
    protected double facteur = 1;

    // import edu.wpi.first.math.geometry.Rotation2d;
    // this.mecanum = new MecanumDrive(this.roueArriereDroite, this.roueArriereGauche, this.roueAvantDroite, this.roueAvantGauche);
    public RouesMecanum()
    {
        this.facteur = FACTEUR_ROUES;
        this.activerModeHolonomique();
    }
    public void activerModeHolonomique()
    {
        this.roueAvantGauche.setInverted(true);
        this.roueArriereGauche.setInverted(true);
        this.roueArriereDroite.setInverted(false);
        this.roueAvantDroite.setInverted(false);
    }
    
    /** 
     * @param facteur
     */
    public void setFacteur(double facteur)
    {
        this.facteur = facteur;
    }
    public void avancer(double vitesse)
    {
        conduireAvecAxes(vitesse, 0, 0);
    }

    public void reculer(double vitesse)
    {
        conduireAvecAxes(-vitesse, 0, 0);
    }

    public void tasserDroite(double vitesse)
    {
        conduireAvecAxes(0, vitesse, 0);
    }
    public void tasserGauche(double vitesse)
    {
        conduireAvecAxes(0, -vitesse, 0);
    }
    public void tournerDroite(double vitesse)
    {
        conduireAvecAxes(0, 0, vitesse);
    }
    public void tournerGauche(double vitesse)
    {
        conduireAvecAxes(0, 0, -vitesse);
    }

    public void avancerEtTournerDroite(double vitesse)
    {
        conduireAvecAxes(vitesse, 0, vitesse);
    }   
    public void avancerEtTournerGauche(double vitesse)
    {
        conduireAvecAxes(vitesse, 0, -vitesse);
    }
    public void reculerEtTournerDroite(double vitesse)
    {
        conduireAvecAxes(-vitesse, 0, vitesse);
    }   
    public void reculerEtTournerGauche(double vitesse)
    {
        conduireAvecAxes(-vitesse, 0, -vitesse);
    }
    @Deprecated
    public void tourner(double vitesseGauche, double vitesseDroite)
    {
    //   System.out.println("tourner("+vitesseGauche+","+vitesseDroite+")");
    //   this.roueAvantGauche  .set(  vitesseGauche );
    //   this.roueAvantDroite  .set( -vitesseDroite );
    //   this.roueArriereGauche.set(  vitesseGauche );
    //   this.roueArriereDroite.set( -vitesseDroite );

    //   this.roueAvantGauche.set(limiter(vitesseGauche));
    //   this.roueArriereGauche.set(limiter(-vitesseGauche));
    //   this.roueAvantDroite.set(limiter(vitesseDroite));
    //   this.roueArriereDroite.set(limiter(-vitesseDroite));
    }

    // @Deprecated
    // public void conduireAvecManette(Manette manette)
    // {
        // double vitesseAvantGauche = facteur*(manette.getAxeMainGauche().y + manette.getAxeMainGauche().x + manette.getAxeMainDroite().x);
        // double vitesseAvantDroite = facteur*(manette.getAxeMainGauche().y - manette.getAxeMainGauche().x - manette.getAxeMainDroite().x);
        // double vitesseArriereGauche = facteur*(manette.getAxeMainGauche().y - manette.getAxeMainGauche().x + manette.getAxeMainDroite().x);
        // double vitesseArriereDroite = facteur*(manette.getAxeMainGauche().y + manette.getAxeMainGauche().x - manette.getAxeMainDroite().x);
        // this.roueAvantGauche.set(limiter(vitesseAvantGauche));
        // this.roueAvantDroite.set(limiter(vitesseAvantDroite));
        // this.roueArriereGauche.set(limiter(vitesseArriereGauche));
        // this.roueArriereDroite.set(limiter(vitesseArriereDroite));
	    //Formule 2017 (x + yGauche, yDroite - x, yGauche - x, x + yDroite);
    //     conduireAvecAxes(manette.getAxeMainGauche().y, manette.getAxeMainGauche().x, manette.getAxeMainDroite().x);
    // }

    /**
     * @param vitesseAvantArriere         1 pour avancer, -1 pour reculer
     * @param vitesseDroiteGauche         1 pour se deplacer a la droite, -1 pour se deplacer a la gauche
     * @param vitesseRotationDroiteGauche 1 pour tourner vers la droite, -1 pour tourner vers la gauche
     */
    public void conduireAvecAxes(double vitesseAvantArriere, double vitesseDroiteGauche, double vitesseRotationDroiteGauche) {

        // System.out.println("vitesseAvantArriere="+vitesseAvantArriere+" vitesseDroiteGauche="+vitesseDroiteGauche+" vitesseRotationDroiteGauche="+vitesseRotationDroiteGauche);

        //this.mecanum.driveCartesian(
        //     vitesseAvantArriere         * facteur,
        //     vitesseDroiteGauche         * facteur,
        //     vitesseRotationDroiteGauche * facteur);

        this.roueAvantGauche  .set( facteur * ( vitesseAvantArriere + vitesseDroiteGauche + vitesseRotationDroiteGauche ) );
        this.roueAvantDroite  .set( facteur * ( vitesseAvantArriere - vitesseDroiteGauche - vitesseRotationDroiteGauche ) );
        this.roueArriereGauche.set( facteur * ( vitesseAvantArriere - vitesseDroiteGauche + vitesseRotationDroiteGauche ) );
        this.roueArriereDroite.set( facteur * ( vitesseAvantArriere + vitesseDroiteGauche - vitesseRotationDroiteGauche ) );

    }

    public void conduire(double vitesseX, double vitesseY)
    {
        System.out.println("conduire("+vitesseY+","+vitesseX+")");
        this.mecanum.driveCartesian(vitesseX, vitesseY, 0);
    }
    // les angles sont en radians
    public void conduireAvecAngle(double vitesse, double angle, double vitesseRotation)
    {
        // System.out.println("conduire("+vitesse+","+angle+")");
        //this.mecanum.drivePolar(vitesse, new Rotation2d(angle), vitesseRotation);
    }
    public void conduireSelonGyro(double x, double y, double z, double gyro)
    {
        //this.mecanum.driveCartesian(x,y,z, new Rotation2d(gyro));
    }

    public void conduireToutesDirections(double vitesseAvantGauche, double vitesseAvantDroite, double vitesseArriereGauche, double vitesseArriereDroite) 
    {
        //System.out.println("conduireToutesDirections("+vitesseAvantGauche+ " "+ vitesseAvantDroite + " " + vitesseArriereGauche + " " + vitesseArriereDroite + ")");
        this.roueAvantGauche  .set( vitesseAvantGauche   );
        this.roueAvantDroite  .set( vitesseAvantDroite   );
        this.roueArriereGauche.set( vitesseArriereGauche );
        this.roueArriereDroite.set( vitesseArriereDroite );
    }

    public double getPosition()
    {
        return this.roueAvantDroite.getEncoder().getPosition();
    }
}

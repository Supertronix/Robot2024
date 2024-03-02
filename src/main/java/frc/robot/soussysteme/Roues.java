package frc.robot.soussysteme;

import com.revrobotics.CANSparkBase;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Materiel;
import frc.robot.Cinematique;
import frc.robot.composant.MoteurSparkMax;

interface Roulable
{
    public void arreter();
    public void avancer(double vitesse);
    public void reculer(double vitesse);
    public void tasserDroite(double vitesse);
    public void tasserGauche(double vitesse);
    public void tournerDroite(double vitesse);
    public void tournerGauche(double vitesse);
    public void tourner(double vitesseGauche, double vitesseDroite);
    public void avancerEtTournerDroite(double vitesse);
    public void avancerEtTournerGauche(double vitesse);
    public void reculerEtTournerDroite(double vitesse);
    public void reculerEtTournerGauche(double vitesse);
}
interface Dirigeable
{
    // @Deprecated
    // public void conduireAvecManette(Manette manette);
    public void conduireAvecAxes(double vitesseAvantArriere, double vitesseDroiteGauche, double vitesseRotationDroiteGauche);
    public void conduire(double vitesseX, double vitesseY);
    public void conduireToutesDirections(double vitesseAvantGauche, double vitesseAvantDroite, double vitesseArriereGauche, double vitesseArriereDroite);
    public void conduireAvecAngle(double vitesse, double angle, double vitesseRotation);
    public void conduireSelonGyro(double x, double y, double z, double gyro); 
}

abstract public class Roues extends SousSysteme implements Roulable, Dirigeable, Materiel.Roues, Cinematique.Roues {

    public MoteurSparkMax roueAvantDroite;
    public MoteurSparkMax roueAvantGauche;
    public MoteurSparkMax roueArriereDroite;
    public MoteurSparkMax roueArriereGauche;
    public RelativeEncoder encodeurAvantDroit;
    public RelativeEncoder encodeurAvantGauche;
    public RelativeEncoder encodeurArriereDroit;
    public RelativeEncoder encodeurArriereGauche;

    private final double LIMITE_BROWNOUT = 6.3;
    private double plage_reduction_avant_brownout = 2.7;

    public Roues()
    {
        this.roueAvantDroite = new MoteurSparkMax(ROUE_AVANT_DROITE);
        this.roueAvantGauche = new MoteurSparkMax(ROUE_AVANT_GAUCHE);
        this.roueArriereDroite = new MoteurSparkMax(ROUE_ARRIERE_DROITE);
        this.roueArriereGauche = new MoteurSparkMax(ROUE_ARRIERE_GAUCHE);

        this.roueAvantDroite.setOpenLoopRampRate(0.75);
        this.roueAvantGauche.setOpenLoopRampRate(0.75);
        this.roueArriereDroite.setOpenLoopRampRate(0.75);
        this.roueArriereGauche.setOpenLoopRampRate(0.75);

        // lire sur getAbsoluteEncoder et getAlternateEncoder()
        this.encodeurAvantDroit = this.roueAvantDroite.getEncoder();
        this.encodeurArriereDroit = this.roueArriereDroite.getEncoder();
        this.encodeurAvantGauche = this.roueAvantGauche.getEncoder();
        this.encodeurArriereGauche = this.roueArriereGauche.getEncoder();
        
        this.arreter();
    }

    public void arreter()
    {
      this.roueAvantDroite.stopMotor();
      this.roueAvantGauche.stopMotor();
      this.roueArriereDroite.stopMotor();
      this.roueArriereGauche.stopMotor();

      // this.roueAvantGauche.set(0);
      // this.roueArriereGauche.set(0);
      // this.roueAvantDroite.set(0);
      // this.roueArriereDroite.set(0);
    }

    @Override
    public void liberer()
    {
        this.roueAvantDroite.liberer();
        this.roueAvantGauche.liberer();
        this.roueArriereDroite.liberer();
        this.roueArriereGauche.liberer();
    }

    /**
     * @param vitesse
     * Réduit la vitesse des moteurs si la batterie est proche ou en dela de la limite de brownout
     */
    public double protegerBrownout(double vitesse)
    {
        double voltage = RobotController.getBatteryVoltage();
        if (voltage < LIMITE_BROWNOUT + 0.2)
        {
            plage_reduction_avant_brownout += 0.1;
            return 0.1;
        }
        else if (voltage < LIMITE_BROWNOUT + plage_reduction_avant_brownout)
        {
            return vitesse * (voltage - LIMITE_BROWNOUT) / plage_reduction_avant_brownout;
        }
        else if (voltage > 12 && plage_reduction_avant_brownout > 2.5)
        {
            plage_reduction_avant_brownout -= 0.1;
            return vitesse;
        }
        else
        {
            return vitesse;
        }
    }
}

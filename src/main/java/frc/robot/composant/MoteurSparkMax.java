package frc.robot.composant;

// https://github.com/REVrobotics/SPARK-MAX-Examples/tree/master/Java
// https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax
import com.revrobotics.CANSparkMax;

// https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmaxlowlevel.motortype
// https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax
// https://docs.wpilib.org/en/stable/docs/software/vscode-overview/3rd-party-libraries.html
// https://software-metadata.revrobotics.com/REVLib-2024.json

// https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Limit%20Switch/src/main/java/frc/robot/Robot.java

import com.revrobotics.SparkMaxLimitSwitch;

@SuppressWarnings({"removal"}) 
public class MoteurSparkMax extends CANSparkMax{


    private SparkMaxLimitSwitch limiteAvant;
    private SparkMaxLimitSwitch limiteArriere;

    public MoteurSparkMax(int id)
    {
        // CANSparkMax​(int deviceId, CANSparkMaxLowLevel.MotorType type)        
        super(id, MotorType.kBrushless);
        this.restoreFactoryDefaults();
        this.setIdleMode(IdleMode.kBrake);
    }
    public MoteurSparkMax(int id, boolean avecBroches) // argument dummy
    {
        // CANSparkMax​(int deviceId, CANSparkMaxLowLevel.MotorType type)        
        super(id, MotorType.kBrushed);
        this.restoreFactoryDefaults();
        this.setIdleMode(IdleMode.kBrake);
    }
    public void initialiser()
    {
        this.restoreFactoryDefaults();
        this.setIdleMode(IdleMode.kBrake);
    }

    private static double LIMIT_MIN = -1;
    private static double LIMIT_MAX =  1;
	
    /** 
     * @param vitesse
     * @return double
     */
    public static double limiter(double vitesse) 
	{
		return Math.max(LIMIT_MIN, Math.min(LIMIT_MAX, vitesse));
	}
    @Override
    public void set(double vitesse){
        super.set(limiter(vitesse));
    }


    // pattern Builder = Monteur
    public MoteurSparkMax avecLimites()
    {
        this.limiteAvant = this.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        this.limiteArriere = this.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
        this.limiteAvant.enableLimitSwitch(true);
        this.limiteArriere.enableLimitSwitch(true);
        return this;
    }

    public SparkMaxLimitSwitch getLimiteAvant()
    {
        return this.limiteAvant;
    }

    public SparkMaxLimitSwitch getLimiteArriere()
    {
        return this.limiteArriere;
    }

    public boolean testerLimiteAvant()
    {
        return this.limiteAvant.isPressed();
    }

    // https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Soft%20Limits/src/main/java/frc/robot/Robot.java
    // this.moteurPrincipal.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    // this.moteurPrincipal.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    // this.moteurPrincipal.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 200);
    // this.moteurPrincipal.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -200);
    public void liberer()
    {
        this.close();
    }

}

//import com.revrobotics.ControlType;
// ancien code
// limit switch
// this.moteurPrincipal.configForwardSoftLimitEnable(true, Constants.kTimeoutMs);
// this.moteurPrincipal.configReverseSoftLimitEnable(true, Constants.kTimeoutMs);
// this.moteurPrincipal.configForwardSoftLimitThreshold(100, Constants.kTimeoutMs);
// this.moteurPrincipal.configReverseSoftLimitThreshold(-100, Constants.kTimeoutMs);


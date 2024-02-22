package frc.robot.soussysteme;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class LanceurAngle {
    private Solenoid solenoideGauche;
    private Solenoid solenoideDroite;
    private Compressor compressor;

    public LanceurAngle() {
        this.solenoideGauche = new Solenoid(22, PneumaticsModuleType.CTREPCM, 1);
        this.solenoideDroite = new Solenoid(22, PneumaticsModuleType.CTREPCM, 2);
    }

    public void ajusterHaut() {
        System.out.println("Ajuster HAUT solenoides");
        this.solenoideGauche.set(true);
        this.solenoideDroite.set(true);
    }

    public void ajusterBas() {
        System.out.println("Ajuster BAS solenoides");
        this.solenoideGauche.set(false);
        this.solenoideDroite.set(false);
    }

}
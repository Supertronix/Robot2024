package frc.robot.soussysteme;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;

public class LanceurAngle extends SousSysteme{
    private Solenoid solenoideGauche;
    private Solenoid solenoideDroite;
    private Compressor compresseur;
    private DigitalInput capteurMagnetiquePositionDeploye = new DigitalInput(1);// flippe aussi
    private DigitalInput capteurMagnetiquePositionRetracte = new DigitalInput(2);
    //this.capteurMagnetiqueHaut = new CapteurMagnetiqueHaut();
    //this.capteurMagnetiqueBas = new CapteurMagnetiqueBas();
    

    public LanceurAngle() {
        this.solenoideGauche = new Solenoid(21, PneumaticsModuleType.CTREPCM, 6);
        this.solenoideDroite = new Solenoid(21, PneumaticsModuleType.CTREPCM, 4);
        this.compresseur = new Compressor(21, PneumaticsModuleType.CTREPCM);
    }

    public void deployer() {
        System.out.println("Deployer solenoides");
        this.compresseur.enableDigital();
        this.solenoideGauche.set(true);
        this.solenoideDroite.set(true);
    }

    public void retracter() {
        System.out.println("Retracter solenoides");
        this.compresseur.enableDigital();
        this.solenoideGauche.set(false);
        this.solenoideDroite.set(false);
    }

    public boolean estDeploye()
    {
        return this.capteurMagnetiquePositionDeploye.get();
    }

    public boolean estRetracte()
    {
        return this.capteurMagnetiquePositionRetracte.get();
    }
}
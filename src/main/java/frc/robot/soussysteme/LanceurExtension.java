package frc.robot.soussysteme;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Materiel;
import frc.robot.interaction.DetecteurLanceur;
import edu.wpi.first.wpilibj.Compressor;

public class LanceurExtension extends SousSysteme implements Materiel.Lanceur.Extension{
    private Solenoid solenoideGauche;
    private Solenoid solenoideDroite;
    private Compressor compresseur;

    public DetecteurLanceur detecteurLanceur;  // PUBLIC
    //private DigitalInput capteurDeploiement = new DigitalInput(PORT_CAPTEUR_MAGNETIQUE_DEPLOYE);// flippe aussi
    //private DigitalInput capteurRetraction = new DigitalInput(PORT_CAPTEUR_MAGNETIQUE_RETRACTE);

    public LanceurExtension() {
        this.detecteurLanceur = new DetecteurLanceur();
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
        //return this.capteurDeploiement.get();
        return this.detecteurLanceur.estDeploye();
    }

    public boolean estRetracte()
    {
        //return this.capteurRetraction.get();
        return this.detecteurLanceur.estRetracte();
    }
}
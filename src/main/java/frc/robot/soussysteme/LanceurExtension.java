package frc.robot.soussysteme;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Materiel;
import frc.robot.interaction.DetecteurLanceur;
import edu.wpi.first.wpilibj.Compressor;

public class LanceurExtension extends SousSysteme implements Materiel.Lanceur.Extension{
    private Solenoid mouvementAngleGauche;
    private Solenoid mouvementAngleDroite;
    private Solenoid mouvementExtension;
    private Compressor compresseur;

    public DetecteurLanceur detecteurLanceur;  // PUBLIC
    //private DigitalInput capteurDeploiement = new DigitalInput(PORT_CAPTEUR_MAGNETIQUE_DEPLOYE);// flippe aussi
    //private DigitalInput capteurRetraction = new DigitalInput(PORT_CAPTEUR_MAGNETIQUE_RETRACTE);

    public static int MOUVEMENT_ANGLE_GAUCHE = 6;
    public static int MOUVEMENT_ANGLE_DROITE = 1;
    public static int MOUVEMENT_EXTENSION = 5;

    public LanceurExtension() {
        this.detecteurLanceur = new DetecteurLanceur();
        this.compresseur = new Compressor(21, PneumaticsModuleType.CTREPCM);

        this.mouvementAngleGauche = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_GAUCHE);
        this.mouvementAngleDroite = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_DROITE);
        this.mouvementExtension = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_EXTENSION);
    }

    public void deployer() {
        System.out.println("Deployer solenoides");
        this.compresseur.enableDigital();
        this.mouvementAngleGauche.set(true);
        this.mouvementAngleDroite.set(true);
    }

    public void retracter() {
        System.out.println("Retracter solenoides");
        this.compresseur.enableDigital();
        this.mouvementAngleGauche.set(false);
        this.mouvementAngleDroite.set(false);
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
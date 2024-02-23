package frc.robot.soussysteme;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Materiel;
import frc.robot.composant.Compresseur;
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


    public LanceurExtension() {
        this.detecteurLanceur = new DetecteurLanceur();
        this.compresseur = new Compresseur(Materiel.COMPRESSEUR_MODULE);

        this.mouvementAngleGauche = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_GAUCHE);
        this.mouvementAngleDroite = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_DROITE);
        this.mouvementExtension = new Solenoid(21, PneumaticsModuleType.CTREPCM, MOUVEMENT_EXTENSION);
    }

    public void allonger()
    {
        System.out.println("LanceurExtension.allonger()");
        this.mouvementExtension.set(true);
        this.compresseur.enableDigital();
    }
    public void retracter()
    {
        System.out.println("LanceurExtension.retracter()");
        this.mouvementExtension.set(false);
        this.compresseur.enableDigital();
    }
    
    public void ouvrir() {
        System.out.println("LanceurExtension.ouvrir()");
        this.compresseur.enableDigital();
        this.mouvementAngleGauche.set(true);
        this.mouvementAngleDroite.set(true);
    }

    public void fermer() {
        System.out.println("LanceurExtension.fermer()");
        this.compresseur.enableDigital();
        this.mouvementAngleGauche.set(false);
        this.mouvementAngleDroite.set(false);
    }

    public boolean estOuvert()
    {
        //return this.capteurDeploiement.get();
        return this.detecteurLanceur.estOuvert();
    }

    public boolean estFerme()
    {
        //return this.capteurRetraction.get();
        return this.detecteurLanceur.estFerme();
    }
}
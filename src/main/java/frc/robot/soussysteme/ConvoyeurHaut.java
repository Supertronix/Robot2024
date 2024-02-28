package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Cinematique;
import frc.robot.Materiel;
import frc.robot.composant.Compresseur;
import frc.robot.composant.MoteurTalon;
import frc.robot.interaction.DetecteurConvoyeurHaut;
import frc.robot.mesure.Chronometre;

// Le deuxième convoyeur qui transporte la note du convoyeur 1 à l'intake
// Fonctionne avec 2 talons SRX
public class ConvoyeurHaut extends SousSysteme implements Materiel.ConvoyeurHaut, Cinematique.Convoyeurs {
    protected boolean actif;

    protected MoteurTalon moteurTalonMaitre;
    protected MoteurTalon moteurTalonEsclave;
    protected Solenoid mouvementOuvertureGauche;
    protected Solenoid mouvementOuvertureDroite;
    protected Solenoid mouvementExtension;
    protected DetecteurConvoyeurHaut detecteurConvoyeurHaut;

    protected boolean estOuvert;
    protected boolean estRetracte;
    protected Chronometre chronoFermer;
    protected Chronometre chronoAllonger;

    public ConvoyeurHaut() {
        moteurTalonMaitre = new MoteurTalon(ID_TALON_CONVOYEUR_MAITRE);
        moteurTalonEsclave = new MoteurTalon(ID_TALON_CONVOYEUR_ESCLAVE);

        Compresseur.getInstance();
        this.mouvementOuvertureGauche = new Solenoid(ID_MODULE_PNEUMATIQUE, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_GAUCHE);
        this.mouvementOuvertureDroite = new Solenoid(ID_MODULE_PNEUMATIQUE, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_DROITE);
        this.mouvementExtension   = new Solenoid(ID_MODULE_PNEUMATIQUE, PneumaticsModuleType.CTREPCM, MOUVEMENT_EXTENSION);
        this.detecteurConvoyeurHaut = new DetecteurConvoyeurHaut();
        this.chronoFermer = new Chronometre();
        this.chronoAllonger = new Chronometre();

        moteurTalonMaitre.setInverted(true);
        moteurTalonEsclave.setInverted(false);
        moteurTalonEsclave.follow(moteurTalonMaitre);
    }

    // Démarre le moteur d'intake avec la vitesse définie dans la config
    public void activer() {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_CONVOYEUR);
        actif = true;
    }
    
    /** 
     * @param vitesse
     */
    // Démarre le moteur d'intake avec la vitesse passée en paramètre
    public void activer(double vitesse) {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, vitesse);
        actif = true;
    }

    // Arrêter les moteurs du convoyeur
    public void desactiver() {
        moteurTalonMaitre.set(TalonSRXControlMode.PercentOutput, 0);
        actif = false;
    }

    // Permet de changer le sens des moteurs pour tirer vers le bas ou monter en haut
    public void setMoteursVersHaut(boolean versLeHaut) {
        if (versLeHaut) {
            moteurTalonMaitre.setInverted(true);
            moteurTalonEsclave.setInverted(false);
        }
        else {
            moteurTalonMaitre.setInverted(false);
            moteurTalonEsclave.setInverted(true);
        }
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.actif;
    }

    public void allonger() {
        System.out.println("ConvoyeurHaut.allonger()");
        if (this.estOuvert()) // Machine état a la place
        {
            this.chronoAllonger.initialiser();
            this.mouvementExtension.set(true);
        }
        else
            System.out.println("Peut pas Allonger");
    }

    public void retracter()
    {
        System.out.println("ConvoyeurHaut.retracter()");
        this.mouvementExtension.set(false);
    }
    
    public void ouvrir() {
        System.out.println("ConvoyeurHaut.ouvrir()");
        this.mouvementOuvertureGauche.set(true);
        this.mouvementOuvertureDroite.set(true);
    }

    public void fermer() {
        System.out.println("ConvoyeurHaut.fermer()");
        if(this.estRetracte()) // machine etat a la place
        {
            this.chronoFermer.initialiser();
            this.mouvementOuvertureGauche.set(false);
            this.mouvementOuvertureDroite.set(false);
        }
        else
            System.out.println("Peut pas fermer");
    }

    public boolean estOuvert()
    {
        if (this.chronoFermer.getDureeActuelle() >= 1000) {
            this.estOuvert = false;
            chronoFermer.desactiver();
        }
        if (this.detecteurConvoyeurHaut.estOuvert()) this.estOuvert = true;
            return this.estOuvert;
    }

    public boolean estRetracte()
    {
        if (this.chronoAllonger.getDureeActuelle() >= 1000) {
            this.estRetracte = false;
            chronoAllonger.desactiver();
        }
        if (this.detecteurConvoyeurHaut.estRetracte())
            this.estRetracte = true;
        
        return this.estRetracte;
    }
}

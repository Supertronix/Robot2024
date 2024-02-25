package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Cinematique;
import frc.robot.Materiel;
import frc.robot.composant.Compresseur;
import frc.robot.composant.MoteurTalon;
import frc.robot.interaction.DetecteurConvoyeurHaut;

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

    public ConvoyeurHaut() {
        moteurTalonMaitre = new MoteurTalon(ID_TALON_CONVOYEUR_MAITRE);
        moteurTalonEsclave = new MoteurTalon(ID_TALON_CONVOYEUR_ESCLAVE);

        Compresseur.getInstance();
        this.mouvementOuvertureGauche = new Solenoid(ID_MODULE_PNEUMATIQUE, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_GAUCHE);
        this.mouvementOuvertureDroite = new Solenoid(ID_MODULE_PNEUMATIQUE, PneumaticsModuleType.CTREPCM, MOUVEMENT_ANGLE_DROITE);
        this.mouvementExtension   = new Solenoid(ID_MODULE_PNEUMATIQUE, PneumaticsModuleType.CTREPCM, MOUVEMENT_EXTENSION);
        this.detecteurConvoyeurHaut = new DetecteurConvoyeurHaut();

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

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean estActif() {
        return this.actif;
    }

    public void allonger()
    {
        System.out.println("LanceurExtension.allonger()");
        //if(this.detecteurConvoyeurHaut.estOuvert()) // machine etat a la place
        {
            this.mouvementExtension.set(true);
        }
    }
    public void retracter()
    {
        System.out.println("LanceurExtension.retracter()");
        this.mouvementExtension.set(false);
    }
    
    public void ouvrir() {
        System.out.println("LanceurExtension.ouvrir()");
        this.mouvementOuvertureGauche.set(true);
        this.mouvementOuvertureDroite.set(true);
    }

    public void fermer() {
        System.out.println("LanceurExtension.fermer()");
        //if(this.detecteurConvoyeurHaut.estRetracte()) // machine etat a la place
        {
            this.mouvementOuvertureGauche.set(false);
            this.mouvementOuvertureDroite.set(false);
        }
    }

    public boolean estOuvert()
    {
        return this.detecteurConvoyeurHaut.estOuvert();
    }

    public boolean estRetracte()
    {
        return this.detecteurConvoyeurHaut.estRetracte();
    }
}

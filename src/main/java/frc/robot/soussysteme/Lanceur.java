package frc.robot.soussysteme;

import frc.robot.Materiel;
import frc.robot.composant.Moteur;

// Le lanceur du robot, composé de 2 moteurs SparkMAX
public class Lanceur implements Materiel.Lanceur {
    protected boolean toggleOnOff;

    protected Moteur moteurMaitre;
    protected Moteur moteurEsclave;

    public Lanceur() {
        toggleOnOff = false;

        moteurMaitre = new Moteur(ID_LANCEUR_MAITRE);
        moteurEsclave = new Moteur(ID_LANCEUR_ESCLAVE);

        moteurEsclave.follow(moteurMaitre, true);
    }

    // Démarre les moteurs avec la vitesse par défaut
    public void demarrerMoteur() {
        moteurMaitre.set(VITESSE_LANCEUR);
        toggleOnOff = true;
    }

    /** 
     * Démarre les moteurs du lanceur avec la vitesse passée en paramètre
     * @param vitesse La vitesse du lanceur entre 0.00 et 1.00
     */
    public void demarrerMoteur(double vitesse) {
        moteurMaitre.set(vitesse);
        toggleOnOff = true;
    }

    // Arrête les moteurs du lanceur
    public void arreterMoteur() {
        moteurMaitre.set(0);
        toggleOnOff = false;
    }

    // Retourne l'état des moteurs (allumé/éteint)
    public boolean moteurOn() {
        return this.toggleOnOff;
    }
}

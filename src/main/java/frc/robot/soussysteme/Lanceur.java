package frc.robot.soussysteme;

import frc.robot.Materiel;
import frc.robot.composant.Moteur;

// Le lanceur du robot, composé de 2 moteurs SparkMAX
public class Lanceur implements Materiel.Lanceur {
    boolean toggleLanceur;

    protected Moteur moteurMaitre;
    protected Moteur moteurEsclave;

    public Lanceur() {
        toggleLanceur = false;

        moteurMaitre = new Moteur(ID_LANCEUR_MAITRE);
        moteurEsclave = new Moteur(ID_LANCEUR_ESCLAVE);

        moteurEsclave.follow(moteurMaitre, true);
    }

    // Démarre les moteurs avec la vitesse par défaut
    public void demarrerLanceur() {
        moteurMaitre.set(VITESSE_LANCEUR);
        toggleLanceur = true;
    }

    // Démarre les moteurs du lanceur avec la vitesse passée en paramètre
    public void demarrerLanceur(double vitesse) {
        moteurMaitre.set(vitesse);
        toggleLanceur = true;
    }

    // Arrête les moteurs du lanceur
    public void arreterLanceur() {
        moteurMaitre.set(0);
        toggleLanceur = false;
    }

    // Retourne l'état du lanceur (allumé/éteint)
    public boolean lanceurOn() {
        return this.toggleLanceur;
    }
}

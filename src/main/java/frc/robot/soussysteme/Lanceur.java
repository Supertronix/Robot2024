package frc.robot.soussysteme;

import frc.robot.Materiel;
import frc.robot.composant.Moteur;

// Le lanceur du robot, composé de 2 moteurs SparkMAX
public class Lanceur implements Materiel.Lanceur {
    protected Moteur moteurMaitre;
    protected Moteur moteurEsclave;

    public Lanceur() {
        moteurMaitre = new Moteur(ID_LANCEUR_MAITRE);
        moteurEsclave = new Moteur(ID_LANCEUR_ESCLAVE);

        moteurEsclave.follow(moteurMaitre, true);
        moteurMaitre.set(VITESSE_LANCEUR);
    }
}

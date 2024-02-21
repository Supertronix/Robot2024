package frc.robot.interaction;

import java.lang.Math;

public class Vecteur3 {
    public double x;
    public double y;
    public double z;

    /*
     * Crée un vecteur 3 avec les membres initialisés à 0
     */
    public Vecteur3() {
        this.x = 0f;
        this.y = 0f;
        this.z = 0f;
    }

    /*
     * Crée un vecteur 3 avec les membres initialisés par des flottants
     */
    public Vecteur3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /*
     * Crée un vecteur 3 avec les membres initialisés avec un autre vecteur
     */
    public Vecteur3(Vecteur3 vecteur) {
        this.x = vecteur.x;
        this.y = vecteur.y;
        this.z = vecteur.z;
    }

    /*
     * Retourne la norme d'un vecteur
     */
    public double getLongueur() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /*
     * Méthode statique qui additionne deux vecteurs entre eux
     */
    public static Vecteur3 Ajouter(Vecteur3 vecteur1, Vecteur3 vecteur2) {
        Vecteur3 resultat = new Vecteur3(vecteur1);
        resultat.x += vecteur2.x;
        resultat.y += vecteur2.y;
        resultat.z += vecteur2.z;

        return resultat;
    }

    /*
     * Méthode statique qui multiplie un vecteur par un scalaire
     */
    public static Vecteur3 Multiplier(Vecteur3 vecteur1, double scalaire) {
        Vecteur3 resultat = new Vecteur3(vecteur1);
        resultat.x *= scalaire;
        resultat.y *= scalaire;
        resultat.z *= scalaire;

        return resultat;
    }

    /*
     * Méthode statique qui multiplie deux vecteurs entre eux
     */
    public static Vecteur3 ProduitScalaire(Vecteur3 vecteur1, Vecteur3 vecteur2) {
        Vecteur3 resultat = new Vecteur3(vecteur1);



        return resultat;
    }
}

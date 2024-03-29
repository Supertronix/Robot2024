package frc.robot;
import java.util.Map;
import static java.util.Map.entry; 
/*
 * Fichier qui prend en note les constantes de ports et id dans le mapping Electro
 */
public interface Materiel {

    public static int COMPRESSEUR_MODULE = 21;

    public interface Affichage
    {
      public static int SIGNAL_ANIMATION_LED = 1; // et plusieurs autres pour les modes
    }

    public interface Roues
    {
        public static int ROUE_AVANT_DROITE = 4; // ID 4 // roule avant
        public static int ROUE_ARRIERE_DROITE = 2; // ID 3 // roule avant
        public static int ROUE_AVANT_GAUCHE = 3; // ID 1  // roule arriere
        public static int ROUE_ARRIERE_GAUCHE = 1; // ID 2 // roule arriere
        public static double LONGUEUR_DU_CENTRE = 0.635;
        public static double LARGEUR_DU_CENTRE = 0.508;
    }
    public interface Manette
    {
      public static final int MANETTE = 0;

      public static final int BOUTON_A = 1;
      public static final int BOUTON_B = 2;
      public static final int BOUTON_X = 3;		
      public static final int BOUTON_Y = 4;
      public static final int BOUTON_RETOUR = 7;
      public static final int BOUTON_DEMARRER = 8;
      public static final int CROIX_HAUT = 9;
      public static final int CROIX_BAS = 10;
    
      public static final int AXE_GAUCHE_X = 0;
      public static final int AXE_GAUCHE_Y = 1;
      public static final int AXE_DROIT_X = 4;    
      public static final int AXE_DROIT_Y = 5;
      
      public static final int GACHETTE_MAIN_GAUCHE = 2;
      public static final int GACHETTE_MAIN_DROITE = 3;
      public static final int BOUTON_MAIN_GAUCHE = 5; 
      public static final int BOUTON_MAIN_DROITE = 6;

      public enum ANGLE {HAUT, HAUT_DROIT, DROIT, BAS_DROIT, BAS, BAS_GAUCHE, GAUCHE, HAUT_GAUCHE};
        
      Map<ANGLE, Integer> ANGLE_POV = Map.ofEntries(
            entry(ANGLE.HAUT, 0),
            entry(ANGLE.HAUT_DROIT, 45),
            entry(ANGLE.DROIT, 90),
            entry(ANGLE.BAS_DROIT, 135),
            entry(ANGLE.BAS, 180),
            entry(ANGLE.BAS_GAUCHE, 225),
            entry(ANGLE.GAUCHE, 270),
            entry(ANGLE.HAUT_GAUCHE, 315)
            );
    
    }

    public interface Avaleur // entrée notes
    {
      public static final int ID_TALON_INTAKE = 10; // CAN ID Moteur intake
      public static int MOUVEMENT_BOUCHE = 2; // PNEUMATIQUE
    }

    public interface ConvoyeurBas
    {  
      public static final int ID_TALON_CONVOYEUR_MAITRE = 16;
      public static final int ID_TALON_CONVOYEUR_ESCLAVE = 13;
    }

    public interface ConvoyeurHaut
    {
      public static final int ID_TALON_CONVOYEUR_MAITRE = 14; // CAN ID Moteur convoyeur
      public static final boolean INVERSION_TALON_CONVOYEUR_MAITRE = true;
      public static final int ID_TALON_CONVOYEUR_ESCLAVE = 15; // CAN ID Moteur convoyeur
      public static final int ID_MODULE_PNEUMATIQUE = 21;
      public static final int PORT_CAPTEUR_MAGNETIQUE_OUVERTURE = 1;
      public static final int PORT_CAPTEUR_MAGNETIQUE_RETRACTAGE = 2;
      public static int MOUVEMENT_ANGLE_GAUCHE = 6;
      public static int MOUVEMENT_ANGLE_DROITE = 1;
      public static int MOUVEMENT_EXTENSION = 5;
    }

    // Config lanceur (pour lancer les notes)
    public interface Lanceur
    {
      public static final int ID_LANCEUR_MAITRE = 5;
      public static final int ID_LANCEUR_ESCLAVE = 6;
    }

    public interface Bras
    {
      public static final int ID_TREUIL = 11;
    }

    public interface DetecteurNote
    {
        public static final int ID_PORT_DIO = 0;
    }

    public interface DetecteurChaine {
        public static final int ID_PORT_DIO = 3;
    }
}

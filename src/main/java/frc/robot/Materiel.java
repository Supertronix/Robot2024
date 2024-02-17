package frc.robot;
import java.util.Map;
import static java.util.Map.entry; 

public interface Materiel {

    public static int INTERRUPTEUR_DROIT = 1;
    public static int INTERRUPTEUR_GAUCHE = 2;

    public interface Affichage
    {
      public static int SIGNAL_ANIMATION_LED = 1; // et plusieurs autres pour les modes
    }

    public interface Roues
    {
        public int ROUE_AVANT_DROITE = 4; // ID 4 // roule avant
        public int ROUE_ARRIERE_DROITE = 2; // ID 3 // roule avant
        public int ROUE_AVANT_GAUCHE = 3; // ID 1  // roule arriere
        public int ROUE_ARRIERE_GAUCHE = 1; // ID 2 // roule arriere
    }
    public interface Manette
    {
      public static final int MANETTE = 0;

      public static final int BOUTON_A = 1;
      public static final int BOUTON_B = 2;
      public static final int BOUTON_X = 3;		
      public static final int BOUTON_Y = 4;
      public static final int BOUTON_MAIN_GAUCHE = 5; 
      public static final int BOUTON_MAIN_DROITE = 6;
      public static final int BOUTON_RETOUR = 7;
      public static final int BOUTON_DEMARRER = 8;
    
      public static final int BATON_GAUCHE_AXE_X = 0;
      public static final int BATON_GAUCHE_AXE_Y = 1;
      public static final int BATON_DROIT_AXE_X = 4;    
      public static final int BATON_DROIT_AXE_Y = 5;
      public static final int MAIN_GAUCHE_AXE = 2;
      public static final int MAIN_DROITE_AXE = 3;

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

    // Config intake (entrée notes)
    public interface Intake
    {
      public static final int ID_TALON_INTAKE = 11; // CAN ID Moteur intake
      public static final double VITESSE_TALON_INTAKE = 0.5; // Vitesse par défaut moteur intake
    }

    public interface ConvoyeurBas
    {  
      public static final int ID_TALON_CONVOYEUR_MAITRE = 16;
      public static final int ID_TALON_CONVOYEUR_ESCLAVE = 13;
      public static final double VITESSE_TALON_CONVOYEUR = 0.1;
    }

    public interface ConvoyeurHaut
    {
      public static final int ID_TALON_CONVOYEUR_MAITRE = 14; // CAN ID Moteur convoyeur
      public static final boolean INVERSION_TALON_CONVOYEUR_MAITRE = true;
      public static final int ID_TALON_CONVOYEUR_ESCLAVE = 15; // CAN ID Moteur convoyeur
      public static final double VITESSE_TALON_CONVOYEUR = 0.1; // Vitesse par défaut moteur convoyeur

    }

    // Config lanceur (pour lancer les notes)
    public interface Lanceur
    {
      public static final int ID_LANCEUR_MAITRE = 5;
      public static final int ID_LANCEUR_ESCLAVE = 6;
      public static final double VITESSE_LANCEUR = 0.2;
    }

    public interface Winch
    {
      public static final int ID_WINCH = 10;
      public static final double VITESSE_WINCH = 1.0;
    }

    /*
    public interface Bras
    {
      public static final int MOTEUR_PRINCIPAL = 5; // tourne vers la batterie
      public static final int MOTEUR_SECONDAIRE = 6; // tourne vers la batterie

      public void abaisser();
    }
    public interface Machoire
    {
      public static final int MOTEUR = 7;
    }
    */
    
}

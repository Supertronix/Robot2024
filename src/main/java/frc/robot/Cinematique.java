package frc.robot;

/*
 * Interface pour configurer les VITESSE, SEUIL, BIAIS, PID, DUREE et autre constantes de cinematique
 */
public interface Cinematique {
    public static double DISTANCE_NULLE = 2; 
    public static int INTERVALLE_IMMOBILITE = 250;

    public interface Manette
    {
      enum Direction {DEVANT, DERRIERE, LATERAL_DROIT, LATERAL_GAUCHE, ROTATION_DROITE, ROTATION_GAUCHE};

      public static final double SEUIL_ZERO = 0.09; // 0.02 // 0.08
      public static final double SEUIL_AXES_OPPOSES = 0.3;
      public static double BIAIS_AXE_GAUCHE_X = 0;
      public static double BIAIS_AXE_GAUCHE_Y = -0;
      //public static double BIAIS_AXE_GAUCHE_X = 0.06299212574958801;
      //public static double BIAIS_AXE_GAUCHE_Y = -0.06299212574958801;
    } 
    public interface Roues
    {
      public double FACTEUR_ROUES = 1; // 0.7
    }
    public interface Bras
    {
      public static final double VITESSE_TREUIL = 1.0;
    }
    public interface Avaleur // entrée notes
    {
      public static final double VITESSE_TALON_INTAKE = 0.5; // Vitesse par défaut moteur intake
    }
    public interface Convoyeurs
    {
      public static final double VITESSE_TALON_CONVOYEUR = 0.5; // Vitesse par défaut moteur convoyeur
    }
    public interface Lanceur
    {
      public static final double VITESSE_LANCEUR = 0.5;
    }    
}








/* 
public interface Machoire
{
  public double VITESSE = 1;
  public double VITESSE_OUVRIR = 0.5;
  public double VITESSE_FERMER = 0.5;
  public double TEMPS_MAXIMUM_OUVRIR = 1000; // ms
  public double TEMPS_MAXIMUM_FERMER = 1000; // ms
}*/

/**
 * 
       public static final float POSITION_AVANT = 12.75f;
      public static final float POSTIION_MILIEU = 6.5f;
      public static final float POSITION_ARRIERE = 0.875f;
      public static final float POSITION_PENCHE_AVANT = 8.5f;
      public static final float POSITION_PENCHE_ARRIERE = 4.5f;


      public interface Bras{
    /*
      // B = 0 - A = 6.5 - X = 13
      // arriere pour scorer- centrer pour deplacement - devant pour ramasser
      public static final float POSITION_AVANT = 11f;
      public static final float POSITION_PENCHE_AVANT = 8.5f;
      public static final float POSTIION_MILIEU = 6.1f;
      public static final float POSITION_PENCHE_ARRIERE = 4f;
      public static final float POSITION_ARRIERE = 0.5f;
      public enum POSITION {POSITION_AVANT, POSITION_PENCHE_AVANT, POSTIION_MILIEU, POSITION_PENCHE_ARRIERE, POSITION_ARRIERE, AJUSTEE, INCONNUE};
      public static final double POSITION_INCONNUE = -1;
      Map<POSITION, Float> POSITION_NUMERIQUE = Map.ofEntries(
        entry(POSITION.POSITION_AVANT, POSITION_AVANT),
        entry(POSITION.POSITION_PENCHE_AVANT, POSITION_PENCHE_AVANT),
        entry(POSITION.POSTIION_MILIEU, POSTIION_MILIEU),
        entry(POSITION.POSITION_PENCHE_ARRIERE, POSITION_PENCHE_ARRIERE),
        entry(POSITION.POSITION_ARRIERE, POSITION_ARRIERE));

      //public static final double P = 0.05;
      //public static final double I = 0.000001;//0.0001;  // gros = bang bang
      public static final double P = 0.1;
      public static final double I = 0.000002;//0.0001;  // gros = bang bang
      public static final double D = 8;
      public static final double MAX = 0.3;

      public static double SEUIL_GRAND_MOUVEMENT = 10;
      public static double SEUIL_PETIT_MOUVEMENT = 4;
      public interface GRAND_MOUVEMENT
      {
        public static final double P = 0.1;
        public static final double I = 0.000002;//0.0001;  // gros = bang bang
        public static final double D = 8;
        public static final double MAX = 0.3;  
      }
      public interface MOYEN_MOUVEMENT
      {
        public static final double P = 0.1;
        public static final double I = 0.000002;//0.0001;  // gros = bang bang
        public static final double D = 8;
        public static final double MAX = 0.3;  
      }
      public interface PETIT_MOUVEMENT
      {
        public static final double P = 0.1;
        public static final double I = 0.000002;//0.0001;  // gros = bang bang
        public static final double D = 8;
        public static final double MAX = 0.3;  
      }
      public interface INCREMENT
      {
        public static final double P = 0.2;
        public static final double I = 0;
        public static final double D = 0;
        public static final double MAX = 0.3;  
      }

      public static double TEMPS_MAXIMUM_CALIBRER = 800;
      public static double TEMPS_MAXIMUM_CALIBRER_AVANT = 1600;
      */

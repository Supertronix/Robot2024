package frc.robot.soussysteme;

import frc.robot.mesure.Vecteur3;

import java.util.Map;

import static java.util.Map.entry;

/*
 * Fichier qui prend en note les positions des positions souhaitées relatives au field global
 */
public interface AprilTags {
    public enum Position {
        SpeakerRouge, SpeakerBleu, AmplificateurRouge, AmplificateurBleu, SceneRougePos1, SceneRougePos2, SceneRougePos3, SceneBleuPos1, SceneBleuPos2, SceneBleuPos3
    }

    public interface SpeakerRouge {
        public static final double X = 6.55;
        public static final double Y = 1.44;
        public static final double ANGLE = 180;
        public static final Vecteur3[] POSITIONS = {
                new Vecteur3(6.55+0.82, 1.44-1.40, 180-45), // Position Droite
                new Vecteur3(6.55, 1.44, 180), // Position Centre
                new Vecteur3(6.55+0.82, 1.44+1.40, 180+45) // Position Gauche
        };
        public static final double[] ID_TAGS = {3, 4};
    }

    public interface SpeakerBleu {
        public static final double X = -6.55;
        public static final double Y = 1.44;
        public static final double ANGLE = 0;
        public static final Vecteur3[] POSITIONS = {
                new Vecteur3(-6.55-0.82, 1.44-1.40, 0+45), // Position Gauche
                new Vecteur3(-6.55, 1.44, 0), // Position Centre
                new Vecteur3(-6.55-0.82, 1.44+1.40, 0-45) // Position Droite
        };
        public static final double[] ID_TAGS = {7, 8};
    }

    public interface AmplificateurRouge {
        public static final double X = 6.43;
        public static final double Y = 3.09; // 4.09, il reste 1m normalement
        public static final double ANGLE = 90;
        public static final double[] ID_TAGS = {5};
    }

    public interface AmplificateurBleu {
        public static final double X = -6.43;
        public static final double Y = 3.09;
        public static final double ANGLE = 90;
        public static final double[] ID_TAGS = {6};
    }

    public interface SceneRougePos1 {
        public static final double X = 0;
        public static final double Y = 0;
        public static final double ANGLE= 0;
        public static final double[] ID_TAGS = {11};
    }

    public interface SceneRougePos2 {
        public static final double X = 0;
        public static final double Y = 0;
        public static final double ANGLE= 0;
        public static final double[] ID_TAGS = {12};
    }

    public interface SceneRougePos3 {
        public static final double X = 0;
        public static final double Y = 0;
        public static final double ANGLE = 0;
        public static final double[] ID_TAGS = {13};
    }

    public interface SceneBleuPos1 {
        public static final double X = 0;
        public static final double Y = 0;
        public static final double ANGLE = 0;
        public static final double[] ID_TAGS = {14};
    }

    public interface SceneBleuPos2 {
        public static final double X = 0;
        public static final double Y = 0;
        public static final double ANGLE = 0;
        public static final double[] ID_TAGS = {15};
    }

    public interface SceneBleuPos3 {
        public static final double X = 0;
        public static final double Y = 0;
        public static final double ANGLE = 0;
        public static final double[] ID_TAGS = {16};
    }
}

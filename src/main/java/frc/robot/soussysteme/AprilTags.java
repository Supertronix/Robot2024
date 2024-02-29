package frc.robot.soussysteme;

import java.util.Map;

import static java.util.Map.entry;

/*
 * Fichier qui prend en note les positions des positions souhaitées relatives au field
 */
public interface AprilTags {

    public interface SpeakerRouge {
        public static final double X = 6.43;
        public static final double Y = 1.44;
        public static final double ANGLE = -90;
        public static final double[] ID_TAGS = {3, 4};
    }

    public interface SpeakerBleu {
        public static final double X = -6.43;
        public static final double Y = 1.44;
        public static final double ANGLE = 90;
        public static final double[] ID_TAGS = {7, 8};
    }

    public interface AmplificateurRouge {
        public static final double X = 6.43;
        public static final double Y = 3.09; // 4.09, il reste 1m normalement
        public static final double ANGLE = 180;
        public static final double[] ID_TAGS = {5};
    }

    public interface AmplificateurBleu {
        public static final double X = -6.43;
        public static final double Y = 3.09;
        public static final double ANGLE = 180;
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

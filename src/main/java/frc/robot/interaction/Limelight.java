package frc.robot.interaction;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private NetworkTable networkTable = null;
    private final double HORIZONTAL_FOV = 32; // Fix ? 29.8 produit plus de décalages
    private final double VERTICAL_FOV = 24.85; // A verifier
    private double multiplicateur = 1.2; // Augmenter pour augmenter la zone de découpage

    // ------------------- CONSTRUCTEUR -------------------

    /**
     * Instancie une caméra Limelight 2
     */
    public Limelight() {
        System.out.println("Limelight()");
        try {
            networkTable = NetworkTableInstance.getDefault().getTable("limelight");
        } catch (Exception e) {
            System.out.println("Limelight() exception: " + e.getMessage());
        }
        setStreamMode(1);
    }

    // ------------------- GETTERS -------------------

    /**
     * @return True si une cible valide est trouvé, false sinon.
     */
    public boolean getEstCibleTrouve() {
        return networkTable.getEntry("tv").getDouble(0) != 0;
    }

    /**
     * @return Décalage horizontal entre le réticule et la cible
     * (LL2: -29.8 to 29.8 degrees)
     */
    public double getDecalageHorizontal() {
        return networkTable.getEntry("tx").getDouble(0);
    }

    /**
     * @return Décalage horizontal entre le réticule et la cible
     * (0 à 1)
     */
    public double getDecalageHorizonPourcent() {
        return getDecalageHorizontal() / HORIZONTAL_FOV;
    }

    /**
     * @return Décalage vertical entre le réticule et la cible
     * (LL2: -24.85 to 24.85 degrees)
     */
    public double getDecalageVertical() {
        return networkTable.getEntry("ty").getDouble(0);
    }

    /**
     * @return Décalage vertical entre le réticule et la cible
     * (0 à 1)
     */
    public double getDecalageVerticalPourcent() {
        return getDecalageVertical() / VERTICAL_FOV;
    }

    /**
     * @return Longueur latérale horizontale de la boîte de délimitation approximative
     * (0 - 320 pixels)
     */
    public double getDelimitationHorizontalApproximative() {
        return networkTable.getEntry("thor").getDouble(0);
    }

    /**
     * @return Longueur latérale horizontale de la boîte de délimitation approximative
     * (0 - 1)
     */
    public double getDelimitationHorizontalApproximativePourcent() {
        return getDelimitationHorizontalApproximative() / 320;
    }

    /**
     * @return Longueur latérale verticale de la boîte de délimitation approximative
     * (0 - 320 pixels)
     */
    public double getDelimitationVerticalApproximative() {
        return networkTable.getEntry("tvert").getDouble(0);
    }

    /**
     * @return Longueur latérale verticale de la boîte de délimitation approximative
     * (0 - 1)
     */
    public double getDelimitationVerticalApproximativePourcent() {
        return getDelimitationVerticalApproximative() / 320;
    }

    /**
     * @return Longueur du côté le plus long de la boîte de délimitation ajustée
     */
    public double getDelimitationAjustee() {
        return networkTable.getEntry("tlong").getDouble(0);
    }

    /**
     * @return Contribution au temps de latence du pipeline (ms).
     */
    public double getLatencePipeline() {
        return networkTable.getEntry("tl").getDouble(0);
    }

    /**
     * @return Temps de latence du pipeline de capture (ms). Temps écoulé entre la fin de l'exposition de
     * la rangée centrale du capteur et le début du pipeline de suivi.
     */
    public double getLatencePipelineTracking() {
        return networkTable.getEntry("cl").getDouble(0);
    }

    /**
     * @return Temps de latence total (ms).
     */
    public double getLatenceTotal() {
        return getLatencePipeline() + getLatencePipelineTracking();
    }


    // ------------------- SETTERS -------------------

    /**
     * @param multiplicateur multiplicateur de la zone de découpage
     * (plus le multiplicateur est grand, plus la zone de découpage est grande)
     * Plus grand : permet un meilleur suivi de la cible
     * Plus petit : permet un meilleur FPS
     */
    public void setMultiplicateur(double multiplicateur) {
        this.multiplicateur = multiplicateur;
    }

    /**
     * @param coordonnees coordonnées de la zone de découpage (x0, x1, y0, y1)
     */
    public void setDecoupageCamera(double[] coordonnees) {
        networkTable.getEntry("crop").setDoubleArray(coordonnees);
    }

    /**
     * Sets limelight's streaming mode
     * @param mode
     * 0	Standard - Side-by-side streams if a webcam is attached to Limelight
     * 1	PiP Main - The secondary camera stream is placed in the lower-right corner of the primary camera stream
     * 2	PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary camera stream
     */
    public void setStreamMode(int mode) {
        networkTable.getEntry("<variablename>").setNumber(mode);
    }

    // ------------------- METHODES -------------------

    /**
     * Découpe la caméra dynamiquement en fonction de la cible, permet d'améliorer la vitesse de traitement
     */
    public void decoupageCameraDynamique() {
        if (getEstCibleTrouve()) {
            decoupageCamera();
        } else {
            resetDecoupageCamera();
        }
    }

    /**
     * Découpe la caméra autour de la cible
     */
    public void decoupageCamera() {
        //System.out.println("Découpage");

        double x1 = getDecalageHorizonPourcent();
        double y1 = getDecalageVerticalPourcent();

        double longueurHorizontal = (getDelimitationHorizontalApproximativePourcent() / 2) * multiplicateur;
        double longueurVertical = (getDelimitationVerticalApproximativePourcent() / 2) * multiplicateur;
        System.out.println("longueurHorizontal: " + longueurHorizontal + " longueurVertical: " + longueurVertical);

        double[] cropValues = new double[4];
        cropValues[0] = x1 - longueurHorizontal;
        cropValues[1] = x1 + longueurHorizontal;
        cropValues[2] = y1 - longueurVertical;
        cropValues[3] = y1 + longueurVertical;

        //System.out.println("x0: " + cropValues[0] + " y0: " + cropValues[1] + " x1: " + cropValues[2] + " y1: " + cropValues[3]);

        setDecoupageCamera(cropValues);
    }

    /**
     * Réinitialise la découpe de la caméra
     */
    public void resetDecoupageCamera() {
        //System.out.println("Reset decoupage");

        double[] cropValues = new double[4];
        cropValues[0] = -1; // x0
        cropValues[1] = 1; // x1
        cropValues[2] = -1; // y0
        cropValues[3] = 1; // y1

        setDecoupageCamera(cropValues);
    }

    
}

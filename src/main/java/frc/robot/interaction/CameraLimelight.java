package frc.robot.interaction;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

import java.util.HashMap;
import java.util.List;

public class CameraLimelight {

    private NetworkTable networkTable = null;
    private final double HORIZONTAL_FOV = 29.8; // A verifier
    private final double VERTICAL_FOV = 24.85;
    private double multiplicateur = 1.5; // Augmenter pour augmenter la zone de découpage
    private DetecteurNote detecteurNote;
    private Field2d arene;

    private final double[] ID_TAGS_BLEU = {1, 2, 6, 7, 8, 14, 15, 16};
    private final double[] ID_TAGS_ROUGE = {3, 4, 5, 9, 10, 11, 12, 13};
    private static HashMap<String, List<Integer>> mapIDTagsRouge = new HashMap<>();
    static {
        mapIDTagsRouge.put("SOURCE", List.of(9, 10));
        mapIDTagsRouge.put("AMP", List.of(5));
        mapIDTagsRouge.put("SPEAKER", List.of(3, 4));
        mapIDTagsRouge.put("SCENE", List.of(11, 12, 13));
    }
    private static HashMap<String, List<Integer>> mapIDTagsBleu = new HashMap<>();
    static {
        mapIDTagsBleu.put("SOURCE", List.of(1, 2));
        mapIDTagsBleu.put("AMP", List.of(6));
        mapIDTagsBleu.put("SPEAKER", List.of(7, 8));
        mapIDTagsBleu.put("SCENE", List.of(14, 15, 16));
    }

    // ------------------- CONSTRUCTEUR -------------------

    /**
     * Instancie une caméra Limelight 2
     */
    public CameraLimelight() {
        System.out.println("Limelight()");
        try {
            networkTable = NetworkTableInstance.getDefault().getTable("limelight");
        } catch (Exception e) {
            System.out.println("Limelight() exception: " + e.getMessage());
        }
    }

    public void initialiser() {
        this.arene = new Field2d();
        this.detecteurNote = Robot.getInstance().detecteurNote;
        SmartDashboard.putData("Arene", arene);
        //setModeStream(1); La caméra sera branché directement sur le RoboRIO
        // brancher la caméra sur la limelight augmente fortement la latence des deux caméras
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
     * A TESTER
     * @return Taille de la cible (0% of image to 100% of image)
     */
    public double getZoneCible() {
        return networkTable.getEntry("ta").getDouble(0);
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

    /**
     * A TESTER
     * @return True active pipeline index of the camera (0 .. 9)
     */
    public double getFlux() {
        return networkTable.getEntry("getpipe").getDouble(0);
    }

    /**
     * A TESTER
     * @return Full JSON dump of targeting results
     */
    public String getJson() {
        return networkTable.getEntry("json").getString("");
    }

    /**
     * A TESTER
     * @return Class ID of primary neural detector result or neural classifier result
     */
    public double getTClass() {
        return networkTable.getEntry("tclass").getDouble(0);
    }

    /**
     * A TESTER
     * @return Get the average HSV color underneath the crosshair region as a NumberArray
     */
    public double[] getMoyenneCouleurs() {
        return networkTable.getEntry("tc").getDoubleArray(new double[6]);
    }

    // ------------------- GETTERS CIBLAGE 3D -------------------

    /**
     * A TESTER
     * @return Robot transform in field-space. Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw), total latency (cl+tl)
     */
    public double[] getBotpose() {
        return networkTable.getEntry("botpose").getDoubleArray(new double[8]);
    }

    /**
     * A TESTER
     * @return Robot transform in field-space (blue driverstation WPILIB origin). Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw), total latency (cl+tl)
     */
    public double[] getBotposeWPIBleu() {
        return networkTable.getEntry("botpose_wpiblue").getDoubleArray(new double[8]);
    }

    /**
     * A TESTER
     * @return Robot transform in field-space (red driverstation WPILIB origin). Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw), total latency (cl+tl)
     */
    public double[] getBotposeWPIRouge() {
        return networkTable.getEntry("botpose_wpired").getDoubleArray(new double[8]);
    }

    /**
     * A TESTER
     * @return 3D transform of the camera in the coordinate system of the primary in-view AprilTag (array (6))
     */
    public double[] getCameraPositionRelatifTag() {
        return networkTable.getEntry("camerapose_targetspace").getDoubleArray(new double[6]);
    }

    /**
     * A TESTER
     * @return 3D transform of the primary in-view AprilTag in the coordinate system of the Camera (array (6))
     */
    public double[] getTagPositionRelatifCamera() {
        return networkTable.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
    }

    /**
     * A TESTER
     * @return 3D transform of the primary in-view AprilTag in the coordinate system of the Robot (array (6))
     */
    public double[] getTagPositionRelatifRobot() {
        return networkTable.getEntry("targetpose_robotspace").getDoubleArray(new double[6]);
    }

    /**
     * A TESTER
     * @return 3D transform of the robot in the coordinate system of the primary in-view AprilTag (array (6))
     */
    public double[] getRobotPositionRelatifTag() {
        return networkTable.getEntry("botpose_targetspace").getDoubleArray(new double[6]);
    }

    /**
     * A TESTER
     * @return 3D transform of the camera in the coordinate system of the robot (array (6))
     */
    public double[] getCameraPositionRelatifRobot() {
        return networkTable.getEntry("camerapose_robotspace").getDoubleArray(new double[6]);
    }

    /**
     * A TESTER
     * @return ID of the primary in-view AprilTag
     */
    public int getTagID() {
        return (int) networkTable.getEntry("tid").getDouble(0);
    }

    // ------------------- RAW GETTERS -------------------

    /**
     * A TESTER
     * @return Number array of corner coordinates [x0,y0,x1,y1......]
     * Moi non plus je ne sais pas ce que ça fait
     */
    public double[] getTCornX() {
        return networkTable.getEntry("tcornx").getDoubleArray(new double[4]);
    }

    /*
    "Limelight posts three raw contours to NetworkTables that are not influenced by your grouping mode.
    That is, they are filtered with your pipeline parameters, but never grouped. X and Y are returned
    in normalized screen space (-1 to 1) rather than degrees."
     */

    /**
     * @return Raw Screenspace X0
     */
    public double getRawScreenSpaceX(int id) {
        if (id == 0) return networkTable.getEntry("tx0").getDouble(0);
        else if (id == 1) return networkTable.getEntry("tx1").getDouble(0);
        else if (id == 2) return networkTable.getEntry("tx2").getDouble(0);
        else {
            System.out.println("getRawScreenSpaceX() id incorrect");
            return 0;
        }
    }

    /**
     * @return Raw Screenspace Y0
     */
    public double getRawScreenSpaceY(int id) {
        if (id == 0) return networkTable.getEntry("ty0").getDouble(0);
        else if (id == 1) return networkTable.getEntry("ty1").getDouble(0);
        else if (id == 2) return networkTable.getEntry("ty2").getDouble(0);
        else {
            System.out.println("getRawScreenSpaceY() id incorrect");
            return 0;
        }
    }

    /**
     * @return Area (0% of image to 100% of image)
     */
    public double getRawArea(int id) {
        if (id == 0) return networkTable.getEntry("ta0").getDouble(0);
        else if (id == 1) return networkTable.getEntry("ta1").getDouble(0);
        else if (id == 2) return networkTable.getEntry("ta2").getDouble(0);
        else {
            System.out.println("getRawArea() id incorrect");
            return 0;
        }
    }

    /**
     * @return Skew or rotation (-90 degrees to 0 degrees)
     */
    public double getRawRotation(int id) {
        if (id == 0) return networkTable.getEntry("ts0").getDouble(0);
        else if (id == 1) return networkTable.getEntry("ts1").getDouble(0);
        else if (id == 2) return networkTable.getEntry("ts2").getDouble(0);
        else {
            System.out.println("getRawSkew() id incorrect");
            return 0;
        }
    }

    // If you are using raw targeting data, you can still utilize your calibrated crosshairs:

    /**
     * @return Crosshair A X in normalized screen space
     */
    public double getCrosshairAX() {
        return networkTable.getEntry("cx0").getDouble(0);
    }

    /**
     * @return Crosshair A Y in normalized screen space
     */
    public double getCrosshairAY() {
        return networkTable.getEntry("cy0").getDouble(0);
    }

    /**
     * @return Crosshair B X in normalized screen space
     */
    public double getCrosshairBX() {
        return networkTable.getEntry("cx1").getDouble(0);
    }

    /**
     * @return Crosshair B Y in normalized screen space
     */
    public double getCrosshairBY() {
        return networkTable.getEntry("cy1").getDouble(0);
    }

    // ------------------- SETTERS -------------------

    /**
     * @param mode
     * Sets limelight's LED state
     * [0]	use the LED Mode set in the current pipeline
     * [1]	force off
     * [2]	force blink
     * [3]	force on
     */
    public void setModeLeds(int mode) {
        networkTable.getEntry("ledMode").setNumber(mode);
    }

    /**
     * @param mode
     * Sets limelight's operation mode
     * [0]  Vision processor
     * [1]  Driver Camera (Increases exposure, disables vision processing)
     */
    public void setModeCamera(int mode) {
        networkTable.getEntry("camMode").setNumber(mode);
    }

    /**
     * @param idPipeline
     * Sets limelight's current pipeline
     */
    public void setPipeline(int idPipeline) {
        networkTable.getEntry("pipeline").setNumber(idPipeline);
    }

    /**
     * Sets limelight's streaming mode
     * @param mode
     * 0	Standard - Side-by-side streams if a webcam is attached to Limelight
     * 1	PiP Main - The secondary camera stream is placed in the lower-right corner of the primary camera stream
     * 2	PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary camera stream
     */
    public void setModeStream(int mode) {
        networkTable.getEntry("stream").setNumber(mode);
    }

    /**
     * @param mode
     * Allows users to take snapshots during a match
     * 0	Reset snapshot mode
     * 1	Take exactly one snapshot
     */
    public void setModeSnapshot(int mode) {
        networkTable.getEntry("snapshot").setNumber(mode);
    }

    /**
     * @param coordonnees coordonnées de la zone de découpage (x0, x1, y0, y1)
     */
    public void setDecoupageCamera(double[] coordonnees) {
        networkTable.getEntry("crop").setDoubleArray(coordonnees);
    }

    /**
     * @param position
     * (Array) Set the camera's pose in the coordinate system of the robot.
     */
    public void setCameraPose(double[] position) {
        networkTable.getEntry("camerapose_robotspace_set").setDoubleArray(position);
    }

    /**
     * @param multiplicateur multiplicateur de la zone de découpage
     * (plus le multiplicateur est grand, plus la zone de découpage est grande)
     * Plus grand : permet un meilleur suivi de la cible
     * Plus petit : permet un meilleur FPS
     */
    public void setMultiplicateur(double multiplicateur) {
        this.multiplicateur = multiplicateur;
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

    public boolean estIDValide(int id) {
        if (Alliance.getInstance().getAllianceRouge()) {
            if (mapIDTagsRouge.get("SPEAKER").contains(id)) return true;
            if (mapIDTagsRouge.get("AMP").contains(id)) return true;
        } else {
            if (mapIDTagsBleu.get("SPEAKER").contains(id)) return true;
            if (mapIDTagsBleu.get("AMP").contains(id)) return true;
        }
        return false;
    }

    /**
     * Découpe la caméra autour de la cible
     */
    public void decoupageCamera() {
        //System.out.println("Découpage");
        if (!estIDValide(getTagID())) {
            resetDecoupageCamera();
            return;
        }

        double x1 = getDecalageHorizonPourcent();
        double y1 = getDecalageVerticalPourcent();

        double longueurHorizontal = (getDelimitationHorizontalApproximativePourcent() / 2) * multiplicateur;
        double longueurVertical = (getDelimitationVerticalApproximativePourcent() / 2) * multiplicateur;
        //System.out.println("longueurHorizontal: " + longueurHorizontal + " longueurVertical: " + longueurVertical);

        double[] cropValues = new double[4];
        cropValues[0] = Math.max(x1 - longueurHorizontal, -1);
        cropValues[1] = Math.min(x1 + longueurHorizontal, 1);
        cropValues[2] = Math.max(y1 - longueurVertical, -1);
        cropValues[3] = Math.min(y1 + longueurVertical, 1);

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

    public void verifierUtilite() {
        if (detecteurNote.detecteNote()) {
            setModeCamera(0);
        }
        else {
            setModeCamera(1);
        }
    }

    public void afficherPosition() {
        double[] position = getBotpose();
        Pose2d pose = new Pose2d(position[0], position[1], Rotation2d.fromDegrees(position[5]));
        arene.setRobotPose(pose);
    }

    public void activerTargeting() {
        setModeCamera(0);
    }
}
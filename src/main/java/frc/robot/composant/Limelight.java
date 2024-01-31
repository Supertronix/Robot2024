package frc.robot.composant;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private NetworkTable networkTable = null;
    private final double HORIZONTAL_FOV = 29.8;
    private final double VERTICAL_FOV = 24.85;

    public Limelight() {
        System.out.println("Limelight()");
        try {
            networkTable = NetworkTableInstance.getDefault().getTable("limelight");
        } catch (Exception e) {
            System.out.println("Limelight() exception: " + e.getMessage());
        }
    }
    public boolean getIsTargetFound() {
        return networkTable.getEntry("tv").getDouble(0) != 0;
    }

    /**
     * @return 	Horizontal Offset From Crosshair To Target
     * (LL2: -29.8 to 29.8 degrees)
     */
    public double getHorizontalOffset() {
        return networkTable.getEntry("tx").getDouble(0);
    }

    public double getHorizontalPercentOffset() {
        return getHorizontalOffset() / HORIZONTAL_FOV;
    }

    /**
     * @return	Vertical Offset From Crosshair To Target
     * (LL2: -24.85 to 24.85 degrees)
     */
    public double getVerticalOffset() {
        return networkTable.getEntry("ty").getDouble(0);
    }

    public double getVerticalPercentOffset() {
        return getVerticalOffset() / VERTICAL_FOV;
    }

    public double getRoughHorizontalBoundingBox() {
        return networkTable.getEntry("thor").getDouble(0);
    }

    public double getRoughVerticalBoundingBox() {
        return networkTable.getEntry("tvert").getDouble(0);
    }

    public double getFittedLongestSide() {
        return networkTable.getEntry("tlong").getDouble(0);
    }

    public void dynamicCrop() {
        if (getIsTargetFound()) {
            //System.out.println("Cropping");

            // X0 - Min or Max X value of crop rectangle (-1 to 1)
            // X1 - Min or Max X value of crop rectangle (-1 to 1)
            // Y0 - Min or Max Y value of crop rectangle (-1 to 1)
            // Y1 - Min or Max Y value of crop rectangle (-1 to 1)

            // get the tag offset from the crosshair
            //double x0 = getHorizontalOffset();
            //double y0 = getVerticalOffset();

            double x1 = getHorizontalPercentOffset();
            double y1 = getVerticalPercentOffset();
            System.out.println("x1: " + x1 + " y1: " + y1);

            // x1 + y1 = center of the target

            //System.out.println("x0: " + x0 + " y0: " + y0 + " x1: " + x1 + " y1: " + y1);

            double horizontalSidelenght = 20 / HORIZONTAL_FOV;
            double verticalSidelenght = 20 / VERTICAL_FOV;
            //System.out.println("horizontalSidelenght: " + horizontalSidelenght + " verticalSidelenght: " + verticalSidelenght);

            double[] cropValues = new double[4];
            cropValues[0] = x1 - horizontalSidelenght;
            cropValues[1] = y1 + verticalSidelenght;
            cropValues[2] = x1 - horizontalSidelenght;
            cropValues[3] = y1 + verticalSidelenght;

            //System.out.println("x0: " + cropValues[0] + " y0: " + cropValues[1] + " x1: " + cropValues[2] + " y1: " + cropValues[3]);

            networkTable.getEntry("crop").setDoubleArray(cropValues);
        } else {
            System.out.println("Not cropping");

            double[] cropValues = new double[4];
            cropValues[0] = -1; // x0
            cropValues[1] = 1; // x1
            cropValues[2] = -1; // y0
            cropValues[3] = 1; // y1

            networkTable.getEntry("crop").setDoubleArray(cropValues);
        }
    }
}

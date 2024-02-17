package frc.robot.interaction;

import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.cscore.HttpCamera;
import frc.robot.Robot;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraConducteur {
    private CapteurLuminosite capteurLuminosite;

    public CameraConducteur()
    {
        this.capteurLuminosite = Robot.getInstance().capteurLuminosite;

        Thread m_visionThread = new Thread(() -> {
            // Creates UsbCamera and MjpegServer [1] and connects them
            CameraServer.startAutomaticCapture();

            // Creates the CvSink and connects it to the UsbCamera
            CvSink cvSink = CameraServer.getVideo();

            // Creates the CvSource and MjpegServer [2] and connects them
            CvSource outputStream = CameraServer.putVideo("Blur", 640, 480);

            // Mats are very memory expensive. Lets reuse this Mat.
            Mat source = new Mat();

            // This cannot be 'true'. The program will never exit if it is. This
            // lets the robot stop this thread when restarting robot code or
            // deploying.

            while (!Thread.interrupted()) {
                // Tell the CvSink to grab a frame from the camera and put it
                // in the source mat.  If there is an error notify the output.
                if (cvSink.grabFrame(source) == 0) {
                    // Send the output the error.
                    outputStream.notifyError(cvSink.getError());
                    // skip the rest of the current iteration
                    continue;
                }
                // Put a rectangle on the image
                //Imgproc.rectangle(source, new Point(5, 5), new Point(100, 100),
                //        new Scalar(255, 255, 255), 2);

                String capteurLuminosite = "false";
                if (this.capteurLuminosite.getLuminosite()) capteurLuminosite = "true";

                Imgproc.putText(source, capteurLuminosite, new Point(10, 125),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255));

                // Give the output stream a new image to display
                outputStream.putFrame(source);
            }

            // Add the limelight camera stream to the dashboard
            //HttpCamera limelightCamera = new HttpCamera("limelight", "http://10.59.10.97:5800/stream.mjpg");
            //CameraServer.startAutomaticCapture(limelightCamera);
        });
        m_visionThread.setDaemon(true);
        m_visionThread.start();
    }
}

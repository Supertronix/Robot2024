package frc.robot.interaction;

import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.util.PixelFormat;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Robot;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CameraConducteur {
    private CapteurLuminosite capteurLuminosite;
    private Thread m_visionThread;
    private boolean actif;
    private int hauteur = (int) (480 / 1); // Limite bande passante et FPS
    private int largeur = (int) (640 / 1);

    public CameraConducteur()
    {
        actif = false;
    }

    public void initialiser() {
        this.capteurLuminosite = Robot.getInstance().capteurLuminosite;
        activer();
    }

    public void activer () {
        if (actif) return;
        else actif = true;

        if (m_visionThread != null && m_visionThread.isAlive()) return;

        m_visionThread = new Thread(() -> {
            // Creates UsbCamera and MjpegServer [1] and connects them
            UsbCamera camera = CameraServer.startAutomaticCapture();
            camera.setVideoMode(PixelFormat.kMJPEG, largeur, hauteur, 30); // 30 FPS (bande passante limitée à 4 Mbits/s)
            hauteur = camera.getVideoMode().height;
            largeur = camera.getVideoMode().width;

            // Creates the CvSink and connects it to the UsbCamera
            CvSink cvSink = CameraServer.getVideo();

            // Creates the CvSource and MjpegServer [2] and connects them
            CvSource outputStream = CameraServer.putVideo("Driver", largeur, hauteur);

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

                /*
                String capteurLuminosite = "false";
                if (this.capteurLuminosite.getLuminosite()) capteurLuminosite = "true";

                Imgproc.putText(source, capteurLuminosite, new Point(10, 125),
                        Imgproc.FONT_HERSHEY_SIMPLEX, 1, new Scalar(255, 255, 255));
                 */

                dessiner(source);
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

    public void desactiver() {
        actif = false;
        m_visionThread.interrupt();
    }

    public void dessiner(Mat source) {
        int tempsMatchDouble = (int) DriverStation.getMatchTime();
        String tempsMatch = String.valueOf(tempsMatchDouble);

        int LARGEUR = source.width();
        int HAUTEUR = source.height();

        System.out.println("LARGEUR: " + LARGEUR + " HAUTEUR: " + HAUTEUR);

        dessinerCoins(source);
        dessinerHorloge(source, tempsMatch);
    }

    public void dessinerCoins(Mat source) {
        // Place un point dans chaque coin de l'image
        Imgproc.circle(source, new Point(0, 0), 5, new Scalar(255, 255, 255), -1);
        Imgproc.circle(source, new Point(largeur, 0), 5, new Scalar(255, 255, 255), -1);
        Imgproc.circle(source, new Point(0, hauteur), 5, new Scalar(255, 255, 255), -1);
        Imgproc.circle(source, new Point(largeur, hauteur), 5, new Scalar(255, 255, 255), -1);
    }

    public void dessinerHorloge(Mat source, String tempsMatch) {
        // Rectangle noir, de longueur 30% Longueur, de couleur (46,46,46) positionné en haut au milieu
        double hauteurRectangle = hauteur * 0.05;
        Imgproc.rectangle(source, new Point(largeur * 0.2, 0), new Point(largeur * 0.8, hauteurRectangle), new Scalar(46, 46, 46), -1);

        double tailleTexte = 0.5;
        double tailleTexteLargeur = Imgproc.getTextSize(tempsMatch, Imgproc.FONT_HERSHEY_SIMPLEX, tailleTexte, 1, null).width;
        double tailleTexteHauteur = Imgproc.getTextSize(tempsMatch, Imgproc.FONT_HERSHEY_SIMPLEX, tailleTexte, 1, null).height;
        Imgproc.putText(source, tempsMatch, new Point((double) largeur / 2 - tailleTexteLargeur / 2, hauteurRectangle - (tailleTexteHauteur/2) - 2), Imgproc.FONT_HERSHEY_SIMPLEX, tailleTexte, new Scalar(255, 255, 255));

    }
}

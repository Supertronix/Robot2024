// Code de DEFINITION du robot 2024 de Supertronix

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.interaction.CameraLimelight;
import frc.robot.interaction.CapteurLuminosite;
import frc.robot.interaction.CameraConducteur;
import frc.robot.interaction.ShuffleBoard;
import frc.robot.soussysteme.*;

import java.util.Optional;

/**
 * Classe qui definit materiellement le robot
 */
public class Robot {

  public PartieDuRobot partie = new PartieDuRobot();
  public Roues roues = null;
  public Intake intake;
  public ConvoyeurBas convoyeurBas;
  public ConvoyeurHaut convoyeurHaut;
  public Lanceur lanceur;
  public Bras bras;

  public CameraLimelight cameraLimelight;
  public CameraConducteur cameraConducteur;
  public CapteurLuminosite capteurLuminosite;
  public ShuffleBoard shuffleBoard;
  private boolean estAllianceRouge = true;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  //private final CommandXboxController m_driverController = new CommandXboxController(0);

  public Robot() 
  {
    this.capteurLuminosite = new CapteurLuminosite();
    this.roues = new RouesMecanumSynchro();  //this.roues = new RouesMecanum();
    this.intake = new Intake();
    this.convoyeurBas = new ConvoyeurBas();
    this.convoyeurHaut = new ConvoyeurHaut();
    this.lanceur = new Lanceur();
    this.bras = new Bras();

    this.cameraLimelight = new CameraLimelight();
    this.cameraConducteur = new CameraConducteur();

    this.shuffleBoard = new ShuffleBoard();

    DriverStation.silenceJoystickConnectionWarning(true);
  }

  public static Robot instance = null;
  
  /** 
   * @return Robot
   */
  public static Robot getInstance()
  {
      if(Robot.instance == null) Robot.instance = new Robot();
      return Robot.instance;
  }

  public void miseAJourAlliance() {
      Optional<DriverStation.Alliance> couleur = DriverStation.getAlliance();
      if (couleur.isEmpty()) {
          System.out.println("Alliance introuvable, défaut à rouge");
          this.estAllianceRouge = true;
          return;
      }
      this.estAllianceRouge = couleur.get() != DriverStation.Alliance.Blue;
  }

  public void setAlliance(boolean estRouge) {
      this.estAllianceRouge = estRouge;
  }

  public boolean getEstAllianceRouge() {
      miseAJourAlliance();
      return this.estAllianceRouge;
  }
}

// Code de DEFINITION du robot 2024 de Supertronix

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.interaction.*;
import frc.robot.soussysteme.*;

import java.util.Optional;

/**
 * Classe qui definit materiellement le robot
 */
public class Robot {

  public RouesMecanum roues;
  public Bras bras;

  public Avaleur avaleur;
  public ConvoyeurBas convoyeurBas;
  public ConvoyeurHaut convoyeurHaut;
  public Lanceur lanceur;
  public LanceurExtension lanceurExtension;

  public CameraLimelight cameraLimelight;
  public CameraConducteur cameraConducteur;
  public DetecteurNote detecteurNote;

  public ShuffleBoard shuffleBoard;
  private boolean estAllianceRouge = true;
  private boolean verrouChangementAlliance = false;

  public Robot() 
  {
    this.detecteurNote = new DetecteurNote();
    this.roues = new RouesMecanum();  //this.roues = new RouesMecanum();
    this.avaleur = new Avaleur();
    this.convoyeurBas = new ConvoyeurBas();
    this.convoyeurHaut = new ConvoyeurHaut();
    this.lanceur = new Lanceur();
    this.lanceurExtension = new LanceurExtension();
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
      if (this.verrouChangementAlliance) return;

      Optional<DriverStation.Alliance> couleur = DriverStation.getAlliance();
      if (couleur.isEmpty()) {
          //System.out.println("Alliance introuvable, défaut à rouge");
          this.estAllianceRouge = true;
          return;
      }
      this.estAllianceRouge = couleur.get() != DriverStation.Alliance.Blue;
  }

  public void setAlliance(boolean estRouge) {
      if (this.verrouChangementAlliance) return;
      this.estAllianceRouge = estRouge;
  }

  /**
   * @param verrou
   * Empêche le changement d'alliance si la méthode automatique ne fonctionne pas
   */
  public void setVerrouChangementAlliance(boolean verrou) {
      this.verrouChangementAlliance = verrou;
  }

  public boolean getEstAllianceRouge() {
      miseAJourAlliance();
      return this.estAllianceRouge;
  }
}

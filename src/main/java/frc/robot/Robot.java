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
  public DetecteurNote detecteurNote;

  public CameraLimelight cameraLimelight;
  public CameraConducteur cameraConducteur;

  private boolean allianceRouge = true;
  private boolean verrouChangementAlliance = false;
  private boolean aveugle = false;

  public Robot() 
  {
    this.detecteurNote = new DetecteurNote();
    this.roues = new RouesMecanum();  //this.roues = new RouesMecanum();
    this.avaleur = new Avaleur();
    this.convoyeurBas = new ConvoyeurBas();
    this.convoyeurHaut = new ConvoyeurHaut();
    this.lanceur = new Lanceur();
    this.bras = new Bras();

    if(!this.aveugle)
    {
        this.cameraLimelight = new CameraLimelight();
        this.cameraConducteur = new CameraConducteur();
    }

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
          this.allianceRouge = true;
          return;
      }
      this.allianceRouge = couleur.get() != DriverStation.Alliance.Blue;
  }

  public void setAlliance(boolean estRouge) {
      if (this.verrouChangementAlliance) return;
      this.allianceRouge = estRouge;
  }

  /**
   * @param verrou
   * Empêche le changement d'alliance si la méthode automatique ne fonctionne pas
   */
  public void setVerrouChangementAlliance(boolean verrou) {
      this.verrouChangementAlliance = verrou;
  }

  public boolean getAllianceRouge() {
      miseAJourAlliance();
      return this.allianceRouge;
  }
  public boolean estAveugle()
  {
    return this.aveugle;
  }
  public boolean estVoyant()
  {
    return !this.aveugle;
  }
  public void setAveugle()
  {
    this.aveugle = true;
  }
  public void setVoyant()
  {
    this.aveugle = false;
  }

}

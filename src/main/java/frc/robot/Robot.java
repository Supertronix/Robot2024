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
  public DetecteurChaine detecteurChaine;
  public CameraLimelight cameraLimelight;
  public CameraConducteur cameraConducteur;

  private boolean aveugle = false;

  public Robot() 
  {
    this.detecteurNote = new DetecteurNote();
    this.detecteurChaine = new DetecteurChaine();
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

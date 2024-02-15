// Code de CONTROLE du robot 2024 de Supertronix

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commande.MouvementDuRobot;
import frc.robot.composant.Limelight;
import frc.robot.soussysteme.*;
import frc.robot.interaction.*;

// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Joystick.html
//import edu.wpi.first.wpilibj2.command.Command;
// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/JoystickButton.html


public class RobotControleur extends TimedRobot {

  private Robot robot;
  private Manette manette;
  private Limelight limelight;
  //private Command trajetAutonome;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    this.manette = RobotControleur.ActionManette.getInstance();
    this.limelight = new Limelight();
    DriverStation.silenceJoystickConnectionWarning(true);
  }

  // This runs after the mode specific periodic functions, but before LiveWindow and SmartDashboard integrated updating.
  // polling buttons // adding newly-scheduled commands, // running already-scheduled commands, removing finished or interrupted commands // running subsystem periodic() methods.  
  // This must be called from the robot's periodic block in order for anything in the Command-based framework to work.
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    limelight.resetDecoupageCamera();
  }

  @Override
  public void disabledPeriodic() {
    //limelight.decoupageCameraDynamique();
  }

  @Override
  public void autonomousInit() {
    //trajetAutonome = new CommandeTrajetAutonome();
    //trajetAutonome.schedule();
  }
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    System.out.println("teleopInit()");
    ((RouesMecanumSynchro)Robot.getInstance().roues).convertirEnRouesHolonomiques();
    ((RouesMecanumSynchro)Robot.getInstance().roues).setFacteur(1); // 0.8
    //    if (trajetAutonome != null) {
     // trajetAutonome.cancel();
    //}
  }

  @Override
  public void teleopPeriodic() {
    //System.out.println("teleopPeriodic()");   
    
    robot.roues.conduireAvecManette(this.manette);
    manette.executerActions();
    limelight.decoupageCameraDynamique();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
  
  @SuppressWarnings({"unused"})
  private void lierInteractions() {
    new Trigger(robot.partie::exampleCondition).onTrue(new MouvementDuRobot(robot.partie));
  }

  
  // https://docs.wpilib.org/en/2020/docs/software/old-commandbased/commands/running-commands-joystick-input.html
  // https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
  static public class ActionManette extends Manette {
  
      protected static ActionManette instance = null;
      public static ActionManette getInstance()
      {
        if(null == ActionManette.instance) ActionManette.instance = new ActionManette();
        return ActionManette.instance;
      };
      // protected JoystickButton boutonControllerAttrapeur;
  
      //@SuppressWarnings("deprecation") // la classe ouverte fonctionne aussi bien que la nouvelle classe proprietaire
      protected ActionManette()
      {

          /* 
          Command commandeCalibration = new CommandeCalibrerBras();
          this.boutonMaison.whenPressed(commandeCalibration);
  
          Command commandeMilieu = new CommandeDeplacerBras(POSITION.POSTIION_MILIEU);
          this.boutonDemarrer.whenPressed(commandeMilieu);
  
          Command commandeArriere = new CommandeDeplacerBras(POSITION.POSITION_AVANT);
          this.boutonArriere.whenPressed(commandeArriere);
  
          Command commandePencheDevant = new CommandeDeplacerBras(POSITION.POSITION_ARRIERE);
          this.boutonPencheDevant.whenPressed(commandePencheDevant);
  
          Command commandeDevant = new CommandeDeplacerBras(POSITION.POSITION_PENCHE_AVANT);
          this.boutonDevant.whenPressed(commandeDevant);
  
          Command commandePencheArriere = new CommandeDeplacerBras(POSITION.POSITION_PENCHE_ARRIERE);
          this.boutonPencheArriere.whenPressed(commandePencheArriere);
          
          Command commandeOuvrirMachoire = new CommandeOuvrirMachoire();
          Command commandeFermerMachoire = new CommandeFermerMachoire();
          this.boutonMainDroite.whenPressed(commandeFermerMachoire);
          this.boutonMainGauche.whenPressed(commandeOuvrirMachoire);
          */
  
      }
   
      public void executerActions()
      {
        if(this.boutonPressionMainGauche.getAsBoolean())
          {
              this.boutonPressionMainGauche.declencher();;
          }
        if(this.boutonPressionMainDroite.getAsBoolean())
          {
              this.boutonPressionMainDroite.declencher();
          }
      }
      
  }
  // this.boutonControllerAttrapeur.whenReleased(new CommandeArmerAttrapeur());
  

}

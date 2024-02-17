// Code de CONTROLE du robot 2024 de Supertronix

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commande.MouvementDuRobot;
import frc.robot.interaction.CameraLimelight;
import frc.robot.soussysteme.*;
import frc.robot.interaction.*;

// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Joystick.html
//import edu.wpi.first.wpilibj2.command.Command;
// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/JoystickButton.html


public class RobotControleur extends TimedRobot {

  private Robot robot;
  private Manette manette;
  //private Command trajetAutonome;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    this.manette = RobotControleur.ActionManette.getInstance();
    DriverStation.silenceJoystickConnectionWarning(true);

    // --------------- Tests --------------- //

    //CameraServer.startAutomaticCapture(); // Méthode simple, mais ne permet pas de manipuler les images
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
    Robot.getInstance().cameraLimelight.resetDecoupageCamera();
  }

  @Override
  public void disabledPeriodic() {
    //Robot.getInstance().limelight.decoupageCameraDynamique();
  }

  @Override
  public void autonomousInit() {
    //trajetAutonome = new CommandeTrajetAutonome();
    //trajetAutonome.schedule();
  }
  
  /** 
   * @param teleopInit(
   */
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
    
    robot.roues.conduireAvecAxes(this.manette.getAxeMainGauche().y, this.manette.getAxeMainGauche().x, this.manette.getAxeMainDroite().x);
    // Méthode vide mais cause des exceptions lorsque gâchette droite utilisée?
    //manette.executerActions();

    // Intake avec toggle on/off
    if (manette.getBoutonPresse(Materiel.Manette.BOUTON_A)) {
      System.out.println("BOUTON A PRESSE");
      
      if (robot.intake.moteurOn())
        robot.intake.arreterMoteur();
      else
        robot.intake.demarrerMoteur();
    }

    // Convoyeur bas avec toggle on/off
    if (manette.getBoutonPresse(Materiel.Manette.BOUTON_X)) {
      System.out.println("BOUTON X PRESSE");
      
      if (robot.convoyeurBas.moteurOn())
        robot.convoyeurBas.arreterMoteur();
      else
        robot.convoyeurBas.demarrerMoteur();
    }

    // Convoyeur haut avec toggle on/off
    if (manette.getBoutonPresse(Materiel.Manette.BOUTON_Y)) {
      System.out.println("BOUTON Y PRESSE");
      
      if (robot.convoyeurHaut.moteurOn())
        robot.convoyeurHaut.arreterMoteur();
      else
        robot.convoyeurHaut.demarrerMoteur();
    }

    // Lanceur avec toggle on/off
    if (manette.getBoutonPresse(Materiel.Manette.BOUTON_B)) {
      System.out.println("BOUTON B PRESSE");

      if (robot.lanceur.moteurOn())
        robot.lanceur.arreterMoteur();
      else
        robot.lanceur.demarrerMoteur();
    }

    // Lanceur contrôlable avec gâchette
    double pressionMainDroite = manette.getPressionMainDroite();
    double pressionMainGauche = manette.getPressionMainGauche();

    if (pressionMainDroite > 0.05) {
      robot.lanceur.demarrerMoteur(pressionMainDroite);
    }
    else if (pressionMainGauche > 0.05) {
      robot.lanceur.demarrerMoteur(-pressionMainGauche);
    }
    else {
      robot.lanceur.arreterMoteur();
    }

    Robot.getInstance().cameraLimelight.decoupageCameraDynamique();

    // test
    //Robot.getInstance().convoyeur2.setVitesse(0.2);
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

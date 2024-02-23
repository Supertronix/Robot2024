// Code de CONTROLE du robot 2024 de Supertronix

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commande.*;
import frc.robot.soussysteme.*;
import frc.robot.interaction.*;

// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj/Joystick.html
//import edu.wpi.first.wpilibj2.command.Command;
// https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/JoystickButton.html


public class RobotControleur extends TimedRobot {

  private Robot robot;
  private ActionManette manette;
  //private Command trajetAutonome;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    this.manette = (ActionManette)RobotControleur.ActionManette.getInstance();
    this.robot.cameraConducteur.initialiser();
    this.robot.shuffleBoard.initialiser();

    // --------------- Tests --------------- //

    //CameraServer.startAutomaticCapture(); // Méthode simple, mais ne permet pas de manipuler les images
  }

  // This runs after the mode specific periodic functions, but before LiveWindow and SmartDashboard integrated updating.
  // polling buttons // adding newly-scheduled commands, // running already-scheduled commands, removing finished or interrupted commands // running subsystem periodic() methods.  
  // This must be called from the robot's periodic block in order for anything in the Command-based framework to work.
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    this.robot.shuffleBoard.mettreAJour();
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
    this.robot = Robot.getInstance();    
    //trajetAutonome = new CommandeTrajetAutonome();
    //trajetAutonome.schedule();
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    this.robot = Robot.getInstance();



    System.out.println("teleopInit()");
    //((RouesMecanumSynchro)robot.roues).convertirEnRouesHolonomiques(); // si necessaire
    robot.roues.setFacteur(1); // 0.8
    
    //    if (trajetAutonome != null) {
     // trajetAutonome.cancel();
    //}
  }

  @Override
  public void teleopPeriodic() {
    //System.out.println("teleopPeriodic()");

    robot.cameraLimelight.decoupageCameraDynamique();
    manette.activerBoutons();


    if (robot.capteurMagnetiqueHaut.estActive()) {
      System.out.println("capteur magnetique haut active");
    } else {
      System.out.println("capteur magnetique haut non active");
    }

    //if (robot.capteurMagnetiqueBas.estActive()) {
    //  System.out.println("capteur magnetique bas active");
    //}
    // test
    //robot.convoyeur2.setVitesse(0.2);
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
          this.boutonY.whenPressed(commandeCalibration);
  
          Command commandeMilieu = new CommandeDeplacerBras(POSITION.POSTIION_MILIEU);
          this.boutonA.whenPressed(commandeMilieu);
  
          Command commandeArriere = new CommandeDeplacerBras(POSITION.POSITION_AVANT);
          this.boutonX.whenPressed(commandeArriere);
  
          Command commandePencheDevant = new CommandeDeplacerBras(POSITION.POSITION_ARRIERE);
          this.boutonB.whenPressed(commandePencheDevant);
  
          Command commandeDevant = new CommandeDeplacerBras(POSITION.POSITION_PENCHE_AVANT);
          this.boutonRetour.whenPressed(commandeDevant);
  
          Command commandePencheArriere = new CommandeDeplacerBras(POSITION.POSITION_PENCHE_ARRIERE);
          this.boutonRetour.whenPressed(commandePencheArriere);
          
          Command commandeOuvrirMachoire = new CommandeOuvrirMachoire();
          Command commandeFermerMachoire = new CommandeFermerMachoire();
          this.boutonMainDroite.whenPressed(commandeFermerMachoire);
          this.boutonMainGauche.whenPressed(commandeOuvrirMachoire);
          */
  
      }

      public void activerBoutons()
      {
        this.getBoutonA().toggleOnTrue(new CommandeAvalerTeleop());    
        this.getBoutonB().onTrue(new CommandeLancerHaut());
        //manette.getBoutonX().toggleOnTrue(new CommandeAllerA(new Vecteur3(0, 0, 0), 0));
        this.getBoutonDemarrer().onTrue(new CommandeBrasMonter());
        this.getBoutonRetour().onTrue(new CommandeBrasDescendre());
    
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

    /*
    //System.out.println(" ly: " + this.manette.getAxeMainGauche().y + " lx: " + this.manette.getAxeMainGauche().x + " rx: " + this.manette.getAxeMainDroite().x);
    robot.roues.conduireAvecAxes(this.manette.getAxeMainGauche().y, this.manette.getAxeMainGauche().x, this.manette.getAxeMainDroite().x);

    if (manette.getBoutonPresse(Materiel.Manette.BOUTON_X)) {
      robot.lanceurAngle.ajusterHaut();
    }
    else if (manette.getBoutonPresse(Materiel.Manette.BOUTON_Y)) {
      robot.lanceurAngle.ajusterBas();
    }
     */
      }
      
  }
  // this.boutonControllerAttrapeur.whenReleased(new CommandeArmerAttrapeur());
  

}

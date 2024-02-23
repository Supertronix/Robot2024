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
    System.out.println("teleopInit()");
    this.robot = Robot.getInstance();
    //((RouesMecanumSynchro)robot.roues).convertirEnRouesHolonomiques(); // si necessaire
    robot.roues.setFacteur(1); // 0.8
    manette.activerBoutonsTests(); // autres boutons
  }

  private int periode;
  @Override
  public void teleopPeriodic() {
    periode++;

    robot.cameraLimelight.decoupageCameraDynamique();
    robot.roues.conduireAvecAxes(this.manette.getAxeMainGauche().y, this.manette.getAxeMainGauche().x, this.manette.getAxeMainDroite().x);

    if((periode % 100) == 0) // pour limiter les logs
    {
      String etatLanceurDeploye = "capteur magnetique haut (flippe) " + ((robot.lanceurExtension.estOuvert())?"ouvert":"non ouvert");
      System.out.println(etatLanceurDeploye);
      String etatLanceurRetracte = "capteur magnetique bas (non flippe)" + ((robot.lanceurExtension.estFerme())?"ferne":"non ferme");
      System.out.println(etatLanceurRetracte);
    }
  }

  @Override
  public void testInit() {
    
    CommandScheduler.getInstance().cancelAll();// Cancelle les commandes 
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
  
  @SuppressWarnings({"unused"})
  private void lierInteractions() {
    new Trigger(robot.partie::exampleCondition).onTrue(new ExempleMouvementDuRobot(robot.partie));
  }
  // https://docs.wpilib.org/en/2020/docs/software/old-commandbased/commands/running-commands-joystick-input.html
  // https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
  // https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html  
  static public class ActionManette extends Manette {
  
      protected static ActionManette instance = null;
      public static ActionManette getInstance()
      {
        if(null == ActionManette.instance) ActionManette.instance = new ActionManette();
        return ActionManette.instance;
      };
  
      //@SuppressWarnings("deprecation") // la classe ouverte fonctionne aussi bien que la nouvelle classe proprietaire
      protected ActionManette()
      {
        //this.boutonMainDroite.toggleOnTrue(new CommandeAvalerTeleop());    
        this.boutonMainDroite.onTrue(new CommandeLancerHaut());
        this.boutonMainGauche.onTrue(new CommandeLancerBas());
        this.boutonDemarrer.onTrue(new CommandeGrimper());
        this.boutonRetour.onTrue(new CommandeGrimpageRedescendre());

        this.povBas.onTrue(new CommandeAvalerAutomatique());
        this.boutonPressionMainGauche.onTrue(new CommandeAvalerTeleop());   
      }

      public void activerBoutonsTests()
      {
          this.boutonB.onTrue(new CommandeLanceurOuvrir());
          this.boutonX.onTrue(new CommandeLanceurFermer());
          this.boutonY.onTrue(new CommandeLanceurAllonger());
          this.boutonA.onTrue(new CommandeLanceurRetracter());
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
  
  //this.boutonX.toggleOnTrue(new CommandeAllerA(new Vecteur3(0, 0, 0), 0)); 
  //Command commandeMilieu = new CommandeDeplacerBras(POSITION.POSTIION_MILIEU);

}

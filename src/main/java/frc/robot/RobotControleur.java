// Code de CONTROLE du robot 2024 de Supertronix
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commande.*;
import frc.robot.commande.robot.*;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.composant.Compresseur;
import frc.robot.interaction.*;
import frc.robot.mesure.Vecteur3;

public class RobotControleur extends TimedRobot {

  private Robot robot;
  private ActionManette manette;
  private int periode;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    Compresseur.getInstance().activer();
    robot.setVoyant();
    if(!robot.estAveugle())
    {
      this.robot.cameraConducteur.initialiser();
      //CameraServer.startAutomaticCapture(); // Méthode simple, mais ne permet pas de manipuler les images
      this.robot.shuffleBoard.initialiser();
    }
  }

  // This runs after the mode specific periodic functions, but before LiveWindow and SmartDashboard integrated updating.
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    if(robot.estVoyant())
    {
      Robot.getInstance().cameraLimelight.resetDecoupageCamera();
    }
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    this.periode = 0;
    this.robot = Robot.getInstance();    
    //trajetAutonome = new CommandeTrajetAutonome();
    //trajetAutonome.schedule();
  }

  @Override
  public void autonomousPeriodic() {
    this.periode++;

    // SHUFFLEBOARD SEULEMEMT POUR DES TESTS 
    // TOUJOURS limiter la frequence avec periode en mode test
    // NE PAS ACTIVER DANS LE VRAI MODE AUTONOME FINAL
    //if((periode % 100) == 0)
    {
      //this.robot.shuffleBoard.mettreAJour();
    }

  }

  @Override
  public void teleopInit() {
    System.out.println("teleopInit()");
    this.manette = (ActionManette)RobotControleur.ActionManette.getInstance();
    this.periode = 0;
    this.robot = Robot.getInstance();
    robot.roues.convertirEnRouesHolonomiques(); // si necessaire
    robot.roues.setFacteur(1); // 0.8
    manette.activerBoutons();
    //manette.activerBoutonsTests(); // boutons temporaires pour equipe mecanique
  }

  @Override
  public void teleopPeriodic() {
    periode++;

    if(!robot.estAveugle())
    {
      robot.cameraLimelight.decoupageCameraDynamique();
    }
    robot.roues.conduireAvecAxes(this.manette.getAxeMainGauche().y, this.manette.getAxeMainGauche().x, this.manette.getAxeMainDroite().x);

    if((periode % 100) == 0 && !robot.estAveugle())
    {
      this.robot.shuffleBoard.mettreAJour();
    }
    if((periode % 100) == 0) // pour limiter les logs dans le periodic = 1 tour sur 100
    {
    //  String etatLanceurDeploye = "capteur magnetique haut (flippe) " + ((robot.lanceurExtension.estOuvert())?"ouvert":"non ouvert");
    //  System.out.println(etatLanceurDeploye);
    }
  }
  
  static public class ActionManette extends Manette {
  
      public void activerBoutons()
      {
        //this.boutonMainDroite.toggleOnTrue(new CommandeAvalerTeleop());    
        //this.boutonMainGauche.onTrue(new CommandeLancerBas());

        this.boutonDemarrer.whileTrue(new CommandeGrimper());
        this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());

        //this.boutonGachetteMainGauche.whileTrue(new CommandeAvalerTeleop());   
        this.boutonMainGauche.whileTrue(new CommandeAvalerTeleop());
        //this.gachetteMainGauche.onTrue(new CommandeAvalerAutomatiquement());
        this.boutonMainDroite.onTrue(new CommandeLancerSpeaker());
        this.boutonX.toggleOnTrue(new CommandeAllerA(new Vecteur3(0, 0, 0), 0));
        this.boutonY.onTrue(new CommandeLanceurOuvrirEtAllonger());
        this.boutonA.onTrue(new CommandeLanceurRetracterEtFermer());
      }

      public void activerBoutonsTests()
      {
          //this.boutonB.onTrue(new CommandeLanceurOuvrir());
          //this.boutonX.onTrue(new CommandeLanceurFermer());
          //this.boutonY.onTrue(new CommandeLanceurAllonger());
          //this.boutonA.onTrue(new CommandeLanceurRetracter());
          //this.povBas.onTrue(new CommandeAvalerAutomatiquement());
      }

      protected static ActionManette instance = null;
      public static ActionManette getInstance()
      {
        if(null == ActionManette.instance) ActionManette.instance = new ActionManette();
        return ActionManette.instance;
      };
  
      //@SuppressWarnings("deprecation") // la classe ouverte fonctionne aussi bien que la nouvelle classe proprietaire
      protected ActionManette()
      {
        System.out.println("new ActionManette()");
      }


  }
}
// https://docs.wpilib.org/en/2020/docs/software/old-commandbased/commands/running-commands-joystick-input.html
// https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
// https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html  

//Exemple d'option sur une commande avec enum
//Command commandeMilieu = new CommandeDeplacerBras(POSITION.POSTIION_MILIEU);

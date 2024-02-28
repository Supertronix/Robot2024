// Code de CONTROLE du robot 2024 de Supertronix
package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.MecanumDriveKinematics;
import edu.wpi.first.math.kinematics.MecanumDriveOdometry;
import edu.wpi.first.math.kinematics.MecanumDriveWheelPositions;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commande.auto.TrajetNoteDansAmplificateur;
import frc.robot.commande.auto.TrajetNoteDansSpeaker;
import frc.robot.commande.robot.*;
import frc.robot.composant.Compresseur;
import frc.robot.interaction.*;
import frc.robot.interaction.SelecteurModeAutonome.MODE;
import frc.robot.interaction.SelecteurModeAutonome.POSITION;

public class RobotControleur extends TimedRobot {

  protected Robot robot;
  protected ActionManette manette;
  protected int periode;
  protected AnimateurLed animateurLed;
  protected ShuffleBoard shuffleBoard;

  protected POSITION positionDepart;
  protected MODE modeAutonome;
  protected String designAutonome;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    Compresseur.getInstance().activer();
    this.shuffleBoard = new ShuffleBoard();
    this.shuffleBoard.initialiser();
    this.animateurLed = new AnimateurLed();
    robot.setVoyant();
  
    /*
    if(!robot.estAveugle())
    {
      this.robot.cameraConducteur.initialiser();
      //CameraServer.startAutomaticCapture(); // Méthode simple, mais ne permet pas de manipuler les images
    }*/
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
      robot.cameraLimelight.resetDecoupageCamera();
    }
    this.animateurLed.choisirAnimation(AnimateurLed.ANIMATION_AUCUNE);
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    this.periode = 0;
    this.robot = Robot.getInstance(); 
    positionDepart = SelecteurModeAutonome.getInstance().lirePosition();
    modeAutonome = SelecteurModeAutonome.getInstance().lireMode();
    if(POSITION.GAUCHE == positionDepart)
    {
      System.out.println("Position gauche");
      //trajetAutonome = new CommandeTrajetAutonome();
      //trajetAutonome.schedule();
    }
    if(POSITION.MILIEU == positionDepart)
    {
      System.out.println("Position milieu");
    }
    if(POSITION.DROITE == positionDepart)
    {
      System.out.println("Position droite");
    }

    if(MODE.AUTOMATIQUE == modeAutonome)
    {
      System.out.println("Mode automatique");
    }
    if(MODE.DESIGN == modeAutonome)
    {
      System.out.println("Mode design");
      designAutonome = SelecteurModeAutonome.getInstance().lireDesign();
      // a interpreter
    }
    this.animateurLed.communiquerAlliance();  
  }

  @Override
  public void autonomousPeriodic() {
    this.periode++;

    if((periode % 100) == 0)
    {
      this.animateurLed.choisirAnimationSelonDashboard();      
    }

    // SHUFFLEBOARD SEULEMEMT POUR DES TESTS 
    // TOUJOURS limiter la frequence avec periode en mode test
    // NE PAS ACTIVER DANS LE VRAI MODE AUTONOME FINAL
    //if((periode % 100) == 0)
    {
      //this.shuffleBoard.mettreAJour();
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
    positionDepart = SelecteurModeAutonome.getInstance().lirePosition();
  }

  @Override
  public void teleopPeriodic() {
    periode++;

    if((periode % 100) == 0)
    {
      this.animateurLed.choisirAnimationSelonDashboard();
      double[] test = Robot.getInstance().cameraLimelight.getBotpose();
      System.out.println(test[0] + " : " + test[1] + " : " + test[5]);
    }
    if(!robot.estAveugle())
    {
      //robot.cameraLimelight.decoupageCameraDynamique();
    }
    robot.roues.conduireAvecAxes(this.manette.getAxeMainGauche().y, this.manette.getAxeMainGauche().x, this.manette.getAxeMainDroite().x);

    if((periode % 10) == 0 && !robot.estAveugle())
    {
      this.shuffleBoard.mettreAJour();
    }
    if((periode % 100) == 0) // pour limiter les logs dans le periodic = 1 tour sur 100
    {
      //System.out.println("Retracte : " + Robot.getInstance().convoyeurHaut.estRetracte());
      //System.out.println("Ouvert : " + Robot.getInstance().convoyeurHaut.estOuvert());
    //  String etatLanceurDeploye = "capteur magnetique haut (flippe) " + ((robot.lanceurExtension.estOuvert())?"ouvert":"non ouvert");
    //  System.out.println(etatLanceurDeploye);
    }
  }
  
  static public class ActionManette extends Manette {

      protected static ActionManette instance = null;

      protected ActionManette()
      {
        System.out.println("new ActionManette()");
      }

      // Une méthode qui permet de mapper les différents inputs avec les actions
      public void activerBoutons()
      {
        //this.boutonMainDroite.toggleOnTrue(new CommandeAvalerTeleop());    
        //this.boutonMainGauche.onTrue(new CommandeLancerBas());

        //this.boutonGachetteMainGauche.whileTrue(new CommandeAvalerTeleop());   
        //this.boutonB.toggleOnTrue(new CommandeAvalerTeleop());
        this.boutonMainDroite.onTrue(new CommandeLanceurOuvrirEtAllonger());
        //this.boutonMainGauche.toggleOnTrue(new CommandeLanceurRetracter().andThen(new CommandeLanceurFermer()));
        this.boutonMainGauche.onTrue(new CommandeLanceurRetracterEtFermer());
        //this.gachetteMainGauche.onTrue(new CommandeAvalerAutomatiquement());
        //this.boutonY.onTrue(new CommandeLanceurOuvrirEtAllonger());

        //this.boutonA.onTrue(new CommandeLanceurOuvrir());
        //this.boutonB.onTrue(new CommandeLanceurFermer());
        //this.boutonX.onTrue(new CommandeLanceurAllonger());
        //this.boutonY.onTrue(new CommandeLanceurRetracter());
        this.boutonDemarrer.toggleOnTrue(new CommandeAvalerTeleop());
        // this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
        // this.boutonMainGauche.whileTrue(new CommandeAvalerTeleop());
        this.boutonA.toggleOnTrue(new TrajetNoteDansAmplificateur());
        this.boutonB.toggleOnTrue(new TrajetNoteDansSpeaker());

        //this.boutonX.toggleOnTrue(new TrajetNoteDansSpeaker());
        //this.boutonY.onTrue(new CommandeLancerAmpli());

        // this.boutonA.onTrue(new CommandeLanceurOuvrir());
        // this.boutonB.onTrue(new CommandeLanceurAllonger());
        // this.boutonY.onTrue(new CommandeLanceurRetracter());
        // this.boutonX.onTrue(new CommandeLanceurFermer());
        
      }

      public void activerBoutonsTests()
      {
          //this.boutonB.onTrue(new CommandeLanceurOuvrir());
          //this.boutonX.onTrue(new CommandeLanceurFermer());
          //this.boutonY.onTrue(new CommandeLanceurAllonger());
          //this.boutonA.onTrue(new CommandeLanceurRetracter());
          //this.povBas.onTrue(new CommandeAvalerAutomatiquement());
      }

      public static ActionManette getInstance()
      {
        if (null == ActionManette.instance)
          ActionManette.instance = new ActionManette();
        
        return ActionManette.instance;
      };
  }
}
// https://docs.wpilib.org/en/2020/docs/software/old-commandbased/commands/running-commands-joystick-input.html
// https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
// https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html  

//Exemple d'option sur une commande avec enum
//Command commandeMilieu = new CommandeDeplacerBras(POSITION.POSTIION_MILIEU);

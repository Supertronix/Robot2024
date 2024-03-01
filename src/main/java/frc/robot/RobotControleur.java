// Code de CONTROLE du robot 2024 de Supertronix
package frc.robot;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commande.auto.TrajetAutonomePosition2;
import frc.robot.commande.auto.TrajetNoteDansSpeaker;
import frc.robot.commande.robot.*;
import frc.robot.commande.terrain.classique.CommandeAvancer;
import frc.robot.composant.Compresseur;
import frc.robot.interaction.*;
import frc.robot.interaction.SelecteurModeAutonome.MODE;
import frc.robot.interaction.SelecteurModeAutonome.POSITION;

public class RobotControleur extends TimedRobot {

  protected Robot robot;
  protected Manette manette;
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
    this.robot.setVoyant();

    for (int port = 5800; port <= 5807; port++) {
      PortForwarder.add(port, "limelight.local", port);
    }

    if(!robot.estAveugle())
    {
      this.robot.cameraConducteur.initialiser();
      this.robot.cameraLimelight.initialiser();
      //CameraServer.startAutomaticCapture(); // Méthode simple, mais ne permet pas de manipuler les images
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
      robot.cameraLimelight.resetDecoupageCamera();
    }
    this.animateurLed.choisirAnimation(AnimateurLed.ANIMATION_AUCUNE);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    this.periode = 0;
    this.robot = Robot.getInstance(); 

    //new TrajetTest().schedule();
    this.robot.cameraLimelight.activerTargeting();
    if (Alliance.getInstance().getPositionDepart() == 2) {
      System.out.println("Position 2");
      new TrajetAutonomePosition2();
    } else {
        System.out.println("Position 1 ou 3");
      new CommandeLancerSpeaker();
      //new WaitCommand(1).andThen(new ViserTag());
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

    this.robot = Robot.getInstance();
    robot.roues.convertirEnRouesHolonomiques(); // si necessaire
    robot.roues.setFacteur(1); // 0.8

    this.manette = (ActionManette)RobotControleur.ActionManette.getInstance();
    //this.manette = (TestManette)RobotControleur.TestManette.getInstance();
    manette.activerBoutons();
    this.periode = 0;
    
    //manette.activerBoutonsTests(); // boutons temporaires pour equipe mecanique
    positionDepart = SelecteurModeAutonome.getInstance().lirePosition();
    this.animateurLed.communiquerAlliance();  
  }

  @Override
  public void teleopPeriodic() {
    periode++;

    robot.roues.conduireAvecAxes(this.manette.getAxeMainGauche().y, this.manette.getAxeMainGauche().x, this.manette.getAxeMainDroite().x);

    // robot.convoyeurHaut.activer(this.manette.getAxeMainDroite().y);
    if((periode % 10) == 0 && !robot.estAveugle())
    {
      this.shuffleBoard.mettreAJour();
      this.robot.cameraLimelight.afficherPosition();
    }
    if((periode % 100) == 0) // pour limiter les logs dans le periodic = 1 tour sur 100
    {
      this.animateurLed.choisirAnimationSelonDashboard();  
      //System.out.println("Retracte : " + Robot.getInstance().convoyeurHaut.estRetracte());
      //System.out.println("Ouvert : " + Robot.getInstance().convoyeurHaut.estOuvert());
      //String etatLanceurDeploye = "capteur magnetique haut (flippe) " + ((robot.lanceurExtension.estOuvert())?"ouvert":"non ouvert");
      //System.out.println(etatLanceurDeploye);

      //double[] test = Robot.getInstance().cameraLimelight.getBotpose();
      //System.out.println(test[0] + " : " + test[1] + " : " + test[5]);

      if(!robot.estAveugle())
      {
        //robot.cameraLimelight.decoupageCameraDynamique();
        robot.cameraLimelight.verifierUtilite();
      }
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
        //this.boutonMainDroite.onTrue(new CommandeLanceurOuvrirEtAllonger());
        //this.boutonMainGauche.onTrue(new CommandeLanceurRetracterEtFermer());

        this.boutonMainGauche.toggleOnTrue(new CommandeAvalerTeleop());
        this.boutonMainDroite.toggleOnTrue(new CommandeLancerSpeaker());

        this.boutonA.onTrue(new CommandeLanceurOuvrirEtAllonger());
        this.boutonB.onTrue(new CommandeLanceurRetracterEtFermer());
        this.boutonX.onTrue(new TrajetNoteDansSpeaker());
        this.boutonY.onTrue(new CommandeLancerAmplificateur());

        this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
        this.boutonDemarrer.whileTrue(new CommandeGrimper());        
      }




      public void activerBoutonsTest()
      {
        this.boutonMainDroite.onTrue(new CommandeLanceurOuvrirEtAllonger());
        this.boutonMainGauche.onTrue(new CommandeLanceurRetracterEtFermer());
        this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
        this.boutonDemarrer.whileTrue(new CommandeGrimper());

        this.boutonA.onTrue(new CommandeAvalerTeleop());
        this.boutonB.onTrue(new CommandeLancerSpeaker());
        this.boutonX.onTrue(new CommandeLancerSpeaker());

        this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
        this.boutonDemarrer.whileTrue(new CommandeGrimper());

        /*
        this.boutonA.onTrue(new CommandeLanceurOuvrir());
        this.boutonB.onTrue(new CommandeLanceurAllonger());
        this.boutonY.onTrue(new CommandeLanceurRetracter());
        this.boutonX.onTrue(new CommandeLanceurFermer());

        /*

        this.boutonA.toggleOnTrue(new CommandeAvalerTeleop());
        this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
        this.boutonDemarrer.whileTrue(new CommandeGrimper());
        //this.boutonX.toggleOnTrue(new TrajetNoteDansSpeaker());
        //this.boutonY.onTrue(new CommandeLancerAmpli());

        this.boutonX.onTrue(new TrajetNoteDansSpeaker());
        this.boutonY.onTrue(new TrajetNoteDansAmplificateur());

        //this.boutonA.onTrue(new CommandeLanceurOuvrir());
        //this.boutonB.onTrue(new CommandeLanceurAllonger());
        //this.boutonY.onTrue(new CommandeLanceurRetracter());
        //this.boutonX.onTrue(new CommandeLanceurFermer());

         */
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

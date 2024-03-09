// Code de CONTROLE du robot 2024 de Supertronix
package frc.robot;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commande.auto.TrajetAutonomePosition1;
import frc.robot.commande.auto.TrajetAutonomePosition2;
import frc.robot.commande.auto.TrajetAutonomePosition3;
import frc.robot.commande.auto.TrajetNoteDansSpeaker;
import frc.robot.commande.robot.*;
import frc.robot.interaction.*;

public class RobotControleur extends TimedRobot {

  protected int periode;
  protected Robot robot;
  protected StationPilotage station;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    this.station = StationPilotage.getInstance();
    this.robot.activerCompresseur();
    station.animateurLed = new AnimateurLed();
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
    station.animateurLed.choisirAnimation(AnimateurLed.ANIMATION_AUCUNE);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {

    this.periode = 0;
    this.robot = Robot.getInstance();
    this.station = StationPilotage.getInstance();
    
    this.robot.compresseur.desactiver();
    this.robot.cameraLimelight.activerTargeting();

    int choixAuto = station.selecteurAutonome.lirePositionNumerique();
    System.out.println("choixMode autonome : " + choixAuto);
    switch(choixAuto)
    {
      case 1:
        System.out.println("Position 1");
        new TrajetAutonomePosition1().schedule();
      break;
      case 2:
        System.out.println("Position 2");
        new TrajetAutonomePosition2().schedule();
      break;      
      case 3:
        System.out.println("Position 3");
        new TrajetAutonomePosition3().schedule();
      break;
    }

    station.animateurLed.communiquerAlliance();  
  }

  @Override
  public void autonomousPeriodic() {
    this.periode++;

    if((periode % 100) == 0)
    {
      station.animateurLed.choisirAnimationSelonDashboard();      
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
    this.robot.compresseur.activer();

    station.manette = (ManetteTest)ManetteTest.getInstance(); // boutons temporaires pour equipe mecanique
    station.manette.activerBoutons(); // appel uniformise   
    station.animateurLed.communiquerAlliance();  

    this.periode = 0;
  }

  @Override
  public void teleopPeriodic() {
    periode++;

    robot.roues.conduireAvecAxes(station.manette.getAxeMainGauche().y, station.manette.getAxeMainGauche().x, station.manette.getAxeMainDroite().x);

    // robot.convoyeurHaut.activer(this.manette.getAxeMainDroite().y);
    if((periode % 10) == 0 && !robot.estAveugle())
    {
      station.shuffleBoard.mettreAJour();
      this.robot.cameraLimelight.afficherPosition();
    }
    if((periode % 100) == 0) // pour limiter les logs dans le periodic = 1 tour sur 100
    {
      station.animateurLed.choisirAnimationSelonDashboard();  
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

        this.boutonMainGauche.toggleOnTrue(new CommandeAvalerTeleop());
        this.boutonMainDroite.toggleOnTrue(new CommandeLancerSpeaker());

        this.boutonA.onTrue(new CommandeLanceurOuvrirEtAllonger());
        this.boutonB.onTrue(new CommandeLanceurRetracterEtFermer());
        this.boutonX.toggleOnTrue(new TrajetNoteDansSpeaker());
        this.boutonY.onTrue(new CommandeLancerAmplificateur());

        this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
        this.boutonDemarrer.whileTrue(new CommandeGrimper());        
      }

      public static ActionManette getInstance()
      {
        if (null == ActionManette.instance)
          ActionManette.instance = new ActionManette();
        
        return ActionManette.instance;
      };
  }
}


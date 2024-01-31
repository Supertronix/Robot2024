// Code de CONTROLE du robot 2024 de Supertronix

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commande.MouvementDuRobot;
import frc.robot.soussysteme.*;
import frc.robot.interaction.*;

public class RobotControleur extends TimedRobot {

  private Robot robot;
  private Manette manette;
  //private Command trajetAutonome;

  @Override
  public void robotInit() {
    this.robot = Robot.getInstance();
    this.manette = Manette.getInstance();
  }

  // This runs after the mode specific periodic functions, but before LiveWindow and SmartDashboard integrated updating.
  // polling buttons // adding newly-scheduled commands, // running already-scheduled commands, removing finished or interrupted commands // running subsystem periodic() methods.  
  // This must be called from the robot's periodic block in order for anything in the Command-based framework to work.
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

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

}

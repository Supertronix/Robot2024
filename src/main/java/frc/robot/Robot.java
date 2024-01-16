package frc.robot;

import frc.robot.soussysteme.*;

/**
 * Classe qui definit materiellement le robot
 */
public class Robot {

  public PartieDuRobot partie = new PartieDuRobot();
  public Roues roues = null;


  // Replace with CommandPS4Controller or CommandJoystick if needed
  //private final CommandXboxController m_driverController = new CommandXboxController(0);

  public Robot() 
  {
    this.roues = new RouesMecanumSynchro();  //this.roues = new RouesMecanum();
  }

  public static Robot instance = null;
  public static Robot getInstance()
  {
      if(Robot.instance == null) Robot.instance = new Robot();
      return Robot.instance;
  }


}

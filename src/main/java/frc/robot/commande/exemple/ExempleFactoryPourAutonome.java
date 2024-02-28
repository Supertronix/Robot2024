package frc.robot.commande.exemple;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.soussysteme.exemple.ExemplePartieDuRobot;

public final class ExempleFactoryPourAutonome {

  public static Command exampleAuto(ExemplePartieDuRobot soussysteme) {
    return Commands.sequence(soussysteme.exampleMethodCommand(), new ExempleAvecSousSysteme(soussysteme));
  }

  private ExempleFactoryPourAutonome() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}

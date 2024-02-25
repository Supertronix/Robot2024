// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commande.exemple;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.soussysteme.exemple.ExemplePartieDuRobot;

public final class ExempleAutos {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(ExemplePartieDuRobot subsystem) {
    return Commands.sequence(subsystem.exampleMethodCommand(), new ExempleMouvementDuRobot(subsystem));
  }

  private ExempleAutos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}

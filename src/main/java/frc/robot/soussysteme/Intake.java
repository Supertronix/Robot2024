// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.soussysteme;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;

import frc.robot.Materiel;
import frc.robot.composant.MoteurTalon;

/** Add your docs here. */
public class Intake extends SousSysteme implements Materiel.Intake
{
    protected MoteurTalon moteurTalon;

    public Intake() {
        moteurTalon = new MoteurTalon(ID_TALON_INTAKE);
        moteurTalon.setInverted(true);
    }

    public void setSpeed()
    {
        moteurTalon.set(TalonSRXControlMode.PercentOutput, VITESSE_TALON_INTAKE);
    }
}

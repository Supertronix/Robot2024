package frc.robot.commande.terrain.duree;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.soussysteme.Roues;
import frc.robot.mesure.LimiteurDuree;

public class CommandeTasserGaucheSelonDuree extends SequentialCommandGroup {

    protected Roues roues = null;
    protected LimiteurDuree detecteur;

    protected double temps = 0;
    protected double vitesse = 0;

    /**
     * Cette classe tasse a droite avec un nombre positif et a gauche avec un nombre negatif
     * @param tempsMs temps en milisecondes
     * @param vitesse de deplacement
     */
    public CommandeTasserGaucheSelonDuree(double temps, double vitesse)
    {
        this.addCommands(
            new CommandeTasserSelonDuree(temps, vitesse)
        );
    }
}

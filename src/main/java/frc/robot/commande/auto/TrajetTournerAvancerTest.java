package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.terrain.classique.CommandeAvancer;
import frc.robot.commande.terrain.classique.CommandeTourner;

public class TrajetTournerAvancerTest extends SequentialCommandGroup {
    public TrajetTournerAvancerTest() {
        addCommands(
                new CommandeTourner(45),
                new CommandeAvancer(10, 2000),
                new CommandeTourner(180),
                new CommandeAvancer(10, 2000),
                new CommandeTourner(-45)
        );
    }
}

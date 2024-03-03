package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.terrain.classique.CommandeTourner;

public class TrajetTournerTest extends SequentialCommandGroup {
    public TrajetTournerTest() {
        addCommands(
                new CommandeTourner(30),
                new CommandeTourner(-30),
                new CommandeTourner(45),
                new CommandeTourner(-45),
                new CommandeTourner(90),
                new CommandeTourner(-90),
                new CommandeTourner(180),
                new CommandeTourner(-180)
        );
    }
}

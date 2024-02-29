package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commande.robot.*;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.CommandeAllerASelonTriplePID;
import frc.robot.interaction.Alliance;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;

public class TrajetNoteDansAmplificateur extends Command {
    public TrajetNoteDansAmplificateur()
	{
		System.out.println("TrajetNoteDansAmplificateur()");
	}

	@Override
	public void initialize()
	{
		System.out.println("TrajetNoteDansAmplificateur.initialize()");

		Vecteur3 cible;
		double angleCible;
		if (Alliance.getInstance().getAllianceRouge()) {
			cible = new Vecteur3(AprilTags.AmplificateurRouge.X, AprilTags.AmplificateurRouge.Y, 0);
			angleCible = AprilTags.AmplificateurRouge.ANGLE;
		} else {
			cible = new Vecteur3(AprilTags.AmplificateurBleu.X, AprilTags.AmplificateurBleu.Y, 0);
			angleCible = AprilTags.AmplificateurBleu.ANGLE;
		}
		new CommandeAllerA(cible, angleCible).andThen(new CommandeLanceurOuvrirEtAllonger()).schedule();
	}

	@Override
	public void end(boolean interrupted) {
		System.out.println("TrajetNoteDansAmplificateur.end()");
	}
}

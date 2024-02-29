package frc.robot.commande.auto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Robot;
import frc.robot.commande.robot.CommandeLancerSpeaker;
import frc.robot.commande.robot.CommandeLanceurAllonger;
import frc.robot.commande.robot.CommandeLanceurOuvrir;
import frc.robot.commande.terrain.CommandeAllerA;
import frc.robot.commande.terrain.CommandeAllerASelonTriplePID;
import frc.robot.interaction.Alliance;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;
import frc.robot.soussysteme.RouesMecanum;

public class TrajetNoteDansSpeaker extends Command {
    public TrajetNoteDansSpeaker()
	{
		System.out.println("TrajetNoteDansSpeaker()");
	}

	@Override
	public void initialize()
	{
		System.out.println("TrajetNoteDansSpeaker.initialize()");

		Vecteur3 cible;
		double angleCible;
		if (Alliance.getInstance().getAllianceRouge()) {
			System.out.println("Aller chercher le speaker rouge");
			cible = new Vecteur3(AprilTags.SpeakerRouge.X, AprilTags.SpeakerRouge.Y, 0);
			angleCible = AprilTags.SpeakerRouge.ANGLE;
		} else {
			System.out.println("Aller chercher le speaker bleu");
			cible = new Vecteur3(AprilTags.SpeakerBleu.X, AprilTags.SpeakerBleu.Y, 0);
			angleCible = AprilTags.SpeakerBleu.ANGLE;
		}
		new CommandeAllerA(cible, angleCible).andThen(new CommandeLancerSpeaker()).schedule();
	}

	@Override
	public void end(boolean interrupted) {
		System.out.println("TrajetNoteDansSpeaker.end()");
	}
}

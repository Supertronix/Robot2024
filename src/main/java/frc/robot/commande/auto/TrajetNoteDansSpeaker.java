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
import frc.robot.mesure.LimiteurDuree;
import frc.robot.mesure.Vecteur3;
import frc.robot.soussysteme.AprilTags;
import frc.robot.soussysteme.RouesMecanum;

public class TrajetNoteDansSpeaker extends Command {
	CommandeLancerSpeaker commandeLancerSpeaker;
	CommandeAllerA commandeAllerA;
	public static int DUREE = 8000;
	protected LimiteurDuree detecteurDuree;
    public TrajetNoteDansSpeaker()
	{
		System.out.println("TrajetNoteDansSpeaker()");
		detecteurDuree = new LimiteurDuree(DUREE);
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
		commandeLancerSpeaker = new CommandeLancerSpeaker();
		commandeAllerA = new CommandeAllerA(cible, angleCible);
		commandeAllerA.andThen(commandeLancerSpeaker).schedule();
	}

	@Override
	public boolean isFinished() {
		// Vérifie si la commandeLancerSpeaker est terminée
		if (commandeLancerSpeaker.isFinished() || commandeAllerA.isFinished()) {
			return true;
		}
		if (detecteurDuree.estTropLongue()) {
			return true;
		}
		return false;
	}


	@Override
	public void end(boolean interrupted) {
		commandeAllerA.end(interrupted);
		commandeLancerSpeaker.end(interrupted);
		System.out.println("TrajetNoteDansSpeaker.end()");
	}
}

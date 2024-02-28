package frc.robot.commande.exemple;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.soussysteme.exemple.ExemplePartieDuRobot;

/** Un exemple de commande qui utilise un sous-systeme */
public class ExempleAvecSousSysteme extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "unused"})
  private final ExemplePartieDuRobot soussysteme;

  public ExempleAvecSousSysteme(ExemplePartieDuRobot sousysteme) {
    this.soussysteme = sousysteme;
    addRequirements(sousysteme);
  }

  // Appele quand la commande est : scheduled.
  @Override
  public void initialize() {}

  // Appele a chaque fois que le schduler runs apres le initialize
  @Override
  public void execute() {}

  // Appele une fois que la commande fini ou est interrompue
  @Override
  public void end(boolean interrupted) {}

  // Retourne vrai quand on veut que la commande termine
  @Override
  public boolean isFinished() {
    return false;
  }
}

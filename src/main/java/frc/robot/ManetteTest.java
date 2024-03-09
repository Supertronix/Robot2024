package frc.robot;

import frc.robot.commande.robot.*;
import frc.robot.interaction.Manette;

public class ManetteTest extends Manette{
    protected static ManetteTest instance = null;
    public static ManetteTest getInstance()
    {
    if (null == ManetteTest.instance)
        ManetteTest.instance = new ManetteTest();
    
    return ManetteTest.instance;
    };

    public void activerBoutons()
    {
    this.boutonMainDroite.onTrue(new CommandeLanceurOuvrirEtAllonger());
    this.boutonMainGauche.onTrue(new CommandeLanceurRetracterEtFermer());
    this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
    this.boutonDemarrer.whileTrue(new CommandeGrimper());

    this.boutonA.onTrue(new CommandeAvalerTeleop());
    this.boutonB.onTrue(new CommandeLancerSpeaker());
    this.boutonX.onTrue(new CommandeLancerSpeaker());

    this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
    this.boutonDemarrer.whileTrue(new CommandeGrimper());

    /*
    this.boutonA.onTrue(new CommandeLanceurOuvrir());
    this.boutonB.onTrue(new CommandeLanceurAllonger());
    this.boutonY.onTrue(new CommandeLanceurRetracter());
    this.boutonX.onTrue(new CommandeLanceurFermer());

    /*

    this.boutonA.toggleOnTrue(new CommandeAvalerTeleop());
    this.boutonRetour.whileTrue(new CommandeGrimpageRedescendre());
    this.boutonDemarrer.whileTrue(new CommandeGrimper());
    //this.boutonX.toggleOnTrue(new TrajetNoteDansSpeaker());
    //this.boutonY.onTrue(new CommandeLancerAmpli());

    this.boutonX.onTrue(new TrajetNoteDansSpeaker());
    this.boutonY.onTrue(new TrajetNoteDansAmplificateur());

    //this.boutonA.onTrue(new CommandeLanceurOuvrir());
    //this.boutonB.onTrue(new CommandeLanceurAllonger());
    //this.boutonY.onTrue(new CommandeLanceurRetracter());
    //this.boutonX.onTrue(new CommandeLanceurFermer());

        */
    }
}

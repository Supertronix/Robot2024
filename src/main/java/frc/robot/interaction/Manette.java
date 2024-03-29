package frc.robot.interaction;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Materiel;
import frc.robot.Cinematique;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.*; // POVButton

public class Manette implements Materiel.Manette, Cinematique.Manette {

    protected Joystick manette = null;
    protected List<Command> commandes;

    protected JoystickButton boutonY;
    protected JoystickButton boutonA;
    protected JoystickButton boutonDemarrer;
    protected JoystickButton boutonX;
    protected JoystickButton boutonB;
    protected JoystickButton boutonRetour;
    protected JoystickButton boutonMainDroite;
    protected JoystickButton boutonMainGauche;

    protected BoutonDeclencheur gachetteMainDroite;
    protected BoutonDeclencheur gachetteMainGauche;

    protected Manette() // pour design pattern singleton
    {
        this.manette = new Joystick(MANETTE);

        this.commandes = new ArrayList<Command>();

        this.preparerPointDeVue();

        //this.boutonPressionMainGauche.setCommande(new CommandeAjusterBras(-0.15));
        //this.boutonPressionMainDroite.setCommande(new CommandeAjusterBras(0.15 ));
        this.gachetteMainGauche = new BoutonDeclencheur(this.manette, GACHETTE_MAIN_GAUCHE);
        this.gachetteMainDroite = new BoutonDeclencheur(this.manette, GACHETTE_MAIN_DROITE);

        this.boutonY = new JoystickButton(this.manette, BOUTON_Y);
        this.boutonA = new JoystickButton(this.manette, BOUTON_A);
        this.boutonX = new JoystickButton(this.manette, BOUTON_X);
        this.boutonB = new JoystickButton(this.manette, BOUTON_B);
        this.boutonDemarrer = new JoystickButton(this.manette, BOUTON_DEMARRER);
        this.boutonRetour = new JoystickButton(this.manette, BOUTON_RETOUR);
        this.boutonMainDroite = new JoystickButton(this.manette, BOUTON_MAIN_DROITE);
        this.boutonMainGauche = new JoystickButton(this.manette, BOUTON_MAIN_GAUCHE);
    }

    //public static void desactiverInstance()
    //{
    //	Manette.instance = null;    	
    //}

    public class Axe 
    {
      public Axe(double x, double y)
      {
        this.x = ( Math.abs(x) > SEUIL_ZERO )?  x : 0;
        this.y = ( Math.abs(y) > SEUIL_ZERO )? -y : 0;
      }
      public double x;
      public double y;
    }

    protected Axe axeMainDroite = null;
    protected Axe axeMainGauche = null;

    
    /** 
     * @return Axe
     */
    public Axe getAxeMainDroite()
    {
      this.axeMainDroite = new Axe(manette.getRawAxis(AXE_DROIT_X), manette.getRawAxis(AXE_DROIT_Y));
      // System.out.println("axe main droite " + this.axeMainDroite.x + " " + this.axeMainDroite.y);
      return this.axeMainDroite;
    }

    public Axe getAxeMainGauche()
    {
      this.axeMainGauche = new Axe(manette.getRawAxis(AXE_GAUCHE_X), manette.getRawAxis(AXE_GAUCHE_Y));
      // System.out.println("axe main gauche " + this.axeMainGauche.x + " " + this.axeMainGauche.y);
      this.axeMainGauche.x = this.axeMainGauche.x - BIAIS_AXE_GAUCHE_X;
      return this.axeMainGauche;
    }

    public JoystickButton getBoutonA() {
      return this.boutonA;
    }

    public JoystickButton getBoutonB() {
      return this.boutonB;
    }

    public JoystickButton getBoutonX() {
      return this.boutonX;
    }

    public JoystickButton getBoutonY() {
      return this.boutonY;
    }

    public JoystickButton getBoutonDemarrer() {
      return this.boutonDemarrer;
    }

    public JoystickButton getBoutonRetour() {
      return this.boutonRetour;
    }

    public double getGachetteMainGauche() 
    {
      //System.out.println("Pression main gauche" + manette.getRawAxis(MAIN_GAUCHE_AXE));
    	return manette.getRawAxis(GACHETTE_MAIN_GAUCHE);
    }

    public double getGachetteMainDroite() 
    {
      //System.out.println("Pression main droite" + manette.getRawAxis(MAIN_DROITE_AXE));
    	return manette.getRawAxis(GACHETTE_MAIN_DROITE);
    }

    // 1 = droite, 0 tout droit, -1 = gauche
    public int getDirection()
    {
      if(this.axeMainDroite.y > SEUIL_AXES_OPPOSES) if(this.axeMainGauche.y < -SEUIL_AXES_OPPOSES) return -1;
      if(this.axeMainGauche.y > SEUIL_AXES_OPPOSES) if(this.axeMainDroite.y < -SEUIL_AXES_OPPOSES) return 1;
      return 0;
    }
    
    /*
     * Retourne l'état du bouton passé en paramètre
     * @return boolean True si le bouton vient de passer de non-pressé à pressé, sinon False
     */
    public boolean getBoutonPresse(int bouton) {
      return this.manette.getRawButtonPressed(bouton);
    }

    /*
     * Retourne l'état du bouton passé en paramètre
     * @return boolean True si le bouton est maintenu, sinon False
     */
    public boolean getBoutonMaintenu(int bouton) {
      return this.manette.getRawButton(bouton);
    }

    public boolean getPOVBoutonPresse(Materiel.Manette.ANGLE angle) {
        return this.manette.getPOV() == ANGLE_POV.get(angle);
    }

    public boolean savoirSiBoutonDroitPresse()
    {
    	System.out.println("Manette.savoirSiBoutonDroitPresse()");
    	return this.manette.getRawButtonPressed(BOUTON_MAIN_DROITE);
    }
    
    public boolean savoirSiBoutonGauchePresse()
    {
    	System.out.println("Manette.savoirSiBoutonGauchePresse()");
    	return this.manette.getRawButtonPressed(BOUTON_MAIN_GAUCHE);
    }

    protected POVButton povHaut;
    protected POVButton povHautDroit;
    protected POVButton povDroit;
    protected POVButton povBasDroit;
    protected POVButton povBas;
    protected POVButton povBasGauche;
    protected POVButton povGauche;
    protected POVButton povHautGauche;

    //On joysticks, the POV is a directional hat that can select one of 8 different angles or read -1 for unpressed.
    //for(int i = 0; i < 8; i++) {
    public void preparerPointDeVue()
    {
      this.povHaut = new POVButton(manette, ANGLE_POV.get(ANGLE.HAUT));
      this.povHautDroit = new POVButton(manette, ANGLE_POV.get(ANGLE.HAUT_DROIT));
      this.povDroit = new POVButton(manette, ANGLE_POV.get(ANGLE.DROIT));
      this.povBasDroit = new POVButton(manette, ANGLE_POV.get(ANGLE.BAS_DROIT));
      this.povBas = new POVButton(manette, ANGLE_POV.get(ANGLE.BAS));
      this.povBasGauche = new POVButton(manette, ANGLE_POV.get(ANGLE.BAS_GAUCHE));
      this.povGauche = new POVButton(manette, ANGLE_POV.get(ANGLE.GAUCHE));
      this.povHautGauche = new POVButton(manette, ANGLE_POV.get(ANGLE.HAUT_GAUCHE));
    }
    public void activerBoutons()
    {
    }
    public void executerActions()
    {
      if(this.gachetteMainGauche.getAsBoolean())
      {
        this.gachetteMainGauche.declencher();
      }
      if(this.gachetteMainDroite.getAsBoolean())
      {
        this.gachetteMainDroite.declencher();
      }
    	//for(Command commande : this.commandes)
      //{
      //  commande.initialize();
      //}
    }         
}

// https://docs.wpilib.org/en/2020/docs/software/old-commandbased/commands/running-commands-joystick-input.html
// https://docs.wpilib.org/en/stable/docs/software/basic-programming/joystick.html
// https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html  

//Exemple d'option sur une commande avec enum
//Command commandeMilieu = new CommandeDeplacerBras(POSITION.POSTIION_MILIEU);

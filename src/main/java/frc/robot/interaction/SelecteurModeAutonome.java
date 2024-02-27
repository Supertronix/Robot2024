package frc.robot.interaction;

import frc.robot.Materiel;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SelecteurModeAutonome implements Materiel{

    protected int position = 0;
    protected SendableChooser<String> selecteurPositionDepart;
    public enum POSITION{GAUCHE, MILIEU, DROITE};
    protected HashMap<String, POSITION> positions = new HashMap<String,POSITION>();
    
    protected static String LIBELLE_SELECTEUR_POSITION = "Position Depart";

    private SelecteurModeAutonome()
    {
      selecteurPositionDepart = new SendableChooser<String>();
      selecteurPositionDepart.setDefaultOption("Milieu", "M");
      selecteurPositionDepart.addOption("Gauche", "G");
      selecteurPositionDepart.addOption("Droit", "D");
      SmartDashboard.putData(LIBELLE_SELECTEUR_POSITION, selecteurPositionDepart);	
  
      positions.put("M", POSITION.MILIEU);
      positions.put("G", POSITION.GAUCHE);
      positions.put("D", POSITION.DROITE);
    }
    
    static protected SelecteurModeAutonome instance = null;    
    static public SelecteurModeAutonome getInstance()
    {
    	if(null == instance) instance = new SelecteurModeAutonome();
    	return instance;
    }
    
    public POSITION lirePosition()
    {
        String positionDepart = this.selecteurPositionDepart.getSelected();
        System.out.println(positionDepart);
        return positions.get(positionDepart);
    }	
}

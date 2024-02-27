package frc.robot.interaction;

import frc.robot.Materiel;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SelecteurModeAutonome implements Materiel{

    protected int position = 0;
    protected SendableChooser<String> selecteurPositionDepart;
    protected SendableChooser<String> selecteurMode;

    public enum POSITION{GAUCHE, MILIEU, DROITE};
    public enum MODE {AUTOMATIQUE, DESIGN};

    protected HashMap<String, POSITION> positions = new HashMap<String,POSITION>();
    protected HashMap<String, MODE> modes = new HashMap<String,MODE>();

    protected static String LIBELLE_SELECTEUR_POSITION = "Position Depart";
    protected static String LIBELLE_SELECTEUR_MODE = "Mode Autonome";
    private SelecteurModeAutonome()
    {
      selecteurPositionDepart = new SendableChooser<String>();
      selecteurPositionDepart.setDefaultOption("MILIEU", "M");
      selecteurPositionDepart.addOption("GAUCHE", "G");
      selecteurPositionDepart.addOption("DROIT", "D");
      SmartDashboard.putData(LIBELLE_SELECTEUR_POSITION, selecteurPositionDepart);	
  
      positions.put("M", POSITION.MILIEU);
      positions.put("G", POSITION.GAUCHE);
      positions.put("D", POSITION.DROITE);
 
      selecteurMode = new SendableChooser<String>();
      selecteurMode.setDefaultOption("AUTOMATIQUE", "A");
      selecteurMode.addOption("DESIGN", "D");
      SmartDashboard.putData(LIBELLE_SELECTEUR_MODE, selecteurMode);
      modes.put("A", MODE.AUTOMATIQUE);
      modes.put("D", MODE.DESIGN);      
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
        return positions.get(positionDepart);
    }	

    public MODE lireMode()
    {
        String mode = this.selecteurMode.getSelected();
        System.out.println(mode);
        return modes.get(mode);
    }
}

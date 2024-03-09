package frc.robot.interaction;

import frc.robot.Materiel;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SelecteurModeAutonomeNumerote implements Materiel{

    protected int position = 0;
    protected SendableChooser<Integer> selecteurPositionDepart;

    public enum POSITION{UN, DEUX, TROIS};
    public enum MODE {AUTOMATIQUE, DESIGN};

    protected HashMap<Integer, POSITION> positions = new HashMap<Integer,POSITION>();
    protected HashMap<String, MODE> modes = new HashMap<String,MODE>();

    protected static String LIBELLE_SELECTEUR_POSITION = "Selecteur AUTO";

    private SelecteurModeAutonomeNumerote()
    {
      selecteurPositionDepart = new SendableChooser<>();
      selecteurPositionDepart.setDefaultOption("Auto1", 1);
      selecteurPositionDepart.addOption("Auto2", 2);
      selecteurPositionDepart.addOption("Auto3", 3);
      SmartDashboard.putData(LIBELLE_SELECTEUR_POSITION, selecteurPositionDepart);	
  
      positions.put(1, POSITION.UN);
      positions.put(2, POSITION.DEUX);
      positions.put(3, POSITION.TROIS);
     }
    
    static protected SelecteurModeAutonomeNumerote instance = null;    
    static public SelecteurModeAutonomeNumerote getInstance()
    {
    	if(null == instance) instance = new SelecteurModeAutonomeNumerote();
    	return instance;
    }
    
    public POSITION lirePosition()
    {
        Integer positionDepart = this.selecteurPositionDepart.getSelected();
        return positions.get(positionDepart);
    }	
    public int lirePositionNumerique()
    {
        return this.selecteurPositionDepart.getSelected();
    }	}

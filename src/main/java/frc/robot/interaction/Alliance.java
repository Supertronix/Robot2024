package frc.robot.interaction;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;

public class Alliance {

    public static Alliance instance;
    public static Alliance getInstance()
    {
        if(null == instance)
        {
            instance = new Alliance();
            return instance;
        }
        return instance;
    }
    private Alliance()
    {

    }

   private boolean allianceRouge = true;
   private boolean verrouChangementAlliance = false;
   private Optional<DriverStation.Alliance> couleur;

  public boolean getAllianceRouge() {
      couleur = DriverStation.getAlliance(); // todo cacher si pas trop tot
      if (couleur.isEmpty()) {
          //System.out.println("Alliance introuvable, défaut à rouge");
          return true;
      }
      this.allianceRouge = couleur.get() != DriverStation.Alliance.Blue;
      return allianceRouge;
  }


/*
  public void setAlliance(boolean estRouge) {
      if (this.verrouChangementAlliance) return;
      this.allianceRouge = estRouge;
  } */

}

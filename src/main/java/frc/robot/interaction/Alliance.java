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

  public void setAlliance(boolean estRouge) {
      if (this.verrouChangementAlliance) return;
      this.allianceRouge = estRouge;
  }

  public boolean getAllianceRouge() {
      Optional<DriverStation.Alliance> couleur = DriverStation.getAlliance();
      if (couleur.isEmpty()) {
          //System.out.println("Alliance introuvable, défaut à rouge");
          return true;
      }
      this.allianceRouge = couleur.get() != DriverStation.Alliance.Blue;
      return this.allianceRouge;
  }

}

package frc.robot.interaction;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;

public class Alliance {

    public static Alliance instance;
    public static Alliance getInstance()
    {
        if(null == instance)
        {
            return instance;
        }
        return instance;
    }
    private Alliance()
    {

    }

    private boolean allianceRouge = true;
    private boolean verrouChangementAlliance = false;
  
    public void miseAJourAlliance() {
      if (this.verrouChangementAlliance) return;

      Optional<DriverStation.Alliance> couleur = DriverStation.getAlliance();
      if (couleur.isEmpty()) {
          //System.out.println("Alliance introuvable, défaut à rouge");
          this.allianceRouge = true;
          return;
      }
      this.allianceRouge = couleur.get() != DriverStation.Alliance.Blue;
  }

  public void setAlliance(boolean estRouge) {
      if (this.verrouChangementAlliance) return;
      this.allianceRouge = estRouge;
  }

  /**
   * @param verrou
   * Empêche le changement d'alliance si la méthode automatique ne fonctionne pas
   */
  public void setVerrouChangementAlliance(boolean verrou) {
      this.verrouChangementAlliance = verrou;
  }

  public boolean getAllianceRouge() {
      miseAJourAlliance();
      return this.allianceRouge;
  }

}

package frc.robot.interaction;

import edu.wpi.first.wpilibj.XboxController;

public class ManetteTestMoteurs {


    private static ManetteTestMoteurs instance = null;

    protected ManetteTestMoteurs() // pour design pattern singleton
    {
        // test manette xbox

    }

    
    /** 
     * @return boolean
     */
    public boolean getAButton() {
        return true;
    }

}

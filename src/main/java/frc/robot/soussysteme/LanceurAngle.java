package frc.robot.soussysteme;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class LanceurAngle {

    private Solenoid solenoid;
    public LanceurAngle() {
        solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    }

    public void ajusterHaut(){
        solenoid.set(true);
    }
    public void ajusterBas(){
        solenoid.set(false);
    }

}
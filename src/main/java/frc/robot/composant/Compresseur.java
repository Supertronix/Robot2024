package frc.robot.composant;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

// pour avoir un seul compresseur = singleton
public class Compresseur extends Compressor{
    
    public Compresseur(int id)
    {
        super(id, PneumaticsModuleType.CTREPCM);

    }
}

package frc.robot.composant;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Materiel;

// pour avoir un seul compresseur = singleton
public class Compresseur extends Compressor{
    

    private static Compresseur instance = null;
    public static Compresseur getInstance()
    {
        if(instance == null) instance = new Compresseur(Materiel.COMPRESSEUR_MODULE);
        return instance;
    }
    private Compresseur(int id)
    {
        super(id, PneumaticsModuleType.CTREPCM);
    }
}

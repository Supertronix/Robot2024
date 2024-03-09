package frc.robot;

import frc.robot.interaction.AnimateurLed;
import frc.robot.interaction.Manette;
import frc.robot.interaction.SelecteurModeAutonomeNumerote;
import frc.robot.interaction.ShuffleBoard;

public class StationPilotage {

    protected Manette manette;
    protected AnimateurLed animateurLed;
    protected ShuffleBoard shuffleBoard;
    protected SelecteurModeAutonomeNumerote selecteurAutonome;
    private StationPilotage()
    {
        this.shuffleBoard = new ShuffleBoard();
        this.shuffleBoard.initialiser();  
        this.selecteurAutonome = SelecteurModeAutonomeNumerote.getInstance();
    }

    protected static StationPilotage instance = null;
    public static StationPilotage getInstance()
    {
        if(null == instance) instance = new StationPilotage();
        return instance;
    }
    
}

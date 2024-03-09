package frc;

public class StationPilotage {

    protected static StationPilotage instance = null;
    public static StationPilotage getInstance()
    {
        if(null == instance) instance = new StationPilotage();
        return instance;
    }
    private StationPilotage()
    {
        
    }
    
}

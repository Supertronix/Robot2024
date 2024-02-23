package frc.robot.mesure;

public class Chronometre{

	protected double tempsDebut; 
	protected double tempsActuel; 
	
	public Chronometre()
	{
		System.out.println("new Chronometre()");
		this.initialiser();
	}
	
	public void initialiser()
	{
		this.tempsDebut = System.currentTimeMillis();
	}

	public void mesurer()
	{
		//System.out.println("mesurer()");
		this.tempsActuel = System.currentTimeMillis();				
	}

	public double getDuree()
	{
		return (this.tempsActuel - this.tempsDebut);
	}
}

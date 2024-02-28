package frc.robot.mesure;

public class Chronometre{

	protected double tempsDebut; 
	protected double tempsActuel;
	protected boolean estActif = true;
	
	public Chronometre()
	{
		System.out.println("new Chronometre()");
		this.initialiser();
	}
	
	public void initialiser()
	{	
		this.estActif = true;
		this.tempsDebut = System.currentTimeMillis();
	}

	public void mesurer()
	{
		//System.out.println("mesurer()");
		this.tempsActuel = System.currentTimeMillis();				
	}

	public double getDureePreMesuree()
	{
		return (this.tempsActuel - this.tempsDebut);
	}

	public double getDuree()
	{
		if (estActif) return (System.currentTimeMillis() - this.tempsDebut);
		return 0;
	}


	public void desactiver(){
		this.estActif = false;
	}
}

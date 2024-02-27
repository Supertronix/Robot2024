package frc.robot.mesure;

public class LimiteurDuree extends Chronometre{

	protected boolean estTropLong = false; // default anyway
	protected double limite;
	
	public interface Immobilisable
	{
		public double getDistancePourImmobilite();
	}
	public LimiteurDuree(double limite)
	{
		super();
		System.out.println("new DetecteurDelais()");
		this.limite = limite;
	}
	
	public void initialiser()
	{
		super.initialiser();
	}

	public void mesurer()
	{
		super.mesurer();
		//System.out.println("mesurer()");
		this.estTropLong = (this.tempsActuel - this.tempsDebut) > this.limite;
		if(this.estTropLong) System.out.println("Délai trop long !");	
	}
	
	/** 
	 * @return boolean
	 */
	public boolean estTropLongue()
	{
		return this.estTropLong;
	}
}

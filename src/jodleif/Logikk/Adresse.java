package jodleif.Logikk;

/**
 * Created by Jo Øivind Gjernes on 18.01.2016.
 *
 */
public class Adresse
{
	private String gateadresse;
	private String poststed;
	private int postnummer;

	/**
	 * Konstruktør for adresse klassen.
	 * Selvforklarende formell parametere
 	 * @param gateadresse
	 * @param poststed
	 * @param postnummer
	 */
	public Adresse(String gateadresse, String poststed, int postnummer)
	{
		this.gateadresse = gateadresse;
		this.poststed = poststed;
		this.postnummer = postnummer;
	}

	public String getGateadresse()
	{
		return gateadresse;
	}

	public String getPoststed()
	{
		return poststed;
	}

	public int getPostnummer()
	{
		return postnummer;
	}
}

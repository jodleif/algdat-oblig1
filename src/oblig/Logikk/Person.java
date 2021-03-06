package oblig.Logikk;

/**
 * Created by Jo Øivind Gjernes on 18.01.2016.
 *
 */
public class Person
{
	protected String navn;
	protected Adresse adresse;
	protected int kundenummer;


	/**
	 * Konstruktør for person
	 * Variable er selvforklarende.
	 * @param navn
	 * @param adresse
	 * @param kundenummer
	 */
	public Person(String navn,  int kundenummer,Adresse adresse)
	{
		this.navn = navn;
		this.adresse = adresse;
		this.kundenummer = kundenummer;
	}

	/**
	 * Konstruktør for person+adresse
 	 * @param navn
	 * @param kundenummer
	 * @param gateadresse
	 * @param poststed
	 * @param postnummer
	 */
	public Person(String navn, int kundenummer, String gateadresse, String poststed, int postnummer )
	{
		this(navn, kundenummer, new Adresse(gateadresse, poststed, postnummer));
	}

	/**
	 *
	 * @return navn
	 */
	public String getNavn()
	{
		return navn;
	}

	/**
	 *
	 * @return adresse objekt!
	 */
	public Adresse getAdresse()
	{
		return adresse;
	}

	/**
	 *
	 * @return kundenummer
	 */
	public int getKundenummer()
	{
		return kundenummer;
	}

	@Override
	public String toString()
	{
		return  "navn='" + navn + '\'' +
			", kundenummer=" + kundenummer +
			"," + adresse;
	}
}

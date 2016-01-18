package oblig.Logikk;

/**
 * Created by Jo Øivind Gjernes on 18.01.2016.
 * <p>
 * Klasse som inneholder komparator metoder!
 *
 * Metodene følger "interfacet" til compare-metoden, men det er ikke implementert.
 */
public class Komparatorer
{
	/**
	 * Komparator som sammenligner personobjekter etter navn
	 *
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int compareNavn(Person p1, Person p2)
	{
		return p1.getNavn().compareTo(p2.getNavn());
	}

	/**
	 * Komparator som sammenligner perosnobjekter etter postnummer
	 *
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int comparePostNr(Person p1, Person p2)
	{
		return p1.getAdresse().getPostnummer() - p2.getAdresse().getPostnummer();
	}

	/**
	 * Komparator som sammenligner personobjekter etter kundenummer..
	 *
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int compareKundenummer(Person p1, Person p2)
	{
		return p1.getKundenummer() - p2.getKundenummer();
	}
}

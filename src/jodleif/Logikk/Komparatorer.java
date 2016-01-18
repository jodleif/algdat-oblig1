package jodleif.Logikk;

/**
 * Created by Jo Øivind Gjernes on 18.01.2016.
 *
 * Klasse som inneholder komparator metoder!
 *
 */
public class Komparatorer
{
	public static int compareNavn(Person p1, Person p2)
	{
		return p1.getNavn().compareTo(p2.getNavn());
	}

	public static int comparePostNr(Person p1, Person p2)
	{
		return p1.getAdresse().getPostnummer() - p2.getAdresse().getPostnummer();
	}

	public static int compareKundenummer(Person p1, Person p2)
	{
		return p1.getKundenummer()-p2.getKundenummer();
	}
}

package jodleif;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jodleif.Logikk.Komparatorer;
import jodleif.Logikk.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

/**
 * Alg.Dat innlevering
 * Versjon 3.
 *
 * Implementert på en litt annen måte enn spesifisert.
 * Syntes dette ble en meget elegant løsning.
 */
public class Main extends Application
{
	private final VBox hovedLayout = new VBox();
	private TextArea tekstOmråde;
	private ComboBox<String> comboBox;
	private Button sortButton;
	private ToggleButton sorterBaklengs;
	private final ArrayList<Person> personer = new ArrayList<>();

	/**
	 * Metodereferanse til komparator metode.
	 * Denne styres av GUIet gjennom comboboxen.
	 * se i metoden "opprettGUIelementer"!
	 *
	 * De ulike komparatorene er definert i klassen Logikk.Komparatorer
	 */
	private Comparator<Person> comparator = Komparatorer::compareNavn; // Initialisere med en standardverdi


	// Standard verdier for comboboxen
	private static final ObservableList<String> comboValg =
		FXCollections.observableArrayList(
			"navn",
			"postnummer",
			"kundenummer"
			);


	/**
	 * GUI-start
	 * @param primaryStage
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Opprette GUI-kontainere
		Group root = new Group();
		Scene scene = new Scene(root);
		hovedLayout.minWidthProperty().bind(scene.widthProperty()); // Sett hovedLayout bredde til vinduets bredde!
		root.getChildren().add(hovedLayout);

		// Diverse initialisering av GUI
		opprettGUIElementer();
		leggTilPersoner();

		// Primary stage
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setTitle("Oblig 1");
		primaryStage.setScene(scene);
		primaryStage.show();


		// Skriv ut personlisten til tekstområdet.
		oppdaterVisning();
	}

	/**
	 * Opprette dummy verdier for personer
	 */
	private void leggTilPersoner()
	{
		personer.add(new Person("Ola", 1, "Gate 1", "Bø", 3800));
		personer.add(new Person("Lise", 2, "Gate 2", "Skien", 3731));
		personer.add(new Person("Bjarne", 3, "Javaveien", "Bø", 3802));
		personer.add(new Person("Kari",0,"Algoritme gata", "Skien", 3701));
	}

	/**
	 * Opprette GUI-elementer
	 */
	private void opprettGUIElementer(){

		tekstOmråde = new TextArea();
		tekstOmråde.setEditable(false);

		comboBox = new ComboBox<>(comboValg);
		comboBox.getSelectionModel().select(0); // Velg "navn" som default.

		// Lytter etter om verdien endres
		comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.compareTo(comboValg.get(0))==0) comparator = Komparatorer::compareNavn;
			if(newValue.compareTo(comboValg.get(1))==0) comparator = Komparatorer::comparePostNr;
			if(newValue.compareTo(comboValg.get(2))==0) comparator = Komparatorer::compareKundenummer;
		});

		// Knapp med boolean verdi. Brukes for å velge om man skal "sortere baklengs"
		sorterBaklengs = new ToggleButton("Omvendt");

		// Når denne er trykt på sorter
		sortButton = new Button("Sorter");
		sortButton.setOnAction(e->sorter());
		Button nyPerson = new Button("Opprett person");
		nyPerson.setOnAction(e->opprettPersonDialog());

		HBox knappeLinje = new HBox();
		knappeLinje.setSpacing(5);
		knappeLinje.getChildren().addAll(comboBox, sorterBaklengs, sortButton);
		hovedLayout.getChildren().addAll(tekstOmråde, knappeLinje, nyPerson);

	}

	/**
	 * Metode for å sortere personlisten. Oppdaterer visningen etter listen er sortert.
	 * Sorteringen er basert på hvilken komparator som er valgt.
	 */
	private void sorter()
	{
		// Sorter!
		if(comparator==null) throw new NullPointerException("Feil! - Ingen komparator tilegnet");
		if(sorterBaklengs.isSelected()){
			Collections.sort(personer, Collections.reverseOrder(comparator));
		} else {
			Collections.sort(personer, comparator);
		}
		oppdaterVisning();
	}

	/**
	 * Oppdaterer visningen i henhold til personlisten
	 */
	private void oppdaterVisning()
	{
		StringBuilder buffer = new StringBuilder(); // Muterbar streng.
		for(Person p : personer){
			buffer.append(p.toString());
			buffer.append('\n'); // En linje for hvert objekt!
		}
		tekstOmråde.setText(buffer.toString());
	}

	/**
	 * Enkel og kjapp dialog for oppretting av ny person
	 */
	private void opprettPersonDialog()
	{
		TextInputDialog opprettPerson = new TextInputDialog();
		opprettPerson.setTitle("Opprett ny person!");
		opprettPerson.setHeaderText("Skriv inn personinformasjon på format:");
		opprettPerson.setContentText("navn, kundenummer(heltall), gateadresse, poststed, postnummer");

		Optional<String> res = opprettPerson.showAndWait();
		String resultat = "";
		boolean ok = false;

		if(res.isPresent()){
			resultat = res.get();
			ok = opprettNyPerson(resultat);
		}
		if(!ok){
			feilmelding("Ugyldig tekststreng, du skrev:\n" + resultat);
		}
	}

	/**
	 * Feilmelding
	 * @param melding feilmelding du ønsker å vise
	 */
	private void feilmelding(String melding)
	{
		Alert feil = new Alert(Alert.AlertType.ERROR);
		feil.setTitle("Feil!");
		feil.setContentText(melding);
		feil.showAndWait();
	}

	/**
	 * Kjapp og gal metode for å opprette en ny person basert på en semikolonseparert liste
	 * @param person Streng der elementer er separert med komma
	 * @return true hvis godkjent, false ellers
	 */
	private boolean opprettNyPerson(String person)
	{
		String[] oppdelt = person.split(",");

		int kundenummer=0;
		int postnummer=0;

		if(oppdelt.length!=5) return false;

		try{
			kundenummer = Integer.valueOf(oppdelt[1]);
			postnummer = Integer.valueOf(oppdelt[4]);
		} catch (Exception e){
			//feilmelding(e.getMessage());
			System.err.println("Feil under parsing av tall!");
			return false;
		}

		personer.add(new Person(oppdelt[0], kundenummer, oppdelt[2], oppdelt[3],postnummer));
		oppdaterVisning();
		return true;
	}
	/**
	 * Programmets entry-point
	 * @param args
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}

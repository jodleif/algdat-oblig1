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

/**
 * Alg.Dat innlevering
 * Versjon 3.
 */
public class Main extends Application
{
	private final VBox hovedLayout = new VBox();
	private TextArea tekstOmråde;
	private ComboBox<String> comboBox;
	private Button sortButton;
	private ToggleButton sorterBaklengs;
	private Comparator<Person> comparator = Komparatorer::comparePostNr; // Initialisere med en standardverdi
	private final ArrayList<Person> personer = new ArrayList<>();

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
		Group root = new Group(); // Overflødig men legger til av gammel vane..
		Scene scene = new Scene(root);
		hovedLayout.minWidthProperty().bind(scene.widthProperty());
		root.getChildren().add(hovedLayout);

		opprettGUIElementer();
		leggTilPersoner();

		// Primary stage
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		primaryStage.setTitle("Program");
		primaryStage.setScene(scene);
		primaryStage.show();
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
		comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.compareTo(comboValg.get(0))==0) comparator = Komparatorer::compareNavn;
			if(newValue.compareTo(comboValg.get(1))==0) comparator = Komparatorer::comparePostNr;
			if(newValue.compareTo(comboValg.get(2))==0) comparator = Komparatorer::compareKundenummer;
		});

		sorterBaklengs = new ToggleButton("Omvendt");
		sortButton = new Button("Sorter");
		sortButton.setOnAction(e->sorter());

		HBox knappeLinje = new HBox();
		knappeLinje.setSpacing(5);
		knappeLinje.getChildren().addAll(comboBox, sortButton, sorterBaklengs);
		hovedLayout.getChildren().addAll(tekstOmråde, knappeLinje);

	}

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

	private void oppdaterVisning()
	{
		StringBuilder buffer = new StringBuilder();
		for(Person p : personer){
			buffer.append(p.toString());
			buffer.append('\n'); // En linje for hvert objekt!
		}
		tekstOmråde.setText(buffer.toString());
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

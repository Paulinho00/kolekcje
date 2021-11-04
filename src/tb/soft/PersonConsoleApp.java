package tb.soft;

import java.io.*;
import java.util.*;

/**
 * Program: Aplikacja działająca w oknie konsoli, która umożliwia testowanie 
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonConsoleApp.java
 *          
 *   Autor: Paweł Rogaliński
 *    Data: październik 2018 r.
 */
public class PersonConsoleApp {

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja konsolowa\n" + 
	        "Autor: Paweł Rogaliński\n" +
			"Data:  październik 2018 r.\n";

	private static final String MENU =
			"    M E N U   G Ł Ó W N E  \n" +
			"1 - Podaj dane nowej osoby \n" +
			"2 - Usuń dane osoby        \n" +
			"3 - Modyfikuj dane osoby   \n" +
			"4 - Wczytaj dane z pliku   \n" +
			"5 - Zapisz dane do pliku   \n" +
			"6 - Wyświetl wszystkie osoby   \n" +
			"7 - Zobacz działanie equals() i hashCode()   \n" +
			"0 - Zakończ program        \n";
	
	private static final String CHANGE_MENU = 
			"   Co zmienić?     \n" + 
	        "1 - Imię           \n" + 
			"2 - Nazwisko       \n" + 
	        "3 - Rok urodzenia  \n" + 
			"4 - Stanowisko     \n" +
	        "0 - Powrót do menu głównego\n";

	private static final String COLLECTIONS_MENU =
			" Jaką kolekcję wybierasz? \n"+
			"1 - HashSet    \n"+
			"2 - TreeSet    \n"+
			"3 - ArrayList  \n"+
			"4 - LinkedList \n"+
			"5 - HashMap    \n"+
			"6 - TreeMap    \n"+
	        "0 - Powrót do menu głównego\n";

	private static final CollectionsContainer collectionsContainer = new CollectionsContainer();

	
	/**
	 * ConsoleUserDialog to pomocnicza klasa zawierająca zestaw
	 * prostych metod do realizacji dialogu z użytkownikiem
	 * w oknie konsoli tekstowej.
	 */
	private static final ConsoleUserDialog UI = new ConsoleUserDialog();

	
	public static void main(String[] args) {
		// Utworzenie obiektu aplikacji konsolowej
		// oraz uruchomienie głównej pętli aplikacji.
		PersonConsoleApp application = new PersonConsoleApp();
		application.runMainLoop();
	} 

	
	/*
	 *  Referencja do obiektu, który zawiera dane aktualnej osoby.
	 */
	private Person currentPerson = null;
	
	
	/*
	 *  Metoda runMainLoop wykonuje główną pętlę aplikacji.
	 *  UWAGA: Ta metoda zawiera nieskończoną pętlę,
	 *         w której program się zatrzymuje aż do zakończenia
	 *         działania za pomocą metody System.exit(0); 
	 */
	public void runMainLoop() {
		UI.printMessage(GREETING_MESSAGE);
		try {
			loadStartingScenario();
		while (true) {

				UI.clearConsole();
				showCurrentPerson();
				switch (UI.enterInt(MENU + "==>> ")) {
				case 1:
					// utworzenie nowej osoby
					currentPerson = createNewPerson();
					collectionsContainer.add(currentPerson);
					break;
				case 2:
					// usunięcie danych aktualnej osoby.
					collectionsContainer.remove(currentPerson);
					currentPerson = null;
					UI.printInfoMessage("Dane aktualnej osoby zostały usunięte");
					break;
				case 3:
					// zmiana danych dla aktualnej osoby
					if (currentPerson == null) throw new PersonException("Żadna osoba nie została utworzona.");
					collectionsContainer.remove(currentPerson);
					changePersonData(currentPerson);
					collectionsContainer.add(currentPerson);
					break;
				case 4: {
					// odczyt danych z pliku tekstowego.
					String file_name = UI.enterString("Podaj nazwę pliku: ");
					currentPerson = Person.readFromFile(file_name);
					collectionsContainer.add(currentPerson);
					UI.printInfoMessage("Dane aktualnej osoby zostały wczytane z pliku " + file_name);
				}
					break;
				case 5: {
					// zapis danych aktualnej osoby do pliku tekstowego 
					String file_name = UI.enterString("Podaj nazwę pliku: ");
					Person.printToFile(file_name, currentPerson);
					UI.printInfoMessage("Dane aktualnej osoby zostały zapisane do pliku " + file_name);
				}
					break;
				case 6: {
					collectionsMenu();
				}
					break;
				case 7: showEqualsHashDiff();
					break;
				case 0:
					// zakończenie działania programu
					UI.printInfoMessage("\nProgram zakończył działanie!");
					System.exit(0);
				} // koniec instrukcji switch
			}
		} catch (PersonException e) {
			// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
			// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
			UI.printErrorMessage(e.getMessage());
		} // koniec pętli while
	}

	public void collectionsMenu() throws PersonException{
		while(true){
			UI.clearConsole();
			showCurrentPerson();

				switch (UI.enterInt(COLLECTIONS_MENU + "==>> ")){
					case 1: showHashSet(); break;
					case 2: showTreeSet(); break;
					case 3: showArrayList(); break;
					case 4: showLinkedList(); break;
					case 5: showHashMap(); break;
					case 6: showTreeMap(); break;
					case 0: return;

				}


		}
	}
	
	
	/*
	 *  Metoda wyświetla w oknie konsoli dane aktualnej osoby 
	 *  pamiętanej w zmiennej currentPerson.
	 */
	void showCurrentPerson() {
		System.out.println("Aktualna osoba:");
		showPerson(currentPerson);
	} 

	
	/* 
	 * Metoda wyświetla w oknie konsoli dane osoby reprezentowanej 
	 * przez obiekt klasy Person
	 */ 
	static void showPerson(Person person) {
		StringBuilder sb = new StringBuilder();
		
		if (person != null) {
			  sb.append("      Imię: ").append(person.getFirstName()).append("\n")
			  .append("  Nazwisko: ").append(person.getLastName()).append("\n")
			  .append("   Rok ur.: ").append(person.getBirthYear()).append("\n")
			  .append("Stanowisko: ").append(person.getJob()).append("\n");
		} else
			sb.append( "Brak danych osoby\n" );
		UI.printMessage( sb.toString() );
	}

	
	/* 
	 * Metoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
	 * klasy Person i wypełnia atrybuty wczytanymi danymi.
	 * Walidacja poprawności danych odbywa się w konstruktorze i setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static Person createNewPerson(){
		String first_name = UI.enterString("Podaj imię: ");
		String last_name = UI.enterString("Podaj nazwisko: ");
		String birth_year = UI.enterString("Podaj rok ur.: ");
		UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
		String job_name = UI.enterString("Podaj stanowisko: ");
		Person person;
		try { 
			// Utworzenie nowego obiektu klasy Person oraz
			// ustawienie wartości wszystkich atrybutów.
			person = new Person(first_name, last_name);
			person.setBirthYear(birth_year);
			person.setJob(job_name);
		} catch (PersonException e) {    
			// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
			// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
			// poszczególnych atrybutów.
			// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
			UI.printErrorMessage(e.getMessage());
			return null;
		}
		return person;
	}
	
	
	/* 
	 * Metoda pozwala wczytać nowe dane dla poszczególnych atrybutów 
	 * obiekty person i zmienia je poprzez wywołanie odpowiednich setterów z klasy Person.
	 * Walidacja poprawności wczytanych danych odbywa się w setterach
	 * klasy Person. Jeśli zostaną wykryte niepoprawne dane,
	 * to zostanie zgłoszony wyjątek, który zawiera komunikat o błędzie.
	 */
	static void changePersonData(Person person)
	{
		while (true) {
			UI.clearConsole();
			showPerson(person);

			try {		
				switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
				case 1:
					person.setFirstName(UI.enterString("Podaj imię: "));
					break;
				case 2:
					person.setLastName(UI.enterString("Podaj nazwisko: "));
					break;
				case 3:
					person.setBirthYear(UI.enterString("Podaj rok ur.: "));
					break;
				case 4:
					UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
					person.setJob(UI.enterString("Podaj stanowisko: "));
					break;
				case 0: return;
				}  // koniec instrukcji switch
			} catch (PersonException e) {     
				// Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person,
				// gdy nie są spełnione ograniczenia nałożone na dopuszczalne wartości
				// poszczególnych atrybutów.
				// Drukowanie komunikatu o błędzie zgłoszonym za pomocą wyjątku PersonException.
				UI.printErrorMessage(e.getMessage());
			}
		}
	}

	private void loadStartingScenario() throws PersonException{
		try {
			BufferedReader rd = new BufferedReader(new FileReader(new File("Starting_Scenario")));
			String line = rd.readLine();
			while(line != null){
				String[] txt = line.split("#");
				Person person = new Person(txt[0], txt[1]);
				person.setBirthYear(txt[2]);
				person.setJob(txt[3]);
				collectionsContainer.add(person);
				currentPerson = person;
				line = rd.readLine();
			}

		}catch(FileNotFoundException e){
			System.out.println("Nie odnaleziono pliku");
		}catch(IOException e){
			System.out.println("Wystąpił błąd podczas pobierania z pliku");
		}

	}

	public void showHashSet() throws PersonException{
		Set<Person> hashSet1 = collectionsContainer.getHashSet1();
		Set<PersonEqualsHash> hashSet2 = collectionsContainer.getHashSet2();
		PersonEqualsHash currentPersonEqualHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("HashSet z obiektami bez implementacji equals() i hashCode()");
		for(Person person: hashSet1) {
			UI.printMessage("######################");
			showPerson(person);
			if(person.equals(currentPerson)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
		UI.clearConsole();
		UI.printMessage("HashSet z obiektami z implementacją equals() i hashCode()");
		for(PersonEqualsHash personEqualsHash:hashSet2){
			UI.printMessage("######################");
			showPerson(personEqualsHash);
			if(personEqualsHash.equals(currentPersonEqualHash)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
	}

	public void showTreeSet() throws PersonException{
		Set<Person> treeSet1 = collectionsContainer.getTreeSet1();
		Set<PersonEqualsHash> treeSet2 = collectionsContainer.getTreeSet2();
		PersonEqualsHash currentPersonEqualHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("TreeSet z obiektami bez implementacji equals() i hashCode()");
		for(Person person: treeSet1) {
			UI.printMessage("######################");
			showPerson(person);
			if(person.equals(currentPerson)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
		UI.clearConsole();
		UI.printMessage("TreeSet z obiektami z implementacją equals() i hashCode()");
		for(PersonEqualsHash personEqualsHash:treeSet2){
			UI.printMessage("######################");
			showPerson(personEqualsHash);
			if(personEqualsHash.equals(currentPersonEqualHash)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
	}

	public void showArrayList() throws PersonException{
		List<Person> arrayList1 = collectionsContainer.getArrayList1();
		List<PersonEqualsHash> arrayList2 = collectionsContainer.getArrayList2();
		PersonEqualsHash currentPersonEqualHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("ArrayList z obiektami bez implementacji equals() i hashCode()");
		for(Person person: arrayList1){
			UI.printMessage("######################");
			showPerson(person);
			if(person.equals(currentPerson)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
		UI.clearConsole();
		UI.printMessage("ArrayList z obiektami z implementacją equals() i hashCode()");
		for(PersonEqualsHash personEqualsHash:arrayList2){
			UI.printMessage("######################");
			showPerson(personEqualsHash);
			if(personEqualsHash.equals(currentPersonEqualHash)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
	}

	public void showLinkedList() throws PersonException{
		List<Person> linkedList1 = collectionsContainer.getLinkedList1();
		List<PersonEqualsHash> linkedList2 = collectionsContainer.getLinkedList2();
		PersonEqualsHash currentPersonEqualHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("LinkedList z obiektami bez implementacji equals() i hashCode()");
		for(Person person: linkedList1){
			UI.printMessage("######################");
			showPerson(person);
			if(person.equals(currentPerson)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
		UI.clearConsole();
		UI.printMessage("LinkedList z obiektami z implementacją equals() i hashCode()");
		for(PersonEqualsHash personEqualsHash:linkedList2){
			UI.printMessage("######################");
			showPerson(personEqualsHash);
			if(personEqualsHash.equals(currentPersonEqualHash)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
	}

	public void showHashMap() throws PersonException{
		Map<Person, Integer> hashMap1 = collectionsContainer.getHashMap1();
		Map<PersonEqualsHash, Integer> hashMap2 = collectionsContainer.getHashMap2();
		PersonEqualsHash currentPersonEqualHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("HashMap z obiektami bez implementacji equals() i hashCode()");
		for(Person person: hashMap1.keySet()){
			UI.printMessage("######################");
			showPerson(person);
			if(person.equals(currentPerson)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
		UI.clearConsole();
		UI.printMessage("HashMap z obiektami z implementacją equals() i hashCode()");
		for(PersonEqualsHash personEqualsHash:hashMap2.keySet()){
			UI.printMessage("######################");
			showPerson(personEqualsHash);
			if(personEqualsHash.equals(currentPersonEqualHash)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
	}

	public void showTreeMap() throws PersonException{
		Map<Person, Integer> treeMap1 = collectionsContainer.getTreeMap1();
		Map<PersonEqualsHash, Integer> treeMap2 = collectionsContainer.getTreeMap2();
		PersonEqualsHash currentPersonEqualHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("TreeMap z obiektami bez implementacji equals() i hashCode()");
		for(Person person: treeMap1.keySet()){
			UI.printMessage("######################");
			showPerson(person);
			if(person.equals(currentPerson)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
		UI.clearConsole();
		UI.printMessage("TreeMap z obiektami z implementacją equals() i hashCode()");
		for(PersonEqualsHash personEqualsHash:treeMap2.keySet()){
			UI.printMessage("######################");
			showPerson(personEqualsHash);
			if(personEqualsHash.equals(currentPersonEqualHash)) UI.printMessage("Porównanie z aktualnie wybraną osobą: to ta sama osoba");
			else UI.printMessage("Porównanie z aktualnie wybraną osobą: to jest inna osoba");
			UI.printMessage("######################\n");
		}
	}

	public void showEqualsHashDiff() throws PersonException {
		PersonEqualsHash personEqualsHash = new PersonEqualsHash(currentPerson);
		UI.printMessage("######################");
		showCurrentPerson();
		UI.printMessage("hashCode bez implementacji: " + currentPerson.hashCode() );
		UI.printMessage("hashCode z implementacją: " + personEqualsHash.hashCode());
		System.out.println("");

		Person clonedPerson = new Person(currentPerson.getFirstName(), currentPerson.getLastName());
		clonedPerson.setBirthYear(currentPerson.getBirthYear());
		clonedPerson.setJob(currentPerson.getJob());
		PersonEqualsHash clonedPersonEqualHash = new PersonEqualsHash(clonedPerson);

		UI.printMessage("Klon aktualnie wybranej osoby: ");
		showPerson(clonedPerson);
		UI.printMessage("Wynik metody equals dla identycznych, sklonowanych obiektów (bez implementacji):" + currentPerson.equals(clonedPerson));
		UI.printMessage("Wynik metody equals dla identycznych, sklonowanych obiektów (z własną implementacją):" + clonedPersonEqualHash.equals(personEqualsHash));

		UI.printMessage("######################");
	}
	
}  // koniec klasy PersonConsoleApp

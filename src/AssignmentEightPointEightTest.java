/*
 * Denna fil innehåller JUnit-testfall för den uppdaterade funktionen för att ta bort en hund U8.8 som också tar bort den från ägaren.
 *
 * Det är starkt rekommenderat att du använder dig av dessa testfall, och att du
 * kör dem i din egen utvecklingsmiljö, och inte i VPL. Du får bättre
 * felmeddelanden i din egen utvecklingsmiljö, och det är svårt att hålla reda
 * på versioner om du hoppar fram och tillbaka.
 *
 * För att köra testerna behöver du lägga till JUnit till ditt projekt, och
 * lägga denna fil, tillsammans med de gemensamma standardfiler som behövs i
 * samma katalog som det som ska testas. Information om hur du gör detta finns i
 * ilearn.
 *
 * Testfallen är ordnade i en "naturlig" ordning. Detta, tillsammans med att
 * hela eller delar av testkoden är bortkommenterad från början är tänkt att
 * hjälpa dig att koncentrera dig på en sak i taget. Försök inte lösa allt på en
 * gång, utan ta ett testfall i taget, uppifrån och ner.
 *
 * Slutligen: dessa testfall, och eventuella extra som körs i ilearn, är tänkta
 * att hjälpa dig på rätt väg, inte att vara perfekta. Det är alltid du själv
 * som ansvarar för att koden du lämnar in uppfyller kraven. Du måste därför
 * testa koden själv också. Men, går koden igenom dessa testfall så kommer du
 * att ha en bra grund att stå på.
 *
 * Testfallen kan också komma att uppdateras under kursens gång om vi märker att
 * de missar något viktigt. Sådana uppdateringar aviseras via ilearn.
 */

// COMMENT


import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName(value = "JUnit-testfall för U8.8 - uppdaterad metod för att ta bort en hund som också tar bort den från ägaren")
public class AssignmentEightPointEightTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointEight.class,
			"AssignmentEightPointEightTest");
	private static final MethodUnderTest REMOVE_DOG_METHOD = CUT.getMethod(TestData.REMOVE_DOG_METHOD_NAME,
			"TestData.REMOVE_DOG_METHOD_NAME");

	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME, Collection.class,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME, Collection.class,
			"TestData.MAIN_OWNER_LIST_NAME");

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class, "AssignmentEightPointEightTest");
	private static final MethodUnderTest ADD_DOG_TO_OWNER_METHOD = OWNER_CLASS
			.getMethod(TestData.ADD_DOG_TO_OWNER_METHOD_NAME, "TestData.ADD_DOG_TO_OWNER_METHOD_NAME", Dog.class);
	private static final MethodUnderTest OWNS_DOG_METHOD = OWNER_CLASS.getMethod(TestData.OWNS_DOG_METHOD_NAME,
			"TestData.OWNS_DOG_METHOD_NAME", Dog.class);

	private final Dog BELLA = new Dog("Bella", "Labrador", 3, 12);
	private final Dog RATATA = new Dog("Ratata", "Tax", 15, 7);
	private final Dog DORIS = new Dog("Doris", "Cocker spaniel", 5, 7);
	private final Dog LASSIE = new Dog("Lassie", "Mops", 11, 11);
	private final Dog CHARLIE = new Dog("Charlie", "Puli", 9, 12);

	private final Dog[] PREPARED_DOGS = { BELLA, RATATA, DORIS, LASSIE, CHARLIE };

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointEightTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(IOBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Finns det några uppenbara problem med uppgiftsklassens struktur?")
	public void basicStructureChecks() {
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, REMOVE_DOG_METHOD);
	}

	@SuppressWarnings("unchecked")
	private void addDogs(AssignmentEightPointEight sut, Dog... dogs) {
		((Collection<Dog>) MAIN_DOG_LIST.getValue(sut)).addAll(Arrays.asList(dogs));
	}

	@SuppressWarnings("unchecked")
	private void addOwners(AssignmentEightPointEight sut, Owner... owners) {
		((Collection<Owner>) MAIN_OWNER_LIST.getValue(sut)).addAll(Arrays.asList(owners));
	}

	@Test
	@DisplayName(value = "Försöker ta bort den enda hunden i listan")
	public void removingOnlyDog() {
		setIn("Lassie\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, LASSIE);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContains(MAIN_DOG_LIST, sut);

		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort den första hunden i listan")
	public void removingFirstDog() {
		setIn("Bella\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, BELLA, (Object[]) PREPARED_DOGS);
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Förösker ta bort den mittersta hunden i listan")
	public void removingMiddleDog() {
		setIn("Doris\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, DORIS, (Object[]) PREPARED_DOGS);
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort den sista hunden i listan")
	public void removingLastDog() {
		setIn("Charlie\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, CHARLIE, (Object[]) PREPARED_DOGS);
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund som inte finns")
	public void removingNonexistingDogGivesErrorMessage() {
		setIn("Ingen hund\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContains(MAIN_DOG_LIST, sut, (Object[]) PREPARED_DOGS);
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund när det inte finns några hundar i listan")
	public void removingDogWithNoDogsInListGivesErrorMessage() {
		setIn("Ingen hund\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContains(MAIN_DOG_LIST, sut);
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund med en ägare, hunden ska tas bort från ägaren också")
	public void removingOwnedDogAlsoRemovesDogFromOwner() {
		setIn("Doris\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		Owner owner = new Owner("Lillsten");
		addOwners(sut, owner);

		ADD_DOG_TO_OWNER_METHOD.invoke(owner, DORIS);
		assertTrue((boolean) OWNS_DOG_METHOD.invoke(owner, DORIS), "Gustav äger inte Sigge som förväntat");

		REMOVE_DOG_METHOD.invoke(sut);

		assertFalse((boolean) OWNS_DOG_METHOD.invoke(owner, DORIS),
				"Gustav äger fortarande SIGGE när denna borde vara borttagen");

	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund med en ägare, ägaren ska finnas kvar")
	public void removingOwnedDogLeavesOwner() {
		setIn("Doris\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		Owner owner = new Owner("Storsten");
		addOwners(sut, owner);

		ADD_DOG_TO_OWNER_METHOD.invoke(owner, DORIS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContains(MAIN_OWNER_LIST, sut, owner);
	}

	// TODO: flera olika felaktiga namnformat
	@Test
	@DisplayName(value = "Försöker ta bort en hund med en ägare med namnet skrivet på fel sätt")
	public void removingDogWithNameInDifferentCase() {
		setIn("RAtaTa\n");
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, RATATA, (Object[]) PREPARED_DOGS);
		out().assertDoesNotContainErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "' \tLassie\t \nKalle\n', Blanktecken runt hundnamnet",
			"'Lassie\n\t Kalle\t \n', Blanktecken runt ägarnamnet" })
	@DisplayName(value = "Hanteras blanktecken tabbar och runt namnen?")
	public void whiteSpaceAroundNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, LASSIE, (Object[]) PREPARED_DOGS);
		out().assertDoesNotContainErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "'\nLassie\n', Blankt hundnamn", "'  \t \nLassie\n', Bara blanktecken i hundnamnet",
			"'\n  \t\n\n\n \nLassie', Flera blandade blanktecken och tomma namn" })
	@DisplayName(value = "Hanteras blanktecken och tomma namn korrekt?")
	public void whiteSpaceAndEmptyNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, LASSIE, (Object[]) PREPARED_DOGS);
		out().assertContainsErrorMessage();
	}

	@ParameterizedTest(name = "{index} {0}")
	@CsvSource(value = { "LASSIE", "lassie", "Lassie, kALLe" })
	@DisplayName(value = "Hanteras namn på olika format?")
	public void differentNameFormatsHandled(String dog) {
		setIn("%s\n".formatted(dog));
		AssignmentEightPointEight sut = new AssignmentEightPointEight();

		addDogs(sut, PREPARED_DOGS);

		REMOVE_DOG_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_DOG_LIST, sut, LASSIE, (Object[]) PREPARED_DOGS);
		out().assertDoesNotContainErrorMessage();
	}

}
// ENDOFCLASS

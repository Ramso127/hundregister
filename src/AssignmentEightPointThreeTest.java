/*
 * Denna fil innehåller JUnit-testfall för att lägga till en hund till en ägare U8.3. Vänta gärna med dessa tester till testen för hunden och ägaren går igenom. Innan dess är det ingen mening med att börja med dessa..
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



import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName(value = "JUnit-testfall för U8.3 - metoden för att lägga till en hund till en ägare")
public class AssignmentEightPointThreeTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointThree.class,
			"AssignmentEightPointThreeTest");
	private static final MethodUnderTest GIVE_DOG_METHOD = CUT.getMethod(TestData.GIVE_DOG_METHOD_NAME,
			"TestData.GIVE_DOG_METHOD_NAME");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME,
			"TestData.MAIN_OWNER_LIST_NAME");

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointThreeTest");
	private static final FieldUnderTest DOGS_OWNER = DOG_CLASS.getOnlyFieldAssignableFromType(Owner.class);

	private static final ClassUnderTest DOG_LIST_CLASS = new ClassUnderTest(TestData.DOG_LIST_CLASS_NAME,
			"TestData.DOG_LIST_CLASS_NAME");
	private static final MethodUnderTest DOG_LIST_CLASS_DOG_EXISTS_METHOD = DOG_LIST_CLASS.getMethod(
			TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME, "TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME",
			Dog.class);

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class, "AssignmentEightPointThreeTest");
	private static final FieldUnderTest OWNED_DOGS = OWNER_CLASS.getOnlyFieldAssignableFromType(DOG_LIST_CLASS);

	// Inte statiska för att testen inte ska störa varandra
	private final Dog CHARLIE = new Dog("Charlie", "Mops", 12, 3);
	private final Dog FIDO = new Dog("Fido", "Dachshund", 14, 12);
	private final Dog LASSIE = new Dog("Lassie", "Golden retriever", 11, 19);
	private final Dog BAMSE = new Dog("Bamse", "Puli", 5, 5);
	private final Dog RONJA = new Dog("Ronja", "Tax", 19, 8);

	private final Owner STEFAN = new Owner("Stefan");
	private final Owner ULLA = new Owner("Ulla");
	private final Owner HELGA = new Owner("Helga");
	private final Owner KALLE = new Owner("Kalle");
	private final Owner PATRICK = new Owner("Patrick");

	private final Dog[] PREPARED_DOGS = { CHARLIE, FIDO, LASSIE, BAMSE, RONJA };
	private final Owner[] PREPARED_OWNERS = { STEFAN, ULLA, HELGA, KALLE, PATRICK };

	private final Map<String, Dog> nameToDog = new HashMap<>();
	private final Map<String, Owner> nameToOwner = new HashMap<>();

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointThreeTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(IOBaseTest.class, 1);

		for (Dog dog : PREPARED_DOGS) {
			nameToDog.put(dog.getName(), dog);
		}

		for (Owner owner : PREPARED_OWNERS) {
			nameToOwner.put(owner.getName(), owner);
		}
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Finns det några uppenbara problem med uppgiftsklassens struktur?")
	public void basicStructureChecks() {
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, GIVE_DOG_METHOD);
	}

	private void assertOwnedBy(Dog dog, Owner expectedOwner) {
		assertEquals(expectedOwner, DOGS_OWNER.getValue(dog), "Fel ägare för %s".formatted(dog.getName()));
	}

	private void assertOwnsDog(Owner owner, Dog expectedDog) {
		var list = OWNED_DOGS.getValue(owner);
		boolean ownsDog = (boolean) DOG_LIST_CLASS_DOG_EXISTS_METHOD.invoke(list, expectedDog);
		assertTrue(ownsDog,
				"Hunden %s borde ägas av %s, men %s-metoden säger att den inte finns i ägarens lista av hundar"
						.formatted(expectedDog.getName(), owner.getName(), DOG_LIST_CLASS_DOG_EXISTS_METHOD));
	}

	@SuppressWarnings("unchecked")
	private void addDogs(AssignmentEightPointThree sut, Dog... dogs) {
		((Collection<Dog>) MAIN_DOG_LIST.getValue(sut)).addAll(Arrays.asList(dogs));
	}

	@SuppressWarnings("unchecked")
	private void addOwners(AssignmentEightPointThree sut, Owner... owners) {
		((Collection<Owner>) MAIN_OWNER_LIST.getValue(sut)).addAll(Arrays.asList(owners));
	}

	@ParameterizedTest(name = "{index} Ge {0} till {1}")
	@CsvSource(value = { "Lassie, Helga", "Ronja, Stefan", "Charlie, Patrick" })
	@DisplayName(value = "Ge hund till ägare")
	public void giveDogGivesTheDogToTheOwner(String dogName, String ownerName) {
		// Måste ske innan sut skapas, annars skapas flera scanners som läser från samma
		// ström
		setIn("%s\n%s\n".formatted(dogName, ownerName));
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);

		assertOwnedBy(nameToDog.get(dogName), nameToOwner.get(ownerName));
		assertOwnsDog(nameToOwner.get(ownerName), nameToDog.get(dogName));
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Ge alla förberedda hundar till en ägare")
	public void giveAllDogsToOneOwners() {
		// Måste ske innan sut skapas, annars skapas flera scanners som läser från samma
		// ström
		setIn("Lassie\nUlla\nFido\nUlla\nRonja\nUlla\nCharlie\nUlla\nBamse\nUlla\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);

		for (Dog dog : PREPARED_DOGS) {
			assertOwnedBy(dog, ULLA);
			assertOwnsDog(ULLA, dog);
		}

		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Ge alla förberedda hundar en ägare")
	public void giveAllDogsToOwners() {
		// Måste ske innan sut skapas, annars skapas flera scanners som läser från samma
		// ström
		setIn("Lassie\nStefan\nFido\nUlla\nRonja\nHelga\nCharlie\nKalle\nBamse\nPatrick\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);
		GIVE_DOG_METHOD.invoke(sut);

		assertOwnedBy(LASSIE, STEFAN);
		assertOwnsDog(STEFAN, LASSIE);

		assertOwnedBy(FIDO, ULLA);
		assertOwnsDog(ULLA, FIDO);

		assertOwnedBy(RONJA, HELGA);
		assertOwnsDog(HELGA, RONJA);

		assertOwnedBy(CHARLIE, KALLE);
		assertOwnsDog(KALLE, CHARLIE);

		assertOwnedBy(BAMSE, PATRICK);
		assertOwnsDog(PATRICK, BAMSE);

		out().assertDoesNotContainErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "' \tFido\t \nKalle\n', Blanktecken runt hundnamnet",
			"'Fido\n\t Kalle\t \n', Blanktecken runt ägarnamnet" })
	@DisplayName(value = "Hanteras blanktecken tabbar och runt namnen?")
	public void whiteSpaceAroundNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);

		assertOwnedBy(FIDO, KALLE);
		assertOwnsDog(KALLE, FIDO);

		out().assertDoesNotContainErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "'\nFido\nKalle\n', Blankt hundnamn", "'Fido\n\nKalle\n', Blankt ägarnamn",
			"'  \t \nFido\nKalle\n', Bara blanktecken i hundnamnet",
			"'Fido\n \t \nKalle\n', Bara blanktecken i ägarnamnet",
			"'\n  \t\n\n\n \nFido\n\t \n   \n\n\nKalle\n', Flera blandade blanktecken och tomma namn" })
	@DisplayName(value = "Hanteras blanktecken och tomma namn korrekt?")
	public void whiteSpaceAndEmptyNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);

		assertOwnedBy(FIDO, KALLE);
		assertOwnsDog(KALLE, FIDO);

		out().assertContainsErrorMessage();
	}

	@ParameterizedTest(name = "{index} {0} ges till {1} ")
	@CsvSource(value = { "FIDO, KALLE", "fido, kalle", "FiDo, kALLe" })
	@DisplayName(value = "Hanteras namn på olika format?")
	public void differentNameFormatsHandled(String dog, String owner) {
		setIn("%s\n%s\n".formatted(dog, owner));
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);

		assertOwnedBy(FIDO, KALLE);
		assertOwnsDog(KALLE, FIDO);

		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försök att ge bort en hund som inte existerar ska ge felmeddelande")
	public void giveNonexistingDogGivesError() {
		setIn("NotADog\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);

		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försök att ge bort en hund när det inte finns några hundar ska ge felmeddelande")
	public void giveDogWithNoDogsRegisteredGivesError() {
		setIn("Anyone\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);

		out().assertContainsErrorMessage();

	}

	@Test
	@DisplayName(value = "Försök att ge bort en hund som redan har en ägare ska ge felmeddelande")
	public void giveAlreadyOwnedDogGivesError() {
		setIn("Charlie\nPatrick\nCharlie\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);
		out().assertDoesNotContainErrorMessage();

		GIVE_DOG_METHOD.invoke(sut);
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försök att ge bort en hund till en ägare som inte existerar ska ge felmeddelande")
	public void giveDogToNonexistingOwnerGivesError() {
		setIn("Charlie\nNoOne\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		GIVE_DOG_METHOD.invoke(sut);
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försök att ge bort en hund när det inte finns några ägare ska ge felmeddelande")
	public void giveDogWithNoOwnersRegisteredGivesError() {
		setIn("Ronja\nUlla\n");
		AssignmentEightPointThree sut = new AssignmentEightPointThree();

		addDogs(sut, PREPARED_DOGS);

		GIVE_DOG_METHOD.invoke(sut);
		out().assertContainsErrorMessage();
	}
}

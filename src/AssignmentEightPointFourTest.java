/*
 * Denna fil innehåller JUnit-testfall för lista ägare och hundar .
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

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;

@DisplayName(value = "JUnit-testfall för U8.4 - lista ägare och uppdaterad lista hundar")
public class AssignmentEightPointFourTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointFour.class,
			"AssignmentEightPointFour");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME,
			"TestData.MAIN_OWNER_LIST_NAME");

	private static final MethodUnderTest LIST_DOGS_METHOD = CUT.getMethod(TestData.LIST_DOGS_METHOD_NAME,
			"TestData.LIST_DOGS_METHOD_NAME");
	private static final MethodUnderTest LIST_OWNERS_METHOD = CUT.getMethod(TestData.LIST_OWNERS_METHOD_NAME,
			"TestData.LIST_OWNERS_METHOD_NAME");

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointFour");
	private static final MethodUnderTest SET_OWNER_OF_DOG_METHOD = DOG_CLASS
			.getMethod(TestData.SET_OWNER_OF_DOG_METHOD_NAME, "TestData.SET_OWNER_OF_DOG_METHOD_NAME", Owner.class);

	// Ej static för att testerna inte ska störa varandra
	private final Dog DORIS = new Dog("Doris", "Yorkshireterrier", 1, 12);
	private final Dog RONJA = new Dog("Ronja", "Tax", 12, 18);
	private final Dog CHARLIE = new Dog("Charlie", "Golden retriever", 10, 19);

	private final Owner FILIPPA = new Owner("Filippa");
	private final Owner ERIK = new Owner("Erik");
	private final Owner MARTIN = new Owner("Martin");

	private final Dog[] PREPARED_DOGS = { DORIS, RONJA, CHARLIE };
	private final Owner[] PREPARED_OWNERS = { FILIPPA, ERIK, MARTIN };

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointFourTest() {
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
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, LIST_OWNERS_METHOD, LIST_DOGS_METHOD);
	}

	@SuppressWarnings("unchecked")
	private void addDogs(AssignmentEightPointFour sut, Dog... dogs) {
		((Collection<Dog>) MAIN_DOG_LIST.getValue(sut)).addAll(Arrays.asList(dogs));
	}

	@SuppressWarnings("unchecked")
	private void addOwners(AssignmentEightPointFour sut, Owner... owners) {
		((Collection<Owner>) MAIN_OWNER_LIST.getValue(sut)).addAll(Arrays.asList(owners));
	}

	private void assertOutContainsOnlyExpected(Collection<String> expected, Collection<String> unexpected) {
		for (String name : expected) {
			out().assertContainsIgnoreCase(name);
		}

		for (String name : unexpected) {
			out().assertDoesNotContainsIgnoreCase(name);
		}
	}

	private void assertOutContainsOnlyExpectedDogs(Dog... dogs) {
		Collection<String> expected = Arrays.stream(dogs).map(d -> d.getName()).toList();
		Collection<String> unexpected = new ArrayList<>(Arrays.stream(PREPARED_DOGS).map(d -> d.getName()).toList());
		unexpected.removeAll(expected);

		assertOutContainsOnlyExpected(expected, unexpected);
	}

	private void assertOutContainsOnlyExpectedOwners(Owner... owners) {
		Collection<String> expected = Arrays.stream(owners).map(o -> o.getName()).toList();
		Collection<String> unexpected = new ArrayList<>(Arrays.stream(PREPARED_OWNERS).map(o -> o.getName()).toList());
		unexpected.removeAll(expected);

		assertOutContainsOnlyExpected(expected, unexpected);
	}

	private void addDogToOwner(Owner owner, Dog dog) {
		SET_OWNER_OF_DOG_METHOD.invoke(dog, owner);
	}

	@Test
	@DisplayName(value = "Lista en ägare utan hund")
	public void listSingleOwnerWithNoDogs() {
		// Används inte, men nödvändig för att input-
		// klassen inte ska kasta ett undantag
		setIn("");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, FILIPPA);

		LIST_OWNERS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(FILIPPA);
		assertOutContainsOnlyExpectedDogs();
	}

	@Test
	@DisplayName(value = "Lista en ägare med en hund")
	public void listSingleOwnerWithSingleDog() {
		// Används inte, men nödvändig för att input-
		// klassen inte ska kasta ett undantag
		setIn("");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, ERIK);
		addDogToOwner(ERIK, RONJA);

		LIST_OWNERS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(ERIK);
		assertOutContainsOnlyExpectedDogs(RONJA);
	}

	@Test
	@DisplayName(value = "Lista en ägare med flera hundar")
	public void listSingleOwnerWithMultipleDogs() {
		// Används inte, men nödvändig för att input-
		// klassen inte ska kasta ett undantag
		setIn("");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, MARTIN);
		addDogToOwner(MARTIN, DORIS);
		addDogToOwner(MARTIN, CHARLIE);
		addDogToOwner(MARTIN, RONJA);

		LIST_OWNERS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(MARTIN);
		assertOutContainsOnlyExpectedDogs(RONJA, DORIS, CHARLIE);
	}

	@Test
	@DisplayName(value = "Lista flera ägare med hundar")
	public void listMultipleOwnersWithDogs() {
		// Används inte, men nödvändig för att input-
		// klassen inte ska kasta ett undantag
		setIn("");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, ERIK, FILIPPA);
		addDogToOwner(ERIK, RONJA);
		addDogToOwner(FILIPPA, CHARLIE);

		LIST_OWNERS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(ERIK, FILIPPA);
		assertOutContainsOnlyExpectedDogs(RONJA, CHARLIE);
	}

	@Test
	@DisplayName(value = "Lista flera ägare, vissa med hundar")
	public void listMultipleOwnersSomeWithDogs() {
		// Används inte, men nödvändig för att input-
		// klassen inte ska kasta ett undantag
		setIn("");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);
		addDogToOwner(ERIK, RONJA);
		addDogToOwner(FILIPPA, CHARLIE);

		LIST_OWNERS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(PREPARED_OWNERS);
		assertOutContainsOnlyExpectedDogs(RONJA, CHARLIE);
	}

	@Test
	@DisplayName(value = "Lista en hund utan ägare")
	public void listDogWithNoOwner() {
		setIn("0\n");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, DORIS);
		addOwners(sut, PREPARED_OWNERS);

		LIST_DOGS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners();
		assertOutContainsOnlyExpectedDogs(DORIS);
	}

	@Test
	@DisplayName(value = "Lista en hund med ägare")
	public void listDogWithOwner() {
		setIn("0\n");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, RONJA);
		addOwners(sut, PREPARED_OWNERS);
		addDogToOwner(ERIK, RONJA);

		LIST_DOGS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(ERIK);
		assertOutContainsOnlyExpectedDogs(RONJA);
	}

	@Test
	@DisplayName(value = "Lista flera hundar, varav vissa har ägare")
	public void listMultipelDogsSomeWithOwners() {
		setIn("0\n");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);
		addDogToOwner(FILIPPA, CHARLIE);
		addDogToOwner(ERIK, RONJA);

		// List dogs
		LIST_DOGS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners(FILIPPA, ERIK);
		assertOutContainsOnlyExpectedDogs(PREPARED_DOGS);
	}

	@Test
	@DisplayName(value = "Lista bara hundar med tillräckligt lång svans")
	public void listOnlyDogsWithLongEnoughTail() {
		setIn("2\n");
		AssignmentEightPointFour sut = new AssignmentEightPointFour();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		// Ronja och Charlie
		LIST_DOGS_METHOD.invoke(sut);

		assertOutContainsOnlyExpectedOwners();
		assertOutContainsOnlyExpectedDogs(RONJA, CHARLIE);
	}

}

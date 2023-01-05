/*
 * Denna fil innehåller JUnit-testfall för att ta bort en hund från en ägare U8.6.
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

@DisplayName(value = "JUnit-testfall för U8.6 - metoden för att ta bort en hund från en ägare")
public class AssignmentEightPointSixTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointSix.class,
			"AssignmentEightPointSixTest");
	private static final MethodUnderTest REMOVE_OWNED_DOG_METHOD = CUT.getMethod(TestData.REMOVE_OWNED_DOG_METHOD_NAME,
			"TestData.REMOVE_OWNED_DOG_METHOD_NAME");

	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME,
			"TestData.MAIN_OWNER_LIST_NAME");

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointSixTest");
	private static final FieldUnderTest DOGS_OWNER = DOG_CLASS.getOnlyFieldAssignableFromType(Owner.class);

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class, "AssignmentEightPointSixTest");
	private static final MethodUnderTest ADD_DOG_TO_OWNER_METHOD = OWNER_CLASS
			.getMethod(TestData.ADD_DOG_TO_OWNER_METHOD_NAME, "TestData.ADD_DOG_TO_OWNER_METHOD_NAME", Dog.class);
	private static final MethodUnderTest OWNS_DOG_METHOD = OWNER_CLASS.getMethod(TestData.OWNS_DOG_METHOD_NAME,
			"TestData.OWNS_DOG_METHOD_NAME", Dog.class);

	// Ej static för att testerna inte ska störa varandra
	private final Dog BAMSE = new Dog("Bamse", "Dobermann", 13, 17);
	private final Dog CHARLIE = new Dog("Charlie", "Tax", 20, 3);
	private final Dog LUDDE = new Dog("Ludde", "Grand danois", 14, 15);
	private final Dog BELLA = new Dog("Bella", "Yorkshireterrier", 2, 13);
	private final Dog SIGGE = new Dog("Sigge", "Tax", 11, 12);

	private final Dog[] PREPARED_DOGS = { BAMSE, CHARLIE, LUDDE, BELLA, SIGGE };

	private final Owner OLLE = new Owner("Olle");
	private final Owner SVEA = new Owner("Svea");
	private final Owner STEFAN = new Owner("Stefan");
	private final Owner GUSTAV = new Owner("Gustav");
	private final Owner HENRIK = new Owner("Henrik");

	private final Owner[] PREPARED_OWNERS = { OLLE, SVEA, STEFAN, GUSTAV, HENRIK };

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointSixTest() {
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
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, REMOVE_OWNED_DOG_METHOD);
	}

	@SuppressWarnings("unchecked")
	private Collection<Dog> dogs(AssignmentEightPointSix sut) {
		return ((Collection<Dog>) MAIN_DOG_LIST.getValue(sut));
	}

	@SuppressWarnings("unchecked")
	private Collection<Owner> owners(AssignmentEightPointSix sut) {
		return ((Collection<Owner>) MAIN_OWNER_LIST.getValue(sut));
	}

	private void addDogs(AssignmentEightPointSix sut, Dog... dogs) {
		dogs(sut).addAll(Arrays.asList(dogs));
	}

	private void addOwners(AssignmentEightPointSix sut, Owner... owners) {
		owners(sut).addAll(Arrays.asList(owners));
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund från dess ägare")
	public void removingOwnedDogRemovesItFromTheOwner() {
		setIn("SIGGE\n");
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);
		ADD_DOG_TO_OWNER_METHOD.invoke(GUSTAV, SIGGE);
		assertTrue((boolean) OWNS_DOG_METHOD.invoke(GUSTAV, SIGGE), "Gustav äger inte Sigge som förväntat");

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		assertFalse((boolean) OWNS_DOG_METHOD.invoke(GUSTAV, SIGGE),
				"Gustav äger fortarande Sigge när denna borde vara borttagen");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund från dess ägare, detta ska leda till att hundens ägare också tas bort")
	public void removingOwnedDogRemovesTheOwnerFromTheDog() {
		setIn("Ludde\n");
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);
		ADD_DOG_TO_OWNER_METHOD.invoke(SVEA, LUDDE);
		assertEquals(SVEA, DOGS_OWNER.getValue(LUDDE), "Luddes ägare är inte Svea som förväntat");

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		assertEquals(null, DOGS_OWNER.getValue(LUDDE), "Luddes ägare är inte null som förväntat");

	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund från dess ägare, hunden ska vara kvar i listan")
	public void removingOwnedDogLeavesItInList() {
		setIn("Ludde\n");
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);
		ADD_DOG_TO_OWNER_METHOD.invoke(SVEA, LUDDE);
		assertEquals(SVEA, DOGS_OWNER.getValue(LUDDE), "Luddes ägare är inte Svea som förväntat");

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		assertTrue(dogs(sut).contains(LUDDE),
				"Ludde har tagits bort ur listan av hundar, men den skulle bara tagits bort från ägaren");
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund som inte ägs av någon från en ägare")
	public void removingDogWithNoOwnerGivesError() {
		setIn("Bamse\n");
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund som finns från en ägare")
	public void removingDogThatDoesNotExistsGivesError() {
		setIn("Nodog\n");
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		out().assertContainsErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "' \tBamse\t \n', Blanktecken runt hundnamnet" })
	@DisplayName(value = "Hanteras blanktecken tabbar och runt namnen?")
	public void whiteSpaceAroundNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		ADD_DOG_TO_OWNER_METHOD.invoke(SVEA, BAMSE);

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		assertFalse((boolean) OWNS_DOG_METHOD.invoke(SVEA, BAMSE),
				"Svea äger fortarande Bamse när denna borde vara borttagen");

		out().assertDoesNotContainErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "'\nBamse\n', Blankt hundnamn", "'  \t \nBamse\n', Bara blanktecken i hundnamnet",
			"'\n  \t\n\n     \n\n \nBamse\n', Flera blandade blanktecken och tomma namn" })
	@DisplayName(value = "Hanteras blanktecken och tomma namn korrekt?")
	public void whiteSpaceAndEmptyNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		ADD_DOG_TO_OWNER_METHOD.invoke(SVEA, BAMSE);

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		assertFalse((boolean) OWNS_DOG_METHOD.invoke(SVEA, BAMSE),
				"Svea äger fortarande Bamse när denna borde vara borttagen");

		out().assertContainsErrorMessage();
	}

	@ParameterizedTest(name = "{index} {0} tas bort från {1} ")
	@CsvSource(value = { "BAMSE, SVEA", "bamse, svea", "BaMsE, sVeA" })
	@DisplayName(value = "Hanteras namn på olika format?")
	public void differentNameFormatsHandled(String dog, String owner) {
		setIn("%s\n%s\n".formatted(dog, owner));
		AssignmentEightPointSix sut = new AssignmentEightPointSix();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		ADD_DOG_TO_OWNER_METHOD.invoke(SVEA, BAMSE);

		REMOVE_OWNED_DOG_METHOD.invoke(sut);

		assertFalse((boolean) OWNS_DOG_METHOD.invoke(SVEA, BAMSE),
				"Svea äger fortarande Bamse när denna borde vara borttagen");

		out().assertDoesNotContainErrorMessage();
	}

}
//ENDOFCLASS

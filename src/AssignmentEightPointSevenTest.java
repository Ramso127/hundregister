/*
 * Denna fil innehåller JUnit-testfall för funktionen för att ta bort en ägare U8.7.
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


import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName(value = "JUnit-testfall för U8.7 - ta bort en ägare")
public class AssignmentEightPointSevenTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointSeven.class,
			"AssignmentEightPointSevenTest");
	private static final MethodUnderTest REMOVE_OWNER_METHOD = CUT.getMethod(TestData.REMOVE_OWNER_METHOD_NAME,
			"TestData.REMOVE_OWNER_METHOD_NAME");

	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME, Collection.class,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME, Collection.class,
			"TestData.MAIN_OWNER_LIST_NAME");

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointSevenTest");
	private static final MethodUnderTest SET_OWNER_OF_DOG_METHOD = DOG_CLASS
			.getMethod(TestData.SET_OWNER_OF_DOG_METHOD_NAME, "TestData.SET_OWNER_OF_DOG_METHOD_NAME", Owner.class);

	// Ej static för att testerna inte ska störa varandra
	private final Dog SIGGE = new Dog("Sigge", "Pudel", 4, 6);
	private final Dog LASSIE = new Dog("Lassie", "Dachshund", 8, 6);
	private final Dog CHARLIE = new Dog("Charlie", "Golden retriever", 2, 12);
	private final Dog LUDDE = new Dog("Ludde", "Vinthund", 11, 6);
	private final Dog DORIS = new Dog("Doris", "Tax", 16, 9);

	private final Owner NIKE = new Owner("Nike");
	private final Owner BERTIL = new Owner("Bertil");
	private final Owner JOZEF = new Owner("Jozef");
	private final Owner PATRICK = new Owner("Patrick");
	private final Owner ERIK = new Owner("Erik");

	private final Dog[] PREPARED_DOGS = { SIGGE, LASSIE, CHARLIE, LUDDE, DORIS };
	private final Owner[] PREPARED_OWNERS = { NIKE, BERTIL, JOZEF, PATRICK, ERIK };

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointSevenTest() {
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
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, REMOVE_OWNER_METHOD);
	}

	@SuppressWarnings("unchecked")
	private void addDogs(AssignmentEightPointSeven sut, Dog... dogs) {
		((Collection<Dog>) MAIN_DOG_LIST.getValue(sut)).addAll(Arrays.asList(dogs));
	}

	@SuppressWarnings("unchecked")
	private void addOwners(AssignmentEightPointSeven sut, Owner... owners) {
		((Collection<Owner>) MAIN_OWNER_LIST.getValue(sut)).addAll(Arrays.asList(owners));
	}

	@Test
	@DisplayName(value = "Försöker ta bort en ägare som existerar")
	public void removeOwnerDoesRemovesOwner() {
		setIn("Bertil\n");
		AssignmentEightPointSeven sut = new AssignmentEightPointSeven();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNER_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_OWNER_LIST, sut, BERTIL, (Object[]) PREPARED_OWNERS);
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en ägare som inte existerar")
	public void removeNonexistingOwnerGivesError() {
		setIn("Nobody\n");
		AssignmentEightPointSeven sut = new AssignmentEightPointSeven();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNER_METHOD.invoke(sut);

		assertListContains(MAIN_OWNER_LIST, sut, (Object[]) PREPARED_OWNERS);
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en ägare med hundar, hundarna ska också tas bort")
	public void removeOwnerAlsoRemovesOwnedDogs() {
		setIn("Jozef\n");
		AssignmentEightPointSeven sut = new AssignmentEightPointSeven();

		addDogs(sut, PREPARED_DOGS);
		addOwners(sut, PREPARED_OWNERS);
		SET_OWNER_OF_DOG_METHOD.invoke(LUDDE, JOZEF);
		SET_OWNER_OF_DOG_METHOD.invoke(DORIS, JOZEF);
		SET_OWNER_OF_DOG_METHOD.invoke(SIGGE, NIKE);

		REMOVE_OWNER_METHOD.invoke(sut);

		assertListContains(MAIN_DOG_LIST, sut, SIGGE, LASSIE, CHARLIE);
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "'\t Patrick\t \n', Blanktecken runt ägarnamnet" })
	@DisplayName(value = "Hanteras blanktecken tabbar och runt namnen?")
	public void whiteSpaceAroundNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointSeven sut = new AssignmentEightPointSeven();

		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNER_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_OWNER_LIST, sut, PATRICK, (Object[]) PREPARED_OWNERS);
		out().assertDoesNotContainErrorMessage();
	}

	@ParameterizedTest(name = "{index} {1}")
	@CsvSource(value = { "'\nPatrick\n', Blankt ägarnamn", "' \t \nPatrick\n', Bara blanktecken i ägarnamnet",
			"'\n\t \n   \n\n\nPatrick\n', Flera blandade blanktecken och tomma namn" })
	@DisplayName(value = "Hanteras blanktecken och tomma namn korrekt?")
	public void whiteSpaceAndEmptyNamesHandled(String in, String description) {
		setIn(in);
		AssignmentEightPointSeven sut = new AssignmentEightPointSeven();

		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNER_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_OWNER_LIST, sut, PATRICK, (Object[]) PREPARED_OWNERS);
		out().assertContainsErrorMessage();
	}

	@ParameterizedTest(name = "{index} {0}")
	@CsvSource(value = { "PATRICK", "patrick", "PaTrIcK" })
	@DisplayName(value = "Hanteras namn på olika format?")
	public void differentNameFormatsHandled(String owner) {
		setIn("%s\n".formatted(owner));
		AssignmentEightPointSeven sut = new AssignmentEightPointSeven();

		addOwners(sut, PREPARED_OWNERS);

		REMOVE_OWNER_METHOD.invoke(sut);

		assertListContainsEveryoneBut(MAIN_OWNER_LIST, sut, PATRICK, (Object[]) PREPARED_OWNERS);
		out().assertDoesNotContainErrorMessage();
	}

}
//ENDOFCLASS

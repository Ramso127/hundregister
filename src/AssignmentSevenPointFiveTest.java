/*
 * Denna fil innehåller JUnit-testfall för metoden för att ta bort en given hund U7.5 .
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

@DisplayName(value = "JUnit-testfall för U7.5 - metoden för att ta bort en hund ur listan")
public class AssignmentSevenPointFiveTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentSevenPointFive.class,
			"AssignmentSevenPointFiveTest");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final MethodUnderTest MUT = CUT.getMethod(TestData.REMOVE_DOG_METHOD_NAME,
			"TestData.REMOVE_DOG_METHOD_NAME");

	private final Dog BELLA = new Dog("Bella", "Labrador", 3, 12);
	private final Dog RATATA = new Dog("Ratata", "Tax", 15, 7);
	private final Dog DORIS = new Dog("Doris", "Cocker spaniel", 7, 7);
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
	public AssignmentSevenPointFiveTest() {
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
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, MUT);
	}

	private List<Dog> dogs(AssignmentSevenPointFive sut) {
		return getList(MAIN_DOG_LIST, sut);
	}

	@Test
	@DisplayName(value = "Försöker ta bort den enda hunden i listan")
	public void removingOnlyDog() {
		setIn("Lassie\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);
		dogs.add(LASSIE);

		MUT.invoke(sut);

		assertTrue(dogs.isEmpty(), String.format("Listan av hundar borde vara tom: %s", dogs));
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort den första hunden i listan")
	public void removingFirstDog() {
		setIn("Bella\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(4, dogs.size(), "Storleken på hundlistan är fel");
		assertEquals(Arrays.asList(RATATA, DORIS, LASSIE, CHARLIE), dogs, "Hundlistan innehåller fel hundar");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund mitt i listan")
	public void removingMiddleDog() {
		setIn("Doris\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(4, dogs.size(), "Storleken på hundlistan är fel");
		assertEquals(Arrays.asList(BELLA, RATATA, LASSIE, CHARLIE), dogs, "Hundlistan innehåller fel hundar");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort den sista hunden i listan")
	public void removingLastDog() {
		setIn("Charlie\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(4, dogs.size(), "Storleken på hundlistan är fel");
		assertEquals(Arrays.asList(BELLA, RATATA, DORIS, LASSIE), dogs, "Hundlistan innehåller fel hundar");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund men anger namnet på ett annat sätt än när hunden skapades")
	public void removingDogWithNameInDifferentCase() {
		setIn("RAtaTa\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(4, dogs.size(), "Storleken på hundlistan är fel");
		assertEquals(Arrays.asList(BELLA, DORIS, LASSIE, CHARLIE), dogs, "Hundlistan innehåller fel hundar");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund som inte finns")
	public void removingNonexistingDogGivesErrorMessage() {
		setIn("Ingen hund\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(5, dogs.size(), "Storleken på hundlistan är fel");
		assertEquals(Arrays.asList(BELLA, RATATA, DORIS, LASSIE, CHARLIE), dogs, "Hundlistan innehåller fel hundar");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund när det inte finns några hundar i listan")
	public void removingDogWithNoDogsInListGivesErrorMessage() {
		setIn("Ingen hund\n");

		AssignmentSevenPointFive sut = new AssignmentSevenPointFive();
		List<Dog> dogs = dogs(sut);

		MUT.invoke(sut);

		assertEquals(0, dogs.size(), "Storleken på hundlistan är fel");
		out().assertContainsErrorMessage();
	}


}

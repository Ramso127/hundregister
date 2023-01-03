/*
 * Denna fil innehåller JUnit-testfall för metoden för att öka en hunds ålder U7.4 .
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



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;

@DisplayName(value = "JUnit-testfall för U7.4 - metod för att öka åldern på en given hund")
public class AssignmentSevenPointFourTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentSevenPointFour.class,
			"AssignmentSevenPointFourTest");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final MethodUnderTest MUT = CUT.getMethod(TestData.INCREASE_AGE_METHOD_NAME,
			"TestData.INCREASE_AGE_METHOD_NAME");

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
	public AssignmentSevenPointFourTest() {
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

	private List<Dog> dogs(AssignmentSevenPointFour sut) {
		return getList(MAIN_DOG_LIST, sut);
	}

	@Test
	@DisplayName(value = "Försöker öka åldern på den enda hunden i listan")
	public void increaseAgeOfOnlyDog() {
		setIn("Ratata\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.add(RATATA);

		MUT.invoke(sut);

		assertEquals(16, RATATA.getAge());
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker öka åldern på den första hunden i listan")
	public void increaseAgeOfFirstDog() {
		setIn("Bella\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(4, BELLA.getAge());
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker öka åldern på hunden mitt i listan")
	public void increaseAgeOfMiddleDog() {
		setIn("Doris\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(8, DORIS.getAge());
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker öka åldern på den sista hunden i listan")
	public void increaseAgeOfLastDog() {
		setIn("Charlie\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(10, CHARLIE.getAge());
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker öka åldern på en hund med namnet angivet i ett annat format")
	public void increaseAgeOfDogWithNameInDifferentCase() {
		setIn("RAtaTa\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(16, RATATA.getAge());
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker öka ålder på en hund som inte existerar")
	public void increaseAgeOfDogNonexistingDogGivesErrorMessage() {
		setIn("Ingen hund\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker öka åldern på en hund när det inte finns några hundar i listan")
	public void increaseAgeOfDogWithNoDogsInListGivesErrorMessage() {
		setIn("Ingen hund\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();

		MUT.invoke(sut);

		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Att öka åldern på en hund ska inte påverka de andra hundarna")
	public void increaseAgeOfDogDoesNotAffectOtherDogs() {
		setIn("Doris\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(3, BELLA.getAge());
		assertEquals(15, RATATA.getAge());
		assertEquals(11, LASSIE.getAge());
		assertEquals(9, CHARLIE.getAge());
	}

	@Test
	@DisplayName(value = "När åldern ökar ska svanslängden också öka")
	public void increaseAgeOfDogUpdatesTailLength() {
		setIn("Charlie\n");

		AssignmentSevenPointFour sut = new AssignmentSevenPointFour();
		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(PREPARED_DOGS));

		MUT.invoke(sut);

		assertEquals(12.0, CHARLIE.getTailLength(), 0.01);
	}

}

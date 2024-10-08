/*
 * Denna fil innehåller JUnit-testfall för metoden för att lägga till en hund i listan U7.1.
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

@DisplayName(value = "JUnit-testfall för U7.1 - metoden för att läsa in data om en hund")
public class AssignmentSevenPointOneTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentSevenPointOne.class,
			"AssignmentSevenPointOneTest");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final MethodUnderTest REGISTER_NEW_DOG_METHOD = CUT.getMethod(TestData.REGISTER_NEW_DOG_METHOD_NAME,
			"TestData.REGISTER_NEW_DOG_METHOD_NAME");

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentSevenPointOneTest() {
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
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, REGISTER_NEW_DOG_METHOD);
	}

	private List<Dog> dogs(AssignmentSevenPointOne sut) {
		return getList(MAIN_DOG_LIST, sut);
	}

	private void assertDogIs(String expectedName, String expectedBreed, int expectedAge, int expectedWeight, Dog dog) {
		assertEqualsIgnoreCase(expectedName, dog.getName(), "Namnet är fel på hunden");
		assertEqualsIgnoreCase(expectedBreed, dog.getBreed(), "Rasen är fel på hunden");
		assertEquals(expectedAge, dog.getAge(), "Åldern är fel på hunden");
		assertEquals(expectedWeight, dog.getWeight(), "Vikten är fel på hunden");
	}

	@Test
	@DisplayName(value = "Går det att lägga till en hund till listan?")
	public void addingOneDog() {
		setIn("A\nB\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertDogIs("A", "B", 1, 2, dogs.get(0));
	}

	@Test
	@DisplayName(value = "Går det att lägga till två hundar till listan?")
	public void addingTwoDogs() {
		setIn("A\nB\n1\n2\nC\nD\n3\n4\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);
		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(2, dogs.size(), "Fel antal hundar i listan");

		// Det finns inget som kräver att hundarna ligger i någon speciell
		// ordning, så vi får mickla lite för att kolla var de finns
		final int[] aFirst = { 0, 1 };
		final int[] bFirst = { 1, 0 };

		int[] index = dogs.get(0).getName().equalsIgnoreCase("A") ? aFirst : bFirst;

		assertDogIs("A", "B", 1, 2, dogs.get(index[0]));
		assertDogIs("C", "D", 3, 4, dogs.get(index[1]));
	}

	@Test
	@DisplayName("Blanktecken i namn bevaras")
	public void spacesAreKeptWithinNames() {
		setIn("Fluffy Destroyer of Worlds\nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Fluffy Destroyer of Worlds", dogs.get(0).getName(), "Fel namn på hunden");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName("Blanktecken tas bort runt namn")
	public void extraWhitespaceRemovedAroundName() {
		setIn(" \t Karo\t \nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Karo", dogs.get(0).getName(), "Fel namn på hunden");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Tomma namn frågas efter igen")
	public void emptyNameAskedForAgain() {
		setIn("\nKaro\nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Karo", dogs.get(0).getName(), "Fel namn på hunden");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Namn som bara består av blanka tecken frågas efter igen")
	public void whitespaceOnlyNameAskedForAgain() {
		setIn("  \t\t  \nKaro\nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Karo", dogs.get(0).getName(), "Fel namn på hunden");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Flera misslyckade försök att skriva in namn hanteras")
	public void multipleEmptyAndWhitespaceOnlyNamesHandled() {
		setIn("\n\t\n \n\nKaro\nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Karo", dogs.get(0).getName(), "Fel namn på hunden");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName("Blanktecken i rasen bevaras")
	public void spacesAreKeptWithinBreed() {
		setIn("Karo\nShih Tzu\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Shih Tzu", dogs.get(0).getBreed(), "Fel ras på hunden");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName("Blanktecken tas bort runt rasen")
	public void extraWhitespaceRemovedAroundBreed() {
		setIn("Karo\n\t Tax\t \n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Tax", dogs.get(0).getBreed(), "Fel ras på hunden");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Tomma rasnamn frågas efter igen")
	public void emptyBreedAskedForAgain() {
		setIn("Karo\n\nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Tax", dogs.get(0).getBreed(), "Fel ras på hunden");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Raser som bara består av blanka tecken frågas efter igen")
	public void whitespaceOnlyBreedAskedForAgain() {
		setIn("Karo\n  \t\t  \nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Tax", dogs.get(0).getBreed(), "Fel ras på hunden");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Flera misslyckade försök att skriva in ras hanteras")
	public void multipleEmptyAndWhitespaceOnlyBreedsHandled() {
		setIn("Karo\n\n\t\n \n\nTax\n1\n2\n");

		AssignmentSevenPointOne sut = new AssignmentSevenPointOne();
		List<Dog> dogs = dogs(sut);

		REGISTER_NEW_DOG_METHOD.invoke(sut);

		assertEquals(1, dogs.size(), "Fel antal hundar i listan");
		assertEqualsIgnoreCase("Tax", dogs.get(0).getBreed(), "Fel ras på hunden");
		out().assertContainsErrorMessage();
	}


}

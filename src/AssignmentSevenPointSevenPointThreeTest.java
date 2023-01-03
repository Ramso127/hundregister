/*
 * Denna fil innehåller JUnit-testfall för metoden för att hitta den minsta hunden U7.6.3, den kräver tillgång till AssignmentSevenPointSixTest.
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

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName(value = "JUnit-testfall för U7.7.3 - metoden för att hitta den \"minsta\" kvarvarande hunden")
public class AssignmentSevenPointSevenPointThreeTest extends AssignmentSevenPointSevenTest {

	private static final MethodUnderTest FIND_SMALLEST_METHOD = CUT.getMethod(TestData.FIND_SMALLEST_METHOD_NAME,
			"TestData.FIND_SMALLEST_METHOD_NAME", int.class);

	public AssignmentSevenPointSevenPointThreeTest() throws IllegalArgumentException, IllegalAccessException {
		super(1, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Är ordningen av hundarna oförändrad?")
	public void noChangeInOrder() {
		addAllPreparedDogs();
		FIND_SMALLEST_METHOD.invoke(sut, 0);
		assertDogsAre("Metoden för att hitta den minsta hunden ska inte förändra listan",
				ORIGINAL_ORDER_OF_PREPARED_DOGS);
	}

	public void indexOfSmallestDogCorrectlyIdentified(int start, int expected) {
		int actual = (Integer) FIND_SMALLEST_METHOD.invoke(sut, start);
		assertEquals(expected, actual, "Fel hund-index identifierat som minst. Startpositionen var %d, occh listan: %s"
				.formatted(start, dogs()));
	}

	@ParameterizedTest(name = "{index} om vi startar från {0} finns den \"minsta\" hunden på {1}")
	@CsvSource(value = { "0,3", "4,7", "7,7" })
	@DisplayName(value = "Hittas den \"minsta\" hunden?")
	public void findsSmallestDog(int start, int expected) {
		addAllPreparedDogs();
		indexOfSmallestDogCorrectlyIdentified(start, expected);
	}

	@Test
	@DisplayName(value = "Samma svanslängd, namnen i \"fel\" ordning")
	public void twoDogsWithSameTailLengthWrongOrder() {
		addDogs(MOLLY, DORIS);
		indexOfSmallestDogCorrectlyIdentified(0, 1);
	}

	@Test
	@DisplayName(value = "Samma svanslängd, namnen i \"rätt\" ordning")
	public void twoDogsWithSameTailLengthCorrectOrder() {
		addDogs(DORIS, MOLLY);
		indexOfSmallestDogCorrectlyIdentified(0, 0);
	}

	@Test
	@DisplayName(value = "Samma svanslängd, namnen i \"fel\" ordning, det kortare namnet är ett prefix till det längre")
	public void twoDogsWithSameTailLengthAndNamesThatDifferInLengthWrongOrder() {
		Dog d1 = new Dog("Fido", "Tax", 1, 2);
		Dog d2 = new Dog("Fidofilus", "Tax", 1, 2);

		addDogs(d2, d1);
		indexOfSmallestDogCorrectlyIdentified(0, 1);
	}

	@Test
	@DisplayName(value = "Samma svanslängd, namnen i \"rätt\" ordning, det kortare namnet är ett prefix till det längre")
	public void twoDogsWithSameTailLengthAndNamesThatDifferInLengthCorrectOrder() {
		Dog d1 = new Dog("Fido", "Tax", 1, 2);
		Dog d2 = new Dog("Fidofilus", "Tax", 1, 2);

		addDogs(d1, d2);
		indexOfSmallestDogCorrectlyIdentified(0, 0);
	}


}

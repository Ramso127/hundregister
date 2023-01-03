/*
 * Denna fil innehåller JUnit-testfall för metoden för att hitta en hund given namnet U7.3.
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
import org.junit.jupiter.params.provider.*;

@DisplayName(value = "JUnit-testfall för U7.3 - metoden för att hitta en given hund")
public class AssignmentSevenPointThreeTest extends ApiBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentSevenPointThree.class,
			"AssignmentSevenPointThreeTest");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final MethodUnderTest FIND_DOG_METHOD = CUT.getMethod(TestData.FIND_DOG_METHOD_NAME,
			"TestData.FIND_DOG_METHOD_NAME", String.class);

	private static final Dog KARO = new Dog("Karo", "Rottweiler", 3, 9);
	private static final Dog LASSIE = new Dog("Lassie", "Dachshund", 4, 16);
	private static final Dog FIDO = new Dog("Fido", "Golden retriever", 5, 13);
	private static final Dog RATATA = new Dog("Ratata", "Labrador", 12, 18);
	private static final Dog DORIS = new Dog("Doris", "Dachshund", 7, 1);

	private AssignmentSevenPointThree sut;

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentSevenPointThreeTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@BeforeEach
	public void preventProblemsWithAnyScannerAdapterThatMightExist() {
		/*
		 * Metoden som ska tas fram i denna uppgift läser inget från användaren. Att vi
		 * ändå sätter om System.in inför varje test beror på att det annars blir
		 * problem om du har kod från de tidigare uppgifterna i
		 * AssignmentSevenPointThree. Det är också därför vi skapar en instans av
		 * klassen här.
		 */
		setIn("");
		sut = new AssignmentSevenPointThree();
	}

	@Test
	@DisplayName(value = "Finns det några uppenbara problem med uppgiftsklassens struktur?")
	public void basicStructureChecks() {
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, FIND_DOG_METHOD);
	}

	private List<Dog> dogs(AssignmentSevenPointThree sut) {
		return getList(MAIN_DOG_LIST, sut);
	}

	private Dog callMethodUnderTest(String name) {
		if (FIND_DOG_METHOD.getReturnType() == Dog.class) {
			return (Dog) FIND_DOG_METHOD.invoke(sut, name);
		}
		if (Collection.class.isAssignableFrom(FIND_DOG_METHOD.getReturnType())) {
			@SuppressWarnings("unchecked")
			Collection<Dog> cd = (Collection<Dog>) FIND_DOG_METHOD.invoke(sut, name);
			return cd.stream().findFirst().orElse(null);
		}
		if (Dog[].class.isAssignableFrom(FIND_DOG_METHOD.getReturnType())) {
			Dog[] da = (Dog[]) FIND_DOG_METHOD.invoke(sut, name);
			return da == null || da.length == 0 ? null : da[0];
		}
		if (FIND_DOG_METHOD.getReturnType() == Optional.class) {
			@SuppressWarnings("unchecked")
			Optional<Dog> od = (Optional<Dog>) FIND_DOG_METHOD.invoke(sut, name);
			return od.orElse(null);
		}
		fail("Returtypen på metoden är inte någon av de förväntade (en hund, en samling av hundar, en array, eller en Optional<Dog>)");
		// Kan inte hända på grund av fail ovan, men krävs av kompilatorn
		return null;
	}

	private void searchForDogThatExists(String name) {
		Dog dog = callMethodUnderTest(name);
		assertNotNull(dog,
				"Fel resultat vid sökning efter %s, en hund som existerar. Ingen hund hittade.".formatted(name));
		assertEqualsIgnoreCase(name, dog.getName(),
				"Fel resultat vid sökning efter %s, en hund som existerar. Hunden som hittades heter %s."
						.formatted(name, dog.getName()));
	}

	private void searchForDogThatDoesNotExists(String name) {
		Dog dog = callMethodUnderTest(name);
		assertNull(dog, "Fel resultat vid sökning efter %s, en hund som inte existerar. Hunden som hittades heter %s."
				.formatted(name, dog == null ? "" : dog.getName()));
	}

	@ParameterizedTest(name = "{index} {0} som står {1}")
	@CsvSource(value = { "Karo, först i listan", "Fido, mitt i listan", "Doris, sist i listan" })
	@DisplayName(value = "Hundar som finns hittas, oavsett postion")
	void searchingForDogThatExistsGivesDog(String name, String description) {
		dogs(sut).addAll(Arrays.asList(new Dog[] { KARO, LASSIE, FIDO, RATATA, DORIS }));

		searchForDogThatExists(name);
	}

	@ParameterizedTest(name = "{index} {0}")
	@ValueSource(strings = { "Karo", "KARO", "karo", "kArO", "Fido", "FIDO", "fido", "FiDo", "Doris", "DORIS", "doris",
			"doRis" })
	@DisplayName(value = "Hundar som finns hittas, oavsett hur namnet är formaterat")
	void searchingForDogThatExistsGivesDogRegardlessOfNameFormat(String name) {
		dogs(sut).addAll(Arrays.asList(new Dog[] { KARO, LASSIE, FIDO, RATATA, DORIS }));

		searchForDogThatExists(name);
	}

	@ParameterizedTest(name = "{index} {0}, {1}")
	@CsvSource(value = { "Devil, vars namn inte liknar något av hundarna i listan",
			"Rat, vars namn matchar början av en av hundarna i listan",
			"Karolina, vars namn börjar med Karo, som i sin tur är en av hundarna i listan" })
	@DisplayName(value = "Hundar som inte finns hittas inte")
	void searchingForDogThatDoesNotExistsYealdsNothing(String name, String description) {
		dogs(sut).addAll(Arrays.asList(new Dog[] { KARO, LASSIE, FIDO, RATATA, DORIS }));

		searchForDogThatDoesNotExists(name);
	}

	@ParameterizedTest(name = "{index} {0}")
	@ValueSource(strings = { "NO SUCH DOG", "Karo" })
	@DisplayName(value = "Det finns inga hundar i en tom lista")
	void searchingInEmptyListYealdsNothing(String name) {
		searchForDogThatDoesNotExists(name);
	}

	// TODO: fler test

}

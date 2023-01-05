/*
 * Denna fil innehåller JUnit-testfall för funktionen för att hitta en ägare U8.2.
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

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

@DisplayName(value = "JUnit-testfall för U8.2 - metoden för att hitta en given ägare")
public class AssignmentEightPointTwoTest extends ApiBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointTwo.class,
			"AssignmentEightPointTwoTest");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME,
			"TestData.MAIN_OWNER_LIST_NAME");
	private static final MethodUnderTest FIND_OWNER_METHOD = CUT.getMethod(TestData.FIND_OWNER_METHOD_NAME,
			"TestData.FIND_OWNER_METHOD_NAME", String.class);

	private static final Owner JENNY = new Owner("Jenny");
	private static final Owner JOHANNA = new Owner("Johanna");
	private static final Owner ULLA = new Owner("Ulla");
	private static final Owner IRIS = new Owner("Iris");
	private static final Owner IVAR = new Owner("Ivar");

	private AssignmentEightPointTwo sut ;

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointTwoTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeEach
	public void preventProblemsWithAnyScannerAdapterThatMightExist() {
		/*
		 * Metoden som ska tas fram i denna uppgift läser inget från användaren. Att vi
		 * ändå sätter om System.in inför varje test beror på att det annars blir
		 * problem om du har kod från de tidigare uppgifterna i
		 * AssignmentEightPointTwo. Det är också därför vi skapar en instans av
		 * klassen här.
		 */
		setIn("");
		sut = new  AssignmentEightPointTwo();
	}
	
	private List<Owner> owners() {
		return getList(MAIN_OWNER_LIST, sut);
	}

	private Owner callMethodUnderTest(String name) {
		if (FIND_OWNER_METHOD.getReturnType() == Owner.class) {
			return (Owner) FIND_OWNER_METHOD.invoke(sut, name);
		}
		if (Collection.class.isAssignableFrom(FIND_OWNER_METHOD.getReturnType())) {
			@SuppressWarnings("unchecked")
			Collection<Owner> co = (Collection<Owner>) FIND_OWNER_METHOD.invoke(sut, name);
			return co.stream().findFirst().orElse(null);
		}
		if (Owner[].class.isAssignableFrom(FIND_OWNER_METHOD.getReturnType())) {
			Owner[] oa = (Owner[]) FIND_OWNER_METHOD.invoke(sut, name);
			return oa == null || oa.length == 0 ? null : oa[0];
		}
		if (FIND_OWNER_METHOD.getReturnType() == Optional.class) {
			@SuppressWarnings("unchecked")
			Optional<Owner> oo = (Optional<Owner>) FIND_OWNER_METHOD.invoke(sut, name);
			return oo.orElse(null);
		}
		fail("Returtypen på metoden är inte någon av de förväntade (en ägare, en samling av ägare, en array, eller en Optional<Owner>)");
		// Kan inte hända på grund av fail ovan, men krävs av kompilatorn
		return null;
	}

	@Test
	@DisplayName(value = "Finns det några uppenbara problem med uppgiftsklassens struktur?")
	public void basicStructureChecks() {
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, FIND_OWNER_METHOD);
	}

	private void searchForOwnerThatExists(String name) {
		Owner owner = callMethodUnderTest(name);
		assertNotNull(owner,
				"Fel resultat vid sökning efter %s, en ägare som existerar. Ingen ägare hittade.".formatted(name));
		assertEqualsIgnoreCase(name, owner.getName(),
				"Fel resultat vid sökning efter %s, en ägare som existerar. Ägarena som hittades heter %s."
						.formatted(name, owner.getName()));
	}

	private void searchForOwnerThatDoesNotExists(String name) {
		Owner owner = callMethodUnderTest(name);
		assertNull(owner,
				"Fel resultat vid sökning efter %s, en ägare som inte existerar. Ägaren som hittades heter %s."
						.formatted(name, owner == null ? "" : owner.getName()));
	}

	@ParameterizedTest(name = "{index} {0} som står {1}")
	@CsvSource(value = { "Jenny, först i listan", "Ulla, mitt i listan", "Ivar, sist i listan" })
	@DisplayName(value = "Ägare som finns hittas, oavsett postion")
	void searchingForOwnerThatExistsGivesOwner(String name, String description) {
		owners().addAll(Arrays.asList(new Owner[] { JENNY, JOHANNA, ULLA, IRIS, IVAR }));

		searchForOwnerThatExists(name);
	}

	@ParameterizedTest(name = "{index} {0}")
	@ValueSource(strings = { "JENNY", "jenny", "JeNnY", "ULLA", "ulla", "uLlA", "IVAR", "ivar", "IvAr" })
	@DisplayName(value = "Ägare som finns hittas, oavsett hur namnet är formaterat")
	void searchingForOwnerThatExistsGivesDogRegardlessOfNameFormat(String name) {
		owners().addAll(Arrays.asList(new Owner[] { JENNY, JOHANNA, ULLA, IRIS, IVAR }));

		searchForOwnerThatExists(name);
	}

	@ParameterizedTest(name = "{index} {0}, {1}")
	@CsvSource(value = { "Karolina, vars namn inte liknar någon av ägarna i listan",
			"Jen, vars namn matchar början av en av ägarna i listan",
			"Ulla-Bella, vars namn börjar med Ulla, som i sin tur är en av ägarna i listan" })
	@DisplayName(value = "Hundar som inte finns hittas inte")
	void searchingForDogThatDoesNotExistsYealdsNothing(String name, String description) {
		owners().addAll(Arrays.asList(new Owner[] { JENNY, JOHANNA, ULLA, IRIS, IVAR }));

		searchForOwnerThatDoesNotExists(name);
	}

	@ParameterizedTest(name = "{index} {0}")
	@ValueSource(strings = { "NO SUCH OWNER", "Iris" })
	@DisplayName(value = "Det finns inga hundar att hitta i en tom lista")
	void searchingInEmptyListYealdsNothing(String name) {
		searchForOwnerThatDoesNotExists(name);
	}


}

/*
 * Denna fil innehåller JUnit-testfall för metoden för att lägga till en ägare U8.1 .
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
import java.util.List;

import org.junit.jupiter.api.*;

@DisplayName(value = "JUnit-testfall för U8.1 - metoden för att läsa in data om en ägare")
public class AssignmentEightPointOneTest extends IOBaseTest {
	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentEightPointOne.class,
			"AssignmentEightPointOneTest");
	private static final FieldUnderTest MAIN_OWNER_LIST = CUT.getField(TestData.MAIN_OWNER_LIST_NAME,
			"TestData.MAIN_OWNER_LIST_NAME");
	private static final MethodUnderTest MUT = CUT.getMethod(TestData.REGISTER_NEW_OWNER_METHOD_NAME,
			"TestData.REGISTER_NEW_OWNER_METHOD_NAME");

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointOneTest() {
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

	private List<Owner> owners(AssignmentEightPointOne sut) {
		return getList(MAIN_OWNER_LIST, sut);
	}

	private List<Owner> invokeMUT(String input, int times) {
		// Måste sättas innan sut, annars läser inläsningsklassen från fel ström
		setIn(input + "\n");
		AssignmentEightPointOne sut = new AssignmentEightPointOne();
		for (int n = 0; n < times; n++) {
			MUT.invoke(sut);
		}
		return owners(sut);
	}

	@Test
	@DisplayName(value = "Försöker lägga till en ägare")
	public void addingOneOwner() {
		List<Owner> owners = invokeMUT("Jozef", 1);

		assertEquals(1, owners.size(), "Listan av ägare innehåller fel antal");
		assertEqualsIgnoreCase("Jozef", owners.get(0).getName(), "Fel namn på ägaren i listan");

		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Försöker lägga till två ägare")
	public void addingTwoOwners() {
		List<Owner> owners = invokeMUT("Olle\nHenrik", 2);

		assertEquals(2, owners.size(), "Listan av ägare innehåller fel antal");

		// Det finns inget som kräver att ägarna ligger i någon speciell
		// ordning, så vi får mickla lite för att kolla var de finns
		final int[] olleFirst = { 0, 1 };
		final int[] henrikFirst = { 1, 0 };

		int[] index = owners.get(0).getName().equalsIgnoreCase("Olle") ? olleFirst : henrikFirst;

		assertEqualsIgnoreCase("Olle", owners.get(index[0]).getName(), "Fel namn på ägaren");
		assertEqualsIgnoreCase("Henrik", owners.get(index[1]).getName(), "Fel namn på ägaren");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName("Blanktecken i namn bevaras")
	public void spacesAreKeptWithinNames() {
		List<Owner> owners = invokeMUT("Adam Bertil", 1);

		assertEquals(1, owners.size(), "Listan av ägare innehåller fel antal");
		assertEqualsIgnoreCase("Adam Bertil", owners.get(0).getName(), "Fel namn på ägaren");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName("Blanktecken tas bort runt namn")
	public void extraWhitespaceRemovedAroundName() {
		List<Owner> owners = invokeMUT(" \t Patrick\t ", 1);

		assertEquals(1, owners.size(), "Listan av ägare innehåller fel antal");
		assertEqualsIgnoreCase("Patrick", owners.get(0).getName(), "Fel namn på ägaren");
		out().assertDoesNotContainErrorMessage();
	}

	@Test
	@DisplayName(value = "Tomma namn frågas efter igen")
	public void emptyNameAskedForAgain() {
		List<Owner> owners = invokeMUT("\nJenny", 1);

		assertEquals(1, owners.size(), "Listan av ägare innehåller fel antal");
		assertEqualsIgnoreCase("Jenny", owners.get(0).getName(), "Fel namn på ägaren");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Namn som bara består av blanka tecken frågas efter igen")
	public void whitespaceOnlyNameAskedForAgain() {
		List<Owner> owners = invokeMUT(" \t \nAnita", 1);

		assertEquals(1, owners.size(), "Listan av ägare innehåller fel antal");
		assertEqualsIgnoreCase("Anita", owners.get(0).getName(), "Fel namn på ägaren");
		out().assertContainsErrorMessage();
	}

	@Test
	@DisplayName(value = "Flera misslyckade försök att skriva in namn hanteras")
	public void multipleEmptyAndWhitespaceOnlyNamesHandled() {
		List<Owner> owners = invokeMUT("\n \n\n\t\nMarie", 1);

		assertEquals(1, owners.size(), "Listan av ägare innehåller fel antal");
		assertEqualsIgnoreCase("Marie", owners.get(0).getName());
		out().assertContainsErrorMessage();
	}


}

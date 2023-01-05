/*
 * Denna fil innehåller JUnit-testfall för ägarklassen i U8.1.
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
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.*;


@DisplayName(value = "JUnit-testfall för U8.1 - ägarklassen")
public class AssignmentEightPointOneOwnerTest extends ApiBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(Owner.class, "AssignmentEightPointOneOwnerTest");

	/**
	 * För U8.1 är detta antal troligtvis inte högre än 2, men senare uppgifter
	 * kommer att lägga till ett par-tre till. Detta plus lite marginal ger 6.
	 */
	private static final int MAXIMUM_EXPECTED_NUMBER_OF_PUBLIC_METHODS_IN_OWNER = 6;

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointOneOwnerTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Finns det några statiska variabler i ägarklassen?")
	public void noStaticVariables() {
		assertEquals(0, CUT.getClassVariables().count(),
				"Ägarklassen ska inte innehålla några statiska variabler, var det möjligen en konstant du avsåg?");
	}

	@Test
	@DisplayName(value = "Finns det en konstruktor?")
	public void onlyOneConstructor() {
		assertEquals(1, CUT.getConstructors().count(), "Ägarklassen ska bara ha en konstruktor");
	}

	@Test
	@DisplayName(value = "Tar konstruktorn namnet som parameter?")
	public void constructorAcceptsName() {
		assumeTrue(CUT.getConstructors().count() == 1, "Fel antal konstruktorer, testet kan inte köras");
		assertDoesNotThrow(() -> CUT.getConstructor(String.class), "Konstruktorn accepterar inte namnet på ägaren");
	}

	@Test
	@DisplayName(value = "Finns det några statiska metoder i ägarklassen?")
	public void noStaticMethods() {
		assertEquals(0, CUT.getClassMethods().count(), "Ägarklassen ska inte innehålla några statiska metoder");
	}

	@Test
	@DisplayName(value = "Är antalet publika metoder i ägarklassen rimligt?")
	public void reasonableNumberOfPublicMethods() {
		long publicMethods = CUT.getPublicMethods().count();
		assertTrue(publicMethods <= MAXIMUM_EXPECTED_NUMBER_OF_PUBLIC_METHODS_IN_OWNER,
				"Det finns (antagligen) för många publika metoder i ägarklassen. Förväntat max %d, men hittade %d."
						.formatted(MAXIMUM_EXPECTED_NUMBER_OF_PUBLIC_METHODS_IN_OWNER, publicMethods));
	}

	@Test
	@DisplayName(value = "Finns det en metod för att läsa av namnet?")
	public void hasMethodToAccessName() throws NoSuchMethodException, SecurityException {
		MethodUnderTest mut = assertDoesNotThrow(
				() -> CUT.getMethod("getName", "AssignmentEightPointOneOwnerTest.hasMethodToAccessName()"),
				"Ingen get-metod för namnet");
		assertEquals(String.class, mut.getReturnType(), "Fel returtyp för getName");
	}

	@Test
	@DisplayName(value = "Sätter konstruktorn namnet på ägaren?")
	public void constructorSetsName() {
		Owner o = new Owner("Stefan");

		MethodUnderTest mut = assertDoesNotThrow(
				() -> CUT.getMethod("getName", "AssignmentEightPointOneOwnerTest.constructorSetsName()"),
				"Ingen get-metod för namnet");
		assertEqualsIgnoreCase("Stefan", mut.invoke(o), "Fel namn på ägaren");
	}

	@Test
	@DisplayName(value = "Innehåller det toString returnerar namnet?")
	public void toStringContainsName() {
		String result = new Owner("Olle").toString();
		assertTrue(result.toLowerCase().contains("olle"),
				String.format("toString innehåller inte namnet Olle: \"%s\"", result));
	}


}

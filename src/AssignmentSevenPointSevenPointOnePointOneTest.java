/*
 * Denna fil innehåller JUnit-testfall för den första metoden för att byta plats på hundar U7.7.1.1, den kräver tillgång till AssignmentSevenPointSevenTest. Observera att testet bara kontrollerar funktionaliteten, inte att det är implementerat på rätt sätt. Detta ansvarar du själv för..
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



import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.*;

@DisplayName(value = "JUnit-testfall för U7.7.1.1 - byte av plats med egen kod")
public class AssignmentSevenPointSevenPointOnePointOneTest extends AssignmentSevenPointSevenTest {

	private static final MethodUnderTest SWAP_DOGS_OWN_METHOD = CUT.getMethod(TestData.SWAP_DOGS_OWN_METHOD_NAME,
			"TestData.SWAP_DOGS_OWN_METHOD_NAME", int.class, int.class);

	protected AssignmentSevenPointSevenPointOnePointOneTest() {
		super(1, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Försöker byta plats på den första och sista hunden i listan")
	public void swapFirstAndLast() {
		addDogs(BAMSE, RONJA, MOLLY);
		SWAP_DOGS_OWN_METHOD.invoke(sut, 0, 2);
		assertDogsAre("Försök att byta plats på första och sista hunden misslyckades", //
				MOLLY, RONJA, BAMSE);
	}

	@Test
	@DisplayName(value = "Försöker byta plats på en hund med sig själv")
	public void swapSame() {
		addDogs(BAMSE, RONJA, MOLLY);
		SWAP_DOGS_OWN_METHOD.invoke(sut, 1, 1);
		assertDogsAre("Försök att byta plats på första och sista hunden misslyckades", //
				BAMSE, RONJA, MOLLY);
	}

}

/*
 * Denna fil innehåller JUnit-testfall för metoden för att lista alla hundar med en given svanslängd U7.2.
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



import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName(value = "JUnit-testfall för U7.2 - metoden för att lista alla hundar med en tillräckligt lång svans")
public class AssignmentSevenPointTwoTest extends IOBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(AssignmentSevenPointTwo.class,
			"AssignmentSevenPointTwoTest");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");
	private static final MethodUnderTest LIST_DOGS_METHOD = CUT.getMethod(TestData.LIST_DOGS_METHOD_NAME,
			"TestData.LIST_DOGS_METHOD_NAME");

	private static final Dog SMALL = new Dog("SmallDog", "Terrier", 3, 7);
	private static final Dog MEDIUM = new Dog("MediumDog", "Cross-breed", 5, 11);
	private static final Dog BIG = new Dog("BigDog", "Great dane", 10, 12);

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * IOBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentSevenPointTwoTest() {
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
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT, LIST_DOGS_METHOD);
	}

	private List<Dog> dogs(AssignmentSevenPointTwo sut) {
		return getList(MAIN_DOG_LIST, sut);
	}

	private void testSearchMethod(Dog[] dogsInList, double minTailLength, Dog... expected) {
		if (dogsInList.length == 0)
			setIn("");
		else
			setIn("%f%n", minTailLength);

		AssignmentSevenPointTwo sut = new AssignmentSevenPointTwo();

		List<Dog> dogs = dogs(sut);
		dogs.addAll(Arrays.asList(dogsInList));

		LIST_DOGS_METHOD.invoke(sut);

		for (Dog dog : expected) {
			out().assertContainsIgnoreCase(dog.getName());
		}

		dogs.removeAll(Arrays.asList(expected));

		for (Dog dog : dogs) {
			out().assertDoesNotContainsIgnoreCase(dog.getName());
		}

		boolean noDogsExpected = expected.length == 0;
		boolean fel = out().get().toUpperCase().contains("FEL");
		boolean error = out().get().toUpperCase().contains("ERROR");

		if (noDogsExpected) {
			assertTrue(fel || error, "Metoden skrev inte ut ett felmeddelande när inga hundar matchade");
		} else {
			assertFalse(fel || error, "Metoden skrev ut ett felmeddelanden när det fanns matchande hundar");
		}
	}

	@Test
	@DisplayName(value = "Inga hundar i listan ska ge felmeddelande")
	void noDogs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testSearchMethod(new Dog[] {}, 0.0);
	}

	@Test
	@DisplayName(value = "Inga hundar har en tillräckligt lång svans ska ge felmeddelande")
	void noDogsMatches() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testSearchMethod(new Dog[] { SMALL, MEDIUM, BIG }, 100.0);
	}

	@Test
	@DisplayName(value = "En hund har en tillräckligt lång svans")
	void oneDogsMatch() {
		testSearchMethod(new Dog[] { MEDIUM, BIG, SMALL }, 7.0, BIG);
	}

	@Test
	@DisplayName(value = "Vissa hundar har en tillräckligt lång svans")
	void someDogsMatch() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testSearchMethod(new Dog[] { MEDIUM, SMALL, BIG }, 5.0, MEDIUM, BIG);
	}

	@Test
	@DisplayName(value = "Alla hundar har en tillräckligt lång svans")
	void allDogsMatch() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		testSearchMethod(new Dog[] { BIG, MEDIUM, SMALL }, 0.0, SMALL, MEDIUM, BIG);
	}

	//TODO: fler test ska läggas till

}

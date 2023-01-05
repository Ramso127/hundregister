/*
 * Denna fil innehåller JUnit-testfall för funktionen för att avgöra om en ägare äger en hund.
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

@DisplayName(value = "JUnit-testfall för U8.5 - fråga om en ägare äger en viss hund")
public class AssignmentEightPointFiveTest extends ApiBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(Owner.class, "AssignmentEightPointFiveTest");
	private static final MethodUnderTest ADD_DOG_TO_OWNER_METHOD = CUT.getMethod(TestData.ADD_DOG_TO_OWNER_METHOD_NAME,
			"TestData.ADD_DOG_TO_OWNER_METHOD_NAME", Dog.class);
	private static final MethodUnderTest OWNS_DOG_METHOD = CUT.getMethod(TestData.OWNS_DOG_METHOD_NAME,
			"TestData.OWNS_DOG_METHOD_NAME", Dog.class);

	// Ej static för att testerna inte ska störa varandra

	private final Dog LUDDE = new Dog("Ludde", "Mops", 6, 15);
	private final Dog LASSIE = new Dog("Lassie", "Tax", 6, 6);
	private final Dog RATATA = new Dog("Ratata", "Grand danois", 17, 1);
	private final Dog KARO = new Dog("Karo", "Dobermann", 12, 9);
	private final Dog FIDO = new Dog("Fido", "Dachshund", 5, 14);

	private final Owner PATRICK = new Owner("Patrick");
	private final Owner STEFAN = new Owner("Stefan");
	private final Owner JOZEF = new Owner("Jozef");
	private final Owner SVEA = new Owner("Svea");
	private final Owner CECILIA = new Owner("Cecilia");

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointFiveTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Finns det några uppenbara problem med uppgiftsklassens struktur?")
	public void basicStructureChecks() {
		assertEquals(boolean.class, OWNS_DOG_METHOD.getReturnType(), "Fel returtyp för metoden");
	}

	private void addDogToOwner(Owner owner, Dog dog) {
		ADD_DOG_TO_OWNER_METHOD.invoke(owner, dog);
	}

	private boolean ownsDog(Owner owner, Dog dog) {
		return (boolean) OWNS_DOG_METHOD.invoke(owner, dog);
	}

	@Test
	@DisplayName(value = "En person som inte äger några hundar alls kan inte äga Ratata")
	public void noDogsOwed() {
		boolean owns = ownsDog(STEFAN, RATATA);
		assertFalse(owns, "Stefan äger Ratata, vilket han inte borde göra");
	}

	@Test
	@DisplayName(value = "Ägaren äger hunden")
	public void singleDogOwnedMatches() {
		addDogToOwner(CECILIA, FIDO);
		boolean owns = ownsDog(CECILIA, FIDO);
		assertTrue(owns, "Cecilia äger inte Fido som hon borde göra");
	}

	@Test
	@DisplayName(value = "Ägaren äger en annan hund")
	public void singleDogOwnedDoesNotMatch() {
		addDogToOwner(JOZEF, KARO);
		boolean owns = ownsDog(JOZEF, RATATA);
		assertFalse(owns, "Jozef äger Ratata, vilket han inte borde göra");
	}

	@Test
	@DisplayName(value = "Den första av ägarens hundar är den som söks")
	public void firstDogOwnedMatches() {
		addDogToOwner(PATRICK, LUDDE);
		addDogToOwner(PATRICK, LASSIE);
		addDogToOwner(PATRICK, FIDO);
		boolean owns = ownsDog(PATRICK, LUDDE);
		assertTrue(owns, "Patrick äger inte Ludde som han borde göra");
	}

	@Test
	@DisplayName(value = "Den mittersta av ägarens hundar är den som söks")
	public void middleDogOwnedMatches() {
		addDogToOwner(SVEA, LUDDE);
		addDogToOwner(SVEA, LASSIE);
		addDogToOwner(SVEA, FIDO);
		boolean owns = ownsDog(SVEA, LASSIE);
		assertTrue(owns, "Svea äger inte Lassie som hon borde göra");
	}

	@Test
	@DisplayName(value = "Den sista av ägarens hundar är den som söks")
	public void lastDogOwnedMatches() {
		addDogToOwner(PATRICK, LUDDE);
		addDogToOwner(PATRICK, LASSIE);
		addDogToOwner(PATRICK, FIDO);
		boolean owns = ownsDog(PATRICK, FIDO);
		assertTrue(owns, "Patrick äger inte Fido som han borde göra");
	}

	@Test
	@DisplayName(value = "Ingen av ägarens hundar är den som söks")
	public void noDogOwedMatches() {
		addDogToOwner(PATRICK, LUDDE);
		addDogToOwner(PATRICK, LASSIE);
		addDogToOwner(PATRICK, FIDO);
		boolean owns = ownsDog(PATRICK, KARO);
		assertFalse(owns, "Patrick äger Karo, vilket han inte borde göra");
	}

}

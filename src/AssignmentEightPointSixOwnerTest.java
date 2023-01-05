/*
 * Denna fil innehåller JUnit-testfall för ägarklassens array av hundar.
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

@DisplayName(value = "JUnit-testfall för U8.6 - metoden i klassen Owner för att ta bort en hund")
public class AssignmentEightPointSixOwnerTest extends ApiBaseTest {

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointSixOwnerTest");
	private static final FieldUnderTest DOGS_OWNER = DOG_CLASS.getOnlyFieldAssignableFromType(Owner.class);

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class,
			"AssignmentEightPointSixOwnerTest");
	private static final MethodUnderTest ADD_DOG_TO_OWNER_METHOD = OWNER_CLASS
			.getMethod(TestData.ADD_DOG_TO_OWNER_METHOD_NAME, "TestData.ADD_DOG_TO_OWNER_METHOD_NAME", Dog.class);
	private static final MethodUnderTest REMOVE_DOG_FROM_OWNER_METHOD = OWNER_CLASS.getMethod(
			TestData.REMOVE_DOG_FROM_OWNER_METHOD_NAME, "TestData.REMOVE_DOG_FROM_OWNER_METHOD_NAME", Dog.class);
	private static final MethodUnderTest OWNS_DOG_METHOD = OWNER_CLASS.getMethod(TestData.OWNS_DOG_METHOD_NAME,
			"TestData.OWNS_DOG_METHOD_NAME", Dog.class);

	// Ej static för att testerna inte ska störa varandra
	private final Dog BELLA = new Dog("Bella", "Yorkshireterrier", 20, 3);
	private final Dog SIGGE = new Dog("Sigge", "Dachshund", 20, 18);
	private final Dog MOLLY = new Dog("Molly", "Bulldogg", 6, 10);

	private final Owner NIKE = new Owner("Nike");
	private final Owner JOZEF = new Owner("Jozef");

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointSixOwnerTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	private void assertOwnsDog(Owner o, Dog d) {
		assertTrue((boolean) OWNS_DOG_METHOD.invoke(o, d),
				"%s äger inte %s som förväntat".formatted(o.getName(), d.getName()));

	}

	private void assertDoesNotOwnDog(Owner o, Dog d) {
		assertFalse((boolean) OWNS_DOG_METHOD.invoke(o, d),
				"%s äger %s vilket hen inte förväntades göra".formatted(o.getName(), d.getName()));
	}

	private void assertIsOwnedBy(Dog d, Owner o) {
		assertEquals(o, DOGS_OWNER.getValue(d), "Fel ägare för " + d.getName());
	}

	@Test
	@DisplayName(value = "Går det att ta bort den först tillagda hunden?")
	public void removingFirstDog() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, BELLA);
	}

	@Test
	@DisplayName(value = "Går det att ta bort den mittersta hunden?")
	public void removingMiddleDog() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);
		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, SIGGE);
		assertDoesNotOwnDog(NIKE, SIGGE);
	}

	@Test
	@DisplayName(value = "Går det att ta bort den sist tillagda hunden?")
	public void removingLastDog() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);
		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, MOLLY);
		assertDoesNotOwnDog(NIKE, MOLLY);
	}

	@Test
	@DisplayName(value = "Inga andra hundar ska tas bort")
	public void removingADogDoesNotRemovaAnyOther() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);
		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, SIGGE);
		assertOwnsDog(NIKE, BELLA);
		assertOwnsDog(NIKE, MOLLY);
	}

	@Test
	@DisplayName(value = "När en hund tas bort från sin ägare ska hundens ägare också tas bort")
	public void removingDogFromOwnerAlsoRemovesItsOwner() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);
		assertIsOwnedBy(BELLA, NIKE);
		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, BELLA);
		assertIsOwnedBy(BELLA, null);
	}

	@Test
	@DisplayName(value = "Vid försök att ta bort en hund som inte ägs av någon ska inget hända med ägaren")
	public void removingADogWithoutOwnerDoesNothing() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		assertOwnsDog(NIKE, BELLA);
		assertOwnsDog(NIKE, SIGGE);
		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, MOLLY);
		assertDoesNotOwnDog(NIKE, MOLLY);
	}

	@Test
	@DisplayName(value = "Vid försök att ta bort en hund som ägs av någon annan ska inget hända med ägaren")
	public void removingAnotherOwnersDogDoesNothing() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(JOZEF, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);

		assertOwnsDog(NIKE, BELLA);
		assertOwnsDog(JOZEF, SIGGE);
		assertOwnsDog(NIKE, MOLLY);
		assertDoesNotOwnDog(JOZEF, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertDoesNotOwnDog(JOZEF, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, SIGGE);

		assertOwnsDog(NIKE, BELLA);
		assertOwnsDog(JOZEF, SIGGE);
		assertOwnsDog(NIKE, MOLLY);
		assertDoesNotOwnDog(JOZEF, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertDoesNotOwnDog(JOZEF, MOLLY);
	}

	@Test
	@DisplayName(value = "Vid försök att ta bort en hund som ägs av någon annan ska inget hända med hunden")
	public void removingAnotherOwnersDogDoesNothingToTheDog() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(JOZEF, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);

		assertIsOwnedBy(BELLA, NIKE);
		assertIsOwnedBy(SIGGE, JOZEF);
		assertIsOwnedBy(MOLLY, NIKE);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, SIGGE);

		assertIsOwnedBy(BELLA, NIKE);
		assertIsOwnedBy(SIGGE, JOZEF);
		assertIsOwnedBy(MOLLY, NIKE);
	}

	@Test
	@DisplayName(value = "Ta bort alla hundar i samma sekvens som de lades in")
	public void removingMultipleDogsInSameSequenceAsInsertion() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);

		assertOwnsDog(NIKE, BELLA);
		assertOwnsDog(NIKE, SIGGE);
		assertOwnsDog(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, BELLA);

		assertDoesNotOwnDog(NIKE, BELLA);
		assertOwnsDog(NIKE, SIGGE);
		assertOwnsDog(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, SIGGE);

		assertDoesNotOwnDog(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertOwnsDog(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, MOLLY);

		assertDoesNotOwnDog(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertDoesNotOwnDog(NIKE, MOLLY);

		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		assertOwnsDog(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertDoesNotOwnDog(NIKE, MOLLY);

		assertIsOwnedBy(BELLA, NIKE);
		assertIsOwnedBy(SIGGE, null);
		assertIsOwnedBy(MOLLY, null);
	}

	@Test
	@DisplayName(value = "Ta bort alla hundar i en annan sekvens än den de lades in i")
	public void removingMultipleDogsInDifferentSequenceThanInsertion() {
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, SIGGE);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, MOLLY);
		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);

		assertOwnsDog(NIKE, BELLA);
		assertOwnsDog(NIKE, SIGGE);
		assertOwnsDog(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, BELLA);

		assertDoesNotOwnDog(NIKE, BELLA);
		assertOwnsDog(NIKE, SIGGE);
		assertOwnsDog(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, SIGGE);

		assertDoesNotOwnDog(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertOwnsDog(NIKE, MOLLY);

		REMOVE_DOG_FROM_OWNER_METHOD.invoke(NIKE, MOLLY);

		assertDoesNotOwnDog(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertDoesNotOwnDog(NIKE, MOLLY);

		ADD_DOG_TO_OWNER_METHOD.invoke(NIKE, BELLA);
		assertOwnsDog(NIKE, BELLA);
		assertDoesNotOwnDog(NIKE, SIGGE);
		assertDoesNotOwnDog(NIKE, MOLLY);

		assertIsOwnedBy(BELLA, NIKE);
		assertIsOwnedBy(SIGGE, null);
		assertIsOwnedBy(MOLLY, null);
	}

}

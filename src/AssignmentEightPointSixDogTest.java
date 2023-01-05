/*
 * Denna fil innehåller JUnit-testfall för ägarklassens array av hundar, och att inget annat än arrayer används för att hantera hundarna.
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

@DisplayName(value = "JUnit-testfall för U8.6 - metoden i klassen Dog för att ta bort en hunds ägare")
public class AssignmentEightPointSixDogTest extends ApiBaseTest {

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointSixDogTest");
	private static final MethodUnderTest SET_OWNER_OF_DOG_METHOD = DOG_CLASS
			.getMethod(TestData.SET_OWNER_OF_DOG_METHOD_NAME, "TestData.SET_OWNER_OF_DOG_METHOD_NAME", Owner.class);
	private static final MethodUnderTest REMOVE_OWNER_OF_DOG_METHOD = TestData.SET_OWNER_OF_DOG_METHOD_NAME
			.equals(TestData.REMOVE_OWNER_OF_DOG_METHOD_NAME) ? null
					: DOG_CLASS.getMethod(TestData.REMOVE_OWNER_OF_DOG_METHOD_NAME,
							"TestData.REMOVE_OWNER_OF_DOG_METHOD_NAME");
	private static final FieldUnderTest DOGS_OWNER = DOG_CLASS.getOnlyFieldAssignableFromType(Owner.class);

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class, "AssignmentEightPointSixDogTest");
	private static final MethodUnderTest OWNS_DOG_METHOD = OWNER_CLASS.getMethod(TestData.OWNS_DOG_METHOD_NAME,
			"TestData.OWNS_DOG_METHOD_NAME", Dog.class);

	// Ej static för att testerna inte ska störa varandra
	private final Dog RATATA = new Dog("Ratata", "Chihuahua", 19, 18);
	private final Owner HENRIK = new Owner("Henrik");

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointSixDogTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	private void removeOwner(Dog dog) {
		if (REMOVE_OWNER_OF_DOG_METHOD == null) {
			SET_OWNER_OF_DOG_METHOD.invoke(dog, (Object) null);
		} else {
			REMOVE_OWNER_OF_DOG_METHOD.invoke(dog);
		}
	}

	@Test
	@DisplayName(value = "Ägaren tas bort från hunden")
	public void removingOwnerDoesRemoveOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(RATATA, HENRIK);
		assertEquals(HENRIK, DOGS_OWNER.getValue(RATATA), "Henrik äger inte Ratata som förväntat");
		removeOwner(RATATA);
		assertEquals(null, DOGS_OWNER.getValue(RATATA), "Ägaren har inte plockats bort från Ratata som förväntat");
	}

	@Test
	@DisplayName(value = "Hunden tas bort från ägaren")
	public void removingOwnerOfOwnedDogAlsoRemovesDogFromOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(RATATA, HENRIK);
		assertTrue((boolean) OWNS_DOG_METHOD.invoke(HENRIK, RATATA), "Henrik äger inte Ratata som förväntat");
		removeOwner(RATATA);
		assertFalse((boolean) OWNS_DOG_METHOD.invoke(HENRIK, RATATA),
				"Henrik äger fortarande Ratata när denna borde vara borttagen");
	}

	@Test
	@DisplayName(value = "Inget händer när man försöker ta bort ägaren från en hund utan ägare")
	public void removingOwnerOfNotOwnedDogDoesNothing() {
		assertEquals(null, DOGS_OWNER.getValue(RATATA), "Ratatas ägare är inte null som förväntat");
		removeOwner(RATATA);
		assertEquals(null, DOGS_OWNER.getValue(RATATA), "Ratatas ägare är inte null som förväntat");
	}

}

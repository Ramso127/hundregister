/*
 * Denna fil innehåller JUnit-testfall för hundarnas ägare U8.3. Detta test ersätter inte testen från AssignmentSixPointTwoDogTest, utan kompletterar dem med test på den nya funktionaliteten .
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

@DisplayName(value = "JUnit-testfall för U8.3 - metoden för att sätta ägaren till en hund i hundklassen")
public class AssignmentEightPointThreeDogTest extends ApiBaseTest {

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointThreeDogTest");
	private static final MethodUnderTest SET_OWNER_OF_DOG_METHOD = DOG_CLASS
			.getMethod(TestData.SET_OWNER_OF_DOG_METHOD_NAME, "TestData.SET_OWNER_OF_DOG_METHOD_NAME", Owner.class);
	private static final FieldUnderTest DOGS_OWNER = DOG_CLASS.getOnlyFieldAssignableFromType(Owner.class);

	private static final ClassUnderTest DOG_LIST_CLASS = new ClassUnderTest(TestData.DOG_LIST_CLASS_NAME,
			"TestData.DOG_LIST_CLASS_NAME");
	private static final MethodUnderTest DOG_LIST_CLASS_DOG_EXISTS_METHOD = DOG_LIST_CLASS.getMethod(
			TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME, "TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME",
			Dog.class);

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class,
			"AssignmentEightPointThreeDogTest");
	private static final FieldUnderTest OWNED_DOGS = OWNER_CLASS.getOnlyFieldAssignableFromType(DOG_LIST_CLASS);

	private Dog defaultDog = new Dog("Karo", "Basset", 1, 2);
	private Owner defaultOwner = new Owner("Columbine");

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public AssignmentEightPointThreeDogTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Innehåller hundklassen några statiska metoder?")
	public void noStaticMethods() {
		assertEquals(0, DOG_CLASS.getClassMethods().count(), "Hundklassen ska inte innehålla några statiska metoder.");
	}

	@Test
	@DisplayName(value = "Innehåller hundklassen några statiska variabler?")
	public void noStaticVariables() {
		assertEquals(0, DOG_CLASS.getClassVariables().count(),
				"Hundklassen ska inte innehålla några statiska variabler. En gissning är att dessa är tänkta att vara konstanter istället, alltså static och final.");
	}

	private void assertOwnedBy(Dog dog, Owner expectedOwner) {
		assertEquals(expectedOwner, DOGS_OWNER.getValue(dog), "Fel ägare för %s".formatted(dog.getName()));
	}

	private void assertNotOwnedBy(Dog dog, Owner unexpectedOwner) {
		assertNotEquals(unexpectedOwner, DOGS_OWNER.getValue(dog), "Fel ägare för %s".formatted(dog.getName()));
	}

	private void assertOwnsDog(Owner owner, Dog expectedDog) {
		var list = OWNED_DOGS.getValue(owner);
		boolean ownsDog = (boolean) DOG_LIST_CLASS_DOG_EXISTS_METHOD.invoke(list, expectedDog);
		assertTrue(ownsDog,
				"Hunden %s borde ägas av %s, men %s-metoden säger att den inte finns i ägarens lista av hundar"
						.formatted(expectedDog.getName(), owner.getName(), DOG_LIST_CLASS_DOG_EXISTS_METHOD));
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren för en hund ska sätta hundens ägare")
	public void settingOwnerOfDogDoesSetOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);
		assertOwnedBy(defaultDog, defaultOwner);
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren för en hund ska lägg till hunden i ägarens lista av hundar?")
	public void settingOwnerOfDogAlsoAddsDogToOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);
		assertOwnsDog(defaultOwner, defaultDog);
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren för en ägd hund ändrar inte på ägaren")
	public void settingOwnerOfAlreadyOwnedDogDoesNotChangeOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);

		Owner anotherOwner = new Owner("Charles");
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, anotherOwner);

		assertOwnedBy(defaultDog, defaultOwner);
		assertNotOwnedBy(defaultDog, anotherOwner);
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren för en ägd hund lägger inte till hunden i den andra ägarens lista")
	public void settingOwnerOfAlreadyOwnedDogDoesNotAddDogToSecondOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);

		Owner anotherOwner = new Owner("Charles");
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, anotherOwner);

		assertNotOwnedBy(defaultDog, anotherOwner);
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren för en ägd hund tar inte bort hunden från den första ägarens lista")
	public void settingOwnerOfAlreadyOwnedDogDoesNotAddRemoveDogFromCurrentOwner() {
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);

		Owner anotherOwner = new Owner("Charles");
		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, anotherOwner);

		assertOwnedBy(defaultDog, defaultOwner);
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren för en hund till samma ägare en gång till ska inte ändra på något")
	public void settingOwnerOfAlreadyOwnedDogToSameOwnerDoesNothing() {
		FieldUnderTest dogArrayField = DOG_LIST_CLASS.getOnlyFieldAssignableFromType(Dog[].class);
		var list = OWNED_DOGS.getValue(defaultOwner);

		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);
		Dog[] expectedDogs = ((Dog[]) dogArrayField.getValue(list)).clone();

		SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, defaultOwner);
		Dog[] dogs = (Dog[]) dogArrayField.getValue(list);

		assertOwnedBy(defaultDog, defaultOwner);
		assertOwnsDog(defaultOwner, defaultDog);

		assertArrayEquals(expectedDogs, dogs, "Ägarens lista av hundar har uppdaterats när så inte borde ha skett");
	}

	@Test
	@DisplayName(value = "Försök att sätta ägaren till null ska inte kasta undantag (krascha)")
	public void setOwnerToNullDoesNotThrowException() {
		assertDoesNotThrow(() -> SET_OWNER_OF_DOG_METHOD.invoke(defaultDog, (Owner) null));
	}

}

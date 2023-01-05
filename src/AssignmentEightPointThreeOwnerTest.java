// JUNIT: ägarklassens array av hundar U8.3. Detta test ersätter inte testen från AssignmentEightPointOneOwnerTest, utan kompletterar dem med test på den nya funktionaliteten


import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandles;

import org.junit.jupiter.api.*;

@DisplayName(value = "JUnit-testfall för U8.3 - metoden för att lägga till en hund till en ägare i ägarklassen")
public class AssignmentEightPointThreeOwnerTest extends ApiBaseTest {

	private static final ClassUnderTest DOG_CLASS = new ClassUnderTest(Dog.class, "AssignmentEightPointThreeOwnerTest");
	private static final FieldUnderTest DOGS_OWNER = DOG_CLASS.getOnlyFieldAssignableFromType(Owner.class);

	private static final ClassUnderTest DOG_LIST_CLASS = new ClassUnderTest(TestData.DOG_LIST_CLASS_NAME,
			"TestData.DOG_LIST_CLASS_NAME");
	private static final MethodUnderTest DOG_LIST_CLASS_DOG_EXISTS_METHOD = DOG_LIST_CLASS.getMethod(
			TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME, "TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME",
			Dog.class);

	private static final ClassUnderTest OWNER_CLASS = new ClassUnderTest(Owner.class,
			"AssignmentEightPointThreeOwnerTest");
	private static final MethodUnderTest ADD_DOG_TO_OWNER_METHOD = OWNER_CLASS
			.getMethod(TestData.ADD_DOG_TO_OWNER_METHOD_NAME, "TestData.ADD_DOG_TO_OWNER_METHOD_NAME", Dog.class);
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
	public AssignmentEightPointThreeOwnerTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Innehåller ägarklassen några statiska metoder?")
	public void noStaticMethods() {
		assertEquals(0, OWNER_CLASS.getClassMethods().count(),
				"Ägarklassen ska inte innehålla några statiska metoder.");
	}

	@Test
	@DisplayName(value = "Innehåller hundklassen några statiska variabler?")
	public void noStaticVariables() {
		assertEquals(0, OWNER_CLASS.getClassVariables().count(),
				"Ägarklassen ska inte innehålla några statiska variabler. En gissning är att dessa är tänkta att vara konstanter istället, alltså static och final.");
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

	private void assertDoesNotOwnDog(Owner owner, Dog expectedDog) {
		var list = OWNED_DOGS.getValue(owner);
		boolean ownsDog = (boolean) DOG_LIST_CLASS_DOG_EXISTS_METHOD.invoke(list, expectedDog);
		assertFalse(ownsDog,
				"Hunden %s borde inte ägas av %s, men %s-metoden säger att den finns i ägarens lista av hundar"
						.formatted(expectedDog.getName(), owner.getName(), DOG_LIST_CLASS_DOG_EXISTS_METHOD));
	}

	@Test
	@DisplayName(value = "Försök att lägga till en hund ska lägga in hunden i ägarens lista")
	public void addingDogToOwnerPutsDogInArray() {
		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);
		assertOwnsDog(defaultOwner, defaultDog);
	}

	@Test
	@DisplayName(value = "Försök att lägga till en hund ska sätta hundens ägare")
	public void addingDogToOwnerAlsoSetsOwnerOnDog() {
		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);
		assertOwnedBy(defaultDog, defaultOwner);
	}

	@Test
	@DisplayName(value = "Försök att lägga till en hund som reda ägs ska inte lägga in hunden i ägarens lista")
	public void addingAlreadyOwnedDogDoesNotAddDogToArray() {
		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);

		Owner anotherOwner = new Owner("Hawkeye");
		ADD_DOG_TO_OWNER_METHOD.invoke(anotherOwner, defaultDog);

		assertDoesNotOwnDog(anotherOwner, defaultDog);
	}

	@Test
	@DisplayName(value = "Försök att lägga till en hund som reda ägs ska inte ändra hundens ägare")
	public void addingAlreadyOwnedDogDoesNotChangeOwner() {
		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);

		Owner anotherOwner = new Owner("Hawkeye");
		ADD_DOG_TO_OWNER_METHOD.invoke(anotherOwner, defaultDog);

		assertNotOwnedBy(defaultDog, anotherOwner);
	}

	@Test
	@DisplayName(value = "Försök att lägga till en hund som reda ägs till samma ägare ska inte ändra på något")
	public void addingAlreadyOwnedDogToSameOwnerDoesNothing() {
		FieldUnderTest dogArrayField = DOG_LIST_CLASS.getOnlyFieldAssignableFromType(Dog[].class);
		var list = OWNED_DOGS.getValue(defaultOwner);

		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);
		Dog[] expectedDogs = ((Dog[]) dogArrayField.getValue(list)).clone();

		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);
		Dog[] dogs = (Dog[]) dogArrayField.getValue(list);

		assertOwnedBy(defaultDog, defaultOwner);
		assertOwnsDog(defaultOwner, defaultDog);

		assertArrayEquals(expectedDogs, dogs, "Ägarens lista av hundar har uppdaterats när så inte borde ha skett");
	}

	@Test
	@DisplayName(value = "Försöker lägga till flera hundar")
	public void addingMoreDogs() {
		Dog first = new Dog("First", "Breed", 1, 2);
		Dog second = new Dog("Second", "Breed", 3, 4);

		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, first);
		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, second);
		ADD_DOG_TO_OWNER_METHOD.invoke(defaultOwner, defaultDog);

		assertOwnedBy(first, defaultOwner);
		assertOwnedBy(second, defaultOwner);
		assertOwnedBy(defaultDog, defaultOwner);

		assertOwnsDog(defaultOwner, first);
		assertOwnsDog(defaultOwner, second);
		assertOwnsDog(defaultOwner, defaultDog);
	}


}

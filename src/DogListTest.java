

import static org.junit.jupiter.api.Assertions.*;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;

// TODO: gå igenom alla asserts och lägg till meddelanden
@DisplayName(value = "JUnit-testfall för U7.6 - en listklass för hundar")
public class DogListTest extends ApiBaseTest {

	private static final ClassUnderTest CUT = new ClassUnderTest(TestData.DOG_LIST_CLASS_NAME,
			"TestData.DOG_LIST_CLASS_NAME");
	private static final MethodUnderTest ADD_METHOD = CUT.getMethod(TestData.DOG_LIST_CLASS_ADD_DOG_METHOD_NAME,
			"TestData.DOG_LIST_CLASS_ADD_DOG_METHOD_NAME", Dog.class);
	private static final MethodUnderTest REMOVE_METHOD = CUT.getMethod(TestData.DOG_LIST_CLASS_REMOVE_DOG_METHOD_NAME,
			"TestData.DOG_LIST_CLASS_REMOVE_DOG_METHOD_NAME", Dog.class);
	private static final MethodUnderTest EXISTS_METHOD = CUT.getMethod(TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME,
			"TestData.DOG_LIST_CLASS_DOG_EXISTS_METHOD_NAME", Dog.class);

	/**
	 * Om denna konstruktor inte kompilerar har du en för gammal version av
	 * ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny version
	 * från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	public DogListTest() {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@Test
	@DisplayName(value = "Det finns en array av hundar i klassen")
	void usesArrayOfDogs() {
		assertDoesNotThrow(() -> CUT.getOnlyFieldOfType(Dog[].class));
	}

	private Dog[] dogs(Object sut) {
		return (Dog[]) CUT.getOnlyFieldOfType(Dog[].class).getValue(sut);
	}

	private boolean dogIsInArray(Object sut, Dog expected) {
		for (Dog dog : dogs(sut)) {
			if (dog == expected)
				return true;
		}
		return false;
	}

	/**
	 * Detta test kontrollerar att inga samlingar används på klassnivå. Det kan
	 * <b>inte</b> kontrollera om samlingar, strömmar, edyl används inuti metoder.
	 * Där ansvarar du helt själv.
	 */
	@Test
	@DisplayName(value = "Inga samlingar används på klassnivå")
	void noCollectionsAtClassLevel() {
		var collections = CUT.getInstanceFieldsAssignableTo(Collection.class).toList();
		assertEquals(0, collections.size(), "Hittade dessa samlingar i klassen: " + collections.toString());
	}

	@Test
	@DisplayName(value = "Försöker lägga till en hund")
	void canAddDog() {
		assertTrue(ADD_METHOD.exists(), "Hittar inte metoden " + ADD_METHOD);

		Object sut = CUT.getConstructor().newInstance();
		Dog dog = new Dog("Fido", "Tax", 1, 2);

		ADD_METHOD.invoke(sut, dog);

		assertTrue(dogIsInArray(sut, dog), "Hunden som precis lades in finns inte i arrayen");
	}

	@Test
	@DisplayName(value = "Försöker kontrollera att en tillagd hund finns")
	void canCheckThatDogExistsInList() {
		assertTrue(EXISTS_METHOD.exists());

		Object sut = CUT.getConstructor().newInstance();
		Dog dog = new Dog("Fido", "Tax", 1, 2);

		assertFalse((boolean) EXISTS_METHOD.invoke(sut, dog),
				"Hunden som precis skapades borde inte finnas i arrayen innan den lagts in.");
		ADD_METHOD.invoke(sut, dog);
		assertTrue((boolean) EXISTS_METHOD.invoke(sut, dog), "Hunden som precis lades in finns inte i arrayen");
	}

	@Test
	@DisplayName(value = "Försöker ta bort en tillagd hund")
	void canRemoveDog() {
		assertTrue(REMOVE_METHOD.exists());

		Object sut = CUT.getConstructor().newInstance();
		Dog dog = new Dog("Fido", "Tax", 1, 2);

		ADD_METHOD.invoke(sut, dog);
		REMOVE_METHOD.invoke(sut, dog);

		assertFalse(dogIsInArray(sut, dog), "Hunden som precis togs bort finns kvar i arrayen");
	}

	@Test
	@DisplayName(value = "Arrayen ökar i storlek när så behövs")
	void arrayIncreasesWhenNecessary() {
		Object sut = CUT.getConstructor().newInstance();
		Collection<Dog> expectedDogs = new ArrayList<>();

		for (int n = 0; n < 2; n++) {
			int dogsToAdd = dogs(sut).length + 2;

			for (int i = 0; i < dogsToAdd; i++) {
				Dog dog = new Dog("Hund %d%d".formatted(n, i), "Blandras", 1, 2);
				expectedDogs.add(dog);
				ADD_METHOD.invoke(sut, dog);
			}

			assertTrue(dogs(sut).length >= dogsToAdd);
		}

		for (Dog dog : expectedDogs) {
			assertTrue(dogIsInArray(sut, dog));
			assertTrue((boolean) EXISTS_METHOD.invoke(sut, dog));
		}
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund i början av arrayen")
	void canRemoveAtTheBeginning() {
		Object sut = CUT.getConstructor().newInstance();

		Dog one = new Dog("One", "Schäfer", 1, 2);
		Dog two = new Dog("Two", "Tax", 1, 2);
		Dog three = new Dog("Three", "Puli", 1, 2);
		Dog four = new Dog("Four", "Bichon havanais", 1, 2);

		ADD_METHOD.invoke(sut, one);
		ADD_METHOD.invoke(sut, two);
		ADD_METHOD.invoke(sut, three);

		REMOVE_METHOD.invoke(sut, one);
		ADD_METHOD.invoke(sut, four);

		assertFalse(dogIsInArray(sut, one));
		assertTrue(dogIsInArray(sut, two));
		assertTrue(dogIsInArray(sut, three));
		assertTrue(dogIsInArray(sut, four));
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund från slutet av arrayen")
	void canRemoveAtTheEnd() {
		Object sut = CUT.getConstructor().newInstance();

		Dog one = new Dog("One", "Schäfer", 1, 2);
		Dog two = new Dog("Two", "Tax", 1, 2);
		Dog three = new Dog("Three", "Puli", 1, 2);
		Dog four = new Dog("Four", "Bichon havanais", 1, 2);

		ADD_METHOD.invoke(sut, one);
		ADD_METHOD.invoke(sut, two);
		ADD_METHOD.invoke(sut, three);

		REMOVE_METHOD.invoke(sut, three);
		ADD_METHOD.invoke(sut, four);

		assertFalse(dogIsInArray(sut, three));
		assertTrue(dogIsInArray(sut, one));
		assertTrue(dogIsInArray(sut, two));
		assertTrue(dogIsInArray(sut, four));
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund från mitten av arrayen")
	void canRemoveInTheMiddle() {
		Object sut = CUT.getConstructor().newInstance();

		Dog one = new Dog("One", "Schäfer", 1, 2);
		Dog two = new Dog("Two", "Tax", 1, 2);
		Dog three = new Dog("Three", "Puli", 1, 2);
		Dog four = new Dog("Four", "Bichon havanais", 1, 2);

		ADD_METHOD.invoke(sut, one);
		ADD_METHOD.invoke(sut, two);
		ADD_METHOD.invoke(sut, three);

		REMOVE_METHOD.invoke(sut, two);
		ADD_METHOD.invoke(sut, four);

		assertFalse(dogIsInArray(sut, two));
		assertTrue(dogIsInArray(sut, one));
		assertTrue(dogIsInArray(sut, three));
		assertTrue(dogIsInArray(sut, four));
	}

	@Test
	@DisplayName(value = "Tomt utrymme i arrayen används innan arrayen ökar i storlek")
	void noSpaceWasted() {
		Object sut = CUT.getConstructor().newInstance();

		int minNumberOfDogs = 5;

		for (int i = 0; i < minNumberOfDogs; i++) {
			Dog dog = new Dog("Hund %d".formatted(i), "Labradoodle", 1, 2);
			ADD_METHOD.invoke(sut, dog);
		}

		int dogsToFillArray = dogs(sut).length - minNumberOfDogs;
		for (int i = 0; i < dogsToFillArray; i++) {
			Dog dog = new Dog("Hund %d".formatted(minNumberOfDogs + i), "Labradoodle", 1, 2);
			ADD_METHOD.invoke(sut, dog);
		}

		int actualNumberOfDogs = minNumberOfDogs + dogsToFillArray;
		assertEquals(actualNumberOfDogs, dogs(sut).length);

		REMOVE_METHOD.invoke(sut, dogs(sut)[actualNumberOfDogs - 1]);
		REMOVE_METHOD.invoke(sut, dogs(sut)[actualNumberOfDogs / 2]);
		REMOVE_METHOD.invoke(sut, dogs(sut)[0]);

		for (int i = 0; i < 3; i++) {
			Dog dog = new Dog("Ny hund %d".formatted(i), "Labradoodle", 1, 2);
			ADD_METHOD.invoke(sut, dog);
		}

		assertEquals(actualNumberOfDogs, dogs(sut).length,
				"Om man tar bort tre hundar från en full array och sen lägger till tre nya borde arrayens storlek vara densamma");
	}

	@Test
	@DisplayName(value = "Försöker lägga till samma hund flera gånger")
	void cantAddTheSameDogTwice() {
		Object sut = CUT.getConstructor().newInstance();

		for (int i = 0; i < 3; i++) {
			Dog dog = new Dog("Hund %d".formatted(i), "Blandras", 1, 2);
			ADD_METHOD.invoke(sut, dog);
		}

		Dog[] expectedDogs = dogs(sut).clone();

		for (int i : new int[] { 1, 0, 2 }) {
			ADD_METHOD.invoke(sut, expectedDogs[i]);
			assertArrayEquals(expectedDogs, dogs(sut),
					"Det går inte att äga samma hund flera gånger, så arrayen borde vara oförändrad efter att man försökt lägga till samma hund igen");
		}
	}

	@Test
	@DisplayName(value = "Försöker ta bort en hund som inte finns")
	void removingNonexistingDogDoesNothing() {
		Object sut = CUT.getConstructor().newInstance();

		Dog one = new Dog("One", "Kromfohrländer", 1, 2);
		Dog two = new Dog("Two", "Russkiy toy", 1, 2);

		Dog other = new Dog("Other", "Pumi", 1, 2);

		ADD_METHOD.invoke(sut, one);
		ADD_METHOD.invoke(sut, two);

		Dog[] expected = dogs(sut).clone();

		REMOVE_METHOD.invoke(sut, other);

		assertArrayEquals(expected, dogs(sut));
	}

	@Test
	@DisplayName(value = "Försöker lägga till null")
	void addingNullDoesNothing() {
		Object sut = CUT.getConstructor().newInstance();

		Dog[] xx = dogs(sut);
		int x = xx == null || xx.length == 0 ? 1 : xx.length;

		for (int i = 0; i < x; i++) {
			Dog dog = new Dog("Hund %d".formatted(i), "Tax", 1, 2);
			ADD_METHOD.invoke(sut, dog);
		}

		Dog[] expected = dogs(sut).clone();

		ADD_METHOD.invoke(sut, (Object) null);

		assertArrayEquals(expected, dogs(sut),
				"Arrayen var %s, men borde varit %s".formatted(Arrays.toString(dogs(sut)), Arrays.toString(expected)));
	}

	@Test
	@DisplayName(value = "Försöker ta bort null")
	void removingNullDoesNothing() {
		Object sut = CUT.getConstructor().newInstance();

		Dog one = new Dog("One", "Kromfohrländer", 1, 2);
		Dog two = new Dog("Two", "Russkiy toy", 1, 2);

		ADD_METHOD.invoke(sut, one);
		ADD_METHOD.invoke(sut, two);

		Dog[] expected = dogs(sut).clone();

		REMOVE_METHOD.invoke(sut, (Object) null);

		assertArrayEquals(expected, dogs(sut),
				"Arrayen var %s, men borde varit %s".formatted(Arrays.toString(dogs(sut)), Arrays.toString(expected)));
	}

//	/**
//	 * Detta test är ganska svagt, och kan lätt misslyckas, men gör i alla fall ett
//	 * försök att kontrollera att allt arbete gällande hundarna görs med hjälp av en
//	 * array.
//	 * <p>
//	 * Det är avslaget på testservern eftersom det kan misslyckas utan att det är fel
//	 * på klassen, till exempel om du implementerat Iterator-interfacet som
//	 * extra-uppgift.
//	 */
//	@Test
//	public void reallyNoCollectionsInOwner() {
//
//		java.nio.file.Path path = java.nio.file.Paths.get(TestData.DOG_LIST_CLASS_NAME + ".java");
//		org.junit.jupiter.api.Assumptions.assumeTrue(java.nio.file.Files.exists(path), "Kunde inte hitta " + path);
//
//		assertDoesNotThrow(() -> {
//			String code = java.nio.file.Files.readString(path);
//
//			// Detta test är ganska svagt, men det gör i alla fall ett försök att
//			// kontrollera att allt arbete gällande hundarna görs med arrayer
//			String[] forbidden = { "<Dog>", "<Dog", "Dog>", "asList", "asSet", "stream", "Spliterator" };
//
//			for (String x : forbidden) {
//				assertFalse(code.contains(x), String.format(
//						"Koden till listklassen innehåller \"%s\" vilket testfallet tolkar som ett möjligt försök att kringgå restriktionen om att arrayer är det enda som ska användas för att hantera ägarens hundar.",
//						x));
//			}
//
//		});
//	}

	// TODO: lägg till, ta bort alla, lägg till
}

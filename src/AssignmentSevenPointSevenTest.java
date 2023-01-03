/*
 * Denna klass ingår i alla testfallen till U7.7 olika delar.
 * Den innehåller gemensamma delar så att dessa inte behöver
 * upprepas i varje testklass. Den kan inte köras separat.
 */


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.junit.jupiter.api.*;

public abstract class AssignmentSevenPointSevenTest extends ApiBaseTest {

	protected static final ClassUnderTest CUT = new ClassUnderTest(AssignmentSevenPointSeven.class,
			"AssignmentSevenPointSevenTest");
	private static final FieldUnderTest MAIN_DOG_LIST = CUT.getField(TestData.MAIN_DOG_LIST_NAME,
			"TestData.MAIN_DOG_LIST_NAME");

	static final Dog BAMSE = new Dog("Bamse", "Vinthund", 3, 7);
	static final Dog RONJA = new Dog("Ronja", "Dachshund", 9, 15);
	static final Dog MOLLY = new Dog("Molly", "Cocker spaniel", 19, 3);
	static final Dog LUDDE = new Dog("Ludde", "Golden retriever", 1, 4);
	static final Dog FIDO = new Dog("Fido", "Labrador", 6, 16);
	static final Dog BELLA = new Dog("Bella", "Boxer", 15, 18);
	static final Dog LASSIE = new Dog("Lassie", "Dachshund", 1, 4);
	static final Dog KARO = new Dog("Karo", "Yorkshireterrier", 5, 7);
	static final Dog CHARLIE = new Dog("Charlie", "Dachshund", 14, 20);
	static final Dog DORIS = new Dog("Doris", "Golden retriever", 19, 3);

	static final Dog[] ORIGINAL_ORDER_OF_PREPARED_DOGS = { //
			BAMSE, RONJA, MOLLY, LUDDE, FIDO, BELLA, LASSIE, KARO, CHARLIE, DORIS };
	static final Dog[] SORTED_ORDER_OF_PREPARED_DOGS = { //
			LUDDE, BAMSE, KARO, CHARLIE, LASSIE, RONJA, DORIS, MOLLY, FIDO, BELLA };

	/**
	 * sut = Software Under Test, en standardförkortning inom testning. Instansen
	 * kan inte skapas här för att detta då skulle ske innan System.in hunnit sättas
	 * om.
	 */
	protected AssignmentSevenPointSeven sut;

	/**
	 * Om denna konstruktor inte kompilerar har du antagligen en för gammal version
	 * av ApiBaseTest.java, och antagligen BaseTest.java också. Ladda ner en ny
	 * version från ilearn.
	 * 
	 * Ett starkt tips är också att prenumerera på forumet där ändringar i
	 * testfallen annonseras så att du inte missar framtida uppdateringar av testen.
	 */
	protected AssignmentSevenPointSevenTest(int requiredVersionOfBaseTest, int requiredVersionOfApiBaseTest) {
		requireVersion(BaseTest.class, 1);
		requireVersion(ApiBaseTest.class, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	@BeforeEach
	public void preventProblemsWithAnyScannerAdapterThatMightExist() {
		/*
		 * Metoden som ska tas fram i denna uppgift läser inget från användaren. Att vi
		 * ändå sätter om System.in inför varje test beror på att det annars blir
		 * problem om du har kod från de tidigare uppgifterna i
		 * AssignmentSevenPointThree. Det är också därför vi skapar en instans av
		 * klassen här.
		 */
		setIn("");
		sut = new AssignmentSevenPointSeven();
	}

	protected List<Dog> dogs() {
		return getList(MAIN_DOG_LIST, sut);
	}

	// Används inte i testerna, men kan vara nyttig vid debugging.
	// Glöm inte att plocka bort anrop till den innan du skickar
	// in i ilearn bara.
	protected void printDogs() {
		for (int n = 0; n < dogs().size(); n++) {
			System.err.printf("%2d: %-7s %5.1f%n", n, dogs().get(n).getName(), dogs().get(n).getTailLength());
		}
	}

	protected void addDog(Dog d) {
		dogs().add(d);
	}

	protected void addDog(String name, String breed, int age, int weight) {
		addDog(new Dog(name, breed, age, weight));
	}

	protected void addDogs(Dog... newDogs) {
		dogs().addAll(Arrays.asList(newDogs));
	}

	protected void addAllPreparedDogs() {
		addDogs(ORIGINAL_ORDER_OF_PREPARED_DOGS);
	}

	protected void assertDogsAre(String msg, Dog... expected) {
		assertEquals(Arrays.asList(expected), dogs(), msg);
	}

	@Test
	@DisplayName(value = "Finns det några uppenbara problem med uppgiftsklassens struktur?")
	public void basicStructureChecks() {
		assertBasicStructureOfSingleMethodAssignmentMainClass(CUT);
	}


}

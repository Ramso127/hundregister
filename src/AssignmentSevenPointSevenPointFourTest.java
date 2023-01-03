/*
 * Denna fil innehåller JUnit-testfall för metoden för att sortera hundar U7.7.4, den kräver tillgång till AssignmentSevenPointSixTest.
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

@DisplayName(value = "JUnit-testfall för U7.4 - sorteringsmetoden")
public class AssignmentSevenPointSevenPointFourTest extends AssignmentSevenPointSevenTest {

	private static final MethodUnderTest SORT_DOGS_METHOD = CUT.getMethod(TestData.SORT_DOGS_METHOD_NAME,
			"TestData.SORT_DOGS_METHOD_NAME");

	public AssignmentSevenPointSevenPointFourTest() throws IllegalArgumentException, IllegalAccessException {
		super(1, 1);
	}

	@BeforeAll
	public static void checkSoftwareUnderTestData() {
		checkSoftwareUnderTestData(MethodHandles.lookup().lookupClass());
	}

	private void sortAndCheck(Dog[] dogsToSort, Dog[] expectedOrder, int expectedSwaps) {
		addDogs(dogsToSort);
		var swaps = SORT_DOGS_METHOD.invoke(sut);
		assertDogsAre("Den sorterade listan av hundar är inte korrekt", expectedOrder);
		assertEquals(expectedSwaps, swaps,
				"Fel antal byten för en korrekt implementation av urvalssortering  (selection sort)");
	}

	@Test
	@DisplayName(value = "Att sortera \"standardhundarna\" från AssignmentSevenPointSevenTest kräver åtta byten")
	public void sortPreparedDogs() {
		sortAndCheck(ORIGINAL_ORDER_OF_PREPARED_DOGS, SORTED_ORDER_OF_PREPARED_DOGS, 8);
	}

	@Test
	@DisplayName(value = "Att sortera en tom lista kräver inga byten")
	public void sortEmptyList() {
		Dog[] arr = {};
		sortAndCheck(arr, arr, 0);
	}

	@Test
	@DisplayName(value = "Att sortera en redan sorterad lista kräver inga byten")
	public void sortingAnAlreadySortedListRequiresNoSwaps() {
		sortAndCheck(SORTED_ORDER_OF_PREPARED_DOGS, SORTED_ORDER_OF_PREPARED_DOGS, 0);
	}

	@Test
	@DisplayName(value = "Att sortera en omvänt sorterad lista kräver hälften av listans storlek byten")
	public void sortingAReversedSortedListRequiresSwapingHalfThePlaces() {
		int count = SORTED_ORDER_OF_PREPARED_DOGS.length;
		Dog[] arr = new Dog[count];
		for (int i = 0; i < count; i++) {
			arr[count - 1 - i] = SORTED_ORDER_OF_PREPARED_DOGS[i];
		}
		sortAndCheck(arr, SORTED_ORDER_OF_PREPARED_DOGS, SORTED_ORDER_OF_PREPARED_DOGS.length / 2);
	}

	// De efterföljande testen är slumpmässigt genererade scenarier

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med fem hundar som kräver ett byten")
	public void sort5DogsThatRequire1Swaps() {
		Dog charlie = new Dog("Charlie", "Vinthund", 20, 9);
		Dog devil = new Dog("Devil", "Dachshund", 15, 10);
		Dog sigge = new Dog("Sigge", "Cocker spaniel", 8, 11);
		Dog doris = new Dog("Doris", "Boxer", 16, 6);
		Dog bella = new Dog("Bella", "Tax", 2, 10);

		Dog[] unsorted = { charlie, devil, sigge, doris, bella };
		Dog[] sorted = { bella, devil, sigge, doris, charlie };

		sortAndCheck(unsorted, sorted, 1);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med fem hundar som kräver två byten")
	public void sort5DogsThatRequire2Swaps() {
		Dog bamse = new Dog("Bamse", "Labrador", 2, 19);
		Dog charlie = new Dog("Charlie", "Tax", 20, 20);
		Dog ronja = new Dog("Ronja", "Shih tzu", 6, 8);
		Dog lassie = new Dog("Lassie", "Mops", 14, 1);
		Dog snobben = new Dog("Snobben", "Dachshund", 4, 8);

		Dog[] unsorted = { bamse, charlie, ronja, lassie, snobben };
		Dog[] sorted = { lassie, charlie, snobben, bamse, ronja };

		sortAndCheck(unsorted, sorted, 2);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med fem hundar som kräver tre byten")
	public void sort5DogsThatRequire3Swaps() {
		Dog lassie = new Dog("Lassie", "Mops", 12, 11);
		Dog wilma = new Dog("Wilma", "Tax", 17, 18);
		Dog sigge = new Dog("Sigge", "Golden retriever", 10, 20);
		Dog ronja = new Dog("Ronja", "Pudel", 12, 6);
		Dog bamse = new Dog("Bamse", "Dachshund", 11, 13);

		Dog[] unsorted = { lassie, wilma, sigge, ronja, bamse };
		Dog[] sorted = { bamse, wilma, ronja, lassie, sigge };

		sortAndCheck(unsorted, sorted, 3);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med fem hundar som kräver fyra byten")
	public void sort5DogsThatRequire4Swaps() {
		Dog ratata = new Dog("Ratata", "Rottweiler", 4, 19);
		Dog fido = new Dog("Fido", "Dachshund", 6, 18);
		Dog charlie = new Dog("Charlie", "Shih tzu", 10, 20);
		Dog snobben = new Dog("Snobben", "Puli", 17, 18);
		Dog lassie = new Dog("Lassie", "Dachshund", 8, 10);

		Dog[] unsorted = { ratata, fido, charlie, snobben, lassie };
		Dog[] sorted = { fido, lassie, ratata, charlie, snobben };

		sortAndCheck(unsorted, sorted, 4);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver två byten")
	public void sort10DogsThatRequire2Swaps() {
		Dog sigge = new Dog("Sigge", "Bulldogg", 8, 12);
		Dog doris = new Dog("Doris", "Tax", 1, 16);
		Dog fido = new Dog("Fido", "Shih tzu", 12, 16);
		Dog lassie = new Dog("Lassie", "Bulldogg", 5, 15);
		Dog devil = new Dog("Devil", "Dachshund", 18, 2);
		Dog milou = new Dog("Milou", "Golden retriever", 6, 20);
		Dog snobben = new Dog("Snobben", "Dobermann", 8, 16);
		Dog rex = new Dog("Rex", "Dachshund", 15, 19);
		Dog ronja = new Dog("Ronja", "Golden retriever", 14, 14);
		Dog molly = new Dog("Molly", "Dobermann", 16, 15);

		Dog[] unsorted = { sigge, doris, fido, lassie, devil, milou, snobben, rex, ronja, molly };
		Dog[] sorted = { devil, doris, rex, lassie, sigge, milou, snobben, fido, ronja, molly };

		sortAndCheck(unsorted, sorted, 2);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver tre byten")
	public void sort10DogsThatRequire3Swaps() {
		Dog snobben = new Dog("Snobben", "Mops", 5, 5);
		Dog lassie = new Dog("Lassie", "Dachshund", 20, 2);
		Dog bella = new Dog("Bella", "Cocker spaniel", 18, 5);
		Dog ludde = new Dog("Ludde", "Yorkshireterrier", 5, 12);
		Dog wilma = new Dog("Wilma", "Dachshund", 5, 18);
		Dog fido = new Dog("Fido", "Shih tzu", 12, 13);
		Dog devil = new Dog("Devil", "Pudel", 13, 12);
		Dog molly = new Dog("Molly", "Dachshund", 19, 15);
		Dog karo = new Dog("Karo", "Golden retriever", 12, 16);
		Dog rex = new Dog("Rex", "Vinthund", 18, 17);

		Dog[] unsorted = { snobben, lassie, bella, ludde, wilma, fido, devil, molly, karo, rex };
		Dog[] sorted = { snobben, lassie, molly, wilma, ludde, bella, devil, fido, karo, rex };

		sortAndCheck(unsorted, sorted, 3);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver fyra byten")
	public void sort10DogsThatRequire4Swaps() {
		Dog devil = new Dog("Devil", "Bulldogg", 11, 17);
		Dog snobben = new Dog("Snobben", "Dachshund", 6, 19);
		Dog bamse = new Dog("Bamse", "Shih tzu", 5, 12);
		Dog doris = new Dog("Doris", "Labrador", 10, 5);
		Dog karo = new Dog("Karo", "Dachshund", 3, 20);
		Dog fido = new Dog("Fido", "Shih tzu", 16, 4);
		Dog wilma = new Dog("Wilma", "Boxer", 10, 11);
		Dog rex = new Dog("Rex", "Tax", 6, 7);
		Dog charlie = new Dog("Charlie", "Grand danois", 10, 20);
		Dog bella = new Dog("Bella", "Mops", 17, 15);

		Dog[] unsorted = { devil, snobben, bamse, doris, karo, fido, wilma, rex, charlie, bella };
		Dog[] sorted = { karo, rex, snobben, doris, bamse, fido, wilma, devil, charlie, bella };

		sortAndCheck(unsorted, sorted, 4);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver fem byten")
	public void sort10DogsThatRequire5Swaps() {
		Dog wilma = new Dog("Wilma", "Beagle", 8, 15);
		Dog devil = new Dog("Devil", "Dachshund", 1, 18);
		Dog bella = new Dog("Bella", "Shih tzu", 9, 7);
		Dog charlie = new Dog("Charlie", "Puli", 16, 11);
		Dog fido = new Dog("Fido", "Tax", 7, 1);
		Dog sigge = new Dog("Sigge", "Border collie", 4, 17);
		Dog ratata = new Dog("Ratata", "Labrador", 12, 17);
		Dog lassie = new Dog("Lassie", "Tax", 10, 9);
		Dog bamse = new Dog("Bamse", "Cocker spaniel", 14, 12);
		Dog milou = new Dog("Milou", "Pudel", 13, 1);

		Dog[] unsorted = { wilma, devil, bella, charlie, fido, sigge, ratata, lassie, bamse, milou };
		Dog[] sorted = { milou, devil, fido, lassie, bella, sigge, wilma, bamse, charlie, ratata };

		sortAndCheck(unsorted, sorted, 5);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver sex byten")
	public void sort10DogsThatRequire6Swaps() {
		Dog wilma = new Dog("Wilma", "Rottweiler", 16, 19);
		Dog molly = new Dog("Molly", "Tax", 2, 16);
		Dog ronja = new Dog("Ronja", "Golden retriever", 15, 9);
		Dog ludde = new Dog("Ludde", "Boxer", 5, 3);
		Dog milou = new Dog("Milou", "Tax", 16, 14);
		Dog rex = new Dog("Rex", "Golden retriever", 13, 12);
		Dog sigge = new Dog("Sigge", "Mops", 9, 9);
		Dog ratata = new Dog("Ratata", "Tax", 4, 17);
		Dog lassie = new Dog("Lassie", "Border collie", 15, 3);
		Dog devil = new Dog("Devil", "Mops", 2, 9);

		Dog[] unsorted = { wilma, molly, ronja, ludde, milou, rex, sigge, ratata, lassie, devil };
		Dog[] sorted = { ludde, devil, milou, molly, ratata, lassie, sigge, ronja, rex, wilma };

		sortAndCheck(unsorted, sorted, 6);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver sju byten")
	public void sort10DogsThatRequire7Swaps() {
		Dog ronja = new Dog("Ronja", "Labrador", 11, 20);
		Dog fido = new Dog("Fido", "Dachshund", 15, 1);
		Dog snobben = new Dog("Snobben", "Cocker spaniel", 16, 2);
		Dog ludde = new Dog("Ludde", "Bulldogg", 7, 6);
		Dog charlie = new Dog("Charlie", "Tax", 6, 11);
		Dog bamse = new Dog("Bamse", "Cocker spaniel", 6, 15);
		Dog milou = new Dog("Milou", "Puli", 7, 3);
		Dog molly = new Dog("Molly", "Tax", 17, 16);
		Dog karo = new Dog("Karo", "Border collie", 15, 10);
		Dog devil = new Dog("Devil", "Dobermann", 11, 4);

		Dog[] unsorted = { ronja, fido, snobben, ludde, charlie, bamse, milou, molly, karo, devil };
		Dog[] sorted = { milou, snobben, charlie, fido, molly, ludde, devil, bamse, karo, ronja };

		sortAndCheck(unsorted, sorted, 7);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver åtta byten")
	public void sort10DogsThatRequire8Swaps() {
		Dog snobben = new Dog("Snobben", "Mops", 10, 1);
		Dog milou = new Dog("Milou", "Dachshund", 9, 11);
		Dog bamse = new Dog("Bamse", "Golden retriever", 12, 12);
		Dog ludde = new Dog("Ludde", "Bulldogg", 5, 11);
		Dog ratata = new Dog("Ratata", "Tax", 17, 3);
		Dog rex = new Dog("Rex", "Shih tzu", 14, 14);
		Dog bella = new Dog("Bella", "Pudel", 20, 8);
		Dog ronja = new Dog("Ronja", "Dachshund", 10, 9);
		Dog charlie = new Dog("Charlie", "Cocker spaniel", 9, 2);
		Dog karo = new Dog("Karo", "Beagle", 5, 20);

		Dog[] unsorted = { snobben, milou, bamse, ludde, ratata, rex, bella, ronja, charlie, karo };
		Dog[] sorted = { snobben, charlie, milou, ratata, ronja, ludde, karo, bamse, bella, rex };

		sortAndCheck(unsorted, sorted, 8);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med tio hundar som kräver nio byten")
	public void sort10DogsThatRequire9Swaps() {
		Dog sigge = new Dog("Sigge", "Vinthund", 19, 10);
		Dog fido = new Dog("Fido", "Dachshund", 2, 3);
		Dog molly = new Dog("Molly", "Golden retriever", 14, 8);
		Dog ratata = new Dog("Ratata", "Vinthund", 4, 3);
		Dog bamse = new Dog("Bamse", "Dachshund", 3, 11);
		Dog rex = new Dog("Rex", "Cocker spaniel", 17, 8);
		Dog lassie = new Dog("Lassie", "Puli", 18, 16);
		Dog wilma = new Dog("Wilma", "Dachshund", 2, 2);
		Dog karo = new Dog("Karo", "Shih tzu", 10, 9);
		Dog ronja = new Dog("Ronja", "Labrador", 7, 14);

		Dog[] unsorted = { sigge, fido, molly, ratata, bamse, rex, lassie, wilma, karo, ronja };
		Dog[] sorted = { ratata, bamse, fido, wilma, karo, ronja, molly, rex, sigge, lassie };

		sortAndCheck(unsorted, sorted, 9);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver sex byten")
	public void sort15DogsThatRequire6Swaps() {
		Dog wilma = new Dog("Wilma", "Boxer", 16, 14);
		Dog sigge = new Dog("Sigge", "Dachshund", 10, 3);
		Dog charlie = new Dog("Charlie", "Golden retriever", 2, 8);
		Dog bella = new Dog("Bella", "Rottweiler", 6, 6);
		Dog snobben = new Dog("Snobben", "Dachshund", 13, 18);
		Dog bamse = new Dog("Bamse", "Grand danois", 8, 1);
		Dog lassie = new Dog("Lassie", "Mops", 1, 1);
		Dog karo = new Dog("Karo", "Dachshund", 12, 13);
		Dog ludde = new Dog("Ludde", "Grand danois", 10, 16);
		Dog ronja = new Dog("Ronja", "Rottweiler", 12, 8);
		Dog fido = new Dog("Fido", "Tax", 11, 18);
		Dog devil = new Dog("Devil", "Cocker spaniel", 14, 13);
		Dog rex = new Dog("Rex", "Yorkshireterrier", 16, 12);
		Dog molly = new Dog("Molly", "Dachshund", 11, 16);
		Dog ratata = new Dog("Ratata", "Golden retriever", 18, 14);

		Dog[] unsorted = { wilma, sigge, charlie, bella, snobben, bamse, lassie, karo, ludde, ronja, fido, devil, rex,
				molly, ratata };
		Dog[] sorted = { lassie, bamse, charlie, bella, fido, karo, molly, sigge, snobben, ronja, ludde, devil, rex,
				wilma, ratata };

		sortAndCheck(unsorted, sorted, 6);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver sju byten")
	public void sort15DogsThatRequire7Swaps() {
		Dog lassie = new Dog("Lassie", "Mops", 4, 2);
		Dog ronja = new Dog("Ronja", "Dachshund", 13, 1);
		Dog ludde = new Dog("Ludde", "Cocker spaniel", 8, 6);
		Dog ratata = new Dog("Ratata", "Puli", 4, 8);
		Dog devil = new Dog("Devil", "Dachshund", 16, 16);
		Dog charlie = new Dog("Charlie", "Golden retriever", 16, 18);
		Dog doris = new Dog("Doris", "Yorkshireterrier", 15, 19);
		Dog wilma = new Dog("Wilma", "Dachshund", 9, 3);
		Dog molly = new Dog("Molly", "Golden retriever", 9, 18);
		Dog rex = new Dog("Rex", "Rottweiler", 15, 10);
		Dog bella = new Dog("Bella", "Dachshund", 11, 3);
		Dog karo = new Dog("Karo", "Grand danois", 2, 15);
		Dog fido = new Dog("Fido", "Vinthund", 10, 18);
		Dog milou = new Dog("Milou", "Tax", 19, 1);
		Dog sigge = new Dog("Sigge", "Grand danois", 16, 18);

		Dog[] unsorted = { lassie, ronja, ludde, ratata, devil, charlie, doris, wilma, molly, rex, bella, karo, fido,
				milou, sigge };
		Dog[] sorted = { lassie, karo, ratata, bella, devil, milou, ronja, wilma, ludde, rex, molly, fido, doris,
				charlie, sigge };

		sortAndCheck(unsorted, sorted, 7);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver åtta byten")
	public void sort15DogsThatRequire8Swaps() {
		Dog bella = new Dog("Bella", "Yorkshireterrier", 8, 11);
		Dog ludde = new Dog("Ludde", "Tax", 11, 7);
		Dog sigge = new Dog("Sigge", "Golden retriever", 2, 16);
		Dog lassie = new Dog("Lassie", "Pudel", 16, 3);
		Dog rex = new Dog("Rex", "Tax", 1, 16);
		Dog bamse = new Dog("Bamse", "Border collie", 11, 20);
		Dog wilma = new Dog("Wilma", "Boxer", 6, 7);
		Dog snobben = new Dog("Snobben", "Dachshund", 18, 9);
		Dog ronja = new Dog("Ronja", "Grand danois", 11, 20);
		Dog fido = new Dog("Fido", "Beagle", 15, 8);
		Dog charlie = new Dog("Charlie", "Tax", 2, 13);
		Dog doris = new Dog("Doris", "Grand danois", 7, 11);
		Dog devil = new Dog("Devil", "Vinthund", 9, 20);
		Dog ratata = new Dog("Ratata", "Dachshund", 2, 16);
		Dog milou = new Dog("Milou", "Golden retriever", 8, 7);

		Dog[] unsorted = { bella, ludde, sigge, lassie, rex, bamse, wilma, snobben, ronja, fido, charlie, doris, devil,
				ratata, milou };
		Dog[] sorted = { sigge, charlie, ludde, ratata, rex, snobben, wilma, lassie, milou, doris, bella, fido, devil,
				bamse, ronja };

		sortAndCheck(unsorted, sorted, 8);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver nio byten")
	public void sort15DogsThatRequire9Swaps() {
		Dog sigge = new Dog("Sigge", "Rottweiler", 4, 20);
		Dog devil = new Dog("Devil", "Tax", 2, 18);
		Dog fido = new Dog("Fido", "Golden retriever", 1, 15);
		Dog bella = new Dog("Bella", "Pudel", 19, 5);
		Dog ratata = new Dog("Ratata", "Dachshund", 19, 19);
		Dog bamse = new Dog("Bamse", "Shih tzu", 16, 14);
		Dog milou = new Dog("Milou", "Bulldogg", 10, 5);
		Dog karo = new Dog("Karo", "Tax", 5, 19);
		Dog molly = new Dog("Molly", "Grand danois", 16, 18);
		Dog doris = new Dog("Doris", "Labrador", 18, 17);
		Dog ludde = new Dog("Ludde", "Dachshund", 4, 9);
		Dog snobben = new Dog("Snobben", "Golden retriever", 5, 20);
		Dog charlie = new Dog("Charlie", "Dobermann", 13, 4);
		Dog lassie = new Dog("Lassie", "Tax", 3, 1);
		Dog wilma = new Dog("Wilma", "Golden retriever", 17, 20);

		Dog[] unsorted = { sigge, devil, fido, bella, ratata, bamse, milou, karo, molly, doris, ludde, snobben, charlie,
				lassie, wilma };
		Dog[] sorted = { fido, devil, karo, lassie, ludde, ratata, milou, charlie, sigge, bella, snobben, bamse, molly,
				doris, wilma };

		sortAndCheck(unsorted, sorted, 9);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver tio byten")
	public void sort15DogsThatRequire10Swaps() {
		Dog milou = new Dog("Milou", "Puli", 3, 9);
		Dog ronja = new Dog("Ronja", "Tax", 2, 17);
		Dog lassie = new Dog("Lassie", "Grand danois", 8, 2);
		Dog fido = new Dog("Fido", "Boxer", 8, 2);
		Dog ludde = new Dog("Ludde", "Tax", 13, 12);
		Dog snobben = new Dog("Snobben", "Grand danois", 11, 20);
		Dog sigge = new Dog("Sigge", "Rottweiler", 7, 12);
		Dog bamse = new Dog("Bamse", "Dachshund", 2, 9);
		Dog doris = new Dog("Doris", "Grand danois", 6, 15);
		Dog charlie = new Dog("Charlie", "Mops", 6, 3);
		Dog rex = new Dog("Rex", "Tax", 20, 11);
		Dog molly = new Dog("Molly", "Shih tzu", 7, 10);
		Dog karo = new Dog("Karo", "Yorkshireterrier", 5, 17);
		Dog devil = new Dog("Devil", "Dachshund", 4, 3);
		Dog bella = new Dog("Bella", "Cocker spaniel", 17, 2);

		Dog[] unsorted = { milou, ronja, lassie, fido, ludde, snobben, sigge, bamse, doris, charlie, rex, molly, karo,
				devil, bella };
		Dog[] sorted = { fido, lassie, charlie, milou, bella, bamse, devil, ludde, rex, ronja, molly, sigge, karo,
				doris, snobben };

		sortAndCheck(unsorted, sorted, 10);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver elva byten")
	public void sort15DogsThatRequire11Swaps() {
		Dog milou = new Dog("Milou", "Boxer", 19, 17);
		Dog sigge = new Dog("Sigge", "Tax", 4, 10);
		Dog bamse = new Dog("Bamse", "Grand danois", 5, 10);
		Dog fido = new Dog("Fido", "Labrador", 1, 20);
		Dog lassie = new Dog("Lassie", "Tax", 10, 1);
		Dog rex = new Dog("Rex", "Cocker spaniel", 15, 18);
		Dog doris = new Dog("Doris", "Bulldogg", 1, 5);
		Dog bella = new Dog("Bella", "Tax", 4, 13);
		Dog karo = new Dog("Karo", "Grand danois", 10, 5);
		Dog ronja = new Dog("Ronja", "Pudel", 15, 5);
		Dog charlie = new Dog("Charlie", "Dachshund", 18, 8);
		Dog wilma = new Dog("Wilma", "Grand danois", 2, 13);
		Dog ratata = new Dog("Ratata", "Labrador", 20, 10);
		Dog molly = new Dog("Molly", "Tax", 14, 9);
		Dog snobben = new Dog("Snobben", "Cocker spaniel", 11, 4);

		Dog[] unsorted = { milou, sigge, bamse, fido, lassie, rex, doris, bella, karo, ronja, charlie, wilma, ratata,
				molly, snobben };
		Dog[] sorted = { doris, fido, wilma, bella, charlie, lassie, molly, sigge, snobben, bamse, karo, ronja, ratata,
				rex, milou };

		sortAndCheck(unsorted, sorted, 11);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver tolv byten")
	public void sort15DogsThatRequire12Swaps() {
		Dog rex = new Dog("Rex", "Beagle", 19, 14);
		Dog doris = new Dog("Doris", "Tax", 16, 13);
		Dog ludde = new Dog("Ludde", "Shih tzu", 12, 2);
		Dog bamse = new Dog("Bamse", "Labrador", 19, 7);
		Dog sigge = new Dog("Sigge", "Tax", 11, 5);
		Dog devil = new Dog("Devil", "Golden retriever", 11, 3);
		Dog wilma = new Dog("Wilma", "Bulldogg", 10, 4);
		Dog fido = new Dog("Fido", "Tax", 3, 2);
		Dog bella = new Dog("Bella", "Grand danois", 9, 6);
		Dog charlie = new Dog("Charlie", "Labrador", 6, 10);
		Dog snobben = new Dog("Snobben", "Dachshund", 18, 19);
		Dog ronja = new Dog("Ronja", "Shih tzu", 13, 7);
		Dog lassie = new Dog("Lassie", "Bulldogg", 7, 1);
		Dog milou = new Dog("Milou", "Dachshund", 20, 3);
		Dog karo = new Dog("Karo", "Golden retriever", 17, 5);

		Dog[] unsorted = { rex, doris, ludde, bamse, sigge, devil, wilma, fido, bella, charlie, snobben, ronja, lassie,
				milou, karo };
		Dog[] sorted = { lassie, ludde, devil, doris, fido, milou, sigge, snobben, wilma, bella, charlie, karo, ronja,
				bamse, rex };

		sortAndCheck(unsorted, sorted, 12);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver tretton byten")
	public void sort15DogsThatRequire13Swaps() {
		Dog bamse = new Dog("Bamse", "Chihuahua", 16, 12);
		Dog devil = new Dog("Devil", "Dachshund", 7, 3);
		Dog ratata = new Dog("Ratata", "Golden retriever", 6, 19);
		Dog lassie = new Dog("Lassie", "Dobermann", 20, 7);
		Dog molly = new Dog("Molly", "Dachshund", 20, 2);
		Dog ronja = new Dog("Ronja", "Grand danois", 14, 3);
		Dog rex = new Dog("Rex", "Mops", 18, 18);
		Dog doris = new Dog("Doris", "Tax", 9, 2);
		Dog charlie = new Dog("Charlie", "Border collie", 11, 20);
		Dog ludde = new Dog("Ludde", "Pudel", 5, 4);
		Dog bella = new Dog("Bella", "Tax", 10, 13);
		Dog sigge = new Dog("Sigge", "Cocker spaniel", 3, 3);
		Dog snobben = new Dog("Snobben", "Rottweiler", 8, 3);
		Dog fido = new Dog("Fido", "Dachshund", 4, 11);
		Dog karo = new Dog("Karo", "Grand danois", 2, 9);

		Dog[] unsorted = { bamse, devil, ratata, lassie, molly, ronja, rex, doris, charlie, ludde, bella, sigge,
				snobben, fido, karo };
		Dog[] sorted = { sigge, karo, ludde, snobben, bella, devil, doris, fido, molly, ronja, ratata, lassie, bamse,
				charlie, rex };

		sortAndCheck(unsorted, sorted, 13);
	}

	@Test
	@DisplayName(value = "Slumpmässigt genererat test med femton hundar som kräver fjorton byten")
	public void sort15DogsThatRequire14Swaps() {
		Dog sigge = new Dog("Sigge", "Rottweiler", 8, 17);
		Dog ronja = new Dog("Ronja", "Dachshund", 4, 2);
		Dog bamse = new Dog("Bamse", "Grand danois", 3, 11);
		Dog karo = new Dog("Karo", "Chihuahua", 1, 15);
		Dog lassie = new Dog("Lassie", "Dachshund", 20, 16);
		Dog fido = new Dog("Fido", "Grand danois", 18, 16);
		Dog snobben = new Dog("Snobben", "Boxer", 7, 17);
		Dog doris = new Dog("Doris", "Tax", 19, 17);
		Dog devil = new Dog("Devil", "Cocker spaniel", 2, 11);
		Dog milou = new Dog("Milou", "Puli", 14, 13);
		Dog wilma = new Dog("Wilma", "Dachshund", 14, 15);
		Dog charlie = new Dog("Charlie", "Golden retriever", 12, 1);
		Dog ludde = new Dog("Ludde", "Vinthund", 1, 3);
		Dog molly = new Dog("Molly", "Dachshund", 16, 19);
		Dog bella = new Dog("Bella", "Golden retriever", 10, 18);

		Dog[] unsorted = { sigge, ronja, bamse, karo, lassie, fido, snobben, doris, devil, milou, wilma, charlie, ludde,
				molly, bella };
		Dog[] sorted = { ludde, charlie, karo, devil, bamse, doris, lassie, molly, ronja, wilma, snobben, sigge, bella,
				milou, fido };

		sortAndCheck(unsorted, sorted, 14);
	}


}

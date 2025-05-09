import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MainTest {

    @ParameterizedTest
    @ValueSource(strings = { "Bob", "Smith", "Joey" })
    void testProperName(String name) {
        assertTrue(Main.properName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = { "bob", "smIth", "JoEy", "", "Bob " })
    void testImproperName(String name) {
        assertFalse(Main.properName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = { "12", "43.23", "-34.5", "+98.7", "0", "0.0230" })
    void testInteger(String number) {
        assertTrue(Main.integer(number));
    }

    @ParameterizedTest
    @ValueSource(strings = { "023", "", " ", "12s", "0.", ".3" })
    void testNonInteger(String number) {
        assertFalse(Main.integer(number));
    }

    @ParameterizedTest
    @ValueSource(strings = { "father", "mother", "great-great-grandmother" })
    void testAncestor(String name) {
        assertTrue(Main.ancestor(name));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "foo" })
    void testNonAncestor(String name) {
        assertFalse(Main.ancestor(name));
    }

    @ParameterizedTest
    @ValueSource(strings = { "asdfggfdsa", "asdfGgfdSa" })
    void testPalindrome(String palindrome) {
        assertTrue(Main.palindrome(palindrome));
    }

    @ParameterizedTest
    @ValueSource(strings = { "asdfggfdss", "basdfggfdsab", "" })
    void testNotPalindrome(String palindrome) {
        assertFalse(Main.palindrome(palindrome));
    }

    @Test
    void testGeneratesValidNextGuess() throws FileNotFoundException {
        List<List<WordleResponse>> previousResponses = getPreviousResponses();
        assertTrue(Main.wordleMatches(previousResponses).contains("WREST"));
    }

    @Test
    void testDoesNotGeneratesInvalidNextGuess() throws FileNotFoundException {
        List<List<WordleResponse>> previousResponses = getPreviousResponses();
        assertFalse(Main.wordleMatches(previousResponses).contains("WOVEN"));
    }

    private List<List<WordleResponse>> getPreviousResponses() {
        List<List<WordleResponse>> previousResponses =  new ArrayList<>();
        previousResponses.add(Arrays.asList(
            new WordleResponse('L', 0, WordleResponse.LetterResponse.WRONG_LETTER),
            new WordleResponse('A', 1, WordleResponse.LetterResponse.WRONG_LETTER),
            new WordleResponse('U', 2, WordleResponse.LetterResponse.WRONG_LETTER),
            new WordleResponse('G', 3, WordleResponse.LetterResponse.WRONG_LETTER),
            new WordleResponse('H', 4, WordleResponse.LetterResponse.WRONG_LETTER)
        ));
        previousResponses.add(Arrays.asList(
            new WordleResponse('W', 0, WordleResponse.LetterResponse.CORRECT_LOCATION),
            new WordleResponse('O', 1, WordleResponse.LetterResponse.WRONG_LETTER),
            new WordleResponse('V', 2, WordleResponse.LetterResponse.WRONG_LETTER),
            new WordleResponse('E', 3, WordleResponse.LetterResponse.WRONG_LOCATION),
            new WordleResponse('N', 4, WordleResponse.LetterResponse.WRONG_LETTER)
        ));
        return previousResponses;
    }

}

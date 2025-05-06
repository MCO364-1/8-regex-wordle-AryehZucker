import static org.junit.jupiter.api.Assertions.*;

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

}

public class WordleResponse {

    public enum LetterResponse {
        CORRECT_LOCATION, WRONG_LOCATION, WRONG_LETTER;
    }

    public final char c;
    public final int index;
    public final LetterResponse response;

    public WordleResponse(char c, int index, LetterResponse response) {
        this.c = c;
        this.index = index;
        this.response = response;
    }

}

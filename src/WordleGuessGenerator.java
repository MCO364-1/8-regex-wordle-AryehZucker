import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class WordleGuessGenerator {

    private static class RegExFilter implements Predicate<String> {

        private final Pattern pattern;

        public RegExFilter(String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        @Override
        public boolean test(String s) {
            return pattern.matcher(s).matches();
        }

    }

    private static class RegExFilterOut extends RegExFilter {

        public RegExFilterOut(String pattern) {
            super(pattern);
        }

        @Override
        public boolean test(String s) {
            return !super.test(s);
        }
    }

    private Stream<String> wordStream;

    public WordleGuessGenerator() throws FileNotFoundException {
        wordStream = getWords().stream();
        wordStream = wordStream.filter(new RegExFilter("[A-Z]{5}"));
    }

    public List<String> getValidGuesses(List<List<WordleResponse>> previousResponses) {
        filterValidGuesses(previousResponses);
        return wordStream.toList();
    }

    private void filterValidGuesses(List<List<WordleResponse>> previousResponses) {
        for (List<WordleResponse> completeResponse : previousResponses) {
            filterForResponse(completeResponse);
        }
    }

    private void filterForResponse(List<WordleResponse> completeResponse) {
        StringBuilder greens = new StringBuilder(".....");
        Character[] yellows = { null, null, null, null, null };
        Set<Character> grays = new HashSet<>();
        Map<Character, Integer> letterCounts = new HashMap<>();

        for (WordleResponse response : completeResponse) {
            if (response.response == WordleResponse.LetterResponse.WRONG_LETTER) {
                grays.add(response.c);
            } else {
                letterCounts.put(response.c, letterCounts.getOrDefault(response.c, 0) + 1);

                if (response.response == WordleResponse.LetterResponse.CORRECT_LOCATION) {
                    greens.setCharAt(response.index, response.c);
                } else if (response.response == WordleResponse.LetterResponse.WRONG_LOCATION) {
                    yellows[response.index] = response.c;
                }
            }
        }

        filterForGreens(greens.toString());
        filterForYellowsInPlace(yellows);
        filterForYellowsInWord(letterCounts);
        filterForGrays(grays, letterCounts);
    }

    private void filterForGreens(String greens) {
        wordStream = wordStream.filter(new RegExFilter(greens));
    }

    private void filterForYellowsInPlace(Character[] yellows) {
        StringBuilder pattern = new StringBuilder();
        for (Character y : yellows) {
            if (y == null) {
                pattern.append(".");
            } else {
                pattern.append("[^" + y + "]");
            }
        }
        wordStream = wordStream.filter(new RegExFilter(pattern.toString()));
    }

    private void filterForYellowsInWord(Map<Character, Integer> letterCounts) {
        for (Entry<Character, Integer> entry : letterCounts.entrySet()) {
            wordStream = wordStream.filter(new RegExFilter(
                    String.format(".*(%c.*){%d}", entry.getKey(), entry.getValue())));
        }
    }

    private void filterForGrays(Set<Character> grays, Map<Character, Integer> letterCounts) {
        for (Character c : grays) {
            wordStream = wordStream.filter(new RegExFilterOut(
                    String.format(".*(%c.*){%d}", c, letterCounts.getOrDefault(c, 0) + 1)));
        }
    }

    private static List<String> getWords() throws FileNotFoundException {
        List<String> words = new ArrayList<>();
        try (Scanner in = new Scanner(new File("words.txt"))) {
            while (in.hasNext()) {
                words.add(in.next());
            }
        }
        System.out.println("Loaded " + words.size() + " words");
        return words;
    }

}

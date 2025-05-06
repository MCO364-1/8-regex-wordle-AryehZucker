import java.util.regex.Pattern;

public class Main {

    private static Pattern properNamePattern = Pattern.compile("[A-Z][a-z]*"),
            integerPattern = Pattern.compile("(\\+|-)?(0|[1-9]\\d*)(\\.\\d+)?"),
            ancestorPattern = Pattern.compile("((great-)*grand)?(father|mother)"),
            palindromePattern = Pattern.compile("([a-z])([a-z])([a-z])([a-z])([a-z])\\5\\4\\3\\2\\1");

    public static boolean properName(String s) {
        return properNamePattern.matcher(s).matches();
    }

    public static boolean integer(String s) {
        return integerPattern.matcher(s).matches();
    }

    public static boolean ancestor(String s) {
        return ancestorPattern.matcher(s).matches();
    }

    public static boolean palindrome(String s) {
        return palindromePattern.matcher(s.toLowerCase()).matches();
    }

}

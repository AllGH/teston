package vote.util;

import java.util.Random;

public class RandomString {
    public static String getRandomString(int count) {
        return new Random().ints(48,122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(count)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}

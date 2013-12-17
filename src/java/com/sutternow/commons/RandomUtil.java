package com.sutternow.commons;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public final class RandomUtil {

    /**
     * list of words.
     */
    private static String[] words = new String[] { "Lorem", "ipsum", "dolor", "sit", "amet", "consetetur", "sadipscing",
            "elitr", "sed", "diam", "nonumy", "eirmod", "tempor", "invidunt", "ut", "labore", "et", "dolore", "magna",
            "aliquyam", "erat", "sed", "diam", "voluptua", "At", "vero", "eos", "et", "accusam", "et", "justo", "duo",
            "dolores", "et", "ea", "rebum", "Stet", "clita", "kasd", "gubergren", "no", "sea", "takimata", "sanctus", "est" };

    /**
     * random number producer.
     */
    private static Random random = new Random();

    /**
     * utility class, don't instantiate.
     */
    private RandomUtil() {
        super();
    }

    /**
     * returns a random word.
     *
     * @return random word
     */
    public static String getRandomWord() {
        return words[random.nextInt(words.length)];
    }

    public static String getRandomChoiceString(String... choices) {
        if (choices == null || choices.length == 0) {
            // bad usage
            throw new IllegalArgumentException("choices cannot be null and must have at least one entry");
        }
        return choices[random.nextInt(choices.length)];
    }

    public static Object getRandomChoice(List<?> choices) {
        if (choices == null || choices.size() == 0) {
            // bad usage
            throw new IllegalArgumentException("choices cannot be null and must have at least one entry");
        }
        return choices.get(random.nextInt(choices.size()));
    }

    /**
     * returns a random sentence.
     *
     * @param numberOfWords
     *            number of word in the sentence
     * @return random sentence made of <code>wordNumber</code> words
     */
    public static String getRandomSentence(int numberOfWords) {
        StringBuffer buffer = new StringBuffer(numberOfWords * 12);

        int j = 0;
        while (j < numberOfWords) {
            buffer.append(getRandomWord());
            buffer.append(" ");
            j++;
        }
        buffer.append(".");
        return buffer.toString();
    }

    /**
     * returns a random email.
     *
     * @return random email
     */
    public static String getRandomEmail() {
        return getRandomWord() + "@" + getRandomWord() + ".com";
    }

    /**
     * returns a random date.
     *
     * @return random date
     */
    public static Date getRandomDate() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 365 - random.nextInt(730));
        return calendar.getTime();
    }

    // RandomCharacter.java: Generate random characters

    /** Generate a random character between ch1 and ch2 */
    public static char getRandomCharacter(char ch1, char ch2) {
        return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));
    }

    /** Generate a random lowercase letter */
    public static char getRandomLowerCaseLetter() {
        return getRandomCharacter('a', 'z');
    }

    /** Generate a random uppercase letter */
    public static char getRandomUpperCaseLetter() {
        return getRandomCharacter('A', 'Z');
    }

    /** Generate a random digit character */
    public static char getRandomDigitCharacter() {
        return getRandomCharacter('0', '9');
    }

    /** Generate a random digit string */
    public static String getRandomDigitString(int length) {
        String digitString = "";
        for (int i = 0; i < length; i++) {
            digitString += getRandomCharacter('0', '9');
        }

        return digitString;
    }
    public static char getRandomAlphaNumCharacter() {
        String choice = getRandomChoiceString("A", "a", "0");
        if (choice.equals("A")) {
            return getRandomUpperCaseLetter();
        } else if (choice.equals("a")) {
            return getRandomLowerCaseLetter();
        } else {
            return getRandomDigitCharacter();
        }
    }


    public static String getRandomPassword(int length) {
        String digitString = "";
        for (int i = 0; i < length; i++) {
            digitString += getRandomAlphaNumCharacter();
        }

        return digitString;
    }

    /** Generate a random character */
    public static char getRandomCharacter() {
        return getRandomCharacter('\u0000', '\uFFFF');
    }

    public static String getRandomSSN() {
        String ssn = getRandomDigitCharacter() + getRandomDigitCharacter() + getRandomDigitCharacter() + "-"
                + getRandomDigitCharacter() + getRandomDigitCharacter() + "-" + getRandomDigitCharacter()
                + getRandomDigitCharacter() + getRandomDigitCharacter() + getRandomDigitCharacter();
        return ssn;
    }

//    public static String getRandomId() {
//        int id = random.nextInt(99998 + 1);
//        String s = String.valueOf(id);
//        return s;
//    }


    public static String getRandomId() {
        String uniqId = "";
        Random randomGenerator = new Random();
        //length - set the unique Id length
        for (int length = 1; length <= 8; ++length) {
            int randomInt = randomGenerator.nextInt(10); //digit range from 0 - 9
            uniqId += randomInt + "";
        }

        // Add current time in milliseconds at behind to make it always unique
        uniqId += new Date().getTime();
        return uniqId;
    }
}

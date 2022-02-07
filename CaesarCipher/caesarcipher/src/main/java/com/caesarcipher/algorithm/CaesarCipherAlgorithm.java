package com.caesarcipher.algorithm;

import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.stream.IntStream;

@Component
public class CaesarCipherAlgorithm {

    private final char letter_a = 'a';
    private final char letter_z = 'z';
    private final int alphabet_size = letter_z - letter_a + 1;
    private final double[] english_letters_probability =
            {
                    0.073, 0.009, 0.030, 0.044, 0.130, 0.028, 0.016, 0.035, 0.074, 0.002, 0.003, 0.035, 0.025, 0.078,
                    0.074, 0.027, 0.003, 0.077, 0.063, 0.093, 0.027, 0.013, 0.016, 0.005, 0.019, 0.001
            };

    public String cipher(String message, int offset) {

        StringBuilder result = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - letter_a;
                int newAlphabetPosition = (originalAlphabetPosition + offset) % alphabet_size;
                char newCharacter = (char) (letter_a + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    public String decipher(String message, int offset) {
        return cipher(message, alphabet_size - (offset % alphabet_size));
    }

    public String breakCipher(String message) {
        return this.decipher(message,probableOffset(chiSquares(message)));
    }

    private double[] chiSquares(String message) {
        double[] expectedLettersFrequencies = expectedLettersFrequencies(message.length());

        double[] chiSquares = new double[alphabet_size];

        for (int offset = 0; offset < chiSquares.length; offset++) {
            String decipheredMessage = decipher(message, offset);
            long[] lettersFrequencies = observedLettersFrequencies(decipheredMessage);
            double chiSquare = new ChiSquareTest().chiSquare(expectedLettersFrequencies, lettersFrequencies);
            chiSquares[offset] = chiSquare;
        }

        return chiSquares;
    }

    private long[] observedLettersFrequencies(String message) {
        return IntStream.rangeClosed(letter_a, letter_z)
                .mapToLong(letter -> countLetter((char) letter, message))
                .toArray();
    }

    private long countLetter(char letter, String message) {
        return message.chars()
                .filter(character -> character == letter)
                .count();
    }

    private double[] expectedLettersFrequencies(int messageLength) {
        return Arrays.stream(english_letters_probability)
                .map(probability -> probability * messageLength)
                .toArray();
    }

    private int probableOffset(double[] chiSquares) {
        int probableOffset = 0;
        for (int offset = 0; offset < chiSquares.length; offset++) {
            if (chiSquares[offset] < chiSquares[probableOffset]) {
                probableOffset = offset;
            }
        }

        return probableOffset;
    }
}



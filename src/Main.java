import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<String> words = initializeWordList();

        // Generate and store sentences with a range of 1 to 10 words
        List<String> sentences10 = generateAndStoreSentences(words, 1, 10, "sentences_10.txt");
        int count10 = countSentencesWithThreeOrMoreWords(sentences10);
        System.out.println("Number of sentences with 3 or more words (length 1-10): " + count10);

        // Duplicate some words, add them to the list, and generate/store sentences with a range of 1 to 15 words
        List<String> sentences15 = duplicateAndGenerateSentences(words, 1, 15, "sentences_15.txt");
        int count15 = countSentencesWithThreeOrMoreWords(sentences15);
        System.out.println("Number of sentences with 3 or more words (length 1-15 with duplicated words): " + count15);
    }

    private static List<String> initializeWordList() {
        List<String> words = new ArrayList<>();
        words.add("apple");
        words.add("banana");
        words.add("orange");
        words.add("grape");
        words.add("pear");
        words.add("kiwi");
        words.add("melon");
        words.add("pineapple");
        words.add("strawberry");
        words.add("blueberry");
        return words;
    }

    private static List<String> generateSentences(List<String> words, int minLength, int maxLength) {
        List<String> sentences = new ArrayList<>();
        Random random = new Random();

        while (sentences.size() < 100) { // Adjusted limit, you can set it as needed
            int sentenceLength = random.nextInt(maxLength - minLength + 1) + minLength; // Random length within the specified range
            List<String> sentenceWords = new ArrayList<>();
            for (int i = 0; i < sentenceLength; i++) {
                String word = words.get(random.nextInt(words.size()));
                sentenceWords.add(word);
            }

            // Shuffle the words to add some randomness
            Collections.shuffle(sentenceWords);

            sentences.add(String.join(" ", sentenceWords));
        }
        return sentences;
    }

    private static List<String> generateAndStoreSentences(List<String> words, int minLength, int maxLength, String filename) {
        List<String> sentences = generateSentences(words, minLength, maxLength);
        writeSentencesToFile(sentences, filename);
        System.out.println("Sentences with a length between " + minLength + " and " + maxLength + " written to file: " + filename);
        return sentences;
    }

    private static List<String> duplicateAndGenerateSentences(List<String> words, int minLength, int maxLength, String filename) {
        // Duplicate some words and add them to the list
        List<String> duplicatedWords = new ArrayList<>(words);
        duplicatedWords.addAll(words.subList(0, 3)); // Duplicate the first three words

        // Generate and store sentences with a length between 1 and 15 using the updated word list
        List<String> sentences = generateSentences(duplicatedWords, minLength, maxLength);
        writeSentencesToFile(sentences, filename);
        System.out.println("Sentences with a length between " + minLength + " and " + maxLength + " (with duplicated words) written to file: " + filename);
        return sentences;
    }

    private static void writeSentencesToFile(List<String> sentences, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String sentence : sentences) {
                writer.write(sentence);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing sentences to file: " + e.getMessage());
        }
    }

    private static int countSentencesWithThreeOrMoreWords(List<String> sentences) {
        int count = 0;
        for (String sentence : sentences) {
            String[] words = sentence.split("\\s+");
            if (words.length >= 3) {
                count++;
            }
        }
        return count;
    }
}

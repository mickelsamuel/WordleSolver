import java.util.List;
import java.util.stream.Collectors;

public class GuessEvaluator {

    // Constants representing the types of feedback for each letter in the guess.
    public static final char CORRECT = 'G'; // Letter is in the correct position.
    public static final char PRESENT = 'Y'; // Letter is in the word but in the wrong position.
    public static final char ABSENT = 'X';  // Letter is not in the word.

    // Method to refine the list of possible words based on the feedback received for a guess.
    public List<String> refineWordListBasedOnFeedback(List<String> wordList, String guess, String feedback) {
        List<String> refinedList = wordList; // Start with the current list of possible words.

        // Iterate over each letter in the guess.
        for (int i = 0; i < guess.length(); i++) {
            final int index = i; // The position of the letter in the guess.
            final char guessChar = guess.charAt(i); // The letter in the guess.
            char feedbackChar = feedback.charAt(i); // The feedback for this letter.

            switch (feedbackChar) {
                case CORRECT:
                    // If the letter is correct, filter the list to keep only words with this letter in the same position.
                    refinedList = refinedList.stream()
                            .filter(word -> word.charAt(index) == guessChar)
                            .collect(Collectors.toList());
                    break;
                case PRESENT:
                    // If the letter is present, keep words that contain this letter but not in the current position.
                    refinedList = refinedList.stream()
                            .filter(word -> word.contains(String.valueOf(guessChar)) && word.charAt(index) != guessChar)
                            .collect(Collectors.toList());
                    break;
                case ABSENT:
                    // If the letter is absent, filter out words that have this letter in this position.
                    refinedList = refinedList.stream()
                            .filter(word -> word.charAt(index) != guessChar)
                            .collect(Collectors.toList());
                    break;
            }
        }
        return refinedList; // Return the refined list of words after applying all feedback.
    }
}
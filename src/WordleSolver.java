import java.util.*;

public class WordleSolver {

    // A constant for the initial guess in the game.
    private static final String INITIAL_GUESS = "audio";
    private List<String> possibleWords; // List to store all possible words.
    private boolean isFirstGuess; // Flag to check if it's the first guess.
    private Set<Character> triedLetters; // Set to store letters already tried.
    private Set<Character> confirmedLetters; // Set to store letters confirmed to be in the word.
    private char[] confirmedPositions; // Array to store confirmed positions of letters in the word.

    // Constructor to initialize the WordleSolver with a list of words.
    public WordleSolver(List<String> wordList) {
        this.possibleWords = new ArrayList<>(wordList);
        this.isFirstGuess = true;
        this.triedLetters = new HashSet<>();
        this.confirmedLetters = new HashSet<>();
        this.confirmedPositions = new char[5];
        Arrays.fill(this.confirmedPositions, '_'); // Fill the positions array with placeholder '_'.
    }

    // Method to get the next guess.
    public String getNextGuess() {
        if (isFirstGuess) {
            isFirstGuess = false; // Set flag to false after the first guess.
            for (char c : INITIAL_GUESS.toCharArray()) {
                triedLetters.add(c); // Add each letter of the initial guess to the tried letters set.
            }
            return INITIAL_GUESS;
        } else {
            return chooseBestWordFromList(); // Choose the next guess based on certain criteria.
        }
    }

    // Private method to choose the best word from the list.
    private String chooseBestWordFromList() {
        // Select the best guess from the remaining possible words.
        String bestGuess = possibleWords.stream()
                .filter(this::fitsConfirmedPositions) // Filter words that fit the confirmed positions.
                .max(Comparator.comparingInt(this::wordScore)) // Choose the word with the highest score.
                .orElse(null); // Return null if no suitable word is found.

        // If no best guess is found, pick a random word.
        if (bestGuess == null) {
            Random random = new Random();
            bestGuess = possibleWords.get(random.nextInt(possibleWords.size()));
        }

        // Add the letters of the best guess to the set of tried letters.
        for (char c : bestGuess.toCharArray()) {
            triedLetters.add(c);
        }

        return bestGuess;
    }

    // Method to check if a word fits the confirmed positions.
    private boolean fitsConfirmedPositions(String word) {
        for (int i = 0; i < 5; i++) {
            if (confirmedPositions[i] != '_' && confirmedPositions[i] != word.charAt(i)) {
                return false; // Return false if any confirmed position does not match.
            }
        }
        return true; // Return true if all confirmed positions match.
    }

    // Method to calculate a score for a word.
    private int wordScore(String word) {
        int score = 0;
        for (char c : word.toCharArray()) {
            if (!triedLetters.contains(c)) {
                score += getLetterFrequencyScore(c); // Increase score for untried letters.
            }
            // Increase score if the letter is a confirmed letter.
            if (confirmedLetters.contains(c)) {
                score += 5; // Arbitrary higher score for confirmed letters
            }
        }
        return score;
    }

    // Method to get a frequency score for a letter.
    private int getLetterFrequencyScore(char c) {
        // This method returns a constant score for simplicity.
        // In a more advanced version, it could return different scores based on letter frequency.
        return 1;
    }

    // Method to update the confirmed positions of letters.
    public void updateConfirmedPositions(char[] newPositions) {
        System.arraycopy(newPositions, 0, this.confirmedPositions, 0, newPositions.length);
    }

    // Method to update the set of confirmed letters.
    public void updateConfirmedLetters(Set<Character> newConfirmedLetters) {
        this.confirmedLetters.addAll(newConfirmedLetters);
    }

    // Method to update the list of possible words.
    public void updatePossibleWords(List<String> updatedList) {
        this.possibleWords = updatedList;
    }
}
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        // Load the list of words from a file.
        WordListLoader wordListLoader = new WordListLoader();
        wordListLoader.loadWordsFromFile("src/words.txt");

        // Retrieve the loaded word list and initialize the Wordle solver with it.
        List<String> words = wordListLoader.getWordList();
        WordleSolver wordleSolver = new WordleSolver(words);
        // Create an instance of GuessEvaluator to refine guesses based on feedback.
        GuessEvaluator guessEvaluator = new GuessEvaluator();
        // Scanner to read user input from the console.
        Scanner scanner = new Scanner(System.in);

        // Loop to keep asking for guesses until the puzzle is solved.
        while (true) {
            // Get the next guess from the Wordle solver.
            String guess = wordleSolver.getNextGuess();
            System.out.println("Try this guess: " + guess);

            // Ask the user to enter feedback for the guess.
            System.out.print("Enter feedback (G for green, Y for yellow, X for gray, e.g., GGXXX): ");
            String feedback = scanner.nextLine().trim().toUpperCase();

            // Exit the loop if the user enters no feedback or types "EXIT".
            if (feedback.isEmpty() || feedback.equals("EXIT")) {
                break;
            }

            // Refine the word list based on the feedback and update the Wordle solver.
            words = guessEvaluator.refineWordListBasedOnFeedback(words, guess, feedback);
            wordleSolver.updatePossibleWords(words);
            // Update the solver's state with new confirmed letters and positions.
            updateWordleSolverState(wordleSolver, guess, feedback);

            // Print the number of possible words remaining.
            System.out.println("Possible words left: " + words.size());
            // If only a few words are left, print them out.
            if (words.size() <= 10) {
                words.forEach(System.out::println);
            }

            // If only one word is left, announce it as the solution and exit.
            if (words.size() == 1) {
                System.out.println("Solved! The word is: " + words.get(0));
                break;
            } else if (words.isEmpty()) {
                // If no words are left, notify the user and exit.
                System.out.println("No possible words left. Please check the inputs.");
                break;
            }
        }

        // Close the scanner to prevent resource leaks.
        scanner.close();
    }

    // Helper method to update the state of the Wordle solver based on feedback.
    private static void updateWordleSolverState(WordleSolver solver, String guess, String feedback) {
        Set<Character> newConfirmedLetters = new HashSet<>();

        char[] newConfirmedPositions = new char[5];
        // Iterate over the feedback characters to update confirmed letters and positions.
        for (int i = 0; i < feedback.length(); i++) {
            if (feedback.charAt(i) == GuessEvaluator.CORRECT) {
                // If the feedback is 'CORRECT', add the letter to confirmed letters and positions.
                newConfirmedLetters.add(guess.charAt(i));
                newConfirmedPositions[i] = guess.charAt(i);
            } else {
                // If the feedback is not 'CORRECT', keep the position unknown ('_').
                newConfirmedPositions[i] = '_';
            }
        }

        // Update the solver with the new sets of confirmed letters and positions.
        solver.updateConfirmedLetters(newConfirmedLetters);
        solver.updateConfirmedPositions(newConfirmedPositions);
    }
}

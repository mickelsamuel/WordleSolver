import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordListLoader {
    private List<String> wordList; // List to store words loaded from a file.

    // Constructor to initialize the word list.
    public WordListLoader() {
        wordList = new ArrayList<>();
    }

    // Method to load words from a file.
    public void loadWordsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // BufferedReader reads text from a character-input stream.
            String line;
            // Read the file line by line.
            while ((line = br.readLine()) != null) {
                // Add each line to the word list, trimming whitespace from the line.
                wordList.add(line.trim());
            }
        } catch (IOException e) {
            // Print an error message if there's an issue reading the file.
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to get the loaded word list.
    public List<String> getWordList() {
        return wordList;
    }
}
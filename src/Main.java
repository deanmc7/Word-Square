import java.util.*;

public class Main {
    /**
     * Builds a Trie based on vector of words passed in
     *
     * @param words - Words to base Trie on
     * @return
     * Root Trie Node
     */
    private static TrieNode BuildTrie(final Vector<String> words) {
        TrieNode root = new TrieNode();

        for (String word : words) {
            TrieNode current = root;

            // Loop through each character of word and
            // build a node for each character
            for(int i = 0; i < word.length(); i++) {
                char letter = word.charAt(i);
                TrieNode node = current.getChildren().get(letter);
                if (node == null) {
                    node = new TrieNode();
                    current.getChildren().put(letter, node);
                }
                current = node;
            }

            current.setEndOfWord(true);
        }

        return root;
    }

    public static void main(String[] args) {

        // Check for valid arguments
        if(args == null){
            System.out.println("No input");
            return;
        }

        if(args[0] == null || args[1] == null) {
            System.out.println("Wrong input");
            return;
        }

        // Collect arguments
        int wordLength = Integer.parseInt(args[0]);
        String letters = args[1];

        // Create Letter Store, stores a count of all letters allowed
        LettersStore lettersStore = new LettersStore();
        lettersStore.setLetterCount(letters.toCharArray());

        // Create Word Dictionary, add all words of correct length.
        WordDictionary wordDictionary = new WordDictionary(wordLength);
        wordDictionary.AddWords();


        WordSquare wordSquare = new WordSquare();

        // Loop through all words and grab words that only used the letters provided
        Vector<String> validWords = new Vector<>();
        var wordList = wordDictionary.getWordDictionary();
        for (String word : wordList) {
            if (wordSquare.CheckValidWord(word, wordDictionary.getWordLength(), lettersStore)) {
                validWords.add(word);
            }
        }

        // Build a Trie so we can iterate through it later.
        TrieNode rootNode = BuildTrie(validWords);

        var correctSquare = wordSquare.BuildWordSquare(rootNode, validWords, lettersStore);

        // Output answer;
        if(correctSquare != null) {
            for (String word : correctSquare)
            {
                System.out.println(word);
            }
        }
        else {
            System.out.println("No Answer");
        }
    }
}
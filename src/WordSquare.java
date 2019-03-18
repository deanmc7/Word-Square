import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

class WordSquare {
    /**
     * This function will loop through each letter of a word checking the letter store
     * to see if the word is valid with the provided letters
     *
     * @param word - Word to check
     * @param wordLength - Word Length
     * @param lettersStore - letter store to reference
     * @return true if word can be used, false if not
     */
    boolean CheckValidWord(final String word, final int wordLength, final LettersStore lettersStore){

        LettersStore lettersUsedStore = new LettersStore();
        boolean diagonalUsed = false;

        for (int i = 0; i < wordLength; i++){
            char letter = word.charAt(i);

            int letterStoreFreq = lettersStore.GetLetterFreq(letter);
            int letterUsedFreq = lettersUsedStore.GetLetterFreq(letter);

            int spaceLeft =  letterStoreFreq - letterUsedFreq;

            // Each letter is used at least twice, except diagonal letters
            if(spaceLeft > 1){
                lettersUsedStore.IncreaseLetterFreq(letter, 2);
            }
            // If 1 space left and a diagonal slot has not been used then increase letter usage
            else if(spaceLeft == 1 && !diagonalUsed){
                lettersUsedStore.IncreaseLetterFreq(letter, 1);
                diagonalUsed = true;
            }
            else{ // Invalid letter
                return false;
            }
        }

        return true;
    }

    /**
     * Searches through trie to get all children of a prefix.
     * e.g. : team takes the e and potentially return:
     * ends
     * errm
     * even
     * etc.
     * @param node - Current node we are on
     * @param word - starts as prefix and builds in a series of words that's added to children
     * @param children - strings get added to this list which are potential children of a beginning word
     */
    private void GetPotentialChildren(final TrieNode node, final String word, final List<String> children) {
        if (node.isEndOfWord()) {
            children.add(word);
            return;
        }

        // Keep looping through nodes until end of word is found
        for(Map.Entry<Character, TrieNode> child : node.getChildren().entrySet()) {
            if (child != null){
                GetPotentialChildren(child.getValue(), word + child.getKey(), children);
            }
        }
    }

    /**
     * Gets the next prefix to be used in getting child nodes
     * e.g. a square with deer returns e
     * a square with deer, eons returns en
     * a square with deer, eons, ends returns rss
     * @param square - current square of words
     * @param index - index when looping through square (square size)
     * @return
     * Returns a string representing a prefix of the letters from the given square
     */
    private String getPrefix(final List<String> square, final int index) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < index; i++) {
            stringBuilder.append(square.get(i).charAt(index));
        }
        return stringBuilder.toString();
    }

    /**
     * Main function of building the wordsquare.
     * Loops through Trie node while validating the letters used, calls itself when a potential word is found
     *
     * @param root - root Trie node
     * @param wordLength - Word length
     * @param square - square of words currently being used
     * @param correctSquare - the correct square that will contain the correct sequence of words
     * @param lettersStore - tracks the letters used to ensure correct amount of letters are used.
     */
    private void BuildSquare(final TrieNode root, final int wordLength, final List<String> square, final List<String> correctSquare, final LettersStore lettersStore) {

        if(square.size() == wordLength) {
            correctSquare.addAll(square);
            return;
        }

        // Get prefix for next word
        // e.g. deer, eons would find a word beginning with en
        String prefix = getPrefix(square, square.size());
        TrieNode node = root.Search(prefix);
        if(node == null) {
            return;
        }

        // Get a list of all children with that prefix
        // e.g. en returns ends, ents, etc.
        List<String> children = new ArrayList<>();
        GetPotentialChildren(node, prefix, children);

        // Loop through children and ensure that the letters used fit in the store
        for (String child : children) {
            square.add(child);
            lettersStore.DecreaseLetterFreqFromWord(child);

            if(lettersStore.IsWordValid(child)) {
                BuildSquare(root, wordLength, square, correctSquare, lettersStore);
            }

            square.remove(square.size() - 1);
            lettersStore.IncreaseLetterFreqFromWord(child);
        }
    }

    /**
     *
     * @param rootNode - Root node of Trie
     * @param words - Vector of words to iterate through
     * @param lettersStore - stores all letters available
     * @return
     * A List of strings that build a valid wordsquare
     */
    List<String> BuildWordSquare(final TrieNode rootNode, final Vector<String> words, LettersStore lettersStore) {

        List<String> correctSquare = new ArrayList<>();

        for (String word : words) {
            List<String> square = new ArrayList<>();
            square.add(word);
            lettersStore.DecreaseLetterFreqFromWord(word);

            this.BuildSquare(rootNode, word.length(),square, correctSquare, lettersStore);

            lettersStore.IncreaseLetterFreqFromWord(word);
        }

        return correctSquare;
    }
}

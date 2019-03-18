import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> children;
    private boolean endOfWord;

    TrieNode() {
        children = new HashMap<>();
        endOfWord = false;
    }

    public final Map<Character, TrieNode> getChildren() {
        return children;
    }

    final boolean isEndOfWord() {
        return endOfWord;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    void setEndOfWord( final boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    /**
     * Searches through Trie using a prefix of characters to find a node
     *
     * @param prefix - prefix of characters used to search Trie
     * @return
     * A Trie Node from the end of the prefix
     */
    TrieNode Search(final String prefix) {
        TrieNode current = this;

        for(char c : prefix.toCharArray()){
            TrieNode node = current.getChildren().get(c);
            if(node == null) {
                return null;
            }
            current = node;
        }

        return current;
    }
}

package autocomplete.implementation;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    private Map<Character, TrieNode> children;
    private boolean endOfWord;
    private int count;

    TrieNode() {
        this.children = new HashMap<>();
        this.endOfWord = false;
        this.count = 0;
    }

    Map<Character, TrieNode> getChildren() {
        return children;
    }

    boolean isEndOfWord() {
        return endOfWord;
    }

    void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    int getCount() {
        return count;
    }

    void setCount(int count) {
        this.count = count;
    }

}

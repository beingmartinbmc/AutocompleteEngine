package autocomplete.implementation;

import java.util.List;

public interface Trie {
    void insert(String word);
    boolean search(String word);
    List<String> startsWith(String word);
}

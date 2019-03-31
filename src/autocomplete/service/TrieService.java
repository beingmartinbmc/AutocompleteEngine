package autocomplete.service;


import autocomplete.implementation.Trie;

import java.util.List;

public interface TrieService {
    void buildTrie(String[] words);
    List<String> prefixSearch(String word);
    void setTrie(Trie trie);
    Trie getTrie();
}

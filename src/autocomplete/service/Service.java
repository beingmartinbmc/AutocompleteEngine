package autocomplete.service;

import autocomplete.implementation.Dictionary;
import autocomplete.implementation.Trie;

import java.util.Arrays;
import java.util.List;

public class Service implements TrieService {

    private Trie trie;
    private Dictionary dictionary = new Dictionary();
    private int sizeOfWords;

    @Override
    public void buildTrie(String[] words) {
        sizeOfWords = words.length;
        Arrays.stream(words).forEach(dictionary::insert);
    }

    @Override
    public List<String> prefixSearch(String word) {
        return dictionary.startsWith(word);
    }

    @Override
    public void setTrie(Trie trie) {
        this.trie = trie;
    }

    @Override
    public Trie getTrie() {
        return this.trie;
    }

    public int getSizeOfWords(){
        return sizeOfWords;
    }
}

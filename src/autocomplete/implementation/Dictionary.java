package autocomplete.implementation;

import autocomplete.Author;

import java.util.ArrayList;
import java.util.List;

@Author(name = "Ankit Sharma")
public class Dictionary implements Trie {
    private final TrieNode root;

    public Dictionary(){
        root = new TrieNode();
    }

    @Override
    public void insert(String word) {
        TrieNode current = root;
        for(char c : word.toCharArray())
            current = current.getChildren().computeIfAbsent(c, e->new TrieNode());
        current.setEndOfWord(true);
    }

    @Override
    public boolean search(String word) {
        TrieNode current = root;
        for(char c : word.toCharArray()){
            TrieNode node = current.getChildren().get(c);
            if(node == null) return false;
            current = node;
        }
        return current.isEndOfWord();
    }

    private List<String> traverse(TrieNode node, String word, List<String> output){
        if(node.isEndOfWord()) output.add(word);
        node.getChildren().forEach((k,v)->traverse(v, word+k, output));
        return output;
    }

    @Override
    public List<String> startsWith(String word) {
        StringBuilder result = new StringBuilder();
        TrieNode current = root;
        for(char c : word.toCharArray()){
            TrieNode node = current.getChildren().get(c);
            if(node == null) break;
            result.append(c);
            current = node;
        }
        return traverse(current, result.toString(), new ArrayList<>());
    }
}

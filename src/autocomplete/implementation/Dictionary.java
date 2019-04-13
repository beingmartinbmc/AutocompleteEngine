package autocomplete.implementation;

import autocomplete.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Author(name = "Ankit Sharma")
public class Dictionary implements Trie {
    private final TrieNode root;

    public Dictionary(){
        root = new TrieNode();
    }

    private TrieNode getRoot(){
        return root;
    }

    @Override
    public void insert(String word){
        TrieNode current = getRoot();
        for(char c : word.toCharArray()){
            TrieNode node = current.getChildren().get(c);
            if(node == null){
                node = new TrieNode();
                current.getChildren().put(c, node);
            }
            current = node;
        }
        current.setEndOfWord(true);
        current.setCount(current.getCount() + 1);
    }

    @Override
    public boolean search(String word) {
        TrieNode current = getRoot();
        for(char c : word.toCharArray()){
            TrieNode node = current.getChildren().get(c);
            if(node == null)
                return false;
            current = node;
        }
        return current.isEndOfWord();
    }

    private List<String> traverse(TrieNode node, String word, List<String> output){
        if(node.isEndOfWord())
            output.add(word);
        for(Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet())
            traverse(entry.getValue(), word + entry.getKey(), output);
        return output;
    }


    @Override
    public List<String> startsWith(String word) {
        TrieNode current = getRoot();
        StringBuilder b = new StringBuilder();
        for (char c : word.toCharArray()) {
            TrieNode node = current.getChildren().get(c);
            if (node == null)
                break;
            b.append(c);
            current = current.getChildren().get(c);
        }
        return traverse(current, b.toString(), new ArrayList<>());
    }

}

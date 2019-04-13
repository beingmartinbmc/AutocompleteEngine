package autocomplete.gui;

import autocomplete.service.TrieService;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;

public class InputField {
    private final int MIN_CHAR_INPUT = 1;
    private TrieService service;
    private JTextField textField;
    private JList<String> list;
    private boolean nextConsumable;
    private int charCount;

    InputField(){
        charCount = 0;
        nextConsumable = false;
    }

    private boolean isNextConsumable() {
        return nextConsumable;
    }

    void setService(TrieService service) {
        this.service = service;
    }

    void setTextField(JTextField textField) {
        this.textField = textField;
    }

    void setList(JList<String> list) {
        this.list = list;
    }

    private boolean isSorted(List<String> a){
        for(int i=0; i < a.size() - 1; i++)
            if(a.get(i).compareTo(a.get(i + 1)) > 0)
                return false;
        return true;
    }

     void setActions(){
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateList();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(isNextConsumable())
                    e.consume();
                nextConsumable = false;
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                stuffOnKeyPress(e);
            }
        });

    }

    private void updateListUtil(){
        String prefix = textField.getText().substring(0, charCount);
        List<String> suggestedWords = service.prefixSearch(prefix.toLowerCase());
        if(!isSorted(suggestedWords))
            Collections.sort(suggestedWords);
        DefaultListModel<String> model = new DefaultListModel<>();
        list.setModel(model);
        suggestedWords.forEach(model::addElement);
        int fieldLength = textField.getDocument().getLength();
        if (fieldLength > MIN_CHAR_INPUT) {
            String suggestedFirstWord = suggestedWords.get(0);
            textField.setText(suggestedFirstWord);
            textField.setCaretPosition(charCount);
            textField.select(charCount, suggestedFirstWord.length());
        }
    }

    private void updateList(){
        final Runnable updater = this::updateListUtil;
        new Thread(updater).start();
    }

    private void stuffOnKeyPress(KeyEvent e){
        boolean isAlphanumeric = Character.isLetterOrDigit(e.getKeyChar());
        boolean isEnterKey = e.getKeyChar() == KeyEvent.VK_ENTER;
        boolean isDeleteKey = e.getKeyChar() == KeyEvent.VK_BACK_SPACE
                                ||
                              e.getKeyChar() == KeyEvent.VK_DELETE;
        boolean isSpaceKey = e.getKeyChar() == KeyEvent.VK_SPACE;
        if(isAlphanumeric || isEnterKey || isDeleteKey){
            if(isEnterKey){
                charCount = textField.getText().length();
                textField.requestFocus();
            }
            else if(isDeleteKey || isSpaceKey){
                if(charCount > 0)
                    charCount -= 1;
            }
            else{
                if(textField.getDocument().getLength() > MIN_CHAR_INPUT)
                    try{
                        if(textField.getText().charAt(charCount) == e.getKeyChar()){
                            nextConsumable = true;
                            charCount ++;
                            updateList();
                            return;
                        }
                    }
                    catch (StringIndexOutOfBoundsException p){
                        p.getMessage();
                    }
                    catch (Exception h){
                        h.getStackTrace();
                    }
                    charCount += 1;
            }
        }
    }
}

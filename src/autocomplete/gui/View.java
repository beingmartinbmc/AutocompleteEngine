package autocomplete.gui;

import autocomplete.service.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class View extends JFrame {
    private GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private JTextField textField;
    private JList<String> list;
    private TrieService service;
    private JPanel panel;

    public View(String title, TrieService service){
        g.getAllFonts();
        setTitle(title);
        this.service = service;
        setLayout(null);
        setSize(550, 550);
        panel = new JPanel();
        panel.setSize(550, 550);
        panel.setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponents();
        addListeners();
        panel.setBackground(new Color(47,79,79));
        add(panel);
        setResizable(false);
    }

    private void addComponents() {
        addTextField();
        addListField();
        addScrollPane();
    }

    private void addListeners() {
        InputField listener = new InputField();
        listener.setService(service);
        listener.setTextField(textField);
        listener.setList(list);
        listener.setActions();
    }

    private void addTextField() {
        Font font = new Font("Times New Roman", Font.PLAIN, 24);
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel label = new JLabel("Enter");
        label.setForeground(new Color(245,245,245));
        label.setFont(font);
        label.setBounds(30, 20, 150, 40);
        textField.setBounds(120, 20, 360, 40);
        textField.setBackground(new Color(240,255,240));
        panel.add(textField);
        panel.add(label);
    }

    private void addListField() {
        list = new JList<>();
        list.setFont(new Font("Trebuchet MS", Font.BOLD, 15));
        panel.add(list, BorderLayout.CENTER);

    }
    private void addScrollPane() {
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(80, 90, 400, 400);
        scrollPane.setBackground(new Color(240,255,255));
        panel.add(scrollPane, BorderLayout.CENTER);
    }
    public static String getPath(){
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        int v = chooser.showOpenDialog(new JFrame());
        if(v == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            return file.getPath();
        }
        return "";
    }
}

package ru.croc.imageTesting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Window extends JFrame {
    private final JLabel label1 = new JLabel("Введите имя пользователя: ");
    private final JLabel label2 = new JLabel("Введите кол-во тестов: ");
    private User user;
    private JPanel menu = new JPanel(), pannel = new JPanel();
    private JLabel picLabel = new JLabel();
    private final JButton startButton = new JButton("начать");
    private TestingWords words;
    private Word currentWord;
    JTextField txt1 = new JTextField("Введите имя пользователя: ");
    JTextField txt2 = new JTextField("Введите кол-во тестов: ");
    private JButton option1 = new JButton(), option2 = new JButton(), option3 = new JButton();


    public Window(String src) {
        super("Для Светы");
        user = new User(src);
        mainWindow();
    }

    private void mainWindow() {
        BoxLayout layout = new BoxLayout(menu, BoxLayout.PAGE_AXIS);
        menu.setLayout(layout);
        txt1.setText("");
        txt1.setPreferredSize(new Dimension(100, 100));
        txt2.setText("");
        txt2.setPreferredSize(new Dimension(100, 100));

        menu.setVisible(true);
        pannel.setVisible(false);
        setResizable(false);
        startButton.setPreferredSize(new Dimension(100, 50));
        menu.add(startButton);
        menu.add(label1);
        menu.add(txt1);
        menu.add(label2);
        menu.add(txt2);
        getContentPane().add(menu);
        setSize(500, 600);
        setLocationRelativeTo(null);
        startButton.addActionListener(e -> {
            user.setUsername(txt1.getText());
            try {
                user.findUserWords();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            List<Word> userWords = user.getWords();
            words = new TestingWords(userWords, Integer.parseInt(txt2.getText()));
            try {
                pannelWindow();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void pannelWindow() throws IOException {
        menu.setVisible(false);
        pannel.setVisible(true);
        setButtons();
        option1.setPreferredSize(new Dimension(100, 50));
        option2.setPreferredSize(new Dimension(100, 50));
        option3.setPreferredSize(new Dimension(100, 50));
        pannel.add(picLabel);
        pannel.add(option1);
        pannel.add(option2);
        pannel.add(option3);
        getContentPane().add(pannel);
        option1.addActionListener(e -> {
            if (!option1.getText().equals(currentWord.getEnglishWord())) {
                words.addCurrentWordInQueue();
            }
            try {
                setButtons();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        option2.addActionListener(e -> {
            if (!option2.getText().equals(currentWord.getEnglishWord())) {
                words.addCurrentWordInQueue();
            }
            try {
                setButtons();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        option3.addActionListener(e -> {
            if (!option3.getText().equals(currentWord.getEnglishWord())) {
                words.addCurrentWordInQueue();
            }
            try {
                setButtons();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void loadImage(String src) throws IOException {
        BufferedImage master = ImageIO.read(new File(src));
        Image scaled = master.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(scaled));
    }

    private void setButtons() throws IOException {
        currentWord = words.next();
        if (currentWord == null) {
            mainWindow();
            return;
        }
        List<String> curr = new ArrayList<>();
        curr.add(currentWord.getEnglishWord());
        Word appendWord = words.getRandomWord();
        while (curr.size() < 3) {
            while (curr.contains(appendWord.getEnglishWord())) {
                appendWord = words.getRandomWord();
            }
            curr.add(appendWord.getEnglishWord());
        }
        Collections.shuffle(curr);
        loadImage(currentWord.getSrc());
        option1.setText(curr.get(0));
        option2.setText(curr.get(1));
        option3.setText(curr.get(2));
    }
}

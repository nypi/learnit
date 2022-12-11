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
import java.util.logging.Logger;

public class Window extends JFrame {
    private int count = 0;
    private final User user;
    private TestingWords words;
    private Word currentWord;
    private final List<JButton> options = new ArrayList<>();
    private final Logger log = Logger.getLogger(Window.class.getName());
    private final List<Word> learnedWords = new ArrayList<>();

    private final JPanel menu = new JPanel(), panel = new JPanel(), end = new JPanel();
    private final JLabel picLabel = new JLabel(), answer = new JLabel("");
    private final JTextField txt1 = new JTextField(), txt2 = new JTextField();
    private final JButton startButton = new JButton("начать");


    public Window(User user) {
        super("Тесты по картинкам");
        this.user = user;
        buttonSettings();
        initMenu();
        mainWindow();
    }

    private void buttonSettings() {
        Font font = new Font("TimesRoman", Font.BOLD, 30);
        answer.setFont(font);
        answer.setPreferredSize(new Dimension(500, 50));
        answer.setHorizontalAlignment(0);
        txt1.setText("");
        txt1.setPreferredSize(new Dimension(400, 50));
        txt1.setFont(font);
        txt2.setText("");
        txt2.setPreferredSize(new Dimension(400, 50));
        txt2.setFont(font);
        startButton.setFont(new Font("TimesRoman", Font.BOLD, 25));
        JButton btn;
        for (int i = 0; i < 3; i++) {
            btn = new JButton();
            btn.setPreferredSize(new Dimension(150, 50));
            btn.setFont(font);
            options.add(btn);
        }
    }

    private void initMenu() {
        JLabel label1 = new JLabel("Введите имя пользователя: ");
        JLabel label2 = new JLabel("Введите кол-во тестов: ");
        label1.setFont(new Font("TimesRoman", Font.BOLD, 30));
        label2.setFont(new Font("TimesRoman", Font.BOLD, 30));
        setResizable(false);

        menu.add(label1);
        menu.add(txt1);
        menu.add(label2);
        menu.add(txt2);
        menu.add(startButton, BorderLayout.SOUTH);
        getContentPane().add(menu);
        setSize(500, 550);
        setLocationRelativeTo(null);
    }

    private void mainWindow() {
        menu.setVisible(true);
        panel.setVisible(false);
        startButton.addActionListener(e -> {
            user.setUsername(txt1.getText());

            try {
                user.findUserWords();
                List<Word> userWords = user.getWords();
                words = new TestingWords(userWords, Integer.parseInt(txt2.getText()));
                testWindow();
            } catch (IOException ex) {
                log.info("файл " + ex.getMessage() + " не найден");
            } catch (UserDoesntExistException ex) {
                log.info(ex.getMessage());
            } catch (NumberFormatException ex) {
                log.info("Должно быть введено целочисленное значение");
            } catch (InputNumberException ex) {
                log.info(ex.getMessage());
            }
        });
    }

    private void EndWindow() {
        menu.setVisible(false);
        panel.setVisible(false);
        end.setVisible(true);
        JLabel txt1 = new JLabel("Количество попыток: " + count);
        txt1.setFont(new Font("TimesRoman", Font.BOLD, 25));
        end.add(txt1);
        getContentPane().add(end);

    }

    private void initTestPanel() {
        panel.add(picLabel);
        for (JButton btn : options) {
            panel.add(btn);
        }
        panel.add(answer);
        getContentPane().add(panel);
    }

    private void testWindow() throws IOException {
        initTestPanel();
        menu.setVisible(false);
        panel.setVisible(true);
        setButtons();
        choiceButtonsAction();
    }

    private void choiceButtonsAction() {
        for (JButton btn : options) {
            btn.addActionListener(e -> {
                if (!btn.getText().equals(currentWord.getEnglishWord())) {
                    words.addCurrentWordInQueue();
                    answer.setBackground(new Color(220, 100, 100));
                    answer.setOpaque(true);
                    answer.setText("Неправильный ответ");
                } else {
                    answer.setBackground(new Color(100, 220, 100));
                    answer.setOpaque(true);
                    answer.setText("Правильный ответ!");
                    addLearnedWord(currentWord);
                }
                try {
                    setButtons();
                } catch (IOException ex) {
                    log.info("файл " + ex.getMessage() + " не найден");
                }
            });
        }
    }

    public void loadImage(String src) throws IOException {
        BufferedImage master = ImageIO.read(new File(src));
        Image scaled = master.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(scaled));
    }

    private void setButtons() throws IOException {
        currentWord = words.next();

        if (currentWord == null) {
            EndWindow();
            return;
        }
        count++;
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
        for (int i = 0; i < 3; i++) {
            options.get(i).setText(curr.get(i));
        }
    }

    private void addLearnedWord(Word word) {
        word.increaseKnowledgeDegree();
        learnedWords.add(word);
    }

    public List<Word> getLearnedWords() {
        return this.learnedWords;
    }
}

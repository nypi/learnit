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

/**
 * Класс окна приложения
 */
public class Window extends JFrame {
    private int count = 0; // кол-во неправильных попыток пользователя
    private final User user; // пользователь
    private TestingWords words; // слова пользователя
    private Word currentWord; // текущее слово в тесте

    private final List<JButton> options = new ArrayList<>(); // кнопки выбора слова
    private final Logger log = Logger.getLogger(Window.class.getName());
    private final List<Word> learnedWords = new ArrayList<>();
    private final JPanel menu = new JPanel(), panel = new JPanel(), end = new JPanel();
    private final JLabel picLabel = new JLabel(), answer = new JLabel("");
    private final JTextField txt1 = new JTextField(), txt2 = new JTextField();
    private final JButton startButton = new JButton("начать");


    /**
     * @param user информация о текущем пользователе
     */
    public Window(User user) {
        super("Тесты по картинкам");
        this.user = user;
        buttonSettings();
        initMenu();
        mainWindow();
    }

    public List<Word> getLearnedWords() {
        return this.learnedWords;
    }

    /**
     * настройки кнопок
     */
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

    /**
     * инициализация окна меню приложения
     */
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

    /**
     * запуск окна меню приложения
     */
    private void mainWindow() {
        menu.setVisible(true);
        panel.setVisible(false);
        startButton.addActionListener(e -> {
            // ввод имени пользователя, полученного из приложения
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
            } catch (TestsNumberException ex) {
                log.info(ex.getMessage());
            }
        });
    }

    /**
     * запуск конечного окна приложения
     */
    private void EndWindow() {
        menu.setVisible(false);
        panel.setVisible(false);
        end.setVisible(true);
        JLabel txt1 = new JLabel("Количество неудачных попыток: " + count);
        txt1.setFont(new Font("TimesRoman", Font.BOLD, 25));
        end.add(txt1);
        getContentPane().add(end);
        printLearnedWords();
    }

    /**
     * Инициализация тестового окна приложения
     */
    private void initTestWindow() {
        panel.add(picLabel);
        for (JButton btn : options) {
            panel.add(btn);
        }
        panel.add(answer);
        getContentPane().add(panel);
    }

    /**
     * Запуск тестового окна приложения
     *
     * @throws IOException исключение, если файл с данными не был найден
     */
    private void testWindow() throws IOException {
        initTestWindow();
        menu.setVisible(false);
        panel.setVisible(true);
        setButtons();
        choiceButtonsAction();
    }

    /**
     * Принятие сигналов с кнопок
     */
    private void choiceButtonsAction() {
        for (JButton btn : options) {
            btn.addActionListener(e -> {
                // сравниваем текущее тестовое слово с выбором пользователя
                if (!btn.getText().equals(currentWord.getEnglishWord())) {
                    words.addCurrentWordInQueue();
                    answer.setBackground(new Color(220, 100, 100));
                    answer.setOpaque(true);
                    answer.setText("Неправильный ответ");
                    count++;
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

    /**
     * Загрузка картинки из файла по ссылке
     *
     * @param src ссылка на картинку в файловой системе
     * @throws IOException исключение, если файл не был найден
     */
    private void loadImage(String src) throws IOException {
        BufferedImage master = ImageIO.read(new File(src));
        Image scaled = master.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(scaled));
    }

    /**
     * Установление значений на кнопках выбора
     *
     * @throws IOException исключение, если файл не был найден
     */
    private void setButtons() throws IOException {
        currentWord = words.next();

        if (currentWord == null) {
            EndWindow();
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
        for (int i = 0; i < 3; i++) {
            options.get(i).setText(curr.get(i));
        }
    }

    /**
     * Добавление слова в список с изученными словами
     *
     * @param word новое изученное слово
     */
    private void addLearnedWord(Word word) {
        // повышаем коэфф изученности в слова
        word.increaseKnowledgeDegree();
        learnedWords.add(word);
    }

    /**
     * вывод на экран всех изученных слов пользователя
     */
    private void printLearnedWords() {
        for (Word word : getLearnedWords()) {
            System.out.println(word);
        }
    }
}

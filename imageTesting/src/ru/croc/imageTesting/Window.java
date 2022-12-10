package ru.croc.imageTesting;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Window extends JFrame {
    JPanel menue, pannel;
    JLabel picLabel = new JLabel();
    private final JButton startButton = new JButton("начать");
    private JButton option1, option2, option3;


    public Window() {
        super("Для Светы");
        mainWindow();
        startButton.addActionListener(e -> {
            try {
                pannelWindow();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void mainWindow(){
        menue = new JPanel();
        setResizable(false);
        startButton.setPreferredSize(new Dimension(100, 50));
        menue.add(startButton);
        //menue.add(text);
        getContentPane().add(menue);
        setSize(500, 600);
        setLocationRelativeTo(null);
    }

    private void pannelWindow() throws FileNotFoundException {
        JPanel pannel = new JPanel();
        menue.setVisible(false);
        pannel.setVisible(true);
        //startButton.setText("текст");
        loadImage("resources/cat.jpeg");
        option1 = new JButton("кнопка1");
        option1.setLocation(100, 200);
        option1.setPreferredSize(new Dimension(100, 50));
        option2 = new JButton("кнопка2");
        option2.setPreferredSize(new Dimension(100, 50));
        option3 = new JButton("кнопка3");
        option3.setPreferredSize(new Dimension(100, 50));
        pannel.add(picLabel);
        pannel.add(option1);
        pannel.add(option2);
        pannel.add(option3);
        getContentPane().add(pannel);
    }

    public void loadImage(String src) throws FileNotFoundException {
        picLabel.setIcon(new ImageIcon(src));
        if (new ImageIcon(src).getIconHeight() < 0) throw new FileNotFoundException(src);
    }
}

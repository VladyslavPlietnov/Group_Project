package org.example.FrontEnd;

import org.example.GameBody.GameBody;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FrontEnd implements ActionListener {
    private static final int HEIGHT_FIRST = 100;
    private static final int HEIGHT_REGULAR = 500;
    private static final int WIDTH = 400;
    private static final int LETTER_SIZE = 18;
    JFrame frame;
    JTextField textField;
    public static ArrayList<String> listOfCities = new ArrayList<>();
    int gamerCount = 0;
    int computerCount = 0;
    Font defaultFont = new Font("Arial", Font.PLAIN, LETTER_SIZE);

    public FrontEnd() {
        frame = new JFrame("Гра в мітса");
        frame.setSize(WIDTH, HEIGHT_FIRST);
        frame.setLayout(new BorderLayout(40, 30));
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Вітаємо в грі в міста, бажаємо успіху!");
        label.setFont(defaultFont);
        frame.add(label, BorderLayout.NORTH);
        JButton button = new JButton("Старт");
        button.addActionListener(this);
        button.setFont(defaultFont);
        frame.add(button, BorderLayout.SOUTH);
        JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();
        frame.add(panelEast, BorderLayout.EAST);
        frame.add(panelWest, BorderLayout.WEST);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (textField == null) {
            if (gamerCount == 0) {
                firstPage();
            } else {
                continuePage();
            }
        } else {
            String input = textField.getText().strip().toLowerCase();
            String city = GameBody.gameBody().getCity();
            if (!input.isBlank()) {
                if (input.equals("здаюсь")) {
                    losingPage();
                } else if (gamerCount != 0 && !String.valueOf(input.charAt(0)).equals(findLast(city))) {
                    brokenRules();
                } else {
                    if (listOfCities.contains(input)) {
                        usedCity();
                    } else if (!GameBody.gameBody().getAllCities().contains(input)) {
                        unknownCity();
                    } else {
                        String lastLetter = findLast(input);
                        GameBody.gameBody().setLastLetter(lastLetter);
                        gamerCount++;
                        listOfCities.add(input);
                        continuePage();
                    }
                }
            } else {
                noCityPage();
            }
        }
    }

    public void usedCity() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Вибачте, це місто вже було використано \n" + "Будь ласка, введіть інше місто");
        textField = new JTextField();
        JButton button = new JButton("Зробити хід");
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        button.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));
        button.setBackground(Color.RED);
        button.addActionListener(this);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void unknownCity() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Вибачте, я не знаю такого міста \n" + "Будь ласка, введіть інше місто");
        textField = new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setBackground(Color.RED);
        button.addActionListener(this);
        frame.add(label, BorderLayout.NORTH);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void losingPage() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Кінець");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Ви програли, сподіваюсь вам сподобалось!");
        label.setFont(defaultFont);
        frame.add(label, BorderLayout.CENTER);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void firstPage() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в міста");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Введіть перше місто!");
        textField = new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));
        button.setBackground(Color.GREEN);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void continuePage() {
        String city = GameBody.gameBody().makeMove();
        if (city.equals("здаюсь")) computerLost();
        city = capitalizeFirstLetter(city);
        computerCount++;
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в міста");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Комп'ютер: " + city + "; Рахунок (комп'ютер:гравець) --" + computerCount + ":" + gamerCount);
        textField = new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));  // Жирний шрифт для кнопки
        button.setBackground(Color.GREEN);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    public void computerLost() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Кінець");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Вітаю, ви виграли! Сподіваюсь вам сподобалось!");
        label.setFont(defaultFont);
        frame.add(label, BorderLayout.CENTER);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void noCityPage() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Ви не ввели місто!");
        textField = new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));
        button.setBackground(Color.RED);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void brokenRules() {
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        Image icon = Toolkit.getDefaultToolkit().getImage("icon.jpg");
        frame.setIconImage(icon);
        JLabel label = new JLabel("Ви порушили правила, місто повинно починатись з \n" +
                "останньої літери попереднього ходу");
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));
        button.setBackground(Color.RED);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(WIDTH, HEIGHT_REGULAR);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public String findLast(String city) {
        String lastLetter;
        if (city.charAt(city.length() - 1) == 'ь' || city.charAt(city.length() - 1) == 'и') {
            lastLetter = String.valueOf(city.charAt(city.length() - 2));
        } else {
            lastLetter = String.valueOf(city.charAt(city.length() - 1));
        }
        return lastLetter;
    }
}
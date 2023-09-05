package org.example.FrontEnd;
import org.example.GameBody.GameBody;
import org.example.Parser.CityParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;

public class FrontEnd implements ActionListener {
    JFrame frame;
    JTextField textField;
    public static ArrayList<String> listOfCities = new ArrayList<>();
    int gamerCount = 0;
    int computerCount = 0;
    Font defaultFont = new Font("Arial", Font.PLAIN, 18);  // Збільшений шрифт

    public FrontEnd() {
        frame = new JFrame("Гра в мітса");
        frame.setSize(400, 100);
        frame.setLayout(new BorderLayout(40, 30));

        JLabel label = new JLabel("Вітаємо в грі в міста, бажаємо успіху!");
        label.setFont(defaultFont);  // Застосування шрифту до label
        frame.add(label, BorderLayout.NORTH);

        textField = new JTextField();
        textField.setFont(defaultFont);  // Застосування шрифту до textField
        frame.add(textField, BorderLayout.CENTER);

        JButton button = new JButton("Старт");
        button.setFont(defaultFont);  // Застосування шрифту до кнопки
        button.addActionListener(this);
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
        if(textField==null){
            if(gamerCount==0){
                firstPage();
            }
            else{
                continuePage();
            }
        }
        else {
            String input = textField.getText().strip().toLowerCase();
            String normalizedInput = input.replace(" ", "");

            if (!input.isBlank()) {
                if (input.equals("здаюсь")) {
                    losingPage();
                } else if (gamerCount != 0 && !String.valueOf(input.charAt(0)).equals(findLast(GameBody.gameBody().getCity()))) {
                    brokenRules();
                } else {
                    if (listOfCities.contains(normalizedInput)) {
                        usedCity();
                    } else if (!GameBody.gameBody().getAllCities().contains(normalizedInput)) {
                        unknownCity();
                    } else {
                        String lastLetter = findLast(input);
                        GameBody.gameBody().setLastLetter(lastLetter);
                        gamerCount++;
                        listOfCities.add(normalizedInput);
                        continuePage();
                    }
                }
            } else {
                noCityPage();
            }
        }
    }







    public void usedCity(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        JLabel label = new JLabel("Вибачте, це місто вже було використано \n"+ "Будь ласка, введіть інше місто");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.GREEN);
        button.addActionListener(this);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(720, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void unknownCity(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        JLabel label = new JLabel("Вибачте, я не знаю такого міста \n"+ "Будь ласка, введіть інше місто");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.RED);
        button.addActionListener(this);
        frame.add(label, BorderLayout.NORTH);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(730, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void losingPage(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Кінець");
        JLabel label = new JLabel("Ви програли, сподіваюсь вам сподобалось!");
        frame.add(label, BorderLayout.CENTER);
        frame.setSize(500, 150);
        label.setFont(defaultFont);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void firstPage(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в міста");
        JLabel label = new JLabel("Введіть наступну назву міста");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.GREEN);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setSize(400, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void continuePage(){
        String city = GameBody.gameBody().makeMove();
        city = capitalizeFirstLetter(city);  // додатковий рядок коду
        computerCount++;
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в міста");
        JLabel label = new JLabel("Комп'ютер: "+ city + "; Рахунок (комп'ютер:гравець) --" + computerCount + ":" + gamerCount);
        textField = new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, 18));  // Жирний шрифт для кнопки
        button.setBackground(Color.GREEN);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setSize(500, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

    public void noCityPage(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        JLabel label = new JLabel("Ведіть місто!");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.GREEN);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(400, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void brokenRules(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        JLabel label = new JLabel("Ви порушили правила, місто повинно починатись з \n" +
                "останньої літери попереднього ходу");
        JButton button = new JButton("Зробити хід");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(Color.RED);
        button.addActionListener(this);
        label.setFont(defaultFont);
        textField.setFont(defaultFont);
        button.setFont(defaultFont);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(820, 150);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String findLast(String city){
        String lastLetter;
        if (city.charAt(city.length() - 1) == 'ь' || city.charAt(city.length() - 1) == 'и') {
            lastLetter = String.valueOf(city.charAt(city.length() - 2));
        } else {
            lastLetter = String.valueOf(city.charAt(city.length() - 1));
        }
        return lastLetter;
    }


    public static void main(String[] args) {
        Set<String> citySet =new CityParser().parseCities() ;

        GameBody.gameBody().setAllCities(citySet);
        new FrontEnd();
    }
}

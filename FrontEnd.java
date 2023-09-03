package org.example.FrontEnd;
import org.example.GameBody.GameBody;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class FrontEnd implements ActionListener {
      JFrame frame;
    JTextField textField;
    public static ArrayList<String> listOfCities = new ArrayList<>();
    int gamerCount =0;
    int computerCount = 0;
    public FrontEnd() {
       frame = new JFrame("Гра в мітса");
       frame.setSize(400,100);
        frame.setLayout(new BorderLayout(40, 30));
        JLabel label = new JLabel("Вітаємо в грі в міста, бажаємо успіху!");
       frame.add(label, BorderLayout.NORTH);
       JButton button = new JButton("Старт");
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
        else{
            String input = textField.getText().strip().toLowerCase();
            String city = GameBody.gameBody().getCity();
            if (!input.isBlank()) {
                if (input.equals("здаюсь")) {
                    losingPage();
                }
                else if(input.charAt(0) != city.charAt(city.length()-1)){
                    brokenRules();
                }
                else {

                    String lastLetter;
                    if (input.charAt(input.length() - 1) == 'ь' || input.charAt(input.length() - 1) == 'и') {
                        lastLetter = String.valueOf(input.charAt(input.length() - 2));
                    } else {
                        lastLetter = String.valueOf(input.charAt(input.length() - 1));
                    }
                    GameBody.gameBody().setLastLetter(lastLetter);
                    gamerCount ++;
                    listOfCities.add(input);
                    //GameBody.gameBody().setUsedList(listOfCities);
                    continuePage();
                }
            } else {
                noCityPage();
            }
        }
    }

    public void losingPage(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Кінець");
        JLabel label = new JLabel("Ви програли, сподіваюсь вам сподобалось!");
        frame.add(label, BorderLayout.CENTER);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void firstPage(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в міста");
        JLabel label = new JLabel("Введіть наступну назву міста");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        button.addActionListener(this);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void continuePage(){
        String city = GameBody.gameBody().makeMove();
        computerCount++;
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в міста");
        JLabel label = new JLabel("Комп'ютер: "+ city+"\n Введіть наступну назву міста");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        button.addActionListener(this);
        frame.add(button, BorderLayout.SOUTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void noCityPage(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        JLabel label = new JLabel("Ви не ввели місто!");
        textField= new JTextField();
        JButton button = new JButton("Зробити хід");
        button.addActionListener(this);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void brokenRules(){
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        frame = new JFrame("Гра в мітса");
        JLabel label = new JLabel("Ви порушили правила, місто повинно починатись з \n" +
                "останньої літери попереднього ходу");
        JButton button = new JButton("Зробити хід");
        button.addActionListener(this);
        frame.add(label, BorderLayout.NORTH);
        frame.add(textField, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
      new FrontEnd();

    }
}
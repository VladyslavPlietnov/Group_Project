package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class FrontEnd implements ActionListener {
      JFrame frame;
    JTextField textField;
    public FrontEnd() {
       frame = new JFrame("Гра в мітса");
       frame.setSize(400,400);
        frame.setLayout(new BorderLayout(40, 30));
        JLabel label = new JLabel("Вітаємо в грі в міста, перший хід за вами, введіть назву міста");
       frame.add(label, BorderLayout.NORTH);
       textField= new JTextField();
       frame.add(textField, BorderLayout.CENTER);
       JButton button = new JButton("Готово!");
       button.addActionListener(this);
       frame.add(button, BorderLayout.SOUTH);
       JPanel panelEast = new JPanel();
        JPanel panelWest = new JPanel();
        frame.add(panelEast, BorderLayout.EAST);
        frame.add(panelWest, BorderLayout.WEST);
       frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = textField.getText().strip().toLowerCase();
        if(!input.isBlank()){
           if(!input.equals("здаюсь")) {
                String lastLetter;
                if (input.charAt(input.length() - 1) == 'ь' || input.charAt(input.length() - 1) == 'и') {
                    lastLetter = String.valueOf(input.charAt(input.length() - 2));
                } else {
                    lastLetter = String.valueOf(input.charAt(input.length() - 1));
                }

                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                frame = new JFrame("Гра в міста");
                JLabel label = new JLabel("Введіть наступну назву міста");
               textField= new JTextField();
               JButton button = new JButton("Готово!");
               button.addActionListener(this);
               frame.add(button, BorderLayout.SOUTH);
               frame.add(textField, BorderLayout.CENTER);
                frame.add(label, BorderLayout.NORTH);
                frame.setSize(400, 400);
                frame.setVisible(true);
            }
           else{
               frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
               frame = new JFrame("Кінець");
               JLabel label = new JLabel("Ви програли, сподіваюсь вам сподобалось!");
               frame.add(label, BorderLayout.CENTER);
               frame.setSize(400, 400);
               frame.setVisible(true);
           }
        }
        else{
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            frame = new JFrame("Гра в мітса");
            JLabel label = new JLabel("Ви не ввели місто!");
            textField= new JTextField();
            JButton button = new JButton("Готово!");
            button.addActionListener(this);
            frame.add(label, BorderLayout.NORTH);
            frame.add(textField, BorderLayout.CENTER);
            frame.add(button, BorderLayout.SOUTH);
            frame.setSize(400, 400);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
      new FrontEnd();

    }
}
package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBandWindow extends JFrame implements ActionListener {
    JTextField nameTextField;
    JTextField genreTextField;
    JTextField countryTextField;
    JButton submitButton;

    public void newWindow(){
        //Panel for the Band Name
        this.nameTextField = new JTextField();
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Band Name");
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout());
        namePanel.add(nameLabel, BorderLayout.NORTH);
        namePanel.add(this.nameTextField, BorderLayout.CENTER);

        //Panel for Band Genre
        this.genreTextField = new JTextField();
        JLabel genreLabel = new JLabel();
        genreLabel.setText("Band Genre");
        JPanel genrePanel = new JPanel();
        genrePanel.setLayout(new BorderLayout());
        genrePanel.add(genreLabel, BorderLayout.NORTH);
        genrePanel.add(this.genreTextField, BorderLayout.CENTER);

        //Panel for Band Country
        this.countryTextField = new JTextField();
        JLabel countryLabel = new JLabel();
        countryLabel.setText("Band Country");
        JPanel countryPanel = new JPanel();
        countryPanel.setLayout(new BorderLayout());
        countryPanel.add(countryLabel, BorderLayout.NORTH);
        countryPanel.add(this.countryTextField, BorderLayout.CENTER);

        //Panel for Submit Button
        this.submitButton = new JButton();
        this.submitButton.setText("Submit");
        this.submitButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(new JPanel(), BorderLayout.NORTH);
        buttonPanel.add(new JPanel(), BorderLayout.EAST);
        buttonPanel.add(new JPanel(), BorderLayout.WEST);
        buttonPanel.add(new JPanel(), BorderLayout.SOUTH);
        buttonPanel.add(this.submitButton, BorderLayout.CENTER);

        //Set details for window
        this.setTitle("Add new Band");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.setSize(500, 150);
        this.setLayout(new GridLayout(2,3,5,5));
        this.setVisible(true);

        this.add(namePanel);
        this.add(genrePanel);
        this.add(countryPanel);
        this.add(new JPanel());
        this.add(new JPanel());
        this.add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.nameTextField.getText().isEmpty() || this.genreTextField.getText().isEmpty() || this.countryTextField.getText().isEmpty()){
            System.out.println("field is empty");
        }
        else {
            System.out.println(this.nameTextField.getText());
            System.out.println(this.genreTextField.getText());
            System.out.println(this.countryTextField.getText());
        }
    }
}

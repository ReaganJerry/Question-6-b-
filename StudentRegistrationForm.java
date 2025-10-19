/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.studentregistrationform;

/**
 *
 * @author Admin
 */import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
public class StudentRegistrationForm extends JFrame {

    // Text fields
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField emailField;
    private final JTextField confirmEmailField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
// Combo boxes for DOB
    private final JComboBox<String> yearBox;
    private final JComboBox<String> monthBox;
    private final JComboBox<String> dayBox;

// Radio buttons for gender
    private final JRadioButton maleButton;
    private final JRadioButton femaleButton;
private final ButtonGroup genderGroup;

// Combo box for department
private final JComboBox<String> departmentBox;

// Buttons
    private final JButton submitButton;
    private final JButton cancelButton;

// Output area
private final JTextArea outputArea;
public StudentRegistrationForm() {
    setTitle("New Student Registration Form");
    setSize(600, 700);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new GridLayout(15, 2, 5, 5));

    // Initialize components
    firstNameField = new JTextField();
    lastNameField = new JTextField();
    emailField = new JTextField();
    confirmEmailField = new JTextField();
    passwordField = new JPasswordField();
    confirmPasswordField = new JPasswordField();

    yearBox = new JComboBox<>();
    monthBox = new JComboBox<>();
    dayBox = new JComboBox<>();

    maleButton = new JRadioButton("Male");
    femaleButton = new JRadioButton("Female");
    genderGroup = new ButtonGroup();
    genderGroup.add(maleButton);
    genderGroup.add(femaleButton);

    departmentBox = new JComboBox<>(new String[]{"Civil", "CSE", "Electrical", "ECE", "Mechanical"});

    submitButton = new JButton("Submit");
    cancelButton = new JButton("Cancel");

    outputArea = new JTextArea(5, 20);
    outputArea.setEditable(false);

    // Add components to frame
    add(new JLabel("First Name:")); add(firstNameField);
    add(new JLabel("Last Name:")); add(lastNameField);
    add(new JLabel("Email:")); add(emailField);
    add(new JLabel("Confirm Email:")); add(confirmEmailField);
    add(new JLabel("Password:")); add(passwordField);
    add(new JLabel("Confirm Password:")); add(confirmPasswordField);

    add(new JLabel("Year of Birth:")); add(yearBox);
    add(new JLabel("Month of Birth:")); add(monthBox);
    add(new JLabel("Day of Birth:")); add(dayBox);

    add(new JLabel("Gender:")); 
    JPanel genderPanel = new JPanel();
    genderPanel.add(maleButton); genderPanel.add(femaleButton);
    add(genderPanel);

    add(new JLabel("Department:")); add(departmentBox);

    add(submitButton); add(cancelButton);
    add(new JLabel("Your Data is Below:")); add(new JScrollPane(outputArea));

    populateDateBoxes();
    addListeners();

    setVisible(true);
}


private void populateDateBoxes() {
    for (int y = 1965; y <= LocalDate.now().getYear(); y++) {
        yearBox.addItem(String.valueOf(y));
    }

    for (int m = 1; m <= 12; m++) {
        monthBox.addItem(String.valueOf(m));
    }

    updateDays(); // Initial day population

    yearBox.addActionListener(e -> updateDays());
    monthBox.addActionListener(e -> updateDays());
}

private void updateDays() {
    dayBox.removeAllItems();
    int year = Integer.parseInt((String) yearBox.getSelectedItem());
    int month = Integer.parseInt((String) monthBox.getSelectedItem());

    int daysInMonth = switch (month) {
        case 2 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
        case 4, 6, 9, 11 -> 30;
        default -> 31;
    };

    for (int d = 1; d <= daysInMonth; d++) {
        dayBox.addItem(String.valueOf(d));
    }
}
private void addListeners() {
    submitButton.addActionListener(e -> {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String confirmEmail = confirmEmailField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        int year = Integer.parseInt((String) yearBox.getSelectedItem());
        int month = Integer.parseInt((String) monthBox.getSelectedItem());
        int day = Integer.parseInt((String) dayBox.getSelectedItem());

        String gender = maleButton.isSelected() ? "Male" : femaleButton.isSelected() ? "Female" : "";
        String department = (String) departmentBox.getSelectedItem();

        // Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || confirmEmail.isEmpty() ||
            password.isEmpty() || confirmPassword.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$", email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.");
            return;
        }

        if (!email.equals(confirmEmail)) {
            JOptionPane.showMessageDialog(this, "Emails do not match.");
            return;
        }

        if (password.length() < 6 || !password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords must be at least 6 characters and match.");
            return;
        }

        LocalDate dob = LocalDate.of(year, month, day);
        int age = Period.between(dob, LocalDate.now()).getYears();

        if (age < 16 || age > 60) {
            JOptionPane.showMessageDialog(this, "Age must be between 16 and 60.");
            return;
        }

        // Output
        String output = String.format(
            "Student %s %s with email %s born on %d-%02d-%02d (aged %d) successfully registered as a student, entering the %s department.",
            firstName, lastName, email, year, month, day, age, department
        );

        outputArea.setText(output);
    });

    cancelButton.addActionListener(e -> {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        confirmEmailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        genderGroup.clearSelection();
        departmentBox.setSelectedIndex(0);
        outputArea.setText("");
    });
}
public static void main(String[] args) {
    SwingUtilities.invokeLater(StudentRegistrationForm::new);
}

}

    


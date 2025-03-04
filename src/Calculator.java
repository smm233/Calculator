import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(185,195,186,255);
    Color customLightGreen = new Color(143,194,93,255);
    Color customDarkGreen = new Color(77,149,83,255);
    Color customDarkBlue = new Color(48,68,77,255);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "C", "0", ".", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String X = "0";
    String operator = null;
    String Y = null;

    Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customDarkBlue);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customDarkBlue);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonValues.length ; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customDarkBlue));
            if (Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customLightGreen);
                button.setForeground(Color.black);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)){
                button.setBackground(customDarkGreen);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customLightGray);
                button.setForeground(Color.black);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)){
                        if (buttonValue == "=") {
                            if (X != null) {
                                Y = displayLabel.getText();
                                double numX = Double.parseDouble(X);
                                double numY = Double.parseDouble(Y);

                                if (operator == "+") {
                                    displayLabel.setText(convertToInteger(numX + numY));
                                } else if (operator == "-") {
                                    displayLabel.setText(convertToInteger(numX - numY));
                                } else if (operator == "×") {
                                    displayLabel.setText(convertToInteger(numX * numY));
                                } else if (operator == "÷") {
                                    displayLabel.setText(convertToInteger(numX / numY));
                                }
                                X = displayLabel.getText();
                                operator = null;
                                Y = "0";
                            }
                        } else if ("+-×÷".contains(buttonValue)){
                            if (operator == null) {
                                X = displayLabel.getText();
                                displayLabel.setText("0");
                                Y = "0";
                            }
                            operator = buttonValue;
                        }
                    } else if (Arrays.asList(topSymbols).contains(buttonValue)){
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (buttonValue == "+/-") {
                            if (displayLabel.getText() != "0") {
                                double displayNumber = Double.parseDouble(displayLabel.getText());
                                displayNumber *= -1;
                                displayLabel.setText(convertToInteger(displayNumber));
                            }
                        } else if (buttonValue == "%") {
                            double displayNumber = Double.parseDouble(displayLabel.getText());
                            displayNumber /= 100;
                            displayLabel.setText(convertToInteger(displayNumber));
                        }
                    } else {
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains(buttonValue)){
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
        frame.setVisible(true);
        }
    }

    void clearAll() {
        X = "0";
        operator = null;
        Y = null;
    }

    String convertToInteger(double displayNumber) {
        if (displayNumber % 1 == 0) {
            return Integer.toString((int) displayNumber);
        }
        return Double.toString(displayNumber);
    }
}

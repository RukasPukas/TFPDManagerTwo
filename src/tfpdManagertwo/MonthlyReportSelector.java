package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MonthlyReportSelector {
    
    JFrame monthlyReportSelectorFrame;
    JPanel panel;
    JComboBox<String> yearComboBox;

    MonthlyReportSelector(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame reportsFrame) {
        
        monthlyReportSelectorFrame = new JFrame("Monthly Reports Selector");
        monthlyReportSelectorFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ImageIcon icon = new ImageIcon("TroyIcon.png");
        Image img = icon.getImage();
        monthlyReportSelectorFrame.setSize(577, 482);
        monthlyReportSelectorFrame.setIconImage(img);
        monthlyReportSelectorFrame.setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(null);
        monthlyReportSelectorFrame.setResizable(false);
        
        monthlyReportSelectorFrame.getContentPane().add(panel);
        String[] MONTHS = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE","JULY","AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        
        JLabel yearPrompt = new JLabel("Select Year:");
        yearPrompt.setForeground(new Color(255, 255, 255));
        yearPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
        yearPrompt.setBounds(190, 11, 188, 79);
        panel.add(yearPrompt);
        
        JLabel monthPrompt = new JLabel("Select Month:");
        monthPrompt.setForeground(Color.WHITE);
        monthPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
        monthPrompt.setBounds(188, 149, 202, 79);
        panel.add(monthPrompt);
        
        yearComboBox = new JComboBox<>();
        yearComboBox.setBounds(167, 69, 211, 48);
        panel.add(yearComboBox);
        
        JComboBox<String> monthComboBox = new JComboBox<>(MONTHS);
        monthComboBox.setBounds(167, 204, 211, 48);
        panel.add(monthComboBox);
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                String annualString = yearComboBox.getSelectedItem().toString();
                String monthlyString = monthComboBox.getSelectedItem().toString();
                
                File checkForReportFile = new File("Reports/dailyLogReports/" + annualString + "/" + monthlyString + "/monthlyReport.txt");
                
                if (checkForReportFile.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(checkForReportFile))) {
                        JTextArea textArea = new JTextArea(50,100); // Rows, Columns
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Exclude specific substrings
                            line = line.replace("+", "").replace("<html>", "").replace("<br>", "");
                            textArea.append(line + "\n");
                        }
                        textArea.setEditable(false); // Ensure the text area is not editable
                        JScrollPane scrollPane = new JScrollPane(textArea);
                        JOptionPane.showMessageDialog(null, scrollPane, "Monthly Report", JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to read the report file.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "That report does not exist yet.");
                }
            }
        });
        submitButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        submitButton.setBounds(400, 299, 153, 56);
        panel.add(submitButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Reports(userEmail, enteredPassword, adminStatus, superAdminStatus, userEmail, reportsFrame);
                monthlyReportSelectorFrame.dispose();
            }
        });
        cancelButton.setFont(new Font("Monospaced", Font.BOLD, 15));
        cancelButton.setBounds(400, 378, 153, 56);
        panel.add(cancelButton);
        
        // Call method to populate yearComboBox
        populateYearComboBox();
        
        monthlyReportSelectorFrame.setVisible(true);
    }

    private void populateYearComboBox() {
        File parentDir = new File("Reports/dailyLogReports");
        if (parentDir.exists() && parentDir.isDirectory()) {
            File[] yearDirs = parentDir.listFiles(File::isDirectory);
            if (yearDirs != null && yearDirs.length > 0) {
                for (File yearDir : yearDirs) {
                    yearComboBox.addItem(yearDir.getName());
                }
            } else {
                JOptionPane.showMessageDialog(null, "No reports currently exist in " + parentDir.getPath() + ".", "No Reports Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Parent directory " + parentDir.getPath() + " does not exist.", "Directory Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Reports {
	
	JPanel panel;
	JFrame reportsFrame;
	private JButton hoursReport;
	private JButton overTimeReports;
	
	
	Reports(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame hubFrame){
		
		reportsFrame = new JFrame("Reports Hub");
		reportsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		reportsFrame.setSize(577,399);
		reportsFrame.setIconImage(img);
		reportsFrame.setLocationRelativeTo(null);
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		reportsFrame.setResizable(false);
		
		
		reportsFrame.getContentPane().add(panel);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				reportsFrame.dispose();
			}
		});
		exitButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		exitButton.setBackground(Color.LIGHT_GRAY);
		exitButton.setBounds(150, 274, 263, 65);
		panel.add(exitButton);
		
		hoursReport = new JButton("Hours Report");
		hoursReport.setFont(new Font("Monospaced", Font.BOLD, 24));
		hoursReport.setBackground(Color.LIGHT_GRAY);
		hoursReport.setBounds(150, 11, 263, 65);
		  hoursReport.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                showHoursWorkedReport();
	            }
	        });

		panel.add(hoursReport);
		
		JButton monthlyReports = new JButton("Montly Reports");
		monthlyReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				File parentDir = new File("Reports/dailyLogReports");
		        
		        // Check if the parent directory exists
		        if (parentDir.exists() && parentDir.isDirectory()) {
		            // List all files and directories in the parent directory
		            File[] filesList = parentDir.listFiles();
		            boolean directoryExists = false;

		            if (filesList != null) {
		                for (File file : filesList) {
		                    if (file.isDirectory()) {
		                        directoryExists = true;
		                        break;
		                    }
		                }
		            }

		            if (!directoryExists) {
		                
		                JOptionPane.showMessageDialog(null, "No reports currently exist in " + parentDir.getPath() + ".", "No Reports Found", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                System.out.println("At least one directory exists in " + parentDir.getPath() + ".");
		                new MonthlyReportSelector( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail,  reportsFrame);
		                reportsFrame.dispose();
		                
		                
		            }
		        } else {
		            System.out.println("Parent directory " + parentDir.getPath() + " does not exist.");
		        }
			}
		});
		monthlyReports.setFont(new Font("Monospaced", Font.BOLD, 24));
		monthlyReports.setBackground(Color.LIGHT_GRAY);
		monthlyReports.setBounds(150, 85, 263, 65);
		panel.add(monthlyReports);
		
		overTimeReports = new JButton("Over Time Reports");
		overTimeReports.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File parentDir = new File("Reports/overTimeReports");
		        
		        // Check if the parent directory exists
		        if (parentDir.exists() && parentDir.isDirectory()) {
		            // List all files and directories in the parent directory
		            File[] filesList = parentDir.listFiles();
		            boolean directoryExists = false;

		            if (filesList != null) {
		                for (File file : filesList) {
		                    if (file.isDirectory()) {
		                        directoryExists = true;
		                        break;
		                    }
		                }
		            }

		            if (!directoryExists) {
		                
		                JOptionPane.showMessageDialog(null, "No reports currently exist in " + parentDir.getPath() + ".", "No Reports Found", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		                System.out.println("At least one directory exists in " + parentDir.getPath() + ".");
		                new OverTimeReportSelector( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail,  reportsFrame);
		                reportsFrame.dispose();
		                
		                
		            }
		        } else {
		            System.out.println("Parent directory " + parentDir.getPath() + " does not exist.");
		        }
				
			}
		});
		overTimeReports.setFont(new Font("Monospaced", Font.BOLD, 22));
		overTimeReports.setBackground(Color.LIGHT_GRAY);
		overTimeReports.setBounds(150, 161, 263, 65);
		panel.add(overTimeReports);
		
		reportsFrame.setVisible(true);
		
	}
	   private void showHoursWorkedReport() {
	        JFrame hoursWorkedFrame = new JFrame("Hours Worked Report");
	        JPanel hoursWorkedPanel = new JPanel(new GridLayout(0, 1));

	        File directory = new File("HoursWorked");
	        File[] files = directory.listFiles();

	        if (files != null) {
	            for (File file : files) {
	                if (file.isFile()) {
	                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
	                        String userName = file.getName().replace(".txt", "");
	                        String hoursWorked = br.readLine();
	                        JLabel label = new JLabel(userName + " Hours Worked: " + hoursWorked);
	                        hoursWorkedPanel.add(label);
	                    } catch (IOException ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }
	        } else {
	            JLabel label = new JLabel("No hours worked data available.");
	            hoursWorkedPanel.add(label);
	        }

	        JScrollPane scrollPane = new JScrollPane(hoursWorkedPanel);
	        hoursWorkedFrame.getContentPane().add(scrollPane);
	        hoursWorkedFrame.setSize(500, 500);
	        hoursWorkedFrame.setLocationRelativeTo(null); // Center the frame on the screen
	        hoursWorkedFrame.setVisible(true);
	    }
	}

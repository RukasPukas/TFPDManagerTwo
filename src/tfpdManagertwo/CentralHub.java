package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;


public class CentralHub {
	
	JFrame hubFrame;
	JPanel panel;
	private JLabel tfpdManagerHeaderlabel;
	private JLabel bulletinLabel;
	String bbContentFinal;
	
	CentralHub(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail)
	{
		
		hubFrame = new JFrame("Central Hub");
		hubFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		hubFrame.setSize(890,871);
		hubFrame.setIconImage(img);
		hubFrame.setLocationRelativeTo(null);
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		hubFrame.setResizable(false);
		
		
		
		
		
		
	       JLabel iconLabel = new JLabel("New label");
           iconLabel.setBounds(20, 11, 273, 212);
           panel.add(iconLabel);
           ImageIcon imageIcon = new ImageIcon("TroyIcon.png");
           Image image = imageIcon.getImage();
           Image resizedImage = image.getScaledInstance(iconLabel.getWidth(), iconLabel.getHeight(), Image.SCALE_SMOOTH);
           ImageIcon resizedIcon = new ImageIcon(resizedImage);
           iconLabel.setIcon(resizedIcon);
		
		
		//<------------------------------------------------Read from BB file--------------------------------------------->
           File getBBFile = new File("bulletinboard/bb.txt");
           if (getBBFile.exists()) {
        	    try {
        	        FileReader fileReader = new FileReader(getBBFile);
        	        BufferedReader bufferedReader = new BufferedReader(fileReader);
        	        StringBuilder bbFileContent = new StringBuilder();
        	        String line;
        	        while ((line = bufferedReader.readLine()) != null) {
        	            // Append each line with a newline character
        	            bbFileContent.append(line).append("\n");
        	        }
        	        bufferedReader.close();

        	        // Convert the StringBuilder to a String
        	        bbContentFinal = bbFileContent.toString();

        	    } catch (IOException e) {
        	        JOptionPane.showMessageDialog(null, "There was an issue retrieving the bulletin board information.");
        	        e.printStackTrace();
        	    }
        	} else {
        	    System.out.println("Hours Worked not found.");
        	    bbContentFinal = "N/A";
        	}
        //<------------------------------------------------END read from BB file----------------------------------------->
		
		
		
		hubFrame.getContentPane().add(panel);
		
		tfpdManagerHeaderlabel = new JLabel("TFPD Manager 2.0");
		tfpdManagerHeaderlabel.setForeground(new Color(255, 255, 255));
		tfpdManagerHeaderlabel.setFont(new Font("Lucida Bright", Font.ITALIC, 29));
		tfpdManagerHeaderlabel.setBounds(30, 201, 273, 44);
		panel.add(tfpdManagerHeaderlabel);
		
		JTextArea textArea = new JTextArea("TFPD MANAGER 2.0 \n" +bbContentFinal);
		textArea.setForeground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBackground(new Color(0, 0, 0));
		textArea.setBounds(1, 1, 5, 0);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		panel.add(textArea);
		
		bulletinLabel = new JLabel("Bulletin Board");
		bulletinLabel.setForeground(Color.WHITE);
		bulletinLabel.setFont(new Font("Lucida Bright", Font.BOLD | Font.ITALIC, 29));
		bulletinLabel.setBounds(510, 62, 207, 44);
		panel.add(bulletinLabel);
		
		JButton formsButton = new JButton("Forms");
		formsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new FormsHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				hubFrame.dispose();
				
			}
		});
		formsButton.setBackground(Color.LIGHT_GRAY);
		formsButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		formsButton.setBounds(30, 365, 263, 65);
		panel.add(formsButton);
		
		JButton itemRequestButton = new JButton("Item Acquisition");
		itemRequestButton.setBackground(Color.LIGHT_GRAY);
		itemRequestButton.setFont(new Font("Monospaced", Font.BOLD, 22));
		itemRequestButton.setBounds(30, 443, 263, 65);
		itemRequestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new ItemHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
				hubFrame.dispose();
			}
		});
		
		panel.add(itemRequestButton);
		
		JButton dailyLogButton = new JButton("Daily Log");
		dailyLogButton.setBackground(Color.LIGHT_GRAY);
		dailyLogButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		dailyLogButton.setBounds(30, 289, 263, 65);
		dailyLogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new DailyLog( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, hubFrame);
				hubFrame.dispose();
			}
		});
		panel.add(dailyLogButton);
		
		JButton messageBlasterButton = new JButton("Broadcast Message");
		messageBlasterButton.setBackground(Color.LIGHT_GRAY);
		messageBlasterButton.setFont(new Font("Monospaced", Font.BOLD, 22));
		messageBlasterButton.setBounds(30, 519, 263, 65);
		messageBlasterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new MessageBlaster( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, hubFrame);
				hubFrame.dispose();
			}
		});
		panel.add(messageBlasterButton);
		
		JButton userOptionsButton = new JButton("User Options");
		userOptionsButton.setBackground(Color.LIGHT_GRAY);
		userOptionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new UserOptionsHub(enteredUserName,enteredPassword,adminStatus,superAdminStatus,userEmail,hubFrame);
				hubFrame.dispose();
			}
		});
		userOptionsButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		userOptionsButton.setBounds(30, 671, 263, 65);
		panel.add(userOptionsButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.setBackground(Color.LIGHT_GRAY);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hubFrame.dispose();
			}
		});
		exitButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		exitButton.setBounds(30, 748, 263, 65);
		panel.add(exitButton);
		
		JScrollPane bulletinScrollPane = new JScrollPane(textArea);
		bulletinScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		bulletinScrollPane.setBounds(418, 118, 424, 619);
		bulletinScrollPane.getViewport().setBackground(Color.BLACK);
		
		
		panel.add(bulletinScrollPane);
		
		JButton reportsButton = new JButton("Reports");
		reportsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Reports (enteredUserName, enteredPassword, adminStatus, superAdminStatus, userEmail, hubFrame);
				hubFrame.dispose();
			}
		});
		reportsButton.setBackground(Color.LIGHT_GRAY);
		reportsButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		reportsButton.setBounds(30, 595, 263, 65);
		panel.add(reportsButton);
		
		hubFrame.setVisible(true);

		
	}
}

package tfpdManagertwo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class AddRun {
	
	JFrame addRunFrame;
	JPanel panel;
	private JTextField runNumberTextField;
	private JTextField impressionTextField;
	JComboBox mutualAidComboBox,destinationComboBox;
	
	
	AddRun(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame dailyLogFrame, DefaultListModel runsListModel)
	{
		String blankSpace = "";
		String truckPath = "vehicles/vehicleroster.txt";
		String maPath = "MA/ma.txt";
		
		addRunFrame = new JFrame("Add Run");
		addRunFrame.setSize(800,965);
		addRunFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		addRunFrame.setLocationRelativeTo(null);
		addRunFrame.setResizable(false);
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		addRunFrame.setIconImage(img);
		addRunFrame.setResizable(false);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		addRunFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel headerLabel = new JLabel("Add Run:");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
		headerLabel.setBounds(320, 31, 191, 58);
		panel.add(headerLabel);
		
		JLabel vehicleLabel = new JLabel("Unit:");
		vehicleLabel.setForeground(Color.WHITE);
		vehicleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		vehicleLabel.setBounds(151, 200, 79, 58);
		panel.add(vehicleLabel);
		
		impressionTextField = new JTextField();
		impressionTextField.setColumns(10);
		impressionTextField.setBounds(10, 575, 352, 39);
		impressionTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_TAB) {
		        	destinationComboBox.requestFocus();
		        }
			}
		});
		panel.add(impressionTextField);
		
		String disposition[]= {blankSpace,"Transported", "Refusal", "Lift Assist - Billed","Lift Assist - Not Billed", "Treated/Released","Cancelled On Scene", "Cancelled Prior To Scene", "No Patient Found", "Care Transferred"
				,"DOA","Agency Assist","Public Standby"};
		
		JComboBox dispositionComboBox = new JComboBox(disposition);
		dispositionComboBox.setBounds(10, 477, 352, 49);
		dispositionComboBox.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_TAB) {
		        	impressionTextField.requestFocus();
		        }
			}
		});
		panel.add(dispositionComboBox);
		
		JTextArea addressText = new JTextArea("N/A");
		addressText.setWrapStyleWord(true);
		addressText.setLineWrap(true);
		addressText.setForeground(Color.BLACK);
		addressText.setCaretColor(Color.BLACK);
		addressText.setBorder(BorderFactory.createEmptyBorder());
		addressText.setBackground(Color.WHITE);
		addressText.setBounds(10, 352, 352, 72);
		addressText.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_TAB) {
		            dispositionComboBox.requestFocus();
		            e.consume();
		        }
		    }
		});
		panel.add(addressText);
		
		JComboBox truckComboBox = new JComboBox();
		truckComboBox.setBounds(10, 247, 352, 49);
		truckComboBox.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_TAB) {
		        	addressText.requestFocus();
		        }
			}
		});
		panel.add(truckComboBox);
		
		
		
		truckComboBox.addItem(blankSpace);
		
		runNumberTextField = new JTextField();
		runNumberTextField.setColumns(10);
		runNumberTextField.setBounds(10, 150, 352, 39);
		runNumberTextField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!(Character.isDigit(c) || c == '-')) {
		            e.consume(); 
		        }  
		        if (c == '-' && runNumberTextField.getText().contains("-")) {
		            e.consume(); 
		        }
		        if (e.getKeyCode() == KeyEvent.VK_TAB) {
		        	truckComboBox.requestFocus();
		        }
		    }
		});
		panel.add(runNumberTextField);
		
		JLabel runNumberPrompt = new JLabel("Run Number:");
		runNumberPrompt.setForeground(Color.WHITE);
		runNumberPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		runNumberPrompt.setBounds(122, 99, 141, 58);
		panel.add(runNumberPrompt);
		

		
		JLabel addressPrompt = new JLabel("Address:");
		addressPrompt.setForeground(Color.WHITE);
		addressPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		addressPrompt.setBounds(132, 307, 116, 58);
		panel.add(addressPrompt);
		
		JLabel dispositionPrompt = new JLabel("Disposition:");
		dispositionPrompt.setForeground(Color.WHITE);
		dispositionPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		dispositionPrompt.setBounds(110, 421, 157, 58);
		panel.add(dispositionPrompt);
		

		
		
		JLabel impressionPrompt = new JLabel("Impression:");
		impressionPrompt.setForeground(Color.WHITE);
		impressionPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		impressionPrompt.setBounds(122, 524, 141, 58);
		panel.add(impressionPrompt);
		
		
		DefaultListModel<String> addRunsListModel = new DefaultListModel<>();
		JList<String> addRunList = new JList<>(addRunsListModel);
		addRunList.setForeground(Color.BLACK);
		addRunList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		addRunList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		addRunList.setVisibleRowCount(-1);
		addRunList.setPreferredSize(new Dimension(300, 464));
		addRunList.setBackground(Color.WHITE);
		addRunList.setBounds(413, 150, 328, 572);
		
		panel.add(addRunList);
		
		JScrollPane listScroller = new JScrollPane(addRunList);
		listScroller.setBounds(413, 150, 363, 574); 
		listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(listScroller);
		
		JButton deleteButton = new JButton("Delete Selected");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = addRunList.getSelectedIndex();
				if(selectedIndex == -1)
				{
					  JOptionPane.showMessageDialog(null, "No item selected to delete.");
					  return;
				}
				else
				{
					addRunsListModel.remove(selectedIndex);
				}
				
			}
			
		});
		deleteButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		deleteButton.setBounds(504, 735, 181, 39);
		panel.add(deleteButton);
		
		JButton addRunButton = new JButton("+");
		addRunButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String runCheck = runNumberTextField.getText().trim();
				Object truckCheck = truckComboBox.getSelectedItem();
				String addressCheck =addressText.getText().trim();
				Object dispositionCheck = dispositionComboBox.getSelectedItem();
				String impressionCheck = impressionTextField.getText();
				
				if(runCheck.equals(""))
				{
					JOptionPane.showMessageDialog(null, "You must enter a vaild run number.");
					return;
				}
				if(truckCheck.equals(blankSpace))
				{
					JOptionPane.showMessageDialog(null, "You must select a Unit.");
					return;
				}
				if(addressCheck.equals("") || addressCheck.equals("N/A"))
				{
					JOptionPane.showMessageDialog(null, "You must enter a valid address.");
					return;
				}
				if(dispositionCheck.equals(blankSpace))
				{
					JOptionPane.showMessageDialog(null, "You must enter a valid disposition.");
					return;
				}
				if(impressionCheck.equals(""))
				{
					JOptionPane.showMessageDialog(null, "You must enter a valid impression.");
					return;
				}
				Object destinationSelection = destinationComboBox.getSelectedItem();
				Object mutualAidSelection = mutualAidComboBox.getSelectedItem();
				
				String runFinal = runCheck;
				String truckFinal = truckCheck.toString();
				String addressFinal = addressCheck;
				String dispositionFinal = dispositionCheck.toString();
				String impressionFinal = impressionCheck;
				String mutualAidFinal = mutualAidSelection.toString();
				String destinationFinal = destinationSelection.toString();
				String finalString = "<html>Run: " + runFinal +"<br>Unit: " + truckFinal + "<br>Address: " + addressFinal + "<br>Disposition: " +
				dispositionFinal  + "<br>Destination: " + destinationFinal+  "<br>Impression: "+ impressionFinal + "<br>Mutual Aid: " + mutualAidFinal +"<br><br>";
				
				addRunsListModel.addElement(finalString);
				
				runNumberTextField.setText("");
				truckComboBox.setSelectedIndex(0);
				addressText.setText("N/A");
				dispositionComboBox.setSelectedIndex(0);
				impressionTextField.setText("");
				mutualAidComboBox.setSelectedIndex(0);
				destinationComboBox.setSelectedIndex(0);
			}
		});
		addRunButton.setFont(new Font("Monospaced", Font.BOLD, 40));
		addRunButton.setBounds(151, 863, 61, 49);
		panel.add(addRunButton);
		
		mutualAidComboBox = new JComboBox(new Object[]{});
		mutualAidComboBox.setBounds(10, 785, 352, 49);
		panel.add(mutualAidComboBox);
		
		//<------------------------------------------------------This is where mutualaid.txt is read------------------------------------->

		try (BufferedReader reader = new BufferedReader(new FileReader(maPath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                mutualAidComboBox.addItem(line);
	            }
	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the Mutual Aid roster.");
	        	System.out.println("An error occurred retrieving the Mutual Aid roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of vehicleroster.txt------------------------------------------->
		
		JLabel mutualAidPrompt = new JLabel("Mutual Aid:");
		mutualAidPrompt.setForeground(Color.WHITE);
		mutualAidPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		mutualAidPrompt.setBounds(107, 735, 141, 58);
		panel.add(mutualAidPrompt);
		
		JLabel destinationPrompt = new JLabel("Destination:");
		destinationPrompt.setForeground(Color.WHITE);
		destinationPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		destinationPrompt.setBounds(118, 625, 160, 58);
		panel.add(destinationPrompt);
		
		destinationComboBox = new JComboBox(new Object[]{});
		destinationComboBox.setBounds(10, 672, 352, 49);
		panel.add(destinationComboBox);
		
		String hospitalPath = "Hospitals/hospitalroster.txt";
		//<------------------------------------------------------This is where hospitalroster.txt is read------------------------------------->
		try (BufferedReader reader = new BufferedReader(new FileReader(hospitalPath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	            	destinationComboBox.addItem(line);
	            }
	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the hospital roster.");
	        	System.out.println("An error occurred retrieving the hospital roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of hospitalroster.txt------------------------------------------->
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        int size = addRunsListModel.getSize();
		        for (int i = 0; i < size; i++) {
		            Object element = addRunsListModel.getElementAt(i);
		            runsListModel.addElement(element);
		        }
		        addRunsListModel.removeAllElements();
		        dailyLogFrame.setEnabled(true);
		        addRunFrame.dispose();
				
			}
		});
		submitButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		submitButton.setBounds(643, 809, 133, 39);
		panel.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dailyLogFrame.setEnabled(true);
				addRunFrame.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		cancelButton.setBounds(643, 863, 133, 39);
		panel.add(cancelButton);
		
		JButton hospitalButton = new JButton("Edit Hospitals");
		hospitalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditHospitalRoster(addRunFrame, enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail,  dailyLogFrame,  runsListModel);
				addRunFrame.setEnabled(false);
			}
		});
		hospitalButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		hospitalButton.setBounds(413, 809, 181, 39);
		panel.add(hospitalButton);
		
		if(adminStatus == true)
		{
			hospitalButton.setVisible(true);
		}
		else
		{
			hospitalButton.setVisible(false);
		}
		
		JButton editMutualAid = new JButton("Edit Mutual Aid");
		editMutualAid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditMutualAidRoster(addRunFrame, enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail,  dailyLogFrame,  runsListModel);
				addRunFrame.setEnabled(false);
			}
		});
		editMutualAid.setFont(new Font("Monospaced", Font.BOLD, 15));
		editMutualAid.setBounds(413, 863, 181, 39);
		panel.add(editMutualAid);
		if(adminStatus==true)
		{
			editMutualAid.setVisible(true);
		}
		else
		{
			editMutualAid.setVisible(false);
		}
		//<------------------------------------------------------This is where vehicleroster.txt is read------------------------------------->

		try (BufferedReader reader = new BufferedReader(new FileReader(truckPath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                truckComboBox.addItem(line);
	            }
	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the vehicle roster.");
	        	System.out.println("An error occurred retrieving the vehicle roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of vehicleroster.txt------------------------------------------->
		
		
		
		
		
		addRunFrame.setVisible(true);
	}
}

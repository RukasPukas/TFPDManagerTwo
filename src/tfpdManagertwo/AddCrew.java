package tfpdManagertwo;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddCrew {
	
	
	JFrame addCrewFrame;
	JPanel panel;
	private JLabel vehicleLabel;
	private JLabel staffPrompt;
	private JComboBox staffMemberComboBox;
	private JTextField hoursWorkedTextField;
	
	AddCrew(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame dailyLogFrame, DefaultListModel truckListModel)
	{
		String truckPath = "vehicles/vehicleroster.txt";
		String filePath = "Generalroster/generalroster.txt";
		int counter = 0;
		String blankSpace = "";
		
		
		addCrewFrame = new JFrame("Add Crew");
		addCrewFrame.setSize(604,668);
		addCrewFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		addCrewFrame.setLocationRelativeTo(null);
		addCrewFrame.setResizable(false);
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		addCrewFrame.setIconImage(img);
		addCrewFrame.setResizable(false);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		addCrewFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel headerLabel = new JLabel("Assign Crew:");
		headerLabel.setForeground(Color.WHITE);
		headerLabel.setFont(new Font("Monospaced", Font.BOLD, 25));
		headerLabel.setBounds(198, 28, 191, 58);
		panel.add(headerLabel);
		
		
		JComboBox truckComboBox = new JComboBox();
		truckComboBox.setBounds(10, 126, 221, 49);
		panel.add(truckComboBox);
		
		vehicleLabel = new JLabel("Unit:");
		vehicleLabel.setForeground(Color.WHITE);
		vehicleLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
		vehicleLabel.setBounds(82, 62, 79, 58);
		panel.add(vehicleLabel);
		
		staffPrompt = new JLabel("Staff Member:");
		staffPrompt.setForeground(Color.WHITE);
		staffPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		staffPrompt.setBounds(39, 186, 171, 58);
		panel.add(staffPrompt);
		
		staffMemberComboBox = new JComboBox();
		staffMemberComboBox.setBounds(10, 247, 221, 49);
		panel.add(staffMemberComboBox);
		
		//<------------------------------------------------------This is where generalroster.txt is read------------------------------------->
		 staffMemberComboBox.addItem(blankSpace);
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                staffMemberComboBox.addItem(line);
	            }
	        } catch (IOException e) {
	        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the roster.");
	        	System.out.println("An error occurred retrieving the roster.");
	            e.printStackTrace();
	        }
		
		//<--------------------------------------------------------End reading of generalroster.txt------------------------------------------->
		
		JLabel hoursWorkedPrompt = new JLabel("Hours Worked:");
		hoursWorkedPrompt.setForeground(Color.WHITE);
		hoursWorkedPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		hoursWorkedPrompt.setBounds(39, 310, 171, 58);
		panel.add(hoursWorkedPrompt);
		
		hoursWorkedTextField = new JTextField();
		hoursWorkedTextField.setBounds(10, 367, 221, 39);
		hoursWorkedTextField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!(Character.isDigit(c) || c == '.')) {
		            e.consume(); 
		        }  
		        if (c == '.' && hoursWorkedTextField.getText().contains(".")) {
		            e.consume(); 
		        }
		    }
		});
		panel.add(hoursWorkedTextField);
		hoursWorkedTextField.setColumns(10);
		
		DefaultListModel<String> listModel = new DefaultListModel<>();

		
		JList addCrewList = new JList(listModel);
		addCrewList.setBounds(299, 122, 262, 344);
		panel.add(addCrewList);
		
		JButton addAssignment = new JButton("+");
		addAssignment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object truckCheck = truckComboBox.getSelectedItem();
				Object crewCheck = staffMemberComboBox.getSelectedItem();
				String hoursCheck = hoursWorkedTextField.getText().trim();
				
				if(truckCheck.equals(blankSpace))
				{
					JOptionPane.showMessageDialog(null, "You must select a Unit.");
					return;
				}
				if(crewCheck.equals(blankSpace))
				{
					JOptionPane.showMessageDialog(null, "You must select a crew member to add.");
					return;
				}
				if(hoursCheck.equals(""))
				{
					JOptionPane.showMessageDialog(null, "You must enter an amount of hours worked.");
					return;
				}
				
				String crewMember ="Crewmember: " + crewCheck.toString();
				String truck = "Unit: " +truckCheck.toString();
				String hoursFinal ="Hours Worked: " + hoursCheck;
				
				String finalString = "<html>"+crewMember +"<br>"+ truck + "<br>"+hoursFinal+"<br></html>";
				
				listModel.addElement(finalString);
				
				truckComboBox.setSelectedIndex(0);
				staffMemberComboBox.setSelectedIndex(0);
				hoursWorkedTextField.setText("");
				
				
				
			}
		});
		addAssignment.setFont(new Font("Monospaced", Font.BOLD, 40));
		addAssignment.setBounds(82, 435, 61, 49);
		panel.add(addAssignment);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int size = listModel.getSize();
				for (int i = 0; i<size; i++)
				{
					Object element = listModel.getElementAt(i);
					truckListModel.addElement(element+"\n");
				}
				listModel.removeAllElements();
				dailyLogFrame.setEnabled(true);
				addCrewFrame.dispose();
			}
		});
		submitButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		submitButton.setBounds(293, 581, 133, 39);
		panel.add(submitButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dailyLogFrame.setEnabled(true);
				addCrewFrame.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		cancelButton.setBounds(442, 581, 133, 39);
		panel.add(cancelButton);
		
		JButton editVehiclesButton = new JButton("Edit Unit List");
		editVehiclesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new EditTruckRoster( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail,  dailyLogFrame,  truckListModel, addCrewFrame,truckComboBox);
				addCrewFrame.setEnabled(false);
			}
		});
		editVehiclesButton.setFont(new Font("Monospaced", Font.BOLD, 11));
		editVehiclesButton.setBounds(442, 11, 133, 39);
		panel.add(editVehiclesButton);
		
		if(adminStatus == false)
		{
			editVehiclesButton.setVisible(false);
		}
		else
		{
			editVehiclesButton.setVisible(true);
		}
		
	
		
		truckComboBox.addItem(blankSpace);
		
		JButton deleteButton = new JButton("Delete Selected");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = addCrewList.getSelectedIndex();
				if(selectedIndex == -1)
				{
					  JOptionPane.showMessageDialog(null, "No item selected to delete.");
					  return;
				}
				else
				{
					listModel.remove(selectedIndex);
				}
				
			}
		});
		deleteButton.setFont(new Font("Monospaced", Font.BOLD, 15));
		deleteButton.setBounds(340, 477, 181, 39);
		panel.add(deleteButton);
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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		addCrewFrame.setVisible(true);
	}
}

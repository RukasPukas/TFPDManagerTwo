package tfpdManagertwo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class EditTruckRoster {
	
	JFrame editTrucksFrame;
	JPanel panel;
	JList list;
	private JButton saveButton;
	private JButton deleteButton;
	private JLabel addNewPrompt;
	private JTextField textField;
	private JButton addVehicleButton;
	
	EditTruckRoster(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame dailyLogFrame, DefaultListModel truckListModel, JFrame addCrewFrame ,JComboBox truckComboBox)
	{
		editTrucksFrame = new JFrame("Edit Fleet Roster");
		editTrucksFrame.setSize(538,785);
		editTrucksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		editTrucksFrame.setLocationRelativeTo(null);
		editTrucksFrame.setResizable(false);
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		editTrucksFrame.setIconImage(img);
		
		panel=new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setLayout(null);
		editTrucksFrame.getContentPane().add(panel);
		
		JLabel currentRecipientsPrompt = new JLabel("Current Vehicles:");
		currentRecipientsPrompt.setForeground(Color.WHITE);
		currentRecipientsPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		currentRecipientsPrompt.setBounds(143, 11, 266, 67);
		panel.add(currentRecipientsPrompt);
		
		list = new JList();
		list.setBounds(98, 62, 311, 381);
		panel.add(list);
		
		//<------------------------------------------------------------Populate vehicle Roster List-------------------------------------------------->
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		String emailFilePath = "vehicles/vehicleroster.txt";

		try (BufferedReader reader = new BufferedReader(new FileReader(emailFilePath))) {
		    String line;
		    while ((line = reader.readLine()) != null) {
		        listModel.addElement(line);
		    }
		    System.out.println("Vehicle roster list loaded successfully");
		} catch (IOException e) {
		    JOptionPane.showMessageDialog(null, "An error occurred retrieving the vehicle roster list.");
		    System.out.println("An error occurred retrieving the vehicle roster list.");
		    e.printStackTrace();
		}

		list.setModel(listModel);
		
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCrewFrame.setEnabled(true);
				editTrucksFrame.dispose();
			}
		});
		cancelButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		cancelButton.setBounds(353, 686, 161, 51);
		panel.add(cancelButton);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListModel<String> model = list.getModel();
		        int size = model.getSize();
		        
		        StringBuilder content = new StringBuilder();
		        
		        for (int i = 0; i < size; i++)
		        {
		            content.append(model.getElementAt(i)).append("\n");
		        }
		        
		        try {
		            Files.write(Paths.get(emailFilePath), content.toString().getBytes());
		            System.out.println("Truck Roster list saved successfully");
		        } 
		        catch (IOException ex) 
		        {
		            JOptionPane.showMessageDialog(null, "An error occurred while saving the truck roster list.");
		            System.out.println("An error occurred while saving the truck roster list.");
		            ex.printStackTrace();
		        }
		        addCrewFrame.setEnabled(true);
		        truckComboBox.repaint();
		        editTrucksFrame.dispose();
			}
		});
		saveButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		saveButton.setBounds(353, 627, 161, 51);
		panel.add(saveButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = list.getSelectedIndex();
				if (selectedIndex == -1) 
				
				{
				    JOptionPane.showMessageDialog(null, "No item selected to delete.");
				}
				 else 
				 {
					    
					    listModel.remove(selectedIndex);
				 }
				
			}
		});
		deleteButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		deleteButton.setBounds(171, 454, 161, 51);
		panel.add(deleteButton);
		
		addNewPrompt = new JLabel("Add New Vehicle:");
		addNewPrompt.setForeground(Color.WHITE);
		addNewPrompt.setFont(new Font("Monospaced", Font.BOLD, 20));
		addNewPrompt.setBounds(10, 541, 266, 67);
		panel.add(addNewPrompt);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
		        char c = e.getKeyChar();
		        if (!(Character.isDigit(c))) {
		            e.consume(); 
		        }  

		    }
		});
		textField.setBounds(10, 588, 190, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		addVehicleButton = new JButton("+");
		addVehicleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newTruckString = textField.getText().trim();
				
				if(newTruckString.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Invalid new vehicle entry.");
					return;
				}
				else
				{
					listModel.addElement(newTruckString);
					textField.setText("");
				}
			}
		});
		addVehicleButton.setFont(new Font("Monospaced", Font.BOLD, 30));
		addVehicleButton.setBounds(68, 624, 61, 51);
		panel.add(addVehicleButton);
		
		
		


		editTrucksFrame.setVisible(true);
		

	}

}

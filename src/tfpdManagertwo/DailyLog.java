package tfpdManagertwo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.JList;

public class DailyLog implements ActionListener, PropertyChangeListener{
	
	JFrame dailyLogFrame, Datepicker;
	JPanel panel;
	JButton datebutton;
	JFormattedTextField datefield;
	private JTextField callOffTextField;
	private JTextField studentsTextField;
	private JTextField mandatesTextField;
	JTextArea notestext;
	JComboBox ambulancesOnComboBox,bouthOutComboBox;
	private JTextField timeTradeTextField;
	StringBuilder crewAssignmentsFinal;
    private List<String> crewMemberList;  
    private List<String> hoursWorkedList;  
    String[][] mergedArray;
    int loadSafetyCheck;
	
	DailyLog(String enteredUserName, String enteredPassword, Boolean adminStatus, Boolean superAdminStatus, String userEmail, JFrame hubFrame)
	{
		loadSafetyCheck = 0;
		dailyLogFrame = new JFrame("Daily Log");
		dailyLogFrame.setSize(1557,880);
		dailyLogFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		dailyLogFrame.setLocationRelativeTo(null);
		dailyLogFrame.setResizable(false);
		
		
		ImageIcon icon = new ImageIcon("TroyIcon.png");
		Image img = icon.getImage();
		dailyLogFrame.setIconImage(img);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		dailyLogFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
	       JLabel iconLabel = new JLabel("New label");
           iconLabel.setBounds(0, 0, 234, 217);
           panel.add(iconLabel);
           ImageIcon imageIcon = new ImageIcon("TroyIcon.png");
           Image image = imageIcon.getImage();
           Image resizedImage = image.getScaledInstance(iconLabel.getWidth(), iconLabel.getHeight(), Image.SCALE_SMOOTH);
           ImageIcon resizedIcon = new ImageIcon(resizedImage);
           iconLabel.setIcon(resizedIcon);
           
           
           
   		JButton exitButton = new JButton("Exit");
   		exitButton.setBackground(Color.LIGHT_GRAY);
   		exitButton.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent e) {
   				
   				int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed? Any unsaved data will be lost.", "Confirmation", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
   				new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
   				dailyLogFrame.dispose();
   				Datepicker.dispose();
				}
				
   			}
   		});
   		exitButton.setFont(new Font("Monospaced", Font.BOLD, 24));
   		exitButton.setBounds(1356, 790, 177, 42);
   		panel.add(exitButton);
   		
   		JLabel headerPrompt = new JLabel("DAILY LOG");
   		headerPrompt.setForeground(Color.WHITE);
   		headerPrompt.setFont(new Font("Monospaced", Font.BOLD, 40));
   		headerPrompt.setBounds(10, 181, 240, 78);
   		panel.add(headerPrompt);
   		
   		String availableShifts[] ={"","A","B","C"};
   		
   		JLabel shiftPrompt = new JLabel("Shift:");
   		shiftPrompt.setForeground(Color.WHITE);
   		shiftPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
   		shiftPrompt.setBounds(10, 294, 95, 42);
   		panel.add(shiftPrompt);
   		
   		JComboBox shiftComboBox = new JComboBox(availableShifts);
   		shiftComboBox.setForeground(Color.WHITE);
   		shiftComboBox.setBackground(Color.BLACK);
   		shiftComboBox.setBounds(214, 303, 177, 35);
   		shiftComboBox.setBorder(BorderFactory.createEmptyBorder());
   		panel.add(shiftComboBox);
   		
   		JLabel shiftLeaderPrompt = new JLabel("Shift Leader:");
   		shiftLeaderPrompt.setForeground(Color.WHITE);
   		shiftLeaderPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
   		shiftLeaderPrompt.setBounds(13, 378, 191, 42);
   		panel.add(shiftLeaderPrompt);
   		
   		JComboBox shiftLeaderComboBox = new JComboBox(new Object[]{});
   		shiftLeaderComboBox.setBackground(Color.BLACK);
   		shiftLeaderComboBox.setForeground(Color.WHITE);
   		shiftLeaderComboBox.setBounds(214, 387, 177, 35);
   		shiftLeaderComboBox.setBorder(BorderFactory.createEmptyBorder());
   		panel.add(shiftLeaderComboBox);
   		
   		JLabel datePrompt = new JLabel("Date:");
   		datePrompt.setForeground(Color.WHITE);
   		datePrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
   		datePrompt.setBounds(10, 460, 95, 42);
   		panel.add(datePrompt);
           
    //<------------------------------------------------POPULATE SHIFT LEADER ROSTSER-------------------------------------------------------->       
	       String filePath = "FTRoster/FTRoster.txt";
	       
	       try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
	       {
		            String line;
		            while ((line = reader.readLine()) != null) 
		            {
		            	shiftLeaderComboBox.addItem(line);
		            	
		            }
		            System.out.println("FT Roster for OT email list loaded successfully");
	        }
	       catch (IOException e1) 
	       {
		        	JOptionPane.showMessageDialog(null, "An Error occurred retrieving the FT Roster for OT notification.");
		        	System.out.println("An Error occurred retrieving the FT Roster for OT Notification List.");
		            e1.printStackTrace();
	       }
           
	       //<--------------------------------------------END POPULATE SHIFT LEADER ROSTSER-------------------------------------------------------->   
           
           
	  		 //<----------------------------------------------------Begin Date Picker--------------------------------------------->
	  		 
	 		datebutton = new JButton("Calendar");
	 		datebutton.setBackground(Color.LIGHT_GRAY);
	 		datebutton.setFont(new Font("Monospaced", Font.BOLD, 12));
	 		datebutton.setBounds(291, 468, 100, 34);
	 		datebutton.addActionListener(this);
	 		panel.add(datebutton);
	 		
	 		Datepicker = new Datepicker();
	 		Datepicker.setUndecorated(true);
	 		Datepicker.addPropertyChangeListener(this);
	 		
			datefield = new JFormattedTextField(DateFormat.getDateInstance(DateFormat.LONG));
			datefield.setForeground(Color.WHITE);
			datefield.setBackground(Color.BLACK);
			datefield.setBounds(131, 468, 150, 35);
			datefield.setBorder(BorderFactory.createEmptyBorder());
			datefield.setCaretColor(Color.WHITE);
			panel.add(datefield);
			
			JLabel callOffPrompt = new JLabel("Call Off/Scheduled Off:");
			callOffPrompt.setForeground(Color.WHITE);
			callOffPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			callOffPrompt.setBounds(10, 541, 359, 42);
			panel.add(callOffPrompt);
			
			callOffTextField = new JTextField("N/A");
			callOffTextField.setForeground(Color.WHITE);
			callOffTextField.setBackground(Color.BLACK);
			callOffTextField.setBounds(10, 578, 381, 35);
			callOffTextField.setBorder(BorderFactory.createEmptyBorder());
			callOffTextField.setCaretColor(Color.WHITE);
			panel.add(callOffTextField);
			
			JLabel studentsPrompt = new JLabel("Students:");
			studentsPrompt.setForeground(Color.WHITE);
			studentsPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			studentsPrompt.setBounds(10, 624, 359, 42);
			panel.add(studentsPrompt);
			
			studentsTextField = new JTextField("N/A");
			studentsTextField.setForeground(Color.WHITE);
			studentsTextField.setBackground(Color.BLACK);
			studentsTextField.setBounds(10, 662, 381, 35);
			studentsTextField.setBorder(BorderFactory.createEmptyBorder());
			studentsTextField.setCaretColor(Color.WHITE);
			panel.add(studentsTextField);
			
			JLabel mandatesPrompt = new JLabel("Mandates:");
			mandatesPrompt.setForeground(Color.WHITE);
			mandatesPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			mandatesPrompt.setBounds(10, 713, 359, 42);
			panel.add(mandatesPrompt);
			
			mandatesTextField = new JTextField("N/A");
			mandatesTextField.setForeground(Color.WHITE);
			mandatesTextField.setBackground(Color.BLACK);
			mandatesTextField.setBounds(10, 749, 381, 35);
			mandatesTextField.setBorder(BorderFactory.createEmptyBorder());
			mandatesTextField.setCaretColor(Color.WHITE);
			panel.add(mandatesTextField);
			
			JLabel notesPrompt = new JLabel("Notes:");
			notesPrompt.setForeground(Color.WHITE);
			notesPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			notesPrompt.setBounds(446, 11, 95, 42);
			panel.add(notesPrompt);
			
			//<----------------------------------------------------END DATE PICKER-------------------------------------------------->
			
           
			notestext = new JTextArea ("N/A");
			notestext.setForeground(Color.WHITE);
			notestext.setBackground(Color.BLACK);
			notestext.setBounds(350, 50, 275, 600);
			notestext.setWrapStyleWord(true);
			notestext.setBorder(BorderFactory.createEmptyBorder());
			notestext.setLineWrap(true);
			notestext.setCaretColor(Color.WHITE);
			panel.add(notestext);
			
	        // Add ActionListener to JTextArea
	        notestext.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                    notestext.append("\n"); // Append a space when Enter is pressed
	                    e.consume(); // Consume the Enter key event
	                }
	            }
	        });
			
			JScrollPane notesscroll = new JScrollPane(notestext);
			notesscroll.setBounds(446, 66, 300, 464);
			notesscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			notesscroll.setBorder(BorderFactory.createEmptyBorder());
			panel.add(notesscroll);
			
			JLabel ambulancesOnPrompt = new JLabel("Ambulances On:");
			ambulancesOnPrompt.setForeground(Color.WHITE);
			ambulancesOnPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			ambulancesOnPrompt.setBounds(446, 541, 359, 42);
			panel.add(ambulancesOnPrompt);
			
			String ba [] = {"Both Full 24","One For Full Shift", "One For Front Half","One For Back Half"};
			
			ambulancesOnComboBox = new JComboBox (ba);
			ambulancesOnComboBox.setForeground(Color.WHITE);
			ambulancesOnComboBox.setBackground(Color.BLACK);
			ambulancesOnComboBox.setFont(new Font("Monospaced", Font.BOLD, 14));
			ambulancesOnComboBox.setBounds (446, 578, 300, 35);
			ambulancesOnComboBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			panel.add(ambulancesOnComboBox);
			
			String numbers [] = {"x0","x1","x2","x3","x4","x5","x6","x7","x8","x9","x10","x11","x12","x13","x14","x15","x16","x17","x18","x19","x20",};
           
			bouthOutComboBox = new JComboBox(numbers);
			bouthOutComboBox.setForeground(Color.WHITE);
			bouthOutComboBox.setBackground(Color.BLACK);
			bouthOutComboBox.setFont(new Font("Monospaced", Font.BOLD, 14));
			bouthOutComboBox.setBounds(446, 662, 300, 35);
			bouthOutComboBox.setBorder(BorderFactory.createEmptyBorder());
			bouthOutComboBox.setBorder(null);
			panel.add(bouthOutComboBox);
			
			JLabel bothAmbulancesPrompt = new JLabel("Both Ambulances Out:");
			bothAmbulancesPrompt.setForeground(Color.WHITE);
			bothAmbulancesPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			bothAmbulancesPrompt.setBounds(446, 624, 359, 42);
			panel.add(bothAmbulancesPrompt);
			
			JLabel timeTradePrompt = new JLabel("Time Trades:");
			timeTradePrompt.setForeground(Color.WHITE);
			timeTradePrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			timeTradePrompt.setBounds(446, 713, 300, 42);
			panel.add(timeTradePrompt);
			
			timeTradeTextField = new JTextField("N/A");
			timeTradeTextField.setBackground(Color.BLACK);
			timeTradeTextField.setForeground(Color.WHITE);
			timeTradeTextField.setBounds(446, 749, 300, 35);
			timeTradeTextField.setBorder(BorderFactory.createEmptyBorder());
			timeTradeTextField.setCaretColor(Color.WHITE);
			panel.add(timeTradeTextField);
			
			DefaultListModel<String> truckListModel = new DefaultListModel<>();
			
			JList truckAssignmentList = new JList(truckListModel);
			truckAssignmentList.setForeground(Color.WHITE);
			truckAssignmentList.setBackground(Color.BLACK);
			truckAssignmentList.setBounds(804, 66, 300, 464);
			panel.add(truckAssignmentList);
			


			
			JLabel truckAssignmentPrompt = new JLabel("Truck Assignments:");
			truckAssignmentPrompt.setForeground(Color.WHITE);
			truckAssignmentPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			
			truckAssignmentPrompt.setBounds(804, 11, 300, 42);
			panel.add(truckAssignmentPrompt);
			

			
			DefaultListModel<String> runsListModel = new DefaultListModel<>();
			JList<String> runsList = new JList<>(runsListModel);
			runsList.setForeground(Color.WHITE);
			runsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			runsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			runsList.setVisibleRowCount(-1);
			runsList.setBackground(Color.BLACK);
            runsList.setPreferredSize(new Dimension(300, 4640)); // 10 times the normal height
            runsList.setBounds(1159, 68, 300, 464);
            panel.add(runsList);
		
			JScrollPane listScroller = new JScrollPane(runsList);
			listScroller.setBounds(1159, 68, 300, 464); 
			panel.add(listScroller);
			
			
			
			
			JLabel callsPrompt = new JLabel("Runs:");
			callsPrompt.setForeground(Color.WHITE);
			callsPrompt.setFont(new Font("Monospaced", Font.BOLD, 24));
			callsPrompt.setBounds(1159, 11, 300, 42);
			panel.add(callsPrompt);
			
			JButton saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String shiftSave = shiftComboBox.getSelectedItem().toString();
					String shiftLeaderSave = shiftLeaderComboBox.getSelectedItem().toString();
					String dateSave = datefield.getText();
					String notesSave = notestext.getText();
					String callOffSave = callOffTextField.getText();
					String studentsSave = studentsTextField.getText();
					String mandatesSave = mandatesTextField.getText();
					String trucksOnSave = ambulancesOnComboBox.getSelectedItem().toString();
					String bothAmbulancesOutSave = bouthOutComboBox.getSelectedItem().toString();
					String timeTradeSave = timeTradeTextField.getText();
					
					int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed? This will overwrite all your previous data.", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) 
					{
					//--------------------------------------SAVE METHOD FOR NOTES-------------------------------------
					try 
					{
						
						
						FileWriter fwNotesWriter = new FileWriter("Saves/" + enteredUserName+"qsnotes.txt");
						fwNotesWriter.write(notesSave);
						fwNotesWriter.close();
					}
					catch(IOException e1)
					{
						JOptionPane.showMessageDialog(null, "An error occurred saving the notes.");
						System.out.println("Error saving notes");
						return;
					}
				//-----------------------------------------END SAVE METHOD FOR NOTES------------------------------------
			    //-----------------------------------------Daily Log general selections---------------------------------
					try
					{
					FileWriter generalDailyLogSelections = new FileWriter ("Saves/" + enteredUserName +"qsgeneral.txt");
					generalDailyLogSelections.write(shiftSave + "\n" + shiftLeaderSave + "\n" + dateSave + "\n" + callOffSave +
							"\n" + studentsSave + "\n" + mandatesSave + "\n" + trucksOnSave + "\n" + bothAmbulancesOutSave +
							"\n" + timeTradeSave);
					generalDailyLogSelections.close();
					}
					catch(IOException e2)
					{
						JOptionPane.showMessageDialog(null, "An error occurred saving the general entries.");
						System.out.println("Error saving general entries");
						return;
					}
				//--------------------------------------------END Daily log general selection save-----------------------
					
				//--------------------------------------------Begin SAVE METHOD FOR TRUCK ASSIGNMENTS--------------------
					try
					{
					FileWriter truckAssignmentWriter = new FileWriter ("Saves/" + enteredUserName + "TruckAssignment.txt");
					ListModel<String> truckAssignmentModel = truckAssignmentList.getModel();
					int assigmentSize = truckAssignmentModel.getSize();
					
						for (int i = 0; i< assigmentSize; i++)
						{
							String truckEntry = truckAssignmentModel.getElementAt(i).toString();
							truckAssignmentWriter.write(truckEntry + "\n");
							truckAssignmentWriter.write("*\n");
						}
						truckAssignmentWriter.close();
					}
					catch(IOException e3)
					{
						JOptionPane.showMessageDialog(null, "An error occurred saving the Truck Assignments.");
						System.out.println("Error saving Truck Assignment entries");
						return;
					}
					
					
				//--------------------------------------------END SAVE METHOD FOR TRUCK ASSIGNMENTS----------------------
				//--------------------------------------------Begin Save method for run assignments----------------------
					try
					{
					FileWriter runAssignmentWriter = new FileWriter ("Saves/" + enteredUserName + "runAssignment.txt");
					ListModel<String> runAssignmentModel = runsList.getModel();
					int runAssigmentSize = runAssignmentModel.getSize();
					
						for (int i = 0; i< runAssigmentSize; i++)
						{
							String truckEntry = runAssignmentModel.getElementAt(i).toString();
							runAssignmentWriter.write(truckEntry + "\n");
							runAssignmentWriter.write("*\n");
						}
						runAssignmentWriter.close();
					}
					catch(IOException e3)
					{
						JOptionPane.showMessageDialog(null, "An error occurred saving the Run Assignments.");
						System.out.println("Error saving Run Assignment entries");
						return;
					}
					JOptionPane.showMessageDialog(null, "Daily Log saved successfully.");
					
				//--------------------------------------------end save method for run assignments------------------------
				}
				
		        else if (result == JOptionPane.NO_OPTION) 
		        {
		            return;
		            
		        }
				}
			});
			saveButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			saveButton.setBackground(Color.LIGHT_GRAY);
			saveButton.setBounds(982, 790, 177, 42);
			panel.add(saveButton);
			
			JButton loadButton = new JButton("Load");
			loadButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (loadSafetyCheck ==0) {
					int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed? This will overwrite all your current data.", "Confirmation", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) 
					{
						loadSafetyCheck=1;
					
				//----------------------------------------LOADING OF NOTES--------------------------------------------
					String Line = "";
					try
					{
						Line = new String (Files.readAllBytes(Paths.get("Saves/" + enteredUserName+"qsnotes.txt")));
						notestext.setText(Line);
					}
					catch(IOException e4)
					{
						JOptionPane.showMessageDialog(null, "An error occurred loading the saved notes.");
						System.out.println("Error loading notes entry");
						return;
					}
				//-----------------------------------------END LOADING OF NOTES-----------------------------------------
				//-----------------------------------------Begin Loading of general entries-----------------------------
					File generalFile = new File("Saves/" + enteredUserName +"qsgeneral.txt");
						try 
						{
							Scanner scanner = new Scanner(generalFile);
							
							String qlShift = scanner.nextLine().toString();
							shiftComboBox.setSelectedItem(qlShift);
							
							String qlShiftLeader = scanner.nextLine().toString();
							shiftLeaderComboBox.setSelectedItem(qlShiftLeader);
							
							String qlDate = scanner.nextLine().toString();
							datefield.setText(qlDate);
							
							String qlCallOff = scanner.nextLine().toString();
							callOffTextField.setText(qlCallOff);
							
							String qlStudents = scanner.nextLine().toString();
							studentsTextField.setText(qlStudents);
							
							
							String qlMandate = scanner.nextLine().toString();
							mandatesTextField.setText(qlMandate);
							
							String qlAmbulancesOn = scanner.nextLine().toString();
							ambulancesOnComboBox.setSelectedItem(qlAmbulancesOn);
							
							String qlBothAmbulanceOut = scanner.nextLine().toString();
							bouthOutComboBox.setSelectedItem(qlBothAmbulanceOut);
							
							String qlTimeTrades = scanner.nextLine().toString();
							timeTradeTextField.setText(qlTimeTrades);	
						}
						catch(IOException e5)
						{
							JOptionPane.showMessageDialog(null, "An error occurred loading the general entries.");
							System.out.println("Error loading general entries");
							return;
						}
				//-----------------------------------------End loading of general entries-------------------------------
						
				//-----------------------------------------BEGIN LOADING TRUCK ASSIGNMENTS------------------------------
						try
						{
							 BufferedReader truckAssignmentReader = new BufferedReader(new FileReader("Saves/" + enteredUserName + "TruckAssignment.txt"));
							
							 String truckAssignmentLine;
							 StringBuilder entryBuilder = new StringBuilder();
							 truckListModel.clear();
							 
							    while ((truckAssignmentLine = truckAssignmentReader.readLine()) != null) {
							        if (truckAssignmentLine.equals("*"))
							        {
							            
							            truckListModel.addElement(entryBuilder.toString());
							            
							            entryBuilder.setLength(0);
							        } 
							        else 
							        {
							            
							            entryBuilder.append(truckAssignmentLine).append("\n");
							        }
							    }

							    truckAssignmentReader.close();
							    truckAssignmentList.setModel(truckListModel);	 
							    truckAssignmentList.setSelectionModel(new DefaultListSelectionModel());
						}
						catch(IOException e6)
						{
							JOptionPane.showMessageDialog(null, "An error occurred loading the truck assignment entries.");
							System.out.println("Error loading truck assignment entries");
							return;
						}
						

				//-----------------------------------------END LOADING TRUCK ASSIGNMENTS--------------------------------
				//-----------------------------------------BEGIN LOADING RUN ASSIGNMENTS------------------------------
	
						
						try
						{
							 BufferedReader runAssignmentReader = new BufferedReader(new FileReader("Saves/" + enteredUserName + "runAssignment.txt"));
							
							 String runAssignmentLine;
							 StringBuilder entryBuilder = new StringBuilder();
							 runsListModel.clear();
							 
							    while ((runAssignmentLine = runAssignmentReader.readLine()) != null) {
							        if (runAssignmentLine.equals("*"))
							        {
							            
							        	runsListModel.addElement(entryBuilder.toString());
							            
							            entryBuilder.setLength(0);
							        } 
							        else 
							        {
							            
							            entryBuilder.append(runAssignmentLine).append("\n");
							        }
							    }

							    runAssignmentReader.close();
							    runsList.setModel(runsListModel);	 
							    runsList.setSelectionModel(new DefaultListSelectionModel());
						}
						catch(IOException e6)
						{
							JOptionPane.showMessageDialog(null, "An error occurred loading the run assignment entries.");
							System.out.println("Error loading run assignment entries");
							return;
						}
						JOptionPane.showMessageDialog(null, "Quick Load Successful.");
				//-----------------------------------------END LOADING RUN ASSIGNMENTS--------------------------------
					}
			        else if (result == JOptionPane.NO_OPTION) 
			        {
			            return;
			            
			        }
					}
					else {
						JOptionPane.showMessageDialog(null, "You have already loaded your previous save.");
					}
				}
			});
			loadButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			loadButton.setBackground(Color.LIGHT_GRAY);
			loadButton.setBounds(1169, 790, 177, 42);
			panel.add(loadButton);
			
			JButton submitButton = new JButton("Submit");
			submitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					  int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed? Please double check your submission before submitting.", "Confirmation", JOptionPane.YES_NO_OPTION);
					  //-----------------------------------------ALL PERTINENT SUBMISSION WORK DONE HERE-----------------------
				        if (result == JOptionPane.YES_OPTION) 
				        {
				            String shiftFinal = shiftComboBox.getSelectedItem().toString();
				            if(shiftFinal.equals(""))
				            {
				            	JOptionPane.showMessageDialog(null, "Your shift selection cannot be blank.");
				            	return;
				            }
				            String shiftLeaderFinal = shiftLeaderComboBox.getSelectedItem().toString();
				            if(shiftLeaderFinal.equals(""))
				            {
				            	JOptionPane.showMessageDialog(null, "Your shift leader selection cannot be blank.");
				            	return;
				            }
				            String dateFinal = datefield.getText();
				            if (dateFinal.equals(""))
				            {
				            	JOptionPane.showMessageDialog(null, "Your date selection cannot be blank.");
				            	return;
				            }
				            String callOffFinal = callOffTextField.getText();
				            String studentsFinal = studentsTextField.getText();
				            String mandatesFinal = mandatesTextField.getText();
				            String ambulancesOnFinal = ambulancesOnComboBox.getSelectedItem().toString();
				            if(ambulancesOnFinal.equals(""))
				            {
				            	JOptionPane.showMessageDialog(null, "Your ambulances on selection cannot be blank.");
				            	return;
				            }
				            String bothAmbulancesOutFinal = bouthOutComboBox.getSelectedItem().toString();
				            if(bothAmbulancesOutFinal.equals(""))
				            {
				            	JOptionPane.showMessageDialog(null, "You must enter an amount of times both ambulances were out.");
				            	return;
				            }
				            String timeTradeFinal = timeTradeTextField.getText();
				            String notesFinal = notestext.getText();
				            
				            //-------------------------------------------BEGIN GET TRUCK ASSIGNMENTS----------------------------
				            
				            if (truckAssignmentList.getModel().getSize() == 0) 
				            {
				                
				                JOptionPane.showMessageDialog(null, "Your crew assignments cannot be blank.");
				                return;
				                
				            }
				            else
				            {
				               
				                crewAssignmentsFinal = new StringBuilder();
				                ListModel<String> model = truckAssignmentList.getModel();
		                        crewMemberList = new ArrayList<>();
		                        hoursWorkedList = new ArrayList<>();
		                        Pattern pattern = Pattern.compile("Crewmember: (.*?)<br>.*?Hours Worked: (\\d+)<br>");
		                        
				                for (int i = 0; i < model.getSize(); i++) 
				                {
		                            String assignment = model.getElementAt(i);
		                            crewAssignmentsFinal.append(i + 1).append(". ").append(assignment).append("\n\n");
				                    
				                    Matcher matcher = pattern.matcher(assignment);
		                            if (matcher.find()) {
		                                String crewMember = matcher.group(1).trim();
		                                String hoursWorked = matcher.group(2).trim();
		                                crewMemberList.add(crewMember);
		                                hoursWorkedList.add(hoursWorked);
		                                
		                                int size = Math.min(crewMemberList.size(), hoursWorkedList.size());
		                                mergedArray = new String[size][2];
		                                for (int i1 = 0; i1 < size; i1++) {
		                                    mergedArray[i1][0] = crewMemberList.get(i1);
		                                    mergedArray[i1][1] = hoursWorkedList.get(i1);
		                                }
		                                
		                                
		                            }
				                }
				                if (model.getSize() > 0) 
				                {
				                    crewAssignmentsFinal.delete(crewAssignmentsFinal.length() - 2, crewAssignmentsFinal.length());
				                }
				                System.out.println("Crew Assignments:\n" + crewAssignmentsFinal.toString());
				                for (String[] pair : mergedArray) {
				                    System.out.println( pair[0] +" " + pair[1]);
				                }

				            }
				            //---------------------------------------------END GET TRUCK ASSIGNMENTS----------------------------------
				            //---------------------------------------------BEGIN GET RUN ASSIGNMENTS----------------------------------
				            StringBuilder runAssignmentFinal = new StringBuilder();
				            ListModel<String> model2 = runsList.getModel();
				            if (model2.getSize() == 0)
				            {
				                
				            	runAssignmentFinal.append("No runs made.");
				            	System.out.println("Run Assignments: None.");
				            }
				            else
				            {
					            for (int i = 0; i < model2.getSize(); i++) 
					            {
					            	runAssignmentFinal.append(i + 1).append(". ");
					            	runAssignmentFinal.append(model2.getElementAt(i));
					            	runAssignmentFinal.append("\n\n");
					            }
					            
					            if (model2.getSize() > 0) 
					            {
					            	runAssignmentFinal.delete(runAssignmentFinal.length() - 2, runAssignmentFinal.length());
					            }
					            System.out.println("Run Assignments:\n" + runAssignmentFinal.toString());
				            }
				            
				            //---------------------------------------------END GET RUN ASSIGNMENTS------------------------------------
				            

				            
				            //----------------------------------------------------BEGIN EMAIL--------------------------------
				      	      //<-------------------------------------------CHECK EMAIL LIST VALIDITY------------------------------->
				      		String checkPath = "EmailLists/dailylogemail.txt";

				      	        try (BufferedReader reader = new BufferedReader(new FileReader(checkPath)))
				      	        {
				      	            String line = reader.readLine();
				      	            if (line == null)
				      	            {
				      	            	JOptionPane.showMessageDialog(null, "There is currently nobody listed to receive your request.");
				      	                System.out.println("No Recipients found in the email file.");
				      	                return;
				      	            }
				      	        } 
				      	        catch (IOException e1) 
				      	        {
				      	            
				      	            System.err.println("Error reading the file: " + e1.getMessage());
				      	        }
				      	        
				      	        
				      		
				      	        //<-------------------------------------------END CHECK EMAIL LIST VALIDITY------------------------------->
								String locateUserNames = "EmailLists/dailylogemail.txt"; 
								
								  try (BufferedReader usersToEmail = new BufferedReader(new FileReader(locateUserNames))) 
								  {
							            String users;
							            while ((users = usersToEmail.readLine()) != null) 
							            {
							               System.out.println("User being emailed: " + users);
								           File userToBeEmailedFile = new File("User/Email/"+users+".txt");
								           System.out.println(userToBeEmailedFile);
								           
								           try (BufferedReader fileReader = new BufferedReader(new FileReader(userToBeEmailedFile))) 
								           {	LocalDate currentDate = LocalDate.now();
							                    String finalEmail;
							                    while ((finalEmail = fileReader.readLine()) != null) 
							                    {
							                        System.out.println("Content of " + userToBeEmailedFile + ": " + finalEmail);
							                        
							                        Properties properties = new Properties();
							 						properties.put("mail.smtp.auth", true);
							 						properties.put("mail.smtp.host", "smtp.gmail.com");
							 						properties.put("mail.smtp.port", 587);
							 						properties.put("mail.smtp.starttls.enable", true);
							 						properties.put("mail.transport.protocol" , "smtp");
							 						

													Session session = Session.getInstance(properties, new Authenticator() 
													{
														
														protected PasswordAuthentication getPasswordAuthentication() 
														{
															return new PasswordAuthentication("tfpdmailerbot@gmail.com", "fnltdivnqdvhlanh");
														}
														
													});
													
													try {
													    Message message = new MimeMessage(session);

													    
													    MimeMultipart multipart = new MimeMultipart("related");

													    // Add HTML text part
											            //ALL ITEMS GATHERED
											            //shiftFinal,shiftLeaderFinal,dateFinal,callOffFinal,studentsFinal,mandatesFinal,ambulancesOnFinal
											            //bothAmbulancesOutFinal,timeTradeFinal,notesFinal,crewAssignmentsFinal,runAssignmentFinal
													    
													    MimeBodyPart textPart = new MimeBodyPart();
													    String htmlContent = "<H2>"+ enteredUserName+ " has submitted a DailyLog from TFPD Manager for "+dateFinal+"</H2>"
													    		+ "<br><br><b>Shift:</b> " +shiftFinal + "<br><br><b>Shift Leader:</b> " + shiftLeaderFinal + "<br><br><b>Date: </b>" + dateFinal
													    		+"<br><br><b>Call Offs: </b>" + callOffFinal + "<br><br><b>Students: </b>" + studentsFinal +"<br><br><b>Mandates: </b>" +
													    		mandatesFinal +"<br><br><b>Ambulances on: </b>" + ambulancesOnFinal + "<br><br><b>Both Ambulances Out: </b>" + bothAmbulancesOutFinal
													    		+"<br><br><b>Time Trades: </b>" + timeTradeFinal +"<br><br><b>Notes:</b><br>" + notesFinal +"<br><br><b>Crews:</b><br>" + crewAssignmentsFinal
													    		+"<br><br><b>Run Log:</b><br>" + runAssignmentFinal + "<br><br><H3><b>End Report</H3></b>";

													    textPart.setContent(htmlContent, "text/html");
													    multipart.addBodyPart(textPart);

													    // Add image part
													    MimeBodyPart imagePart = new MimeBodyPart();
													    DataSource fds = new FileDataSource("TroyIcon.png"); 
													    imagePart.setDataHandler(new DataHandler(fds));
													    imagePart.setHeader("Content-ID", "<image>");

													    multipart.addBodyPart(imagePart);

													    // Set the content of the message to the MimeMultipart
													    message.setContent(multipart);

													    message.setSubject("DailyLog from: "+ enteredUserName + " for " +dateFinal);
													    message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(finalEmail));

													    Transport.send(message);
													    
													    System.out.println("The email has been sent to the user(s).");

														
														} 
														catch (MessagingException e1)
														{
																JOptionPane.showMessageDialog(null, "No Email was generated due to an error. The request has not been received.");
																e1.printStackTrace();
																
														} 
													
							                    }
				   
							                }
								           catch (IOException e1) 
								           {
								        	   JOptionPane.showMessageDialog(null, "Issue finding " +userToBeEmailedFile+" to receive email.");
							                    e1.printStackTrace();
							               }
							            }
							            

							        } 
								    catch (IOException e1) 
								    {
								    	JOptionPane.showMessageDialog(null, "Issue finding email roster.");
							            e1.printStackTrace();
							        }  
				            //END EMAIL
								  
						    // BEGIN DAILY LOG TEXT FORM CREATION---------------------------------------------------------------------------------
								  //BEGIN CHECKING FOR DIRECTORY EXISTENCE
							        Year year = Year.now();
							        LocalDate currentDate = LocalDate.now();
							        Month currentMonth = currentDate.getMonth();


							        Path physicalDailyLogYearPath = Paths.get("DailyLogs", String.valueOf(year));
							        Path physicalDailyLogMonthPath = Paths.get("DailyLogs", String.valueOf(year), String.valueOf(currentMonth));
							        String physicalDailyLogMonthString = physicalDailyLogMonthPath.toString();
								  
							        System.out.println("PhysicalDailyLogMonthPath: "+ physicalDailyLogMonthString);
								  if (Files.exists(physicalDailyLogYearPath))
								  {
							            System.out.println("Annual Directory Found");
							      }
								  else
								  {
									  try 
									  {
							               
							                Files.createDirectories(physicalDailyLogYearPath);
							                System.out.println("New Anuual Directory created successfully.");
						              } 
									  catch (IOException e1) 
									  {
							                
							                System.err.println("Error creating directory: " + e1.getMessage());
							          }
								  
								  }
								  if (Files.exists(physicalDailyLogMonthPath))
								  {
							            System.out.println("Monthly Directory Found");
							      }
								  else
								  {
									  try 
									  {
							               
							                Files.createDirectories(physicalDailyLogMonthPath);
							                System.out.println("New Monthly Directory created successfully.");
						              } 
									  catch (IOException e1) 
									  {
							                
							                System.err.println("Error creating directory: " + e1.getMessage());
							          }
								  }
									//END CHECKING FOR DIRECTORY EXISTENCE
									  
									  //BEGIN CREATING TEXT FORM
									
									  String dailyLogFileFinalPath = physicalDailyLogMonthString +  File.separator  + dateFinal + ".txt";
									  System.out.println("Final Path = " + dailyLogFileFinalPath);
									  
									  File dailyLogTextFile = new File(dailyLogFileFinalPath);
									  if(dailyLogTextFile.exists())
									  {
										  System.out.println("File already exists");
							                try (BufferedWriter writer = new BufferedWriter(new FileWriter(dailyLogTextFile))) 
							                {
							                    
							                    writer.write("Date: " + dateFinal + "\n\nShift Leader: "+ shiftLeaderFinal + "\n\nShift: " + shiftFinal +"\n\nCall Offs:\n"+
							                    		callOffFinal+ "\n\nStudents:\n" + studentsFinal + "\n\nMandates:\n" + mandatesFinal +"\n\nAmbulances On:\n"+ ambulancesOnFinal + 
							                    		"\n\nBoth Ambulances Out:\n" + bothAmbulancesOutFinal + "\n\nTime Trades:\n" + timeTradeFinal + "\n\nNotes: \n" +notesFinal +
							                    		"\n\nCrews:\n" + crewAssignmentsFinal +"\n\nRun Log: \n" + runAssignmentFinal);
							                    
							                    System.out.println("Content written to the file.");
							                } 
							                catch (IOException e1)
							                {
							                    System.err.println("Error writing to the file: " + e1.getMessage());
							                }
									  }
									  else
									  {
								        try 
								        {
								        	dailyLogTextFile.createNewFile();

								                System.out.println("File created successfully");
								                try (BufferedWriter writer = new BufferedWriter(new FileWriter(dailyLogTextFile))) 
								                {
								                    
								                    writer.write("Date: " + dateFinal + "\n\nShift Leader: "+ shiftLeaderFinal + "\n\nShift: " + shiftFinal +"\n\nCall Offs:\n"+
								                    		callOffFinal+ "\n\nStudents:\n" + studentsFinal + "\n\nMandates:\n" + mandatesFinal +"\n\nAmbulances On:\n"+ ambulancesOnFinal + 
								                    		"\n\nBoth Ambulances Out:\n" + bothAmbulancesOutFinal + "\n\nTime Trades:\n" + timeTradeFinal + "\n\nNotes: \n" +notesFinal +
								                    		"\n\nCrews:\n" + crewAssignmentsFinal +"\n\nRun Log: \n" + runAssignmentFinal);
								                    
								                    System.out.println("Content written to the file.");
								                } 
								                catch (IOException e1)
								                {
								                    System.err.println("Error writing to the file: " + e1.getMessage());
								                }
								            

								        } 
								        catch (IOException e1) 
								        {
								            
								            System.err.println("Error creating file: " + e1.getMessage());
								        }
									  }
									  
								  
								  
						    // END DAILY LOG TEXT FORM CREATION-----------------------------------------------------------------------------------
							// Begin Collecting report Data---------------------------------------------------------------------------------------
									  //shiftFinal,shiftLeaderFinal,dateFinal,callOffFinal,studentsFinal,mandatesFinal,ambulancesOnFinal
							            //bothAmbulancesOutFinal,timeTradeFinal,notesFinal,crewAssignmentsFinal,runAssignmentFinal
									  
									  //---------------------------------------THIS SECTION GATHERS HOURS WORKED
									  for(String[] pair : mergedArray) {
										    String userName = pair[0];
										    String hoursWorked = pair[1];
										    
										    // Construct the file path
										    String filePath = "HoursWorked/" + userName + ".txt";
										    
										    // Read the existing value from the file or initialize to 0 if the file doesn't exist
										    int existingHours = 0;
										    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
										        String line;
										        if ((line = reader.readLine()) != null) {
										            existingHours = Integer.parseInt(line);
										        }
										    } catch (IOException | NumberFormatException e1) {
										        e1.printStackTrace();
										    }
										    
										    // Add the new value to the existing value
										    int newHours = existingHours + Integer.parseInt(hoursWorked);
										    
										    // Write the updated value back to the file
										    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
										        writer.write(String.valueOf(newHours));
										    } catch (IOException e1) {

										        e1.printStackTrace();
										    }
										}
									  //-------------------------------------------END GATHERING HOURS WORKED
									  //BEGIN DAILY LOG COMPONENT GATHERING------------------------------------------
									  
									  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
									  try {
										  LocalDate dateOutput = LocalDate.parse(dateFinal, formatter);
										  System.out.println("DATE OUTPUT = " +dateOutput);
										  
										  String monthOutput = dateOutput.getMonth().toString();
										  String yearOutput = Integer.toString(dateOutput.getYear());
										  
										  File monthlyReportFile = new File ("Reports/dailyLogReports/"+yearOutput+"/"+monthOutput+"/monthlyReport.txt");
										  	if(!monthlyReportFile.getParentFile().exists()) {
										  		monthlyReportFile.getParentFile().mkdirs();
										  	}
										    if(!monthlyReportFile.exists()) {
										  		monthlyReportFile.createNewFile();
										  		
										  	}
										    
										    
										    try (FileWriter fileWriter = new FileWriter(monthlyReportFile, true)) {
										    	 String contentToWrite = "\n\nDate: " + dateFinal + "\n\n+Run Log:+\n" + runAssignmentFinal + "\n\n";
									                fileWriter.write(contentToWrite);
								            } catch (IOException e2) {
								                System.out.println("Failed to write to the monthly report file");
								            }

										  
										  
									  }
									  catch(DateTimeParseException e1){
										  System.out.println("Failed to parse date.");
									  } 
									  catch (IOException e1)
									  {
										
										System.out.println("Failed to create monthly report file");
									  }
									  
									  
									  
									  //END DAILY LOG COMPONENT GATHERING
									 
									  
									  try 
									  {
										  LocalDate date = tryParse(dateFinal);
								          String reportMonth = date.getMonth().toString();
								          int reportYear = date.getYear();
								          
								          System.out.println("Report date: "+ dateFinal+"\nReport Month: " + reportMonth+"\nReport Year: " + reportYear);
								          
										  //BEGIN CHECKING FOR DIRECTORY EXISTENCe


									        Path reportsDailyLogReportsYearPath = Paths.get("Reports", "dailyLogReports",String.valueOf(reportYear));
									        Path reportsDailyLogReportsMonthPath = Paths.get("Reports", "dailyLogReports", String.valueOf(reportYear), String.valueOf(reportMonth));
									        String dailyLogReportString = reportsDailyLogReportsMonthPath.toString();
										  
									        System.out.println("PhysicalDailyLogMonthPath: "+ dailyLogReportString);
										  if (Files.exists(reportsDailyLogReportsYearPath))
										  {
									            System.out.println("Annual Report Directory Found");
									      }
										  else
										  {
											  try 
											  {
									               
									                Files.createDirectories(reportsDailyLogReportsYearPath);
									                System.out.println("New Anuual DailyLog Report Directory created successfully.");
								              } 
											  catch (IOException e1) 
											  {
									                
									                System.err.println("Error creating DailyLog Annual Report directory: " + e1.getMessage());
									          }
										  
										  }
										  if (Files.exists(reportsDailyLogReportsMonthPath))
										  {
									            System.out.println("Monthly DailyLog Report Directory Found");
									      }
										  else
										  {
											  try 
											  {
									               
									                Files.createDirectories(reportsDailyLogReportsMonthPath);
									                System.out.println("New DailyLog Report Monthly Directory created successfully.");
								              } 
											  catch (IOException e1) 
											  {
									                
									                System.err.println("Error creating DailyLog Monthly Report directory: " + e1.getMessage());
									          }
										  }
											//END CHECKING FOR DIRECTORY EXISTENCE
								          
								          
								          
								          
									  }
							         catch (DateTimeParseException e1) 
									  {
							            System.err.println("Invalid date format: " + dateFinal);
							            JOptionPane.showMessageDialog(null, "There was an issue specifying the daily log report date to update report file.");
							          }
									  

								
				            
				            //----------------------------------------------END ALL PERTINENT SUBMISSION WORK-----------------
								  
								  JOptionPane.showMessageDialog(null, "Your Daily log has been submitted.");
								  new CentralHub( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail);
								  dailyLogFrame.dispose();
			        } 
				        
			        else if (result == JOptionPane.NO_OPTION) 
			        {
			            return;
			            
			        }
				        

					  
					  
					  
				}
			});
			submitButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			submitButton.setBackground(Color.LIGHT_GRAY);
			submitButton.setBounds(795, 790, 177, 42);
			panel.add(submitButton);
			
			JButton addCrewButton = new JButton("Add Crew");
			addCrewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					new AddCrew( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, dailyLogFrame,truckListModel);
					dailyLogFrame.setEnabled(false);
				}
			});
			addCrewButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			addCrewButton.setBackground(Color.LIGHT_GRAY);
			addCrewButton.setBounds(861, 541, 177, 42);
			panel.add(addCrewButton);
			
			JButton btnDeleteCrew = new JButton("Delete Crew");
			btnDeleteCrew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedIndex = truckAssignmentList.getSelectedIndex();
					if(selectedIndex == -1)
					{
						JOptionPane.showMessageDialog(null, "You must select a crew assignment to remove.");
						return;
					}
					else
					{
			            DefaultListModel<String> truckListModel = (DefaultListModel<String>) truckAssignmentList.getModel();
			            truckListModel.remove(selectedIndex);
					}
					
				}
			});
			btnDeleteCrew.setFont(new Font("Monospaced", Font.BOLD, 20));
			btnDeleteCrew.setBackground(Color.LIGHT_GRAY);
			btnDeleteCrew.setBounds(861, 612, 177, 42);
			panel.add(btnDeleteCrew);
			
			JButton addRunButton = new JButton("Add Run");
			addRunButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddRun( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail, dailyLogFrame,runsListModel);
					dailyLogFrame.setEnabled(false);
				}
			});
			addRunButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			addRunButton.setBackground(Color.LIGHT_GRAY);
			addRunButton.setBounds(1223, 541, 177, 42);
			panel.add(addRunButton);
			
			JButton deleteRunButton = new JButton("Delete Run");
			deleteRunButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					int selectedIndex = runsList.getSelectedIndex();
					if(selectedIndex == -1)
					{
						JOptionPane.showMessageDialog(null, "You must select a run assignment to remove.");
						return;
					}
					else
					{
			            DefaultListModel<String> runListModel = (DefaultListModel<String>) runsList.getModel();
			            runListModel.remove(selectedIndex);
					}
					
				
				}
			});
			deleteRunButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			deleteRunButton.setBackground(Color.LIGHT_GRAY);
			deleteRunButton.setBounds(1223, 612, 177, 42);
			panel.add(deleteRunButton);
			
			JButton emailListButton = new JButton("Email List");
			emailListButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					new DailyLogRecipients( enteredUserName,  enteredPassword,  adminStatus,  superAdminStatus,  userEmail,  dailyLogFrame);
					dailyLogFrame.setEnabled(false);
				}
			});
			emailListButton.setFont(new Font("Monospaced", Font.BOLD, 20));
			emailListButton.setBackground(Color.LIGHT_GRAY);
			emailListButton.setBounds(1048, 713, 177, 42);
			panel.add(emailListButton);
			if (superAdminStatus == false)
			{
				emailListButton.setVisible(false);
			}
			else
			{
				emailListButton.setVisible(true);
			}
           
           
           
           
           
           
           
           
           
           dailyLogFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		Datepicker.setLocation(datefield.getLocationOnScreen().x,
				(datefield.getLocationOnScreen().y + datefield.getHeight()));
		Date selectedDate = (Date) datefield.getValue();
		datefield.setValue(selectedDate);
		Datepicker.setVisible(true);
			
		}

	public void propertyChange(PropertyChangeEvent event) {
		
		if(event.getPropertyName().equals("selectedDate"))
		{
			java.util.Calendar cal = (java.util.Calendar)event.getNewValue();
			Date selDate = cal.getTime();
			datefield.setValue(selDate);
		}
		
	}
	
    private static LocalDate tryParse(String dateFinal) {
        // Define possible date patterns
        String[] patterns = {
                "MMMM d, yyyy",
                "MMM d, yyyy",
                "MMMM dd, yyyy",
                "MMM dd, yyyy"
                // Add more patterns if needed
        };
        
        for (String pattern : patterns) {
            try {
                return LocalDate.parse(dateFinal, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException ignored) {
                // Try the next pattern if parsing fails
            }
        }
        throw new DateTimeParseException("No matching pattern found", dateFinal, 0);
    }
}

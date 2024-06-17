package tfpdManagertwo;

import java.io.File;

import javax.swing.JFrame;

public class SecurityAuthenticator {
	
	Boolean superAdminStatus,AdminStatus;
	
	SecurityAuthenticator(String enteredUserName, String enteredPassword, JFrame loginFrame){
		
		File superAdmin = new File("User/Permissions/SuperAdmin/"+enteredUserName+".txt");
		if(superAdmin.exists())
		{
			superAdminStatus = true;
			System.out.println("SuperAdmin File found.");
			System.out.println("Super Admin set to: " + superAdminStatus);
		}
		else
		{
			superAdminStatus = false;
			System.out.println("SuperAdmin File not found.");
			System.out.println("Super Admin set to: " + superAdminStatus);
		}
		
		File Admin = new File("User/Permissions/Admin/"+enteredUserName+".txt");
		if(Admin.exists())
		{
			AdminStatus = true;
			System.out.println("Admin File found.");
			System.out.println("Admin set to: " + AdminStatus);
		}
		else
		{
			AdminStatus = false;
			System.out.println("Admin File not found.");
			System.out.println(" Admin set to: " + AdminStatus);
		}
		
		new EmailAuthenticator(enteredUserName,enteredPassword,AdminStatus,superAdminStatus,loginFrame);

	}
		
	

}

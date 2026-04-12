package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import negocio.Encryption;
import negocio.Hashing;
import persistencia.FileManipulation;

public class TelaLogin extends JFrame{
	private JTextField password = new JTextField();
	private JLabel passwordLabel = new JLabel("Senha: ");
	private JButton login = new JButton("Login");
	
	public TelaLogin(String mainFolderPath) {
		setTitle("ProtoNota - Login");
		setSize(500, 400);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//place elements
		password.setBounds(100,10,200,20);
		passwordLabel.setBounds(10,10,200,20);
		login.setBounds(10,30,200,20);
		
		//add elements
		add(password);
		add(passwordLabel);
		add(login);
		
		//System.out.println("df " + mainFolderPath);
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String passwordHashedFromFile = "";
				String passwordHashedNow = "";
				
				try {
					//System.out.println(mainFolderPath);
					passwordHashedFromFile += Encryption.ProgramDecrypt(FileManipulation.readFirstLineFrom(mainFolderPath.concat("/StateFile.txt")));
					//System.out.println("phff: " + passwordHashedFromFile);
					passwordHashedNow += Hashing.Hash(password.getText());
					//System.out.println("phn: " + passwordHashedNow);

				} catch  (Exception e1){
					e1.printStackTrace();
				}
				//check if password is correct
				if(passwordHashedFromFile.equals(passwordHashedNow)) {
					dispose();
					//open main screen
					new TelaPrincipal(mainFolderPath).setVisible(true);
				}
			}
		});
	}
}

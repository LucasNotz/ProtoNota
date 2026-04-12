package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import negocio.Encryption;
import negocio.Hashing;
import persistencia.FileManipulation;

public class TelaRegistro extends JFrame {
	private JTextField password = new JTextField();
	private JTextField passwordConfirm = new JTextField();
	private JLabel passwordLabel = new JLabel("Nova Senha");
	private JLabel passwordConfirmLabel = new JLabel("Confirmar Senha");
	private JButton createPass = new JButton("Definir senha");
	private JButton exitProcess = new JButton("Sair e não registrar senha");
	private JLabel caution = new JLabel("<html>Se a aplicação for fechada por outro meio a não <br/> "
			+ "ser pelo butão designado, será necessário ir à página do github <br/>"
			+ "para descobrir o que deve ser feito.</html>");
	
	public TelaRegistro(String mainFolderPath) {
		//Configure frame 
		setTitle("ProtoNota - Configuração inicial");
		setSize(500, 300);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		//Configure labels and inputs
		password.setBounds(150,10,200,30);
		passwordConfirm.setBounds(150,40,200,30);
		passwordLabel.setBounds(10,10,100,20);
		passwordConfirmLabel.setBounds(10,40,100,20);
		createPass.setBounds(145,100,200,25);
		exitProcess.setBounds(145,130,200,25);
		caution.setBounds(10,50,400,300);
		
		//Place labels and inputs
		add(password);
		add(passwordConfirm);
		add(passwordLabel);
		add(passwordConfirmLabel);
		add(createPass);
		add(exitProcess);
		add(caution);
		
		//Create pass actionListener
		createPass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Data check
				if(password.getText().length() >= 10) {
					//Confirm password
					if(passwordConfirm.getText().equals(password.getText())) {
						try {
							//hash password, then encrypt, then store to statefile
							FileManipulation.OverwriteThisToThat(Encryption.ProgramEncrypt(Hashing.Hash(password.getText())), mainFolderPath.concat("/StateFile.txt"));
							dispose();
							new TelaLogin(mainFolderPath).setVisible(true);
						} catch (Exception e1) {
							e1.printStackTrace();
							dispose();
						}
					}
				}
			}
		});
		
		//Delete zProtoNotaFoler so program can do "first run" again
		exitProcess.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String infoSistema = "";
				Process processo = null;
				try {
					processo = Runtime.getRuntime().exec("rm -rf zProtoNotaFolder");
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
					dispose();
				}
			}
		});
	}
}

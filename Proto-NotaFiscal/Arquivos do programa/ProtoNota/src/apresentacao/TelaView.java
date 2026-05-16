package apresentacao;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import negocio.BaseUtil;
import negocio.CEP;
import negocio.Encryption;
import persistencia.FileManipulation;

public class TelaView extends JFrame{
	private JLabel titleLabel = new JLabel("Título: ");
	private JTextField title = new JTextField();
	private JButton saveImage = new JButton("Salvar Imagem");
	private JLabel descriptionLabel = new JLabel("Descrição");
	private JTextArea description = new JTextArea();
	private JButton delete = new JButton("Deletar");
	private JTextField cepAPIinput = new JTextField();
	private JButton cepAPIbutton = new JButton("Consultar CEP");
	//private JTextArea cepResults = new JTextArea();
	private JList<String> cepList = new JList<String>();
	private JScrollPane cepScroll = new JScrollPane(cepList);
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	
	
	private String img64Unencrypted = "";
	private String noteTitle = "";
	private String noteDescription = "";
	
	public TelaView(String mainFolderPath, String fileName) {
		setTitle("ProtoNota - View Note");
		setSize(300, 600);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//System.out.println(mainFolderPath + "/" + fileName);
		
		//place
		titleLabel.setBounds(10,10,100,20);
		title.setBounds(70,10,200,30);
		saveImage.setBounds(90, 60, 120, 25);
		descriptionLabel.setBounds(10,90,200,20);
		description.setBounds(10,120,280,200);
		delete.setBounds(10,330,100,25);
		
		//add
		add(titleLabel);
		add(title);
		add(saveImage);
		add(descriptionLabel);
		add(description);
		add(delete);
		
		//configure
		title.setEditable(false);
		description.setEditable(false);
		
		//read values
		String tempAll = FileManipulation.readFromThis(mainFolderPath + "/" + fileName);
		//System.out.println(tempAll);
		
		//define and separate values
		
		//title
		noteTitle = fileName.substring(0, fileName.length() -4);
		title.setText(noteTitle);
		
		//description and image
		String[] tempAllDivided = tempAll.split("ü");
		String tempDescription = tempAllDivided[0];
		String tempImage = tempAllDivided[1];
		//System.out.println(tempDescription);
		//System.out.println(tempImage);
		
		//decrypt image
		try {
			img64Unencrypted = Encryption.ProgramDecrypt(tempImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//decrypt description
		try {
			noteDescription = Encryption.ProgramDecrypt(tempDescription);
		} catch (Exception e) {
			e.printStackTrace();
		}
		description.setText(noteDescription);
		
		
		
		
		
		saveImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//create temp img file
				File tmpImg = new File(mainFolderPath + "/tmpImg.txt");
				try {
					tmpImg.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				FileManipulation.OverwriteThisToThat(img64Unencrypted, (mainFolderPath + "/tmpImg.txt"));
				
				try {
					BaseUtil.decodeImage((mainFolderPath + "/tmpImg.txt"), (mainFolderPath + "/" + noteTitle + "Image.png"));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				try {
					Image picture = ImageIO.read(new File((mainFolderPath + "/" + noteTitle + "Image.png")));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				FileManipulation.DeleteFile((mainFolderPath + "/tmpImg.txt"));
				
				JOptionPane.showMessageDialog(null, "Imagem foi salva em: " + mainFolderPath);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmDeleteNote = JOptionPane.showConfirmDialog(null, "Deletar nota?", "Confirmar", JOptionPane.YES_NO_OPTION);
				if (confirmDeleteNote == JOptionPane.YES_OPTION) {
					FileManipulation.DeleteFile(mainFolderPath + "/" + fileName);
					TelaPrincipal.refresh(mainFolderPath);
					dispose();
					
				} else {
					dispose();
					
				}
			}
		});
		
		cepAPIinput.setBounds(10,370,200,25);
		add(cepAPIinput);
		
		cepAPIbutton.setBounds(10,400,200,25);
		add(cepAPIbutton);
		
		cepAPIbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					dlm.clear();
					String dados = CEP.getCEP(cepAPIinput.getText());
					String[] dadosProcessados = CEP.seperateJSONby(dados, ',', 10);
					for (String i : dadosProcessados) {
						dlm.addElement(i);
					}
					cepList.setModel(dlm);
					cepScroll.setBounds(10, 440, 280,100);
					add(cepScroll);
					System.out.println(dadosProcessados[1]);
				} catch (IOException e1) {
					e1.printStackTrace();
				}				
			}
		});
	}
}
package apresentacao;


import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import negocio.BaseUtil;
import negocio.Encryption;
import persistencia.FileManipulation;

public class TelaView extends JFrame{
	private JLabel titleLabel = new JLabel("Título: ");
	private JTextField title = new JTextField();
	private JButton saveImage = new JButton("Salvar Imagem");
	private JLabel descriptionLabel = new JLabel("Descrição");
	private JTextArea description = new JTextArea();
	private JButton delete = new JButton("Deletar");

	
	private String img64Unencrypted = "";
	private String noteTitle = "";
	private String noteDescription = "";
	
	public TelaView(String mainFolderPath, String fileName) {
		setTitle("ProtoNota - View Note");
		setSize(300, 400);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		//System.out.println(mainFolderPath + "/" + fileName);
		
		//place
		titleLabel.setBounds(10,10,100,20);
		title.setBounds(70,10,200,30);
		saveImage.setBounds(90, 60, 120, 25);
		descriptionLabel.setBounds(10,90,200,20);
		description.setBounds(10,120,280,200);
		delete.setBounds(10,330,100,25);
		
		//add
		add(titleLabel);
		add(title);
		add(saveImage);
		add(descriptionLabel);
		add(description);
		add(delete);
		
		//configure
		title.setEditable(false);
		description.setEditable(false);
		
		//read values
		String tempAll = FileManipulation.readFromThis(mainFolderPath + "/" + fileName);
		//System.out.println(tempAll);
		
		//define and separate values
		
		//title
		noteTitle = fileName.substring(0, fileName.length() -4);
		title.setText(noteTitle);
		
		//description and image
		String[] tempAllDivided = tempAll.split("ü");
		String tempDescription = tempAllDivided[0];
		String tempImage = tempAllDivided[1];
		//System.out.println(tempDescription);
		//System.out.println(tempImage);
		
		//decrypt image
		try {
			img64Unencrypted = Encryption.ProgramDecrypt(tempImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//decrypt description
		try {
			noteDescription = Encryption.ProgramDecrypt(tempDescription);
		} catch (Exception e) {
			e.printStackTrace();
		}
		description.setText(noteDescription);
		
		
		
		
		
		saveImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//create temp img file
				File tmpImg = new File(mainFolderPath + "/tmpImg.txt");
				try {
					tmpImg.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				FileManipulation.OverwriteThisToThat(img64Unencrypted, (mainFolderPath + "/tmpImg.txt"));
				
				try {
					BaseUtil.decodeImage((mainFolderPath + "/tmpImg.txt"), (mainFolderPath + "/" + noteTitle + "Image.png"));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				try {
					Image picture = ImageIO.read(new File((mainFolderPath + "/" + noteTitle + "Image.png")));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				FileManipulation.DeleteFile((mainFolderPath + "/tmpImg.txt"));
				
				JOptionPane.showMessageDialog(null, "Imagem foi salva em: " + mainFolderPath);
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirmDeleteNote = JOptionPane.showConfirmDialog(null, "Deletar nota?", "Confirmar", JOptionPane.YES_NO_OPTION);
				if (confirmDeleteNote == JOptionPane.YES_OPTION) {
					FileManipulation.DeleteFile(mainFolderPath + "/" + fileName);
					TelaPrincipal.refresh(mainFolderPath);
					dispose();
					
				} else {
					dispose();
					
				}
			}
		});

		
	}
}

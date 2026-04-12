package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import negocio.BaseUtil;
import negocio.Encryption;
import persistencia.FileManipulation;

public class TelaAdd extends JFrame{
	private JLabel titleLabel = new JLabel("Título: ");
	private JTextField title = new JTextField();
	private JButton chooseImage = new JButton("Selecione Imagem");
	private JLabel imageStatus = new JLabel("Sem imagem");
	private JButton previewImage = new JButton("Preview");
	private JLabel descriptionLabel = new JLabel("Descrição");
	private JTextArea description = new JTextArea();
	private JButton saveNota = new JButton("Salvar Nota");
	private JButton cancel = new JButton("Cancelar");
	
	private String img64Encrypted = "";
	private String noteTitle = "";
	private String noteDescription = "";
	
	public TelaAdd(String mainFolderPath) {
		setTitle("ProtoNota - Nova Nota");
		setSize(300, 400);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		//place
		titleLabel.setBounds(10,10,100,20);
		title.setBounds(100,10,200,20);
		chooseImage.setBounds(10,30,200,20);
		imageStatus.setBounds(10,60,200,20);
		previewImage.setBounds(210, 60, 100, 20);
		descriptionLabel.setBounds(10,90,200,20);
		description.setBounds(10,120,280,200);
		saveNota.setBounds(10,330,200,20);
		cancel.setBounds(100,330,200,20);
		
		//add
		add(titleLabel);
		add(title);
		add(chooseImage);
		add(imageStatus);
		add(previewImage);
		add(descriptionLabel);
		add(description);
		add(saveNota);
		add(cancel);
		
		//create new temp note file (will be deleted if operation is not finished)
		// if created it will be renamed (or copied into a new file and the temp deleted)
		/*File newFile = new File(mainFolderPath.toString() + "/temp.txt");
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		//inicial button configuration
		previewImage.setEnabled(false);
		description.setLineWrap(true);
		
		//chooseimage configuration
		chooseImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File img = getFileName();
				imageStatus.setText("IMAGEM ADICIONADA");
				System.out.println("file img: " + img);
				//System.out.println(getExtension(img.toString()));
				
				if(getExtension(img.toString()).equals(".jpg") || getExtension(img.toString()).equals(".png") || getExtension(img.toString()).equals(".jpeg")) {
					previewImage.setEnabled(true);
					chooseImage.setEnabled(false);
					
					//image to base64
					try {
						BaseUtil.encodeImage(img.toString(), mainFolderPath.concat("/tempUn.txt"));
						img64Encrypted = Encryption.ProgramEncrypt(FileManipulation.readFromThis(mainFolderPath + "/tempUn.txt"));
						FileManipulation.DeleteFile(mainFolderPath.concat("/tempUn.txt"));
						//String img64Unencrypted = Encryption.ProgramDecrypt(img64Encrypted);
						System.out.println("En: " + img64Encrypted);
						//System.out.println("Un: " + img64Unencrypted);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		
		//cancel
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dispose();
			}
		});
		
		saveNota.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(img64Encrypted);
				//System.out.println(title.getText());
				//System.out.println(description.getText());
				
				//Data check
				if(title.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Escolha um título");
					return;
				}
				if(description.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Escolha uma descrição");
					return;
				}
				if(img64Encrypted.equals("")) {
					JOptionPane.showMessageDialog(null, "Escolha uma imagem ou reinicie o processo");
					return;
				}
				
				//Create file with title name
				File newNote = new File(mainFolderPath + "/" + title.getText() + ".txt");
				try {
					newNote.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//encrypt and add description
				String encryptedDescription = "";
				try {
					encryptedDescription = Encryption.ProgramEncrypt(description.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				FileManipulation.OverwriteThisToThat(encryptedDescription, newNote.toString());
				
				//add separator ü
				FileManipulation.AppendThisToThat("ü", newNote.toString());
				
				//add encrypted img
				FileManipulation.AppendThisToThat(img64Encrypted, newNote.toString());
				
				dispose();
				TelaPrincipal.refresh(mainFolderPath);
			}
		});
		
		
	}
	
	public static File getFileName() {
		File filepath = null;
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileFilter(new FileFilter() {

			   public String getDescription() {
			       return "JPG Images (*.jpg)";
			   }

			   public boolean accept(File f) {
			       if (f.isDirectory()) {
			           return true;
			       } else {
			           String filename = f.getName().toLowerCase();
			           return filename.endsWith(".jpg") || filename.endsWith(".jpeg") ;
			       }
			   }
			});
		chooser.setFileFilter(new FileFilter() {

			   public String getDescription() {
			       return "PNG Images (*.png)";
			   }

			   public boolean accept(File f) {
			       if (f.isDirectory()) {
			           return true;
			       } else {
			           String filename = f.getName().toLowerCase();
			           return filename.endsWith(".png");
			       }
			   }
			});

		
		chooser.setCurrentDirectory(new File("."));
		int result = chooser.showOpenDialog(chooser);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			filepath = new File(selectedFile.getAbsolutePath());
		}
		
		return filepath;
	}
	
	public static String getExtension(String file) {
		String extension = "";
		int i = file.lastIndexOf(".");
		extension = file.substring(i);
		return extension;
	}
	
	public byte[] extractBytes(String ImageName,String extension) throws IOException {
		File fnew=new File(ImageName);
		BufferedImage originalImage=ImageIO.read(fnew);
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ImageIO.write(originalImage, extension, baos );
		byte[] imageInByte=baos.toByteArray();
		return imageInByte;
		}
	
}

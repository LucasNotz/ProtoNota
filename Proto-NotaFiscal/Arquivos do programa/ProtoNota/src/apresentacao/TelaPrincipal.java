package apresentacao;

import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class TelaPrincipal extends JFrame{
	private JList<String> list = new JList<String>();
	private JScrollPane scroll = new JScrollPane(list);
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JButton addNota = new JButton("Novo");
	
	TelaPrincipal(String mainFolderPath) {
		setTitle("ProtoNota - TelaPrincipal");
		setSize(500, 400);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//place elements
		scroll.setBounds(10,10,480,300);
		addNota.setBounds(10,330,100,20);
		
		//loop through all files in folder and display their names, execpt for statefile
		File folder = new File(mainFolderPath);
		File[] listOfFiles = folder.listFiles();
		
		if(listOfFiles != null) {
			for(File child: listOfFiles) {
				//do nothing if it is the statefile
				if(child.compareTo(new File(mainFolderPath.concat("/StateFile.txt").toString())) == 0) {
					continue;
				}
				//add to dlm
				dlm.addElement(child.getName());
			}
		}
		
		list.setModel(dlm);
		
		//add elements to frame
		add(scroll);
		add(addNota);
		
		
	}
}

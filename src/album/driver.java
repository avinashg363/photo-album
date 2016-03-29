package album;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;



import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class driver extends JFrame {
    public int i;
	private JPanel contentPane;
	private JTextField textField;
	private static ArrayList p=new ArrayList <photo>();
    JFileChooser filechooser = new JFileChooser();
    BufferedImage img,image;
    File file;
    private JTextField textField_1;
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		loadFromFile();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					driver frame = new driver();
					frame.setTitle("PHOTO ALBUM");
					
					frame.addWindowListener(new java.awt.event.WindowAdapter() {
					    @Override
					    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					        if (JOptionPane.showConfirmDialog(frame, 
					            "Are you sure to close this window?", "Really Closing?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					        	try {
									saveToFile();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					    	
					            System.exit(0);
					        }
					    }
					});
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void loadFromFile() throws IOException, ClassNotFoundException {
		ObjectInputStream is;
		int coursecount;
		try{
			is = new ObjectInputStream(new FileInputStream("photo"));
		}catch(FileNotFoundException e){
			coursecount = 0; return;
		}
		coursecount = is.readInt();
		for(int i=0; i<coursecount; i++){
			{photo c=(photo) is.readObject();
			p.add(c);
			}
			
		}
		
		is.close();
	}
	
	public static void saveToFile() throws FileNotFoundException, IOException {
		int coursecount=p.size();
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("photo"));
		os.writeInt(coursecount);
		for(int i=0; i<coursecount; i++){
			os.writeObject(p.get(i));
		}
		os.close();
	}
	 
	
	public driver() {
		getContentPane().setLayout(null);
		setBounds(100, 100, 408, 372);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PHOTO LIST", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(16, 11, 177, 254);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JList list = new JList();
		DefaultListModel model = new DefaultListModel();
		list.setModel(model);
		list.setVisibleRowCount(10);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int a=0;
				try{
				 a=list.getSelectedIndex();
				((photo) getP().get(a)).display();}
				catch (Exception e){
					JOptionPane.showMessageDialog(null,"Please click on the title of image to display it or the photo may be no longer present in the given location.\nDeleting the photo if it is not present in the location","ERROR", JOptionPane.ERROR_MESSAGE);
				p.remove(a);
				model.removeAllElements();
				int is;
				for( is=0;is<p.size();is++)
				{
					model.addElement(is+" "+((photo) p.get(is)).getTitle());
				}
			   }
			}
		});
		
		list.setBounds(6, 16, 161, 228);
		panel.add(list);
		
		
		for(int is=0;is<p.size();is++)
		{
			model.addElement(is+" " +((photo) p.get(is)).getTitle());
		}
		
		
		JLabel lblTitle = new JLabel("TITLE");
		lblTitle.setBounds(219, 17, 74, 21);
		getContentPane().add(lblTitle);
		
		textField = new JTextField();
		textField.setBounds(219, 49, 155, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("BROWSE  PIC");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("JPEG Files","jpg","jpeg","Images", "png", "gif", "bmp");
				filechooser.addChoosableFileFilter(filter);
				filechooser.setAcceptAllFileFilterUsed(false);
				
				int ret = filechooser.showDialog(null, "Open File");
				
				if(ret == filechooser.APPROVE_OPTION){
					file = filechooser.getSelectedFile();
					try {
					   img = ImageIO.read(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					btnNewButton.setText(file.getName());
					
				}else{
					JOptionPane.showMessageDialog(null, "Please Choose a file!!");
				}
			}
		});
		btnNewButton.setBounds(219, 93, 130, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblAnnotations = new JLabel("ANNOTATIONS");
		lblAnnotations.setBounds(219, 139, 130, 14);
		getContentPane().add(lblAnnotations);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(219, 164, 155, 90);
		getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		
		JButton btnAddPhoto = new JButton("ADD PHOTO");
		btnAddPhoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(p.size()>=10)
				{
					JOptionPane.showMessageDialog(null,"10 photos already added","ERROR", JOptionPane.ERROR_MESSAGE);
					return;	
				}
				if(btnNewButton.getText().equals("BROWSE  PIC"))
				{
					JOptionPane.showMessageDialog(null,"Please select a photo","ERROR", JOptionPane.ERROR_MESSAGE);
					return;	
				}
				String path = file.getAbsolutePath();
				if(textField.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null,"Please give a title to the photo","ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String s=textField.getText();
				if(s.length()>20)
				{
					JOptionPane.showMessageDialog(null,"Title should be less than 20 chars","ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				s=textArea.getText();
						if(s.length()>20)
						{
							JOptionPane.showMessageDialog(null,"Annotation should be less than 100 chars","ERROR", JOptionPane.ERROR_MESSAGE);
							return;
						}	
			      photo pho=new photo(path,textField.getText(),textArea.getText());
			      getP().add(pho);
			      model.addElement(model.getSize() + " "+textField.getText());
			      textField_1.setText("Give index to delete");
			      textArea.setText(null);
			      textField.setText(null);
			  	}
		});
		btnAddPhoto.setBounds(216, 265, 133, 23);
		getContentPane().add(btnAddPhoto);
		
		aa d= new aa();
		
		JButton btnNewButton_1 = new JButton("Display all photos");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				
				   d.display(p); 
				} catch (Exception e2) {
				}
			}
		});
		btnNewButton_1.setBounds(16, 265, 142, 23);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblDelete = new JLabel("DELETE");
		lblDelete.setBounds(26, 308, 59, 14);
		getContentPane().add(lblDelete);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Enter the index displayed before title in title list");
		textField_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textField_1.setText(null);
			}
		});
		textField_1.setBounds(95, 305, 155, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText("Give index to delete");
		
		JButton btnNewButton_2 = new JButton("DELETE PIC");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int l;
				try{
				l=Integer.valueOf(textField_1.getText());
				if(l<0 || l>= p.size())
				{
					JOptionPane.showMessageDialog(null,"Index not valid.","ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				}
				catch(Exception e5){
					JOptionPane.showMessageDialog(null,"Please give an integer index","ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				p.remove(l);
				int is ;
				model.removeAllElements();
				for( is=0;is<p.size();is++)
				{
					model.addElement(is+" "+((photo) p.get(is)).getTitle());
				}
				textField_1.setText("Give index to delete");
			}
		});
		btnNewButton_2.setBounds(272, 304, 102, 23);
		getContentPane().add(btnNewButton_2);
	}

	public ArrayList getP() {
		return p;
	}

	public void setP(ArrayList p) {
		this.p = p;
	}
}

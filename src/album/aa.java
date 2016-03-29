package album;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class aa {

	private int i=0;
	private JTextField textField_2;
	private JTextField textField_1;
	BufferedImage img,image;
	
	
	 public static BufferedImage resizeImage(final Image image, int width, int height) {
	        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        final Graphics2D graphics2D = bufferedImage.createGraphics();
	        graphics2D.setComposite(AlphaComposite.Src);
	        //below three lines are for RenderingHints for better image quality at cost of higher processing time
	        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	        graphics2D.drawImage(image, 0, 0, width, height, null);
	        graphics2D.dispose();
	        return bufferedImage;
	    }
	
    /**
     * @wbp.parser.entryPoint
     */
    public void display(ArrayList <photo>p){
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize() ;
    	JFrame comp=new JFrame();
    	comp.setTitle("VIEW PHOTO ALBUM");
    	comp.getContentPane().setLayout(null);
    	comp.setSize(d);
	    comp.setVisible(true);
    	
    	JLabel label = new JLabel("");
    	
    	comp.getContentPane().add(label);
    	
    	JLabel lblNewLabel = new JLabel("TITLE");
    	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
    	lblNewLabel.setBounds((d.width/2)-70, d.height-145, 50, 20);
    	comp.getContentPane().add(lblNewLabel);
    	
    	JButton btnPrevious = new JButton("PREVIOUS");
    	btnPrevious.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(0<i && i<p.size()){
					i--;
					try {                
				          image = ImageIO.read(new File(((photo) p.get(i)).getSrc()));
				       } catch (IOException ex) {	
				    	   JOptionPane.showMessageDialog(null,"The photo may be no longer present in the given location.Please click on next or previous to view another photo","ERROR", JOptionPane.ERROR_MESSAGE);
				    	   return;
				    }
					int w = image.getWidth(null);
			          int h = image.getHeight(null);
			          if((w/(d.width-15))>(h/(d.height-150)))
			        	 {
			        	  int k=h*(d.width-15)/w;
			        	  img=resizeImage(image,d.width-15,k);
			             label.setBounds(0,(d.height-150 -k)/2, d.width-15, k);}
			          else
			        	  {
			        	  int k=w*(d.height-150)/h;
			        	  img=resizeImage(image,k,d.height-150);
				             label.setBounds((d.width-15-k)/2,0, k, d.height-150);
			        	  }
					  ImageIcon icon=new ImageIcon(img); // ADDED
					  label.setIcon(icon); // ADDED
					  label.revalidate(); // ADDED
					  label.repaint(); // ADDED   
					  textField_2.setText((String) ((photo) p.get(i)).getTitle());
					  textField_1.setText((String) ((photo) p.get(i)).getAnnot());
					}
					else{
						JOptionPane.showMessageDialog(null,"Photo not available.");
					}	
    		}
    	});
    	btnPrevious.setFont(new Font("Tahoma", Font.BOLD, 15));
    	btnPrevious.setBounds(10, d.height-145, 150, 20);
    	comp.getContentPane().add(btnPrevious);
    	
    	textField_2 = new JTextField();
    	textField_2.setBounds((d.width/2)+20, d.height-145, 200, 20);
    	comp.getContentPane().add(textField_2);
    	textField_2.setColumns(10);
    	
    	JButton btnNext = new JButton("NEXT");
    	btnNext.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			if(i<p.size()-1){
					i++;
				try {                
			          image = ImageIO.read(new File(((photo) p.get(i)).getSrc()));
			       } catch (IOException ex) {
			    	   JOptionPane.showMessageDialog(null,"The photo may be no longer present in the given location.Please click on next or previous to view another photo","ERROR", JOptionPane.ERROR_MESSAGE);
			    	   return;
			    }
				int w = image.getWidth(null);
		          int h = image.getHeight(null);
		          if((w/(d.width-15))>(h/(d.height-150)))
		        	 {
		        	  int k=h*(d.width-15)/w;
		        	  img=resizeImage(image,d.width-15,k);
		             label.setBounds(0,(d.height-150 -k)/2, d.width-15, k);}
		          else
		        	  {
		        	  int k=w*(d.height-150)/h;
		        	  img=resizeImage(image,k,d.height-150);
			             label.setBounds((d.width-15-k)/2,0, k, d.height-150);
		        	  }
				  ImageIcon icon=new ImageIcon(img); // ADDED
				  label.setIcon(icon); // ADDED
				  label.revalidate(); // ADDED
				  label.repaint(); // ADDED   
				  textField_2.setText((String) ((photo) p.get(i)).getTitle());
				  textField_1.setText((String) ((photo) p.get(i)).getAnnot());
				}
				else{
					JOptionPane.showMessageDialog(null,"Photo not available.");
				}
    		}
    	});
    	btnNext.setFont(new Font("Tahoma", Font.BOLD, 15));
    	btnNext.setBounds(d.width-170, d.height-145, 150, 20);
    	comp.getContentPane().add(btnNext);
    	
    	JLabel lblNewLabel_1 = new JLabel("ANNOTATION");
    	lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
    	lblNewLabel_1.setBounds(20, d.height-100, 130, 20);
    	comp.getContentPane().add(lblNewLabel_1);
    	
    	textField_1 = new JTextField();
    	textField_1.setBounds(170,d.height-100 ,d.width-190, 20);
    	comp.getContentPane().add(textField_1);
    	textField_1.setColumns(10);
    	try {                
	          image = ImageIO.read(new File(((photo) p.get(0)).getSrc()));
	          int w = image.getWidth(null);
	          int h = image.getHeight(null);
	          if((w/(d.width-15))>(h/(d.height-150)))
	        	 {
	        	  int k=h*(d.width-15)/w;
	        	  img=resizeImage(image,d.width-15,k);
	             label.setBounds(0,(d.height-150 -k)/2, d.width-15, k);}
	          else
	        	  {
	        	  int k=w*(d.height-150)/h;
	        	  img=resizeImage(image,k,d.height-150);
		             label.setBounds((d.width-15-k)/2,0, k, d.height-150);
	        	  }
			  ImageIcon icon=new ImageIcon(img); // ADDED
			  label.setIcon(icon); // ADDED
			  label.revalidate(); // ADDED
			  label.repaint(); // ADDED   
			  textField_2.setText((String) ((photo) p.get(0)).getTitle());
			  textField_1.setText((String) ((photo) p.get(0)).getAnnot());
	       } catch (IOException ex) {
	            // handle exception...
	    }	
    }
}

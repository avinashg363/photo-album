package album;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;

public class photo implements Serializable {
     private String src;
     private String title;
     private String annot;
     public  photo(String file,String Title,String ann){
    	 src=file;
    	 title=Title;
    	 annot=ann;
     };
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnnot() {
		return annot;
	}
	public void setAnnot(String annot) {
		this.annot = annot;
	}
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
	public void display()
	{
		 JFrame comp=new JFrame();
		 comp.getContentPane().setLayout(null);
	        comp.setTitle(title);
		 BufferedImage img,image = null;
		 JLabel label = new JLabel("");
		 
		try {                
	          image = ImageIO.read(new File(src));
	       } catch (IOException ex) {
	            // handle exception...
	    }
		  
		int w = image.getWidth(null);
        int h = image.getHeight(null);
        if((w/(700))>(h/(400)))
      	 {
      	  int k=(h*700)/w;
      	  img=resizeImage(image,700,k);
           label.setBounds(0,(400-k)/2, 700, k);}
        else
      	  {
      	  int k=w*(400)/h;
      	  img=resizeImage(image,k,400);
	             label.setBounds((700-k)/2,0, k, 400);
      	  }
		  ImageIcon icon=new ImageIcon(img); // ADDED
		  
		label.setIcon(icon); // ADDED
		   Dimension imageSize = new Dimension(icon.getIconWidth(),icon.getIconHeight()); // ADDED
		  comp.setSize(700,482);
		  

		  label.revalidate(); // ADDED
		  label.repaint(); // ADDED                                    
        comp.getContentPane().add(label);
        
        JTextArea textArea = new JTextArea();
        textArea.append(annot);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        textArea.setRows(2);
        textArea.setBounds(115, 403, 559, 40);
        comp.getContentPane().add(textArea);
        
        JLabel lblNewLabel = new JLabel("ANNOTATION");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel.setBounds(10, 408, 95, 14);
        comp.getContentPane().add(lblNewLabel);
        comp.setVisible(true);
	}
}

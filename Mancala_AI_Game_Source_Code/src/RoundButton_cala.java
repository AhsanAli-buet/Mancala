import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


import javax.imageio.ImageIO;
import javax.swing.*;

public class RoundButton_cala extends JButton {
	public BufferedImage image;
	public BufferedImage image2;
	public BufferedImage image3;
	public Dimension Location;
	
  public RoundButton_cala(String label,int width,int height) {
	  
	  super(label);
	  setSize(width, height);
	  
	    URL resource = getClass().getResource("cala_btn_new31.png");
	    //URL resource2 = getClass().getResource("btn_grey2.png");
	    //URL resource3 = getClass().getResource("btn_new_hvr.png");
	    try {
	        image = ImageIO.read(resource);
	        //image2 = ImageIO.read(resource2);
	        //image3 = ImageIO.read(resource3);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
// These statements enlarge the button so that it 
// becomes a circle rather than an oval.
    Dimension size = getSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);

// This call causes the JButton not to paint 
   // the background.
// This allows us to paint a round background.
    setContentAreaFilled(false);
  }
  
  public void set_armed(){
	  getModel().setArmed(true);
  }
// Paint the round background and label.
  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
// You might want to make the highlight color 
   // a property of the RoundButton class.
      //g.setColor(Color.lightGray);
    	this.setForeground(Color.BLACK);
    	g.drawImage(image, 0,0, this);
    }else if(getModel().isRollover()){
    	g.drawImage(image, 0,0, this);
    	this.setForeground(Color.WHITE);
    }
    else {
      //g.setColor(getBackground());
    	this.setForeground(Color.WHITE);
    	g.drawImage(image, 0,0, this);
    }
    
    //g.fillOval(0, 0, getSize().width, getSize().height);
    
    //g.drawImage(image, 0, 0, this);
// This call will paint the label and the 
   // focus rectangle.
    super.paintComponent(g);
  }

// Paint the border of the button using a simple stroke.
  protected void paintBorder(Graphics g) {
    g.setColor(new Color(255, 255, 255,1));
    
    g.drawOval(0,0,getSize().width-1, getSize().height-1);
  }

// Hit detection.
  Shape shape;
  public boolean contains(int x, int y) {
// If the button has changed size, 
   // make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0,0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
}


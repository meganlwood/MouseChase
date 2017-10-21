import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

public class ObjectIcon implements Icon {
	Image i;
	String name;
	public ObjectIcon(String object, String degrees) {
		i = Toolkit.getDefaultToolkit().getImage("assets/images/" + object + degrees + ".png");
		name = object;
	}
	
	public ObjectIcon(String object) {
		i = Toolkit.getDefaultToolkit().getImage("assets/images/" + object);
		name = object;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		c.repaint();
		c.validate();
		g.drawImage(i, 0, 0, c.getWidth(), c.getHeight(), null);
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Image getImage() {
		return i;
	}
}

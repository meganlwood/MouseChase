import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	Image i;
	public BackgroundPanel() {
		Toolkit t = Toolkit.getDefaultToolkit();
		i = t.getImage("assets/images/wood.jpg");
	}
	
	protected void paintComponent(Graphics g) {
		g.drawImage(i, 0, 0, this.getWidth(), this.getHeight(), null);
		//g.drawImage(bg, 0, 0, width, height, observer)
		repaint();
		validate();
	}
	
}

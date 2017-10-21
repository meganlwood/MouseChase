import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InstructionsWindow extends JFrame {
	public InstructionsWindow() {
		super("Instructions");
		setSize(600,600);
		Instructions i = new Instructions(this);
		i.setVisible(true);
		add(new JScrollPane(i));
	}
}

class Instructions extends JPanel {
	String newLine = "\n";
	Font f;
	Image background = Toolkit.getDefaultToolkit().createImage("assets/images/instructions.jpg");
	public Instructions(InstructionsWindow iw) {
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("assets/fonts/SEASRN__.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      f = ttfBase.deriveFont(Font.BOLD, 12);
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		
		setLayout(new BorderLayout());
		JPanel closePanel = new JPanel();
		JButton closeButton = new JButton("Close");
		closeButton.setFont(GUI.f);
		closePanel.add(closeButton);
		add(closePanel, BorderLayout.SOUTH);
		closePanel.setOpaque(false);
		
		
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				iw.dispose();
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
}

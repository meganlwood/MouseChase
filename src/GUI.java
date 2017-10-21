import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUI extends JFrame {
	static JPanel outerPanel;
	static Font f;
	static Font fBig;
	static Image buttonImage;
	public GUI() {
		super("Game");
		
		try {
		      InputStream in = new BufferedInputStream(new FileInputStream("assets/fonts/Capture_it.ttf"));
		      InputStream in2 = new BufferedInputStream(new FileInputStream("assets/fonts/Capture_it_2.ttf"));
		      Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, in);
		      Font ttfBase2 = Font.createFont(Font.TRUETYPE_FONT, in2);
		      f = ttfBase.deriveFont(Font.BOLD, 24);
		      fBig = ttfBase2.deriveFont(Font.BOLD, 60);
		      buttonImage = ImageIO.read(new FileInputStream("assets/images/grey_button00.png"));
		
		} catch (FontFormatException e) {
		        e.printStackTrace();
		} catch (IOException e) {
		        e.printStackTrace();
		}
		
		outerPanel = new JPanel(new CardLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 800);
		WelcomeGUI wg = new WelcomeGUI();
		outerPanel.add(wg);
		add(outerPanel);
		wg.setVisible(true);
	}
}

class WelcomeGUI extends JPanel {
	//Image background = getImage(new URL("http://dreamatico.com/data_images/mouse/mouse-1.jpg"));
	Image background = Toolkit.getDefaultToolkit().createImage("assets/images/mouseandcat.jpg");
	LeaderBoardWindow lbw = new LeaderBoardWindow();
	public WelcomeGUI() {
		WelcomeGUI wg = this;
		setSize(800, 800);
		setLayout(new BorderLayout());
		JButton viewInstructions = new JButton("Instructions");
		viewInstructions.setFont(GUI.f);
		viewInstructions.setIcon(new BackgroundIcon(GUI.buttonImage));
		JButton startButton = new JButton("Start");
		startButton.setFont(GUI.f);
		startButton.setIcon(new BackgroundIcon(GUI.buttonImage));
		JButton leaderBoardButton = new JButton("LeaderBoard");
		leaderBoardButton.setFont(GUI.f);
		leaderBoardButton.setIcon(new BackgroundIcon(GUI.buttonImage));
		JLabel gameNameLabel = new JLabel("Mouse Chase", SwingConstants.CENTER);
		gameNameLabel.setFont(GUI.fBig);
		gameNameLabel.setOpaque(false);
		JPanel namePanel = new JPanel();
		namePanel.add(gameNameLabel);
		namePanel.setOpaque(false);
		//ImageIcon im = new ImageIcon("assets/images/mouseandcat.jpeg");
		namePanel.add(new JLabel());
		JLabel mouseandcatlabel = new JLabel();
		//mouseandcatlabel.setIcon(im);
		namePanel.add(mouseandcatlabel);
		add(namePanel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(leaderBoardButton);
		buttonPanel.add(viewInstructions);
		buttonPanel.add(startButton);
		buttonPanel.setOpaque(false);
		add(buttonPanel, BorderLayout.SOUTH);
		setOpaque(false);
		
		viewInstructions.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InstructionsWindow iw = new InstructionsWindow();
				iw.setVisible(true);
			}
			
		});
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				GamePanel gp = new GamePanel(wg);
				GUI.outerPanel.add(gp);
				gp.setVisible(true);
				//PlayerTypeGUI ptg = new PlayerTypeGUI();
				//GUI.outerPanel.add(ptg);
				//ptg.setVisible(true);
			}
		});
		
		leaderBoardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				lbw = new LeaderBoardWindow();
				lbw.setVisible(true);
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

/*
class PlayerTypeGUI extends JPanel {
	Image background = Toolkit.getDefaultToolkit().createImage("assets/images/mouseandcat.jpg");
	PlayerTypeGUI() {
		setLayout(new GridLayout(1, 2));
		JPanel singlePanel = new JPanel();
		JPanel multiPanel = new JPanel();
		JButton singlePlayerButton = new JButton("Single Player");
		singlePlayerButton.setFont(GUI.f);
		singlePlayerButton.setIcon(new BackgroundIcon(GUI.buttonImage));
		JButton multiPlayerButton = new JButton("MultiPlayer");
		multiPlayerButton.setFont(GUI.f);
		multiPlayerButton.setIcon(new BackgroundIcon(GUI.buttonImage));
		singlePanel.add(singlePlayerButton);
		multiPanel.add(multiPlayerButton);
		add(singlePanel);
		add(multiPanel);
		setOpaque(false);
		singlePanel.setOpaque(false);
		multiPanel.setOpaque(false);
		
		
		singlePlayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				GamePanel g = new GamePanel();
				GUI.outerPanel.add(g);
				g.setVisible(true);
			}
			
		});
		
		multiPlayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				MultiPlayerGUI mpg = new MultiPlayerGUI();
				GUI.outerPanel.add(mpg);
				mpg.setVisible(true);
			}
			
		});
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

class MultiPlayerGUI extends JPanel {
	public MultiPlayerGUI() {
		setLayout(new GridLayout(1, 2));
		JPanel hostPanel = new JPanel();
		JPanel joinPanel = new JPanel();
		JButton singlePlayerButton = new JButton("Host");
		JButton multiPlayerButton = new JButton("Join");
		hostPanel.add(singlePlayerButton);
		joinPanel.add(multiPlayerButton);
		add(hostPanel);
		add(joinPanel);
	}
}
*/
class BackgroundIcon implements Icon {
	private Image image;
	public BackgroundIcon(Image img) {
		image = img;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.drawImage(image, 0, 0, c.getWidth(), c.getHeight(), c);
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
	
}


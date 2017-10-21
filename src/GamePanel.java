import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends BackgroundPanel {
	private JLabel[][] board;
	private int x, y;
	private GamePlay gp;
	WelcomeGUI wg;
	JLabel scoreLabel;
	
	public GamePanel(WelcomeGUI wg) {
		this.wg = wg;
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(12, 12));
		setLayout(new BorderLayout());
		board = new JLabel[12][12];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				board[i][j] = new JLabel();
				board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				board[i][j].setForeground(Color.WHITE);
				boardPanel.add(board[i][j]);
			}
		}
		JPanel scorePanel = new JPanel();
		scoreLabel = new JLabel("Score: 0");
		scoreLabel.setFont(GUI.f);
		scorePanel.add(scoreLabel);
		add(scorePanel, BorderLayout.SOUTH);
		add(boardPanel, BorderLayout.CENTER);
		boardPanel.setOpaque(false);
		scorePanel.setOpaque(false);
		setOpaque(false);
		gp = new GamePlay(board, this);
		setup();
		addActions();
	}
	
	public void setup() {
		board[7][7].setIcon(new ObjectIcon("mouse", "0"));
		gp.addCheese();
		gp.addCheese();
		gp.addCheese();
		gp.addCheese();
		gp.addHole();
		x = 7;
		y = 7;
	}
	
	public void updateScore() {
		scoreLabel.setText("Score: " + gp.getScore());
	}

	public void move(int newx, int newy, String deg) {
		board[x][y].setIcon(null);
		x = newx;
		y = newy;
		board[x][y].setIcon(new ObjectIcon("mouse", deg));
		
		gp.updateLocation(x, y, newx, newy, "mouse");
		gp.moveCat(x, y);
	}


	public void addActions() {
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				board[i][j].addMouseListener(new MouseAdapter() {
					private String degrees = "0";
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						int clickedx = 0, clickedy = 0;
						for (int i = 0; i < 12; i++) {
							for (int j = 0; j < 12; j++) {
								if (e.getSource() == board[i][j]) {
									clickedx = i;
									clickedy = j;
								}
							}
						}
						int distance = Math.abs(x - clickedx) + Math.abs(y - clickedy);
						boolean diagonal = (clickedx + 1 == x && (clickedy + 1 == y || clickedy - 1 == y)) || (clickedx - 1 == x && (clickedy + 1 == y || clickedy - 1 == y));
						if (distance == 1 || diagonal) {
							if (GamePlay.locations[clickedx][clickedy] != null && GamePlay.locations[clickedx][clickedy].getName().equals("mousehole")) {
								board[clickedx][clickedy].setIcon(null);
								MouseHole mh = (MouseHole) GamePlay.locations[clickedx][clickedy];
								int x = mh.getPortal().getX();
								int y = mh.getPortal().getY();
								gp.locations[clickedx][clickedy] = null;
								move(x, y, "0");
								gp.addHole();
							}
							else move(clickedx, clickedy, degrees);
						}
					}
					
					public void mouseEntered(MouseEvent e) {
						int clickedx = 0, clickedy = 0;
						for (int i = 0; i < 12; i++) {
							for (int j = 0; j < 12; j++) {
								if (e.getSource() == board[i][j]) {
									clickedx = i;
									clickedy = j;
								}
							}
						}
						int distance = Math.abs(x - clickedx) + Math.abs(y - clickedy);
						boolean diagonal = (clickedx + 1 == x && (clickedy + 1 == y || clickedy - 1 == y)) || (clickedx - 1 == x && (clickedy + 1 == y || clickedy - 1 == y));
						if (distance == 1 || diagonal) {
							board[clickedx][clickedy].setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
							if (x < clickedx && y == clickedy) { //newx is lower
								//facing down
								//System.out.println("facing down");
								board[x][y].setIcon(new ObjectIcon("mouse", "180"));
								degrees = "180";
							}
							else if (x > clickedx && y == clickedy) {
								//facing up
								board[x][y].setIcon(new ObjectIcon("mouse", "0"));
								degrees = "0";
							}
							else if (x == clickedx && y < clickedy) {
								//facing right
								board[x][y].setIcon(new ObjectIcon("mouse", "90"));
								degrees = "90";
							}
							else if (x == clickedx && y > clickedy) {
								//facing left
								board[x][y].setIcon(new ObjectIcon("mouse", "270"));
								degrees = "270";
							}
							else if (x < clickedx && y < clickedy) {
								//facing bottom right corner
								board[x][y].setIcon(new ObjectIcon("mouse", "135"));
								degrees = "135";
							}
							else if (x > clickedx && y > clickedy) {
								//facing top left corner
								board[x][y].setIcon(new ObjectIcon("mouse", "315"));
								degrees = "315";
							}
							else if (x < clickedx && y > clickedy) {
								board[x][y].setIcon(new ObjectIcon("mouse", "225"));
								degrees = "225";
							}
							else if (x > clickedx && y < clickedy) {
								board[x][y].setIcon(new ObjectIcon("mouse", "45"));
								degrees = "45";
								//upper right
							}
						}
					}
					
					public void mouseExited(MouseEvent e) {
						int clickedx = 0, clickedy = 0;
						for (int i = 0; i < 12; i++) {
							for (int j = 0; j < 12; j++) {
								if (e.getSource() == board[i][j]) {
									clickedx = i;
									clickedy = j;
								}
							}
						}
						board[clickedx][clickedy].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
					}
				});
			}
		}
	}


}

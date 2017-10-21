import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GamePlay {
	public static JLabel[][] board;
	public static GameObject[][] locations;
	int points = 0;
	private Cat cat;
	private GamePanel g;
	public GamePlay(JLabel[][] board, GamePanel g) {
		this.board = board;
		this.g = g;
		locations = new GameObject[12][12];
		locations[7][7] = new Mouse(7, 7);
		cat = new Cat(0, 0);
		locations[0][0] = cat;
		board[0][0].setIcon(new ObjectIcon("cat", "135"));
	}
	
	public void updateLocation(int prevx, int prevy, int x, int y, String type) {
		if (type.equals("mouse")) {
			if (locations[x][y] != null) {
				String objectType = locations[x][y].getName();
				if (objectType.equals("cheese")) {
					points++;
					addCheese();
					g.updateScore();
				}
				/*
				else if (objectType.equals("mousehole")) {
					MouseHole mh = (MouseHole) locations[x][y];
					locations[x][y] = null;
					x = mh.portal.getX();
					y = mh.portal.getY();
					System.out.println(x + " " + y);
					g.move(x, y, "mouse");
				}
				*/
			}
			locations[prevx][prevy] = null;
			locations[x][y] = new Mouse(x, y);
		}
	}
	
	public void addCheese() {
		Cheese c = new Cheese(0, 0);
	}
	
	public void addHole() {
		MouseHole m = new MouseHole(0, 0, true, null);
	}
	
	public int getScore() {
		return points;
	}
	
	public void moveCat(int mouseX, int mouseY) {
		board[cat.getX()][cat.getY()].setIcon(null);
		cat.move(mouseX, mouseY);
		if (cat.getX() == mouseX && cat.getY() == mouseY) {
			//JOptionPane.showMessageDialog(board[4][4], "You lose! Score: " + points);
			String name = JOptionPane.showInputDialog("Your score: " + points + "\nEnter your name:");
			if (g == null) System.out.println("g is null");
			if (g.wg == null) System.out.println("wg is null");
			if (g.wg.lbw == null) System.out.println("lbw is null");
			g.wg.lbw.addRow(name, points);
			g.setVisible(false);
			WelcomeGUI wg = new WelcomeGUI();
			GUI.outerPanel.add(wg);
			/*
			Object[] choices = {"Main Menu", "Play Again"};
			int selection = JOptionPane.showOptionDialog(null, "Play Again?", "Select an Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			if (selection == 0) {
				g.setVisible(false);
				WelcomeGUI wg = new WelcomeGUI();
				GUI.outerPanel.add(wg);
			}
			*/
		}
	}
}

class GameObject {
	private int x;
	private int y;
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String getName() {
		return "";
	}
}

class Cat extends GameObject{
	private int x;
	private int y;
	public Cat(int x, int y) {
		super(x, y);
	}
	
	public void move(int mouseX, int mouseY) {
		String degrees = "0";
		int prevx = x;
		int prevy = y;
		if (x == mouseX) {
			//same row
			if (mouseY > y) {
				y++;
				degrees = "90";
			}
			else {
				y--;
				degrees = "270";
			}
		}
		else if (y == mouseY) {
			if (mouseX > x) {
				x++;
				degrees = "180";
			}
			else {
				x--;
				degrees = "0";
			}
		}
		else {
			if (mouseX > x && mouseY > y) {
				x++;
				y++;
				degrees = "135";
			}
			else if (mouseX < x && mouseY > y) {
				x--;
				y++;
				degrees = "45";
			}
			else if (mouseX > x && mouseY < y) {
				x++;
				y--;
				degrees = "225";
			}
			else if (mouseX < x && mouseY < y) {
				x--;
				y--;
				degrees = "315";
			}
		}
		if (GamePlay.locations[x][y] == null) {
			GamePlay.board[x][y].setIcon(new ObjectIcon("cat", degrees));
		}
		else {
			if (!GamePlay.locations[x][y].getName().equals("cheese") && !GamePlay.locations[x][y].getName().equals("mousehole")) {
				GamePlay.board[x][y].setIcon(new ObjectIcon("cat", degrees));
			}
			else {
				x = prevx;
				y = prevy;
				GamePlay.board[x][y].setIcon(new ObjectIcon("cat", degrees));
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return "cat";
	}
	
}


class MouseHole extends GameObject {
	private int x;
    private int y;
    public MouseHole portal;
    public MouseHole(int x, int y, boolean setRandom, MouseHole portal) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.portal = portal;
        
        if (setRandom) setRandomLocation();
        GamePlay.locations[getX()][getY()] = this;
        int[] opposite = getOpposite();
        if (portal == null) {
        	portal = new MouseHole(opposite[0], opposite[1], false, this);
        	portal.setPortal(this);
        }
        else {
        	portal.setPortal(this);
        }
        GamePlay.locations[opposite[0]][opposite[1]] = getPortal();
        GamePlay.board[getX()][getY()].setIcon(new ObjectIcon("mousehole.gif"));
        setLocation(getX(), getY());
    }
    
    public int[] getOpposite() {
        int[] opposite = new int[2];
        opposite[0] = Math.abs(11 - x);
        opposite[1] = Math.abs(11 - y);
        return opposite;
    }
    
    public void setPortal(MouseHole p) {
    	portal = p;
    }
    
    public MouseHole getPortal() {
    	return portal;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setRandomLocation() {
        Random rand = new Random();
        boolean done = false;
        while (!done) {
           int newx = rand.nextInt(12);
            int newy = rand.nextInt(12);
            if (GamePlay.locations[newx][newy] == null && (newx == 0 || newx == 11 || newy == 0 || newy == 11)) {
                done = true;
                x = newx;
                y = newy;
            }
        }
    }
    
    public String getName() {
        return "mousehole";
    }
}

class Mouse extends GameObject{
	private int x;
	private int y;
	public Mouse(int x, int y) {
		super(x, y);
	}
	
	public String getName() {
		return "mouse";
	}
}

class Cheese extends GameObject {
	private int x;
	private int y;
	public Cheese(int x, int y) {
		super(x, y);
		boolean done = false;
		while (!done) {
			if (setRandomLocation()) {
				done = true;
				GamePlay.locations[getX()][getY()] = this;
				GamePlay.board[getX()][getY()].setIcon(new ObjectIcon("cheese", ""));
				setLocation(getX(), getY());
			}
		}
	}
	
	
	public boolean setRandomLocation() {
		Random rand = new Random();
		int newx = rand.nextInt(10) + 1;
		int newy = rand.nextInt(10) + 1;
		
		if (GamePlay.locations[newx][newy] == null) {
			x = newx;
			y = newy;
			return true;
		}
		else return false;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getName() {
		return "cheese";
	}
}


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class gridCreate extends JPanel implements ActionListener {
	int m = 5;// x
	int n = 5;// y
	int k = 3;
	public static final int TILE_SIZE = 35;
	public static placeToken[][] grid;
	public boolean p1 = true;
	public boolean p2 = false;
	public boolean cpu = false;
	public static int cpuNumPlays = 0;
	JLabel l0;// main menu/gameboard;
	JPanel p;// XO area
	JLabel winloc;
	JLabel l[];// XO squares
	ImageIcon X;
	ImageIcon O;
	JButton b0;// Quit
	JButton b1;// Play
	JButton b2;// 1P
	JButton b3;// 2P
	JButton b[];// XOs
	ImageIcon img1;
	ImageIcon img2;
	ImageIcon img[];
	JButton sq[];// top-left square

	public void scale() {

	}

	public gridCreate() {
		grid = new placeToken[m][n];

		for (int x = 0; x < m; x++) {
			for (int y = 0; y < n; y++) {
				int nx = x * TILE_SIZE;
				int ny = y * TILE_SIZE;

				placeToken v = new placeToken(nx, ny);
				grid[x][y] = v;
			}
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(() -> {
			gridCreate view = new gridCreate();
			view.setPreferredSize(new Dimension(1210, 1000));
			JFrame frame = new JFrame();
			frame.setTitle("TicTacToo");
			frame.getContentPane().add(view);
			frame.setResizable(false);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			view.requestFocus();
			view.start();
		});
	}

	@Override
	protected void paintComponent(Graphics graph) {
		draw((Graphics2D) graph);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	public void start() {

		MouseHandler mouseHandler = new MouseHandler();
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);

		// thumbnail
		ImageIcon img0 = new ImageIcon("icon0.png");

		// Main menu
		l0 = new JLabel();
		img1 = new ImageIcon("MainMenu.png");
		l0.setIcon(img1);
		l0.setBounds(0, 0, 500, 400);

		winloc = new JLabel();
		winloc.setLayout(null);
		winloc.setBounds(0, 357, 350, 150);
		winloc.setVisible(true);

		// play
		b1 = new JButton();
		b1.setBounds(245, 400, 100, 50);
		b1.addActionListener(this);
		b1.setText("Play");

		// 1P
		b2 = new JButton();
		b2.setBounds(140, 400, 100, 50);
		b2.addActionListener(this);
		b2.setText("1P");

		// 2P
		b3 = new JButton();
		b3.setBounds(35, 400, 100, 50);
		b3.addActionListener(this);
		b3.setText("2P");
	}

	public void draw(Graphics2D graphed) {
		for (int x = 0; x < m; x++) {
			for (int y = 0; y < n; y++) {
				graphed.setColor(Color.white);
				placeToken rx = grid[x][y];
				graphed.fillRect(rx.point.x, rx.point.y, TILE_SIZE, TILE_SIZE);
				if (rx.img != null) {
					graphed.drawImage(rx.img, rx.point.x + 1, rx.point.y + 1, null);
				}
				graphed.setColor(Color.BLACK);
				graphed.drawRect(rx.point.x, rx.point.y, TILE_SIZE, TILE_SIZE);
			}
		}

	}

	private class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			int row = e.getX() / TILE_SIZE;
			int col = e.getY() / TILE_SIZE;
			if (col < 0 || row < 0 || col > m - 1 || row > m - 1) {
				return;
			}

			if (grid[row][col].img == null) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (p1 == true) {// Player 1 plays
						if (cpu == false) {
							p1 = false;
							p2 = true;
						}
						grid[row][col].img = getOImage();
						grid[row][col].hiddenValue = 1;
						if (cpu == true) {// CPU plays
							playTheCpu();

						}

					} else if (p2 == true && cpu == false) {// Player 2 plays if cpu is false
						p1 = true;
						p2 = false;
						grid[row][col].img = getXImage();
						grid[row][col].hiddenValue = 2;
					}
				}
			}
			repaint();
			int win = 0;

			for (int y = col - k <= 0 ? 1 : col - k; y < n; y++) {// checking top to bottom
				if (grid[row][y - 1].img == null || grid[row][y].img == null) {
					continue;
				}
				if (grid[row][y - 1].hiddenValue == (grid[row][y].hiddenValue)) {
					win++;
				} else {
					win = 0;
				}
				if (win == k - 1) {
					if (grid[row][col].hiddenValue == 1) {
						System.out.println("Player 1 wins!");
					} else if (grid[row][col].hiddenValue == 2) {
						System.out.println("Player 2 wins!");
					} else if (grid[row][col].hiddenValue == 3) {
						System.out.println("CPU wins!");
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
			win = 0;
			for (int x = row - k <= 0 ? 1 : row - k; x < n; x++) {// checking horizontal
				if (grid[x - 1][col].img == null || grid[x][col].img == null) {
					continue;
				}
				if (grid[x - 1][col].hiddenValue == (grid[x][col].hiddenValue)) {
					win++;
				} else {
					win = 0;
				}
				if (win == k - 1) {
					if (grid[row][col].hiddenValue == 1) {
						System.out.println("Player 1 wins!");
					} else if (grid[row][col].hiddenValue == 2) {
						System.out.println("Player 2 wins!");
					} else if (grid[row][col].hiddenValue == 3) {
						System.out.println("CPU wins!");
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
			win = 0;
			for (int x = row - k <= 0 ? 1 : row - k, y = col - k <= 0 ? 1 : col - k; y < n && x < m; x++, y++) {
				if ((grid[x - 1][y - 1].img == null || grid[x][y].img == null)) {
					continue;
				}
				if ((grid[x - 1][y - 1].hiddenValue == grid[x][y].hiddenValue)) {
					win++;
				} else {
					win = 0;
				}
				if (win == k - 1) {
					if (grid[row][col].hiddenValue == 1) {
						System.out.println("Player 1 wins!");
					} else if (grid[row][col].hiddenValue == 2) {
						System.out.println("Player 2 wins!");
					} else if (grid[row][col].hiddenValue == 3) {
						System.out.println("CPU wins!");
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
			win = 0;
			for (int x = row + k >= m ? m -2: row + k, y = col - k <= 0 ? 1 : col - k; y < n && x > 0; x--, y++) {
				// top right to bottom left
				if ((grid[x + 1][y - 1].img == null || grid[x][y].img == null)) {
					continue;
				}

				if ((grid[x + 1][y - 1].hiddenValue == grid[x][y].hiddenValue)) {
					win++;
				} else {
					win = 0;
				}

				if (win == k - 1) {
					if (grid[row][col].hiddenValue == 1) {
						System.out.println("Player 1 wins!");
					} else if (grid[row][col].hiddenValue == 2) {
						System.out.println("Player 2 wins!");
					} else if (grid[row][col].hiddenValue == 3) {
						System.out.println("CPU wins!");
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
			/*
			 * if(win == k - 1) { if (grid[row][col].hiddenValue == 1) {
			 * System.out.println("Player 1 wins!"); } else if (grid[row][col].hiddenValue
			 * == 2) { System.out.println("Player 2 wins!"); } else if
			 * (grid[row][col].hiddenValue == 3){ System.out.println("CPU wins!"); }
			 * System.exit(0); }
			 */
		}
	}

	public placeToken randomCoords(int m, int n) {
		Random randCoord = new Random();
		int upperX = randCoord.nextInt(m);
		int upperY = randCoord.nextInt(n);
		return new placeToken(upperX, upperY);
	}

	public Image getXImage() {
		BufferedImage tokenX = null;
		try {
			// String stringPatch = Class.getResource("Images/O.png");
			tokenX = ImageIO.read(new File("X.png"));
			return tokenX.getScaledInstance(TILE_SIZE - 2, TILE_SIZE - 2, Image.SCALE_SMOOTH);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public Image getOImage() {
		BufferedImage tokenO = null;
		try {
			// String stringPatch = Class.getResource("Images/O.png");
			tokenO = ImageIO.read(new File("O.png"));
			return tokenO.getScaledInstance(TILE_SIZE - 2, TILE_SIZE - 2, Image.SCALE_SMOOTH);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public void playTheCpu() {
		do {
			if (cpuNumPlays >= Math.floor(m * n / 2)) {
				break;
			}
			placeToken cpuPlay = randomCoords(m, n);
			if (grid[cpuPlay.point.x][cpuPlay.point.y].img == null) {
				grid[cpuPlay.point.x][cpuPlay.point.y].img = getXImage();
				cpuNumPlays++;
				grid[cpuPlay.point.x][cpuPlay.point.y].hiddenValue = 3;
				break;
			}
		} while (true);
	}

	public void chooseWinner() {

	}
}

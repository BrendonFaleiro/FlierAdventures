package superjackgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import superjack.framework.Animation;

public class StartingGame extends Applet implements Runnable, KeyListener {
	// private static Robot robot;
	public static Heliboy hb;

	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);

	public static enum GameState {
		Running, Dead, Won
	}

	public static GameState state = GameState.Running;

	private Image image, background, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;

	public static Image tilegrassTop, tilegrassBot, tilegrassLeft, tilegrassRight, tiledirt, finishFront, finishBack;

	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation hanim;

	private int width = 0;
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	private long starttime;

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Heliboy Adventures");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// // Image Setups
		// character = getImage(base, "data/character.png");
		// character2 = getImage(base, "data/character2.png");
		// character3 = getImage(base, "data/character3.png");
		//
		// characterDown = getImage(base, "data/down.png");
		// characterJumped = getImage(base, "data/jumped.png");
		//
		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		background = getImage(base, "data/background_city.jpg");

		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");

		finishFront = getImage(base, "data/finishfront.png");
		finishBack = getImage(base, "data/finishback.png");
		// anim = new Animation();
		// anim.addFrame(character, 1250);
		// anim.addFrame(character2, 50);
		// anim.addFrame(character3, 50);
		// anim.addFrame(character2, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		// currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		setupforgame();
	}

	public void setupforgame() {
		bg1 = new Background(0, 0);
		bg2 = new Background(1397, 0);
		hb = new Heliboy(180, 100);

		// Initialize Tiles
		try {
			loadMap("data/map2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		starttime = System.currentTimeMillis();

		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}

		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}

			}
		}

	}

	@Override
	public void run() {
		if (state == GameState.Running) {
			while (true) {

				updateTiles();
				bg1.update();
				bg2.update();
				hb.update();

				// currentSprite = hanim.getImage();

				animate();
				repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void animate() {
		hanim.update(50);
	}

	@Override
	public void update(Graphics g) {

		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());

		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		if (state == GameState.Running) {
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintTiles(g);
			g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);

			g.setFont(font);
			g.setColor(Color.WHITE);
			score = getScore();
			g.drawString(Integer.toString(score), 740, 30);
		} else if (state == GameState.Dead) {
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);
			g.drawString("Your score: " + Integer.toString(score), 360, 300);
		} else {
			g.setColor(Color.WHITE);
			g.drawString("Level Cleared!!!", 360, 240);
			g.drawString("Your score: " + Integer.toString(score), 360, 300);
		}
	}

	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}

	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	private int getScore() {
		int nowscore = (int) ((System.currentTimeMillis() - starttime) / 100);
		return nowscore;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			hb.jump();
			break;

		// case KeyEvent.VK_LEFT:
		// hb.moveLeft();
		// hb.setMovingLeft(true);
		// break;

		// case KeyEvent.VK_RIGHT:
		// hb.moveRight();
		// hb.setMovingRight(true);
		// break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			hb.stopJump();
			break;

		case KeyEvent.VK_LEFT:
			hb.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			hb.stopRight();
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Heliboy getHeliboy() {
		return hb;
	}

}
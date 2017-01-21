package superjackgame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile {

	private int tileX, tileY, speedX, type;
	public Image tileImage;

	private Heliboy hb = StartingGame.getHeliboy();
	private Background bg = StartingGame.getBg1();

	private Rectangle r;

	public Tile(int x, int y, int typeInt) {
		tileX = x * 40;
		tileY = y * 40;

		type = typeInt;

		r = new Rectangle();

		if (type == 5) {
			tileImage = StartingGame.tiledirt;
		} else if (type == 8) {
			tileImage = StartingGame.tilegrassTop;
		} else if (type == 4) {
			tileImage = StartingGame.tilegrassLeft;

		} else if (type == 6) {
			tileImage = StartingGame.tilegrassRight;

		} else if (type == 2) {
			tileImage = StartingGame.tilegrassBot;
		} else if (type == 9) {
			tileImage = StartingGame.finishBack;
		} else if (type == 7) {
			tileImage = StartingGame.finishFront;
		} else {
			type = 0;
		}

	}

	public void update() {
		if (StartingGame.state == StartingGame.GameState.Running) {

			speedX = bg.getSpeedX() * 5;
			tileX += speedX;
			r.setBounds(tileX, tileY, 40, 40);

			if (r.intersects(Flier.flierBox)) {
				if (type != 0) {
					onCollision(type);
				}
			}
		} else {
			speedX = 0;
		}

	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {
		if (rtop.intersects(r)) {

		}

		if (rbot.intersects(r) && type == 8) {
			hb.setJumped(false);
			hb.setSpeedY(0);
			hb.setCenterY(tileY - 63);
		}
	}

	public void onCollision(int type) {
		if (type != 0 && type != 7 && type != 9) {
			StartingGame.state = StartingGame.GameState.Dead;
		} else if (type != 0) {
			StartingGame.state = StartingGame.GameState.Won;
		}
	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftfoot, Rectangle rightfoot) {
		if (type != 5 && type != 2 && type != 0) {
			if (rleft.intersects(r)) {
				hb.setCenterX(tileX + 102);

				hb.setSpeedX(0);

			} else if (leftfoot.intersects(r)) {
				hb.setCenterX(tileX + 85);
				hb.setSpeedX(0);
			}

			if (rright.intersects(r)) {
				hb.setCenterX(tileX - 62);

				hb.setSpeedX(0);
			}

			else if (rightfoot.intersects(r)) {
				hb.setCenterX(tileX - 45);
				hb.setSpeedX(0);
			}
		}
	}

}

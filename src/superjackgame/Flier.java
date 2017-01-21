package superjackgame;

import java.awt.Rectangle;
//import java.util.ArrayList;

public class Flier {

	// Constants are Here
	final int JUMPSPEED = -4;
	final int MOVESPEED = 5;

	private int centerX;
	private int centerY;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	private boolean readyToFire = true;

	private int speedX = MOVESPEED;
	private int speedY = 2;

	public static Rectangle flierBox = new Rectangle(0, 0, 0, 0);

	private Background bg1 = StartingGame.getBg1();
	private Background bg2 = StartingGame.getBg2();

	// private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update() {
		// Moves Character or Scrolls Background accordingly.

		// if (speedX <= 0) {
		// centerX += speedX;
		// bg1.setSpeedX(0);
		// bg2.setSpeedX(0);
		// }
		//
		// if (centerX <= 300 && speedX > 0) {
		// centerX += speedX;
		// }
		//
		// if (speedX > 0 && centerX > 300) {
		// bg1.setSpeedX(-MOVESPEED / 5);
		// bg2.setSpeedX(-MOVESPEED / 5);
		// }

		if (StartingGame.state == StartingGame.GameState.Running) {

			bg1.setSpeedX(-MOVESPEED / 5);
			bg2.setSpeedX(-MOVESPEED / 5);

			// Updates Y Position
			centerY += speedY;

			// Prevents going beyond X coordinate of 0
			if (centerX + speedX <= 25) {
				centerX = 25;
			}

			flierBox.setBounds(centerX - 25, centerY - 25, 50, 60);
		} else {
			speedX = 0;
			speedY = 0;
		}

	}

	public void moveRight() {
		speedX = MOVESPEED;
	}

	public void moveLeft() {
		speedX = -MOVESPEED;
	}

	public void stopRight() {
		setMovingRight(false);
		stop();
	}

	public void stopLeft() {
		setMovingLeft(false);
		stop();
	}

	private void stop() {
		if (isMovingRight() == false && isMovingLeft() == false) {
			speedX = MOVESPEED;
		}

		if (isMovingRight() == false && isMovingLeft() == true) {
			moveLeft();
		}

		if (isMovingRight() == true && isMovingLeft() == false) {
			moveRight();
		}

	}

	public void jump() {
		speedY = JUMPSPEED;
	}

	public void stopJump() {
		speedY = 2;
	}

	// public void shoot() {
	// if (readyToFire) {
	// Projectile p = new Projectile(centerX + 50, centerY - 25);
	// projectiles.add(p);
	// }
	// }

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public boolean isDucked() {
		return ducked;
	}

	public void setDucked(boolean ducked) {
		this.ducked = ducked;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	// public ArrayList getProjectiles() {
	// return projectiles;
	// }

	public boolean isReadyToFire() {
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire) {
		this.readyToFire = readyToFire;
	}

}

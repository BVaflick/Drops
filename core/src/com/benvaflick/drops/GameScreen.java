package com.benvaflick.drops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {
	static int SCREEN_WIDTH = 800;
	static int SCREEN_HEIGHT = 480;

	final Drop game;
	long lastDropTime;

	int dropsGatchered;
	int livesLeft;

	Vector3 touchPos;
	OrthographicCamera camera;
	SpriteBatch batch;

	Texture dropImg;
	Texture bucketImg;
	Texture gatheredDropsImg;
	Texture livesImg;
	Texture rect;

	Rectangle bucket;
	Array<Rectangle> drops;

	Sound dropSound;
	Music rainMusic;

	public GameScreen (final Drop game) {
		this.game = game;
		this.batch = game.batch;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		dropImg = new Texture("droplet.png");
		bucketImg = new Texture("bucket.png");
		gatheredDropsImg = new Texture("drops.png");
		livesImg = new Texture("lives.png");
		rect = new Texture("rect.png");

		dropsGatchered = 0;
		livesLeft = 3;

		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		touchPos = new Vector3();
		rainMusic.setLooping(true);

		bucket = new Rectangle();
		bucket.x = SCREEN_WIDTH / 2 - bucketImg.getWidth() / 2;
		bucket.y = 20;
		bucket.width = bucketImg.getWidth();
		bucket.height = bucketImg.getHeight();

		drops = new Array<Rectangle>();
		spawnDrops();
	}

	private void spawnDrops(){
		Rectangle drop = new Rectangle();
		drop.x = MathUtils.random(0, SCREEN_WIDTH - dropImg.getWidth());
		drop.y = SCREEN_HEIGHT;
		drop.width = dropImg.getWidth()-10;
		drop.height = dropImg.getHeight()-10;
		drops.add(drop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void show() {
		rainMusic.play();
	}

	@Override
	public void render (float delta) {

		Gdx.gl.glClearColor(0.66f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImg, bucket.x, bucket.y);
		batch.draw(livesImg, 10, 445);
		game.font.draw(batch, livesLeft + "", 35, 460);
		batch.draw(gatheredDropsImg, 45, 445);
		game.font.draw(batch, dropsGatchered + "", 70, 460);

		for(Rectangle drop: drops) {
			batch.draw(dropImg, drop.x - 5, drop.y - 5);
		}
		batch.end();
		if(Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - (bucketImg.getWidth() / 2);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 400 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 400 * Gdx.graphics.getDeltaTime();

		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > SCREEN_WIDTH - bucketImg.getWidth()) bucket.x = SCREEN_WIDTH - bucketImg.getWidth();

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnDrops();

		Iterator<Rectangle> iter = drops.iterator();
		while (iter.hasNext()) {
			Rectangle drop = iter.next();
			drop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(drop.y + dropImg.getHeight() < 0) {
				iter.remove();
				livesLeft--;
				if(livesLeft == 0) {
					game.setScreen(new MainMenuScreen(game));
					dispose();
				}
			}
			if(drop.overlaps(bucket)) {
				dropSound.play();
				dropsGatchered++;
				iter.remove();
			}
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		dropImg.dispose();
		bucketImg.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
}

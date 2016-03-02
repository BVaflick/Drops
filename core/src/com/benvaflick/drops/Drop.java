package com.benvaflick.drops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Drop {

    static Texture DROP_IMAGE = new Texture("droplet.png");

    int x;
    int y;
    int width;
    int height;
    int speed;
    Rectangle rect;

    public Drop(int gameMode, int difficulty){
        x = MathUtils.random(0, GameScreen.SCREEN_WIDTH - DROP_IMAGE.getWidth());
        y = GameScreen.SCREEN_HEIGHT;
        width = DROP_IMAGE.getWidth()-10;
        height = DROP_IMAGE.getHeight()-10;
        rect = new Rectangle(x, y , width, height);
        speed = (100 * gameMode) + (10 * difficulty);
    }
}

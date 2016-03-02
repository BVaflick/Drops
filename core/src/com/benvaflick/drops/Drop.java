package com.benvaflick.drops;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Drop {



    int x;
    int y;
    int width;
    int height;
    int speed;
    Rectangle rect;

    public Drop(int gameMode, int difficulty){
        x = MathUtils.random(0, GameScreen.SCREEN_WIDTH - 40);
        y = GameScreen.SCREEN_HEIGHT;
        width = 30;
        height = 30;
        rect = new Rectangle(x, y , width, height);
        speed = (100 * gameMode) + (10 * difficulty);
    }
}

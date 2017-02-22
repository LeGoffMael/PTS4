package com.pts4.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by legof on 22/02/2017.
 */

public class PlayState extends State {
    Texture train;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        train = new Texture("images/train.png");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public Array<Rectangle> getButtonsPosition() {
        return null;
    }
}

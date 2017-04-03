package com.pts4.game.sprites.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pts4.game.Settings;

/**
 * Created by PC on 23/02/2017.
 */

public class TrafficLight extends Obstacle {


    /**
     * Constructeur
     * @param x
     * @param y
     * @param z
     */
    public TrafficLight(int x, int y, int z) {
        super(x, y, z);
        img = Settings.getInstance().getThemeManager().getTheme().getTrafficLight(z);

        //La hitbox de l'obstacle
        hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    /**
     * Vide la mémoire
     */
    @Override
    public void dispose() {
        this.img.dispose();
    }


}

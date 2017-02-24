package com.pts4.game.sprites.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Le Goff Maël on 24/02/2017.
 */

public class Bats extends Obstacle {

    /**
     * Constructeur
     * @param x
     * @param y
     * @param z
     */
    public Bats(int x, int y, int z) {
        super(x, y, z);

        img = new Texture("images/obstacles/bat.png");

        //La hitbox de l'obstacle
        hitbox = new Rectangle(x, y, img.getWidth(), img.getHeight());
    }

    /**
     *
     * @return texture image
     */
    @Override
    public Texture img() {
        return this.img;
    }

    /**
     *
     * @return position obstacle
     */
    @Override
    public Vector3 posObstacle() {
        return this.posObstacle;
    }

    /**
     *
     * @return hitbox
     */
    @Override
    public Rectangle getHitbox() {
        return this.hitbox;
    }

    /**
     * Vide la mémoire
     */
    @Override
    public void dispose() {
        this.img.dispose();
    }
}

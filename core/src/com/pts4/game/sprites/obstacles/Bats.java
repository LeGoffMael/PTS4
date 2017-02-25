package com.pts4.game.sprites.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pts4.game.sprites.Animation;

/**
 * Created by Le Goff Maël on 24/02/2017.
 */

public class Bats extends Obstacle {
    private Animation batsAnimation;

    /**
     * Constructeur
     * @param x
     * @param y
     * @param z
     */
    public Bats(int x, int y, int z) {
        super(x, y, z);

        img = new Texture("images/obstacles/bats.png");

        //L'animation est composée de 3 images et la durée entre chaque est de 0,5 seconde
        batsAnimation = new Animation(new TextureRegion(img), 4, 0.5f);

        //La hitbox de l'obstacle
        hitbox = new Rectangle(x, y, img.getWidth() / 4 - 5, img.getHeight() - 5);
    }

    public void update(float dt) {
        //On met à jour l'animation
        batsAnimation.update(dt);
    }


    /**
     * Retourne la texture courante
     * @return
     */
    public TextureRegion getTextureOfRegion() {
        return batsAnimation.getFrame();
    }


    /**
     * Vide la mémoire
     */
    @Override
    public void dispose() {
        this.img.dispose();
    }
}

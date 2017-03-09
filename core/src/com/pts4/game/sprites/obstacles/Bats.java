package com.pts4.game.sprites.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pts4.game.Singleton;
import com.pts4.game.sprites.Animation;

/**
 * Created by Le Goff Maël on 24/02/2017.
 */

public class Bats extends Obstacle {
    private Animation batsAnimation;
    private Vector3 velocity;
    private static final float SPEED = -1;

    /**
     * Constructeur
     * @param x
     * @param y
     * @param z
     */
    public Bats(int x, int y, int z) {
        super(x, y, z);

        img = Singleton.getInstance().getThemeManager().getTheme().getBats(z);

        //L'animation est composée de 3 images et la durée entre chaque est de 0,35 seconde
        batsAnimation = new Animation(new TextureRegion(img), 4, 0.35f);

        //La hitbox de l'obstacle
        hitbox = new Rectangle(x, y, img.getWidth() / 4 - 5, img.getHeight() - 5);

        this.velocity = new Vector3(0,0,0);
    }

    public void update(float dt) {
        //On met à jour l'animation
        batsAnimation.update(dt);

        velocity.add((int)SPEED, 0, 0);
        //Multiplié par delta time
        velocity.scl(dt/10);

        //On bouge la position des bats et de leur hitbox
        this.posObstacle.add(velocity.x, 0, 0);
        this.hitbox.setPosition(this.posObstacle.x, this.posObstacle.y);

        //On inverse ce qu'on a multiplié précédemment
        velocity.scl(1/(dt/10));
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

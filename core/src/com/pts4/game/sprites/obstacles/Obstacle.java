package com.pts4.game.sprites.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by PC on 23/02/2017.
 */

public abstract class Obstacle {
    protected Texture img;
    protected Vector3 posObstacle;
    protected Rectangle hitbox;

    /**
     * Constructeur
     * @param x position
     * @param y
     * @param z
     */
    public Obstacle(int x, int y, int z){
        int new_z;

        //On gère que la position ne dépasse pas [1;3]
        if(z > 3)
            new_z = 3;
        else if(z < 1)
            new_z = 1;
        else
            new_z = z;

        posObstacle = new Vector3(x,y,new_z);
    }

    /**
     * Méthode abstraite qui vide la mémoire de l'objet
     */
    public abstract void dispose();


    /**
     * Retourne l'image représentant l'obstacle
     * @return
     */
    public Texture getTexture() {
        return this.img;
    }

    /**
     * Retourne la position de l'obstacle
     * @return
     */
    public Vector3 getPosition() {
        return this.posObstacle;
    }

    /**
     * Retourne la hit box de l'obstacle
     * @return
     */
    public Rectangle getHitbox() {
        return this.hitbox;
    }

    /**
     * Retourne vrai si le rectangle passé en paramétre touche la hitbox de l'obstacle
     * @param player hitbox du character
     * @return
     */
    public boolean collides(Rectangle player) {
        return player.overlaps(hitbox);
    }

    /**
     * Retourne vrai si vector passé en paramètre est égale sur l'axe z à celui de l'obstacle
     * @param pos
     * @return
     */
    public boolean samePlan(Vector3 pos) {
        boolean res=false;
        if (pos.z == this.posObstacle.z)
            res=true;
        return res;
    }
}

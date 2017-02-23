package com.pts4.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by PC on 23/02/2017.
 */

public abstract class Obstacle {
    protected Texture img;
    protected Vector3 posObstacle;
    protected Random rand;

    /**
     * Constructeur
     * @param x
     * @param y
     * @param z
     */
    public Obstacle(int x, int y, int z){
        rand = new Random();

        posObstacle = new Vector3(x,y,z);
    }

    /**
     * Méthode abstraite return img
     * @return
     */
    public abstract Texture img();

    /**
     * Méthode abstraite return posObstacle
     * @return
     */
    public abstract Vector3 posObstacle();
}

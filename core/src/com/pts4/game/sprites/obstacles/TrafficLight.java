package com.pts4.game.sprites.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

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
        if(z == 1){
            img = new Texture("images/obstacles/feuVert.png");
        }
        if(z == 2){
            img = new Texture("images/obstacles/feuOrange.png");
        }
        if(z == 3){
            img = new Texture("images/obstacles/feuRouge.png");
        }
    }

    /**
     *
     * @return la texture de l'image
     */
    @Override
    public Texture img() {
        return this.img;
    }

    /**
     *
     * @return position de l'obstacle
     */
    @Override
    public Vector3 posObstacle() {
        return this.posObstacle;
    }


}

package com.pts4.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Le Goff Maël on 23/02/2017.
 */

public class Character {
    private Vector3 position; //De profondeur z de 0 (1er plan) à 2 (dernier plan)
    private Vector3 velocity;
    private Texture character;

    public Character(int x, int y) {
        this.position = new Vector3(x, y, 1);
        this.velocity = new Vector3(0, 0, 0);

        this.character = new Texture("images/character/character_default.png");
    }

    /**
     * Met à jour le personnage
     * @param dt
     */
    public void update(float dt) {
        velocity.scl(dt);

        //On bouge l'oiseau en conséquence
        position.add(100 * dt, velocity.y, position.z);
    }

    /**
     * Fait sauter le personnage
     */
    public void jump() {
        velocity.y = 250;
    }

    /**
     * Fait glisser le personnage
     */
    public void slide() {
    }

    /**
     * Change la position en profondeur du personnage
     * @param avant à true si vers l'avant à false si vers l'arriere
     */
    public void changePlan(boolean avant) {
        if (avant==false) {
            //Si il n'est pas a la derniere position on le recule
            if (position.z < 2)
                position.z += 1;
        }
        else {
            //Si il n'est pas a la premiere position on l'avance
            if (position.z > 0)
                position.z -= 1;
        }
    }

    /**
     * Retourne la position du personnage
     * @return
     */
    public Vector3 getPosition() {
        return this.position;
    }

    /**
     * Retourne la texture du personnage
     * @return
     */
    public Texture getTexture() {
        return this.character;
    }


    /**
     * Libère la mémoire
     */
    public void dispose() {
        this.character.dispose();
    }
}

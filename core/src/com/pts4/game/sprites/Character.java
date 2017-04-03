package com.pts4.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pts4.game.Settings;

/**
 * Created by Le Goff Maël on 23/02/2017.
 */

public class Character {
    //Represente la chute du personnage
    private static final int GRAVITY = -10;
    //Represente la position y minimale
    private int y_min;

    private Vector3 position; //De profondeur z de 1 (1er plan) à 3 (dernier plan)
    private Vector3 velocity;
    private Texture character;

    private Rectangle hitbox;

    public Character(int x, int y) {
        this.y_min = y;
        this.position = new Vector3(x, y, 2);
        this.velocity = new Vector3(0, 0, 0);

        this.character = Settings.getInstance().getThemeManager().getTheme().getCharacter("default");

        //La hitbox du personnage
        hitbox = new Rectangle(x, y, character.getWidth(), character.getHeight());
    }

    /**
     * Met à jour le personnage
     * @param dt
     */
    public void update(float dt) {
        //Si la position en y du personnage est supérieur à celle initiale
        if(position.y > y_min) {
            //le personnage subit de la gravité
            velocity.add(0, GRAVITY, 0);
        }

        //Vitesse multiplié par delta time
        velocity.scl(dt);

        //On bouge le personnage en conséquence
        position.add(100 * dt, velocity.y, 0);

        //On empêche le personnage d'aller en dessous de sa position initiale
        if(position.y < y_min) {
            position.y = y_min;
        }

        //On inverse ce qu'on a multiplié précédemment
        velocity.scl(1/dt);

        //On positionne la hitbox aux coordonnées du personnage
        hitbox.setPosition(position.x, position.y);
    }

    /**
     * Fait sauter le personnage
     */
    public void jump() {
        if (position.y == y_min) {
            velocity.y = 310;
            this.character = Settings.getInstance().getThemeManager().getTheme().getCharacter("jump");
        }
    }

    /**
     * Fait glisser le personnage
     */
    public void slide() {
        this.character = Settings.getInstance().getThemeManager().getTheme().getCharacter("slide");
    }

    /**
     * Change la position en profondeur du personnage
     * @param index
     */
    public void changePlan(int index) {
        if (index==0) {
            //Si il n'est pas a la derniere position on le recule
            if (position.z < 3)
                position.z += 1;
        }
        else if (index==1){
            //Si il n'est pas a la premiere position on l'avance
            if (position.z > 1)
                position.z -= 1;
        }
        this.character = Settings.getInstance().getThemeManager().getTheme().getCharacter("changePlan");
    }

    /**
     * Lorsque le personnage meurt
     */
    public void die() {
        this.y_min = -500;
    }

    /**
     * Setter du sprite du character
     * Je pensais en avoir besoin mais en fait non
     * @param chemin
     */
    public void setSprite(String chemin){
        this.character = new Texture("chemin");
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
     * Retourne la hitbox du personnage
     * @return
     */
    public Rectangle getHitBox() {
        return this.hitbox;
    }

    /**
     * Libère la mémoire
     */
    public void dispose() {
        this.character.dispose();
    }
}

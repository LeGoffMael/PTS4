package com.pts4.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Maël Le Goff on 22/02/2017.
 * Représente les états possibles du jeu (Menu, Jeu, Game Over, ...)
 */

public abstract class State {
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    public abstract void handleInput();

    /**
     * @param dt : delta time, la différence entre une image et la prochaine
     */
    public abstract void update(float dt);

    /**
     * @param sb : tous ce qu'on à besoin d'afficher à l'écran
     */
    public abstract void render(SpriteBatch sb);

    /**
     * Permet de retirer les éléments aux fur et à mesure pour ne pas "saturer" la mémoire
     */
    public abstract void dispose();

    /**
     * Retourne la caméra
     * @return
     */
    public abstract OrthographicCamera getCamera();

    /**
     * Retourne la hitbox du bouton
     * @return
     */
    public abstract Array<Rectangle> getButtonsPosition();
}

package com.pts4.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Maël Le Goff on 22/02/2017.
 * Manage tous les états
 */

public class GameStateManager {

    //contient tous les états, l'état courant est au sommet de la pile
    private Stack<State> states;

    public GameStateManager() {
        states = new Stack<State>();
    }

    /**
     * Place l'état au sommet de la pile
     * @param state
     */
    public void push(State state) {
        states.push(state);
    }

    /**
     * Supprime l'état au sommet de la pile
     */
    public void pop() {
        states.pop().dispose();
    }

    /**
     * Supprime l'état au sommet de la pile et le remplace par celui passé en paramètre
     * @param state
     */
    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Appelle la fonction update de l'état qui est au sommet de la pile
     * @param dt
     */
    public void update(float dt) {
        states.peek().update(dt); //peek retourne l'objet au sommet de la pile
    }

    /**
     * Appelle la fonction render de l'état qui est au sommet de la pile
     * @param sb
     */
    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }

    public State getState() {
        return this.states.firstElement();
    }
}


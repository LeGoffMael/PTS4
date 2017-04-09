package com.pts4.game.themes;

import com.badlogic.gdx.graphics.Texture;

import java.util.Stack;

/**
 * Created by Le Goff Maël on 03/03/2017.
 */
public class ThemeManager {

    //contient tous les themes, le theme courant est au sommet de la pile
    private Stack<Theme> themes;

    public ThemeManager() {
        themes = new Stack<Theme>();
    }

    /**
     * Place le theme au sommet de la pile
     * @param theme
     */
    public void push(Theme theme) {
        themes.push(theme);
    }

    /**
     * Supprime le theme au sommet de la pile
     */
    public void pop() {
        themes.pop().dispose();
    }

    /**
     * Supprime le theme au sommet de la pile et le remplace par celui passé en paramètre
     * @param state
     */
    public void set(Theme state) {
        themes.pop().dispose();
        themes.push(state);
    }

    /**
     * Retourne le theme courant
     */
    public Theme getTheme() {
        return this.themes.firstElement();
    }

    public Theme getThemeString(String name) {
        Theme res = null;

        if (name.equals("default")) {
            res = new DefaultTheme();
        }

        return res;
    }

    /**
     * Crée et applique le thème jour
     */
    public void setThemeDay() {
        Theme day = new DefaultTheme();
        day.setBackgroundSky(new Texture("images/backgrounds/day/daySky.png"));
        day.setBackgroundGround(new Texture("images/backgrounds/day/dayGround.png"));
        this.push(day);
    }
}

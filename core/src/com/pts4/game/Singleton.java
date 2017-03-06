package com.pts4.game;

import com.pts4.game.themes.Theme;
import com.pts4.game.themes.ThemeManager;

/**
 * Created by Le Goff Maël on 06/03/2017.
 */

public class Singleton {
    /** L'instance statique */
    private static Singleton instance;

    private ThemeManager themeManager;
    private boolean music;
    private boolean effects;

    /** Constructeur redéfini comme étant privé pour interdire
     * son appel et forcer à passer par la méthode <link
     */
    private Singleton() {
        this.themeManager = new ThemeManager();
        this.getThemeManager().setThemeDay();
        this.music = true;
        this.effects = true;
    }

    /** Récupère l'instance unique de la class Singleton.<p>
     * Remarque : le constructeur est rendu inaccessible
     */
    public static Singleton getInstance() {
        if (null == instance) { // Premier appel
            instance = new Singleton();
        }
        return instance;
    }

    public ThemeManager getThemeManager() {
        return themeManager;
    }

    public void setThemeManager(ThemeManager themeManager) {
        this.themeManager = themeManager;
    }

    public boolean getMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public boolean getEffects() {
        return effects;
    }

    public void setEffects(boolean effects) {
        this.effects = effects;
    }
}

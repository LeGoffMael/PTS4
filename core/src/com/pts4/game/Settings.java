package com.pts4.game;

import com.badlogic.gdx.graphics.Texture;
import com.pts4.game.themes.Theme;
import com.pts4.game.themes.ThemeManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Le Goff Maël on 06/03/2017.
 */

public class Settings {
    /** L'instance statique */
    private static Settings instance;

    private ThemeManager themeManager;
    private boolean music;
    private boolean effects;

    /** Constructeur redéfini comme étant privé pour interdire
     * son appel et forcer à passer par la méthode <link
     */
    private Settings() {
        this.themeManager = new ThemeManager();
        this.getThemeManager().setThemeDay();
        this.music = true;
        this.effects = true;
        this.load();
    }

    /** Récupère l'instance unique de la class Settings.<p>
     * Remarque : le constructeur est rendu inaccessible
     */
    public static Settings getInstance() {
        if (null == instance) { // Premier appel
            instance = new Settings();
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
        this.save();
    }

    public boolean getEffects() {
        return effects;
    }

    public void setEffects(boolean effects) {
        this.effects = effects;
        this.save();
    }

    /**
     * On sauvegarde les attributs et le thème courant
     */
    public void save() {
        this.saveSettings();
        this.saveTheme(this.getThemeManager().getTheme());
    }

    /**
     * On charge les attributs et le thème courant
     */
    public void load() {
        this.loadSettings();
        this.loadTheme(this.getThemeManager().getTheme());
    }

    public void saveSettings() {
        FileWriter file_writer = null;
        BufferedWriter tampon_writer = null;
        try {
            file_writer = new FileWriter("settings.txt");
            tampon_writer = new BufferedWriter(file_writer);
            tampon_writer.write(this.getMusic() + ";" + this.getEffects());
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (tampon_writer != null) {
                    tampon_writer.flush();
                    tampon_writer.close();
                }
                if (file_writer != null)
                    file_writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * On enregistre le theme courant
     * @param theme
     */
    public void saveTheme(Theme theme) {
        FileWriter file_writer = null;
        BufferedWriter tampon_writer = null;
        try {
            file_writer = new FileWriter("theme.txt");
            tampon_writer = new BufferedWriter(file_writer);

            //On enregistre les paramètres du thème
            tampon_writer.write(theme.getAllPath());

        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (tampon_writer != null) {
                    tampon_writer.flush();
                    tampon_writer.close();
                }
                if (file_writer != null)
                    file_writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void loadSettings() {
        FileReader file_reader = null;
        BufferedReader tampon_reader = null;

        try {
            file_reader = new FileReader("settings.txt");
            tampon_reader = new BufferedReader(file_reader);

            while (true) {
                // Lit une ligne du fichier
                String ligne = tampon_reader.readLine();
                // Vérifie la fin de fichier
                if (ligne == null)
                    break;
                //On assigne les valeurs trouvées aux variables locales
                String settings[] = ligne.split(";");
                this.setMusic(Boolean.valueOf(settings[0]));
                this.setEffects(Boolean.valueOf(settings[1]));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (tampon_reader != null)
                    tampon_reader.close();
                if (file_reader != null)
                    file_reader.close();
            } catch(IOException exception1) {
                exception1.printStackTrace();
            }
        }
    }

    /**
     * On initialise le theme courant
     */
    public void loadTheme(Theme theme) {
        FileReader file_reader = null;
        BufferedReader tampon_reader = null;

        try {
            file_reader = new FileReader("theme.txt");
            tampon_reader = new BufferedReader(file_reader);

            while (true) {
                // Lit une ligne du fichier
                String ligne = tampon_reader.readLine();
                // Vérifie la fin de fichier
                if (ligne == null)
                    break;

                //On assigne les valeurs trouvées au theme courant
                String themeData[] = ligne.split(";");

                Theme themeCurrent = new Theme(themeData[0]);

                //Les Textures
                themeCurrent.setBackgroundSky(new Texture(themeData[1]));
                themeCurrent.setBackgroundGround(new Texture(themeData[2]));
                themeCurrent.setTrain(new Texture(themeData[3]));
                themeCurrent.setFrontTrain(new Texture(themeData[4]));
                themeCurrent.setCharacter(new Texture(themeData[5]),"default");
                themeCurrent.setCharacter(new Texture(themeData[6]),"jump");
                themeCurrent.setCharacter(new Texture(themeData[7]),"slide");
                themeCurrent.setCharacter(new Texture(themeData[8]),"changePlan");
                themeCurrent.setBats(new Texture(themeData[9]),1);
                themeCurrent.setBats(new Texture(themeData[10]),2);
                themeCurrent.setBats(new Texture(themeData[11]),3);
                themeCurrent.setTrafficLight(new Texture(themeData[12]),1);
                themeCurrent.setTrafficLight(new Texture(themeData[13]),2);
                themeCurrent.setTrafficLight(new Texture(themeData[14]),3);

                //Les Sons
                themeCurrent.setMusic(themeData[15]);
                themeCurrent.setDie(themeData[16]);
                themeCurrent.setJump(themeData[17]);
                themeCurrent.setSlide(themeData[18]);

                this.getThemeManager().push(themeCurrent);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (tampon_reader != null)
                    tampon_reader.close();
                if (file_reader != null)
                    file_reader.close();
            } catch(IOException exception1) {
                exception1.printStackTrace();
            }
        }
    }
}

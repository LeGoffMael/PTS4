package com.pts4.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pts4.game.LevelManager;
import com.pts4.game.PTS4;
import com.pts4.game.Singleton;

/**
 * Created by Le Goff Maël on 28/02/2017.
 */

public class PauseState extends State {
    private LevelManager level;
    private Texture pausePlayButton, musicSetting, effectsSetting;

    public static BitmapFont font, shadow;

    public PauseState(GameStateManager gsm, LevelManager lm) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        this.level = lm;
        this.camera = this.level.getCamera();

        this.pausePlayButton = new Texture("images/buttons/pausePlayBtn.png");

        //La police
        font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        font.getData().setScale(.20f, .20f);
        shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
        shadow.getData().setScale(.20f, .20f);
    }

    @Override
    public void handleInput() {
        //Représente les coordonnées du bouton
        Rectangle bounds_play = new Rectangle(this.getButtonsPosition().first());
        Rectangle bounds_music = new Rectangle(this.getButtonsPosition().get(1));
        Rectangle bounds_effects = new Rectangle(this.getButtonsPosition().get(2));
        Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(tmp);

        //Si l'écran est touché
        if(Gdx.input.justTouched()) {
            //Au coordonnées du bouton play
            if (bounds_play.contains(tmp.x, tmp.y)) {
                PlayState playState = new PlayState(gsm);
                //On remet le niveau
                playState.setLevel(this.level);
                gsm.set(playState);
            }
            //Au coordonnées du bouton music
            if (bounds_music.contains(tmp.x, tmp.y)) {
                if(Singleton.getInstance().getMusic())
                    Singleton.getInstance().setMusic(false);
                else
                    Singleton.getInstance().setMusic(true);
            }
            //Au coordonnées du bouton effets
            if (bounds_effects.contains(tmp.x, tmp.y)) {
                if(Singleton.getInstance().getEffects())
                    Singleton.getInstance().setEffects(false);
                else
                    Singleton.getInstance().setEffects(true);
            }
        }
    }

    /**
     * @param dt : delta time, la différence entre une image et la prochaine
     */
    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * @param sb : tous ce qu'on à besoin d'afficher à l'écran
     */
    @Override
    public void render(SpriteBatch sb) {
        if(Singleton.getInstance().getMusic())
            this.musicSetting = new Texture("images/buttons/soundBtn.png");
        else
            this.musicSetting = new Texture("images/buttons/muteBtn.png");

        if(Singleton.getInstance().getEffects())
            this.effectsSetting = new Texture("images/buttons/soundBtn.png");
        else
            this.effectsSetting = new Texture("images/buttons/muteBtn.png");

        //définie la matrice de projection des batch
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        //On affiche les éléments composants le niveau
        this.level.render(sb);

        //On place le bouton de pause
        sb.draw(pausePlayButton, camera.position.x - pausePlayButton.getWidth() / 2,   PTS4.HEIGHT / 4 - pausePlayButton.getHeight());

        //On place le texte musique
        shadow.draw(sb, "MUSIQUE", camera.position.x - musicSetting.getWidth() * 2 - (font.getSpaceWidth() * font.toString().length()) / 2,  PTS4.HEIGHT / 3 + musicSetting.getHeight() / 2);
        font.draw(sb, "MUSIQUE", camera.position.x - musicSetting.getWidth() * 2 - (font.getSpaceWidth() * font.toString().length()) / 2,  PTS4.HEIGHT / 3 + musicSetting.getHeight() / 2);
        //On place le bouton de gestion des musiques
        sb.draw(musicSetting, camera.position.x - musicSetting.getWidth() * 2,  PTS4.HEIGHT / 4 + musicSetting.getHeight() / 2);

        //On place le texte effets
        shadow.draw(sb, "EFFETS", camera.position.x + effectsSetting.getWidth() - (font.getSpaceWidth() * font.toString().length()) / 2,  PTS4.HEIGHT / 3 + effectsSetting.getHeight() / 2);
        font.draw(sb, "EFFETS", camera.position.x + effectsSetting.getWidth() - (font.getSpaceWidth() * font.toString().length()) / 2,  PTS4.HEIGHT / 3 + effectsSetting.getHeight() / 2);
        //On place le bouton de gestion des effets
        sb.draw(effectsSetting, camera.position.x + effectsSetting.getWidth(),  PTS4.HEIGHT / 4 + effectsSetting.getHeight() / 2);

        sb.end();
    }

    /**
     * Permet de retirer les éléments aux fur et à mesure pour ne pas "saturer" la mémoire
     */
    @Override
    public void dispose() {
        pausePlayButton.dispose();
        musicSetting.dispose();
        effectsSetting.dispose();
        System.out.println("Pause State Disposed");
    }

    /**
     * Retourne la caméra
     *
     * @return
     */
    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    /**
     * Retourne la hitbox du bouton
     *
     * @return
     */
    @Override
    public Array<Rectangle> getButtonsPosition() {
        Array<Rectangle> tab_rectangle = new Array();
        //On ajoute le bouton play
        tab_rectangle.add(new Rectangle(camera.position.x - pausePlayButton.getWidth() / 2,   PTS4.HEIGHT / 4 - pausePlayButton.getHeight(), pausePlayButton.getWidth(), pausePlayButton.getHeight()));
        //On ajoute le bouton music
        tab_rectangle.add(new Rectangle(camera.position.x - musicSetting.getWidth() * 2,  PTS4.HEIGHT / 4 + musicSetting.getHeight() / 2, musicSetting.getWidth(), musicSetting.getHeight()));
        //On ajoute le bouton effets
        tab_rectangle.add(new Rectangle(camera.position.x + effectsSetting.getWidth(),  PTS4.HEIGHT / 4 + effectsSetting.getHeight() / 2, effectsSetting.getWidth(), effectsSetting.getHeight()));
        return tab_rectangle;
    }
}

package com.pts4.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pts4.game.LevelManager;
import com.pts4.game.PTS4;

/**
 * Created by Le Goff Maël on 28/02/2017.
 */

public class PauseState extends State {
    private LevelManager level;
    private Texture background_sky,  background_ground;

    public PauseState(GameStateManager gsm, LevelManager lm) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        this.background_sky = new Texture("images/backgrounds/day/daySky.png");
        this.background_ground = new Texture("images/backgrounds/day/dayGround.png");

        this.level = lm;
        this.camera = this.level.getCamera();
    }

    @Override
    public void handleInput() {

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
        //définie la matrice de projection des batch
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        sb.draw(background_ground, camera.position.x - camera.viewportWidth / 2, 0, PTS4.WIDTH / 2, PTS4.HEIGHT / 2 - background_sky.getHeight() / 4);
        sb.draw(background_sky, camera.position.x - PTS4.WIDTH / 4, PTS4.HEIGHT / 2 - background_sky.getHeight() / 4, PTS4.WIDTH / 2, background_sky.getHeight() / 4);

        //On affiche les éléments composants le niveau
        this.level.render(sb);

        sb.end();
    }

    /**
     * Permet de retirer les éléments aux fur et à mesure pour ne pas "saturer" la mémoire
     */
    @Override
    public void dispose() {
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
        return null;
    }
}

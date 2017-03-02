package com.pts4.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.pts4.game.PTS4;

/**
 * Created by Le Goff Maël on 22/02/2017.
 */

public class MenuState extends State {
    private Texture background_sky;
    private Texture background_ground;
    private Vector2 groundPos1, groundPos2;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        background_sky = new Texture("images/backgrounds/day/daySky.png");
        background_ground = new Texture("images/backgrounds/day/dayGround.png");
        //On détermine la premiere position
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, 0);
        //On détermine la seconde position par rapport à la première
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + PTS4.WIDTH / 2, 0);

        playBtn = new Texture("images/buttons/playBtn.png");
    }

    @Override
    public void handleInput() {
        //Représente les coordonnées du bouton
        Rectangle bounds_play = new Rectangle(this.getButtonsPosition().first());
        Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(tmp);

        //Si l'écran est touché
        if(Gdx.input.justTouched()) {
            //Au coordonnées du bouton play
            if (bounds_play.contains(tmp.x, tmp.y)) {
                gsm.set(new PlayState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        //On test tout le temps si il y a une intéraction
        handleInput();

        updateBackground();

        //On déplace la camera
        camera.position.x =  camera.position.x + 100 * dt;

        //On repositionne la caméra
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        //définie la matrice de projection des batch
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        //On place les 2 sols
        sb.draw(background_ground, groundPos1.x, groundPos1.y, PTS4.WIDTH / 2, PTS4.HEIGHT / 2 - background_sky.getHeight() / 4);
        sb.draw(background_ground, groundPos2.x, groundPos2.y, PTS4.WIDTH / 2, PTS4.HEIGHT / 2 - background_sky.getHeight() / 4);

        //On place le ciel au dessus du sol
        sb.draw(background_sky, camera.position.x - PTS4.WIDTH / 4, PTS4.HEIGHT / 2 - background_sky.getHeight() / 4, PTS4.WIDTH / 2, background_sky.getHeight() / 4);

        //On place le bouton
        sb.draw(playBtn, camera.position.x - playBtn.getWidth() / 2,  PTS4.HEIGHT / 4 - playBtn.getHeight() / 2);

        sb.end();
    }

    @Override
    public void dispose() {
        background_ground.dispose();
        background_sky.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }

    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public Array<Rectangle> getButtonsPosition() {
        Array<Rectangle> tab_rectangle = new Array();
        //On ajoute le bouton de lancement de partie
        tab_rectangle.add(new Rectangle(camera.position.x - playBtn.getWidth() / 2,  PTS4.HEIGHT / 4 - playBtn.getHeight() / 2, playBtn.getWidth(), playBtn.getHeight()));
        return tab_rectangle;
    }

    /**
     * On replace le background pour qu'il soit visible à l'écran
     */
    private void updateBackground() {
        //si la position x de la caméra et supérieur à la première position du sol plus sa largeur
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + PTS4.WIDTH / 2) {
            //on ajoute une position à 2 fois sa largeur
            groundPos1.add(PTS4.WIDTH, 0);
        }
        //si la position x de la caméra et supérieur à la seconde position du sol plus sa largeur
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + PTS4.WIDTH / 2) {
            //on ajoute une position à 2 fois sa largeur
            groundPos2.add(PTS4.WIDTH, 0);
        }
    }
}
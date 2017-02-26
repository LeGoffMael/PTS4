package com.pts4.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pts4.game.LevelManager;
import com.pts4.game.PTS4;
import com.pts4.game.SimpleDirectionGestureDetector;
import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by Le Goff Maël on 22/02/2017.
 */

public class PlayState extends State {
    private Texture background_sky;
    private Texture background_ground;
    private Vector2 groundPos1, groundPos2;

    private LevelManager level;

    //Represente le temps passé dans le niveau
    private float timeCount;

    //Represente si le joueur à perdu
    private boolean gameOver = false;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        background_sky = new Texture("images/backgrounds/day/daySky.png");
        background_ground = new Texture("images/backgrounds/day/dayGround.png");
        //On détermine la premiere position
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, 0);
        //On détermine la seconde position par rapport à la première
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + PTS4.WIDTH / 2, 0);

        level = new LevelManager(this.getCamera());

        this.timeCount = 0;

        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            /**
             * Si on a glissé vers le haut on eloigne le personnage
             */
            @Override
            public void onUp() {
                level.getPlayer().changePlan(0);
                System.out.println(level.getPlayer().getPosition().z);
            }

            /**
             * Si on a glissé vers le bas on rapproche le personnage
             */
            @Override
            public void onDown() {
                level.getPlayer().changePlan(1);
                System.out.println(level.getPlayer().getPosition().z);
            }

            /**
             * Si on touche l'écran le personnage saute
             */
            @Override
            public void onTap() {
                level.getPlayer().jump();
            }

            /**
             * Si on reste appuyé sur l'écran le personnage glisse
             */
            @Override
            public void onLongPress() {
                System.out.println("long press");
                level.getPlayer().slide();
            }
        }));
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        this.timeCount += dt;

        handleInput();

        //On met à jour le background
        updateBackground();

        //Mise à jour du niveau
        level.setCamera(camera);
        level.update(dt, this.timeCount);

        //On vérifie si le personnage est rentré en contact avec un obstacle
        checkCollides(this.level);

        //On déplace la camera
        camera.position.x = camera.position.x + 100 * dt;

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

        //On affiche les éléments composants le niveau
        level.render(sb);

        sb.end();
    }

    /**
     * Analyse si le personnage est rentré en contact avec des obstacles
     * @param level
     */
    public void checkCollides(LevelManager level) {
        //Si on a pas perdu
        if(gameOver == false) {
            for (int i = 0; i < level.getBatsArray().size; i++) {
                //Si le personnage est sur le même plan que les bats
                if(level.getBatsArray().get(i).samePlan(level.getPlayer().getPosition())) {
                    //Si ils se touchent
                    if(level.getBatsArray().get(i).collides(level.getPlayer().getHitBox())) {
                        gameOver = true;
                    }
                }
            }

            for (int i = 0; i < level.getTLightsArray().size; i++) {
                //Si le personnage est sur le même plan que le feu
                if(level.getTLightsArray().get(i).samePlan(level.getPlayer().getPosition())) {
                    //Si ils se touchent
                    if(level.getTLightsArray().get(i).collides(level.getPlayer().getHitBox())) {
                        gameOver = true;
                    }
                }
            }
            if(gameOver)
                System.out.println("game over");
        }
    }

    /**
     * On replace le background pour qu'il soit visible à l'écran
     */
    public void updateBackground() {
        //si la position x de la caméra et supérieur à la première position du sol plus sa largeur
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + PTS4.WIDTH / 2) {
            //on ajoute une position à 2 fois sa largeur
            groundPos1.add(PTS4.WIDTH, 0);
        }
        //si la position x de la caméra et supérieur à la seconde position du sol plus sa largeur
        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + PTS4.WIDTH / 2) {
            //on ajoute une position à 2 fois sa largeur
            groundPos2.add(PTS4.WIDTH, 0);
        }
    }

    @Override
    public void dispose() {
        level.dispose();
    }

    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public Array<Rectangle> getButtonsPosition() {
        return null;
    }

}
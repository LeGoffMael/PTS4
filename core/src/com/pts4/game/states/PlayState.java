package com.pts4.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pts4.game.PTS4;
import com.pts4.game.sprites.Character;
import com.pts4.game.sprites.obstacles.Bats;
import com.pts4.game.sprites.obstacles.TrafficLight;

/**
 * Created by Le Goff Maël on 22/02/2017.
 */

public class PlayState extends State {
    private Texture background_sky;
    private Texture background_ground;
    private Vector2 groundPos1, groundPos2;

    private Texture train, train_front;
    private Character character;

    //Obstacles
    private TrafficLight tLight;
    private Bats bats;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        background_sky = new Texture("images/backgrounds/day/daySky.png");
        background_ground = new Texture("images/backgrounds/day/dayGround.png");
        //On détermine la premiere position
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, 0);
        //On détermine la seconde position par rapport à la première
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + background_ground.getWidth() / 4, 0);

        this.train = new Texture("images/trains/train.png");
        this.train_front = new Texture("images/trains/train_front.png");
        this.character = new Character(50 , this.train.getHeight());

        //Création d'un obstacle
        this.tLight = new TrafficLight(500,0,2);
        this.bats = new Bats(750, train.getHeight(), 1);
    }

    @Override
    public void handleInput() {
        //Si l'écran touché
        if(Gdx.input.justTouched()) {
            //on fait sauter le personnage
            character.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateBackground();
        character.update(dt);

        //On update l'animation des bats
        bats.update(dt);

        //Si on touche l'obstacle et qu'on est sur le même axe z
        if (tLight.samePlan(character.getPosition()) && tLight.collides(character.getHitBox()))
            gsm.set(new PlayState(gsm));
        if (bats.samePlan(character.getPosition()) && bats.collides(character.getHitBox()))
            gsm.set(new PlayState(gsm));

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

        //On place les wagons
        sb.draw(train, camera.position.x - train.getWidth(), 0);
        sb.draw(train_front, camera.position.x, 0);

        //On place le personnage
        sb.draw(character.getTexture(), character.getPosition().x, character.getPosition().y);

        //Dessin d'un obstacle
        sb.draw(tLight.getTexture(),tLight.getPosition().x,tLight.getPosition().y);
        sb.draw(bats.getTextureOfRegion(),bats.getPosition().x,bats.getPosition().y);

        sb.end();
    }

    @Override
    public void dispose() {
        this.train.dispose();
        this.train_front.dispose();
        this.character.dispose();
        this.tLight.dispose();
        this.bats.dispose();
    }

    @Override
    public OrthographicCamera getCamera() {
        return null;
    }

    @Override
    public Array<Rectangle> getButtonsPosition() {
        return null;
    }

    /**
     * On replace le background pour qu'il soit visible à l'écran
     */
    private void updateBackground() {
        //si la position x de la caméra et supérieur à la première position du sol plus sa largeur
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + background_ground.getWidth() / 4) {
            //on ajoute une position à 2 fois sa largeur
            groundPos1.add(background_ground.getWidth() / 2, 0);
            System.out.println("> 1");
        }
        //si la position x de la caméra et supérieur à la seconde position du sol plus sa largeur
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + background_ground.getWidth() / 4) {
            //on ajoute une position à 2 fois sa largeur
            groundPos2.add(background_ground.getWidth() / 2, 0);
            System.out.println("> 2");
        }
    }
}

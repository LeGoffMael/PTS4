package com.pts4.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.pts4.game.LevelManager;
import com.pts4.game.PTS4;
import com.pts4.game.Score;
import com.pts4.game.SimpleDirectionGestureDetector;

/**
 * Created by Le Goff Maël on 22/02/2017.
 */

public class PlayState extends State {
    private LevelManager level;

    //Represente le temps passé dans le niveau
    private float timeCount = 0;
    //Represente le score
    private Score score = new Score();

    //Represente si le joueur à perdu
    private boolean gameOver = false;

    private Texture pauseButton;

    public static BitmapFont big_font, big_shadow, little_font, little_shadow;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        level = new LevelManager(this.getCamera());

        this.pauseButton = new Texture("images/buttons/pauseBtn.png");

        //La grosse police
        big_font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        big_font.getData().setScale(.75f, .75f);
        big_shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
        big_shadow.getData().setScale(.75f, .75f);

        //La petite police
        little_font = big_font;
        little_font.getData().setScale(.50f, .50f);
        little_shadow = little_font;
        little_shadow.getData().setScale(.50f, .50f);

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
        //Représente les coordonnées du bouton
        Rectangle bounds_pause = new Rectangle(this.getButtonsPosition().first());
        Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(tmp);

        //Si l'écran est touché
        if(Gdx.input.justTouched()) {
            //Au coordonnées du bouton pause
            if (bounds_pause.contains(tmp.x, tmp.y)) {
                gsm.set(new PauseState(gsm, this.level, this.timeCount));
            }
        }
    }

    @Override
    public void update(float dt) {
        this.timeCount += dt;

        //Score player
        this.score.setScore(this.timeCount);
        System.out.println(this.score.getScore());

        handleInput();

        //Mise à jour du niveau
        level.setCamera(camera);
        level.update(dt, this.timeCount);

        //On vérifie si le personnage est rentré en contact avec un obstacle
        checkCollides(this.level);

        //Si on a perdu on va dans l'état Game Over
        if (gameOver == true) {
            gsm.set(new GameOverState(gsm, getLevel()));
            if(this.score.getScore() > this.score.getScoreMax())
                this.score.saveScoreMax();
        }

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

        //On affiche les éléments composants le niveau
        level.render(sb);

        //On place le bouton de pause
        sb.draw(pauseButton, (camera.position.x + camera.viewportWidth / 2) - pauseButton.getWidth() - 1,   PTS4.HEIGHT / 2 - pauseButton.getHeight() - 1);

        //On place le score
        big_shadow.draw(sb, String.valueOf(score.getScore()), camera.position.x  - (String.valueOf(score.getScore()).length() * big_shadow.getSpaceWidth() / 2),  camera.position.y + big_shadow.getLineHeight() * 3);
        big_font.draw(sb, String.valueOf(score.getScore()), camera.position.x - (String.valueOf(score.getScore()).length() * big_font.getSpaceWidth() / 2),  camera.position.y + big_font.getLineHeight() * 3);

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
            if(gameOver){
                System.out.println("game over");
                //this.scorePlayer.saveScoreMax();
            }
        }
    }

    @Override
    public void dispose() {
        System.out.println("Play State Disposed");
    }

    @Override
    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public Array<Rectangle> getButtonsPosition() {
        Array<Rectangle> tab_rectangle = new Array();
        //On ajoute le bouton de pause
        tab_rectangle.add(new Rectangle((camera.position.x + camera.viewportWidth / 2) - pauseButton.getWidth() - 1,   PTS4.HEIGHT / 2 - pauseButton.getHeight() - 1, pauseButton.getWidth(), pauseButton.getHeight()));
        return tab_rectangle;
    }

    public void setLevel(LevelManager lvl) {
        this.level = lvl;
        this.camera = this.level.getCamera();
    }

    public LevelManager getLevel() {return this.level; }

    public void setTime(float time) {
        this.timeCount = time;
    }
}
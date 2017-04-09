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
import com.pts4.game.Settings;

/**
 * Created by Le Goff Maël on 28/02/2017.
 */

public class GameOverState extends State {
    private Texture playBtn;
    private Texture menuBtn;
    private Texture scorePanel;
    private LevelManager level;
    private Score score;

    private Texture background_sky,  background_ground;

    public static BitmapFont little_font, little_shadow;

    public GameOverState(GameStateManager gsm, LevelManager lm, Score s) {
        super(gsm);

        //On "zoom" la caméra à la moitié de la largeur et la moitié de la longueur (permet de ne pas voir toutes la map mais que la partie zoomée)
        camera.setToOrtho(false, PTS4.WIDTH / 2, PTS4.HEIGHT / 2);

        this.background_sky = Settings.getInstance().getThemeManager().getTheme().getBackgroundSky();
        this.background_ground = Settings.getInstance().getThemeManager().getTheme().getBackgroundGround();
        this.playBtn = new Texture("images/buttons/playBtn.png");
        this.menuBtn = new Texture("images/buttons/menuBtn.png");
        this.scorePanel = new Texture("images/buttons/score.png");
        this.level = lm;
        this.camera = this.level.getCamera();
        this.score = s;

        //La petite police
        little_font = new BitmapFont(Gdx.files.internal("fonts/text.fnt"));
        little_font.getData().setScale(.50f, .50f);
        little_shadow = new BitmapFont(Gdx.files.internal("fonts/shadow.fnt"));
        little_shadow.getData().setScale(.50f, .50f);
    }

    @Override
    public void handleInput() {
        //Représente les coordonnées du bouton play
        Rectangle bounds_play = new Rectangle(this.getButtonsPosition().first());
        //Représente les coordonnées du bouton home
        Rectangle bounds_home = new Rectangle(this.getButtonsPosition().get(1));
        Vector3 tmp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(tmp);

        //Si l'écran est touché
        if(Gdx.input.justTouched()) {
            //Au coordonnées du bouton play
            if (bounds_play.contains(tmp.x, tmp.y)) {
                gsm.set(new PlayState(gsm));
            }
            if (bounds_home.contains(tmp.x, tmp.y)) {
                gsm.set(new MenuState(gsm));
            }
        }
    }

    /**
     * @param dt : delta time, la différence entre une image et la prochaine
     */
    @Override
    public void update(float dt) {
        handleInput();
        //On arrete de créer de nouveaux obstacles
        this.level.setMakeObsatcles(false);
        //Le personnage meurt
        this.level.getPlayer().die();

        //Mise à jour du niveau
        level.setCamera(camera);
        level.update(dt, 0);

        //On déplace la camera
        camera.position.x = camera.position.x + 100 * dt;

        //On repositionne la caméra
        camera.update();
    }

    /**
     * @param sb : tous ce qu'on à besoin d'afficher à l'écran
     */
    @Override
    public void render(SpriteBatch sb) {
        //définie la matrice de projection des batch
        sb.setProjectionMatrix(camera.combined);

        sb.begin();

        //On affiche les éléments composants le niveau
        this.level.render(sb);

        //Si le personnage est hors de la caméra
        if(this.level.getPlayer().getPosition().y < 0) {
            //On place le panneau de score
            sb.draw(scorePanel,  camera.position.x - scorePanel.getWidth(), camera.position.y - scorePanel.getHeight() / 2 - scorePanel.getHeight() / 4, scorePanel.getWidth() * 2, scorePanel.getHeight() * 2);

            //On place le score
            little_shadow.draw(sb, String.valueOf(score.getScore()), camera.position.x  - (little_shadow.getSpaceWidth() * 4) - (String.valueOf(score.getScore()).length()),  camera.position.y + little_shadow.getLineHeight() / 2  + little_shadow.getLineHeight() / 3);
            little_font.draw(sb, String.valueOf(score.getScore()), camera.position.x - (little_font.getSpaceWidth() * 4) - (String.valueOf(score.getScore()).length()),  camera.position.y + little_font.getLineHeight() / 2 + little_font.getLineHeight() / 3);

            //On place le record
            little_shadow.draw(sb, String.valueOf(score.getScoreMax()), camera.position.x  + (little_shadow.getSpaceWidth() * 3) - (String.valueOf(score.getScoreMax()).length() * little_shadow.getSpaceWidth() / 2),  camera.position.y + little_shadow.getLineHeight() / 2 + little_shadow.getLineHeight() / 3);
            little_font.draw(sb, String.valueOf(score.getScoreMax()), camera.position.x + (little_font.getSpaceWidth() * 3) - (String.valueOf(score.getScoreMax()).length() *  little_font.getSpaceWidth() / 2),  camera.position.y + little_font.getLineHeight() / 2 + little_font.getLineHeight() / 3);


            sb.draw(playBtn, camera.position.x - playBtn.getWidth() / 2 - 5,  PTS4.HEIGHT / 4 - playBtn.getHeight(), playBtn.getWidth() / 2, playBtn.getHeight() / 2);
            sb.draw(menuBtn, camera.position.x + 5,  PTS4.HEIGHT / 4 - menuBtn.getHeight(), menuBtn.getWidth() / 2, menuBtn.getHeight() / 2);
        }

        sb.end();
    }

    /**
     * Permet de retirer les éléments aux fur et à mesure pour ne pas "saturer" la mémoire
     */
    @Override
    public void dispose() {
        playBtn.dispose();
        menuBtn.dispose();
        level.dispose();
        System.out.println("Game Over State Disposed");
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
     * Retourne la hitbox des boutons
     *
     * @return
     */
    @Override
    public Array<Rectangle> getButtonsPosition() {
        Array<Rectangle> tab_rectangle = new Array();
        //On ajoute le bouton restart
        tab_rectangle.add(new Rectangle(camera.position.x - playBtn.getWidth() / 2 - 5,  PTS4.HEIGHT / 4 - playBtn.getHeight(), playBtn.getWidth() / 2, playBtn.getHeight() / 2));
        //On ajoute le bouton home
        tab_rectangle.add(new Rectangle(camera.position.x + 5,  PTS4.HEIGHT / 4 - menuBtn.getHeight(), menuBtn.getWidth() / 2, menuBtn.getHeight() / 2));
        return tab_rectangle;
    }
}

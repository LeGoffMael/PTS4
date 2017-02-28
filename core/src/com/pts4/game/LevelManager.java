package com.pts4.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.pts4.game.sprites.Character;
import com.pts4.game.sprites.obstacles.Bats;
import com.pts4.game.sprites.obstacles.TrafficLight;

import java.util.Random;

/**
 * Created by Le Goff Maël on 25/02/2017.
 */
public class LevelManager {
    OrthographicCamera camera;
    //Represente la position x du dernier obstacle
    private int x_last;
    private Random rand;

    //Represente l'espace maximal entre des obstacles
    private static final int MAX_SPACE = 150;
    //Respresente l'espace minimal entre des obstacles
    private static final int MIN_SPACE = 70;
    //Respresente le nombre d'obstacle différent
    private static final int NB_OBSTACLES = 2;

    //Les trains et le joueur
    private Texture train, train_front;
    private Character character;

    //Les tableaux des obstacles
    Array<Bats> batsArray;
    Array<TrafficLight> tLightsArray;

    /**
     * Constructeur
     */
    public LevelManager(OrthographicCamera cam) {
        this.camera = cam;

        this.x_last = (int)this.camera.position.x  + 1;

        this.train = new Texture("images/trains/train.png");
        this.train_front = new Texture("images/trains/train_front.png");
        this.character = new Character(50 , this.train.getHeight());

        this.rand = new Random();
        this.batsArray = new Array<Bats>();
        this.tLightsArray = new Array<TrafficLight>();
    }

    /**
     * Mise a jour du niveau
     * @param dt
     */
    public void update(float dt, float time) {
        //On met à jour le personnage
        this.character.update(dt);

        //On ajoute les obstacles
        addObstacles(time);

        //On parcourt le tableau de bats
        for (int i = 0; i < this.batsArray.size; i++) {
            //On met à jour l'animation
            this.batsArray.get(i).update(dt);
        }

        //On supprime les obstacles qui sont hors champ
        clearUseless(this.camera, this.batsArray, this.tLightsArray);
    }

    /**
     * Fonction qui place des obstacles aléatoirement
     * @param time
     */
    public void addObstacles(float time) {
        //Si la camera a dépassé le premier obstacle
        if(this.x_last < this.camera.position.x)
        {
            //Génére aléatoirement un chiffre représentant le type d'obstacle
            int obstacle = this.rand.nextInt((NB_OBSTACLES - 1) + 1) + 1;
            //Génére aléatoirement un chiffre représentant le position sur l'axe z de l'obstacle
            int z = this.rand.nextInt((3 - 1) + 1) + 1;
            //Génére aléatoirement un chiffre représentant si on place un autre obstacle sur l'axe y
            int other = this.rand.nextInt((3 - 0) + 1) + 0;

            int space = MAX_SPACE - (int)time;
            if (space<MIN_SPACE)
                space = MIN_SPACE;

            Bats bats = null;
            TrafficLight tLights = null;

            //On met des bats
            if(obstacle == 1) {
                bats = new Bats((int)space + this.x_last + PTS4.WIDTH / 2, this.train.getHeight(), z);
                //On crée de nouvelles bats
                this.batsArray.add(bats);

                //On met a jour la position du dernier obstacle
                this.x_last = (int)space + x_last + bats.getTextureOfRegion().getRegionWidth();
            }
            //On met un feu
            else if(obstacle == 2) {
                tLights = new TrafficLight((int)space + this.x_last + PTS4.WIDTH / 2, 0, z);
                this.tLightsArray.add(tLights);
                //On met a jour la position du dernier obstacle
                this.x_last =(int)space + x_last + tLights.getTexture().getWidth();
            }

            //Si l'aléatoire à tiré 5 on place un autre obstacle
            if(other == 3) {
                //Génére aléatoirement un chiffre représentant le type d'obstacle
                int other_obstacle = this.rand.nextInt((NB_OBSTACLES - 1) + 1) + 1;
                //On recommence si c'est le même obstacle que le précédent
                do {
                    other_obstacle = this.rand.nextInt((NB_OBSTACLES - 1) + 1) + 1;
                } while (other_obstacle == obstacle);


                //Génére aléatoirement un chiffre représentant la position sur l'axe x du second obstacle
                int other_x = 0;
                if (obstacle == 1)
                    other_x = this.rand.nextInt((bats.getTextureOfRegion().getRegionWidth() + (int)bats.getPosition().x - (int)bats.getPosition().x) + 1) + (int)bats.getPosition().x;
                else if (obstacle == 2)
                    other_x = this.rand.nextInt((tLights.getTexture().getWidth() + (int)tLights.getPosition().x - (int)tLights.getPosition().x) + 1) + (int)tLights.getPosition().x;

                //Génére aléatoirement un chiffre représentant la position sur l'axe z du second obstacle
                int other_z = this.rand.nextInt((3 - 1) + 1) + 1;
                //On recommence si c'est sur le même axe que le précédent
                do {
                    other_z = this.rand.nextInt((3 - 1) + 1) + 1;
                } while (other_z == z);

                //On met des bats
                if(other_obstacle == 1) {
                    Bats other_bats = new Bats(other_x, this.train.getHeight(), other_z);
                    //On crée de nouvelles bats
                    this.batsArray.add(other_bats);
                    //On met a jour la position du dernier obstacle
                    this.x_last =(int)space + x_last + other_bats.getTextureOfRegion().getRegionWidth();
                }
                //On met un feu
                else if(other_obstacle == 2) {
                    TrafficLight other_tLights = new TrafficLight(other_x, 0, other_z);
                    this.tLightsArray.add(other_tLights);
                    //On met a jour la position du dernier obstacle
                    this.x_last =(int)space + x_last + other_tLights.getTexture().getWidth();
                }
            }
        }
    }

    /**
     * Rendu des obstacles des niveaux
     * @param sb
     */
    public void render(SpriteBatch sb) {
        //On place les wagons
        sb.draw(train, camera.position.x - train.getWidth(), 0);
        sb.draw(train_front, camera.position.x, 0);

        //On place les obstacles qui sont positionnés plus loin que le personnage
        for(int i = (int)character.getPosition().z; i<=3; i++) {
            depthRender(sb, i, this.batsArray, this.tLightsArray);
        }

        //On place le personnage
        sb.draw(character.getTexture(), character.getPosition().x, character.getPosition().y);

        //On place les obstacles qui sont positionnés plus proche que le personnage
        for(int i = 1; i<=character.getPosition().z; i++) {
            depthRender(sb, i, this.batsArray, this.tLightsArray);
        }
    }

    /**
     * Affiche les objets qui sont à la position z passées en paramètres
     * @param sb le SpriteBatch
     * @param z la position z
     * @param tab_bats
     * @param tab_tLights
     */
    public void depthRender(SpriteBatch sb, int z, Array<Bats> tab_bats, Array<TrafficLight> tab_tLights) {
        //On parcourt le tableau de bats
        for (int i = 0; i < tab_bats.size; i++) {
            if(tab_bats.get(i).getPosition().z == z)
                sb.draw(tab_bats.get(i).getTextureOfRegion(), tab_bats.get(i).getPosition().x, tab_bats.get(i).getPosition().y);
        }
        //On parcourt le tableau de feux tricolores
        for (int i = 0; i < tab_tLights.size; i++) {
            if(tab_tLights.get(i).getPosition().z == z)
                sb.draw(tab_tLights.get(i).getTexture(), tab_tLights.get(i).getPosition().x, tab_tLights.get(i).getPosition().y);
        }
    }

    /**
     * On supprime les obstacles qui sont hors de la caméra
     * @param cam
     * @param tab_bats
     * @param tab_tLights
     */
    public void clearUseless(OrthographicCamera cam, Array<Bats> tab_bats, Array<TrafficLight> tab_tLights) {
        //On parcourt le tableau de bats
        for (int i = 0; i < tab_bats.size; i++) {
            //Si les bats sont hors de l'écran
            if(cam.position.x - (cam.viewportWidth / 2) > tab_bats.get(i).getPosition().x + tab_bats.get(i).getTextureOfRegion().getRegionWidth()) {
                tab_bats.get(i).dispose();
                tab_bats.removeIndex(i);
            }

        }
        //On parcourt le tableau de feux tricolores
        for (int i = 0; i < tab_tLights.size; i++) {
            //Si le feu est hors de l'écran
            if(cam.position.x - (cam.viewportWidth / 2) > tab_tLights.get(i).getPosition().x + tab_tLights.get(i).getTexture().getWidth()) {
                tab_tLights.get(i).dispose();
                tab_tLights.removeIndex(i);
            }
        }
    }

    /**
     * Vide la mémoire
     */
    public void dispose() {
        this.train.dispose();
        this.train_front.dispose();
        this.character.dispose();
        for (int i = 0; i < batsArray.size; i++) {
            batsArray.get(i).dispose();
        }
        for (int i = 0; i < tLightsArray.size; i++) {
            tLightsArray.get(i).dispose();
        }
    }

    public void setCamera(OrthographicCamera cam) {
        this.camera = cam;
    }

    public OrthographicCamera getCamera() {
        return this.camera; }

    public Character getPlayer () {
        return this.character;
    }

    public Array<Bats> getBatsArray() {
        return this.batsArray;
    }

    public Array<TrafficLight> getTLightsArray() {
        return this.tLightsArray;
    }
}
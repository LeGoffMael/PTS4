package com.pts4.game.themes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Le Goff Maël on 03/03/2017.
 */

public abstract class Theme {
    protected String name;
    protected Texture backgroundSky, backgroundGround, train, train_front;
    protected Array<Texture> character, bats, trafficLight;
    protected Music music;
    protected Sound die, jump, slide;
    protected String musicPath, diePath, jumpPath, slidePath;

    /**
     * Par défaut
     * @param s
     */
    public Theme(String s) {
        this.name = s;
    }

    public String getTexturePath(Texture texture) {
        String path = null;
        TextureData textureData = texture.getTextureData();
        if (textureData instanceof FileTextureData) {
            FileTextureData fileTextureData = (FileTextureData) textureData;
            path = fileTextureData.getFileHandle().path();

        }
        return path;
    }

    public String getName() {
        return this.name;
    }

    public Texture getBackgroundSky() {
        return backgroundSky;
    }

    public void setBackgroundSky(Texture background) {
        this.backgroundSky = background;
    }

    public Texture getBackgroundGround() {
        return backgroundGround;
    }

    public void setBackgroundGround(Texture background) {
        this.backgroundGround = background;
    }

    public Texture getTrain() {
        return train;
    }

    public void setTrain(Texture train) {
        this.train = train;
    }

    public Texture getFrontTrain() {
        return train_front;
    }

    public void setFrontTrain(Texture train_front) {
        this.train_front = train_front;
    }

    /**
     * Retourne la texture du personnage suivant l'état passé en paramètre
     * @param state (default,jump,slide,changePlan)
     * @return
     */
    public Texture getCharacter(String state) {
        Texture t = new Texture("images/default.jpg");

        if(state.equals("default"))
            t = this.character.first();
        else if(state.equals("jump"))
            t = this.character.get(1);
        else if(state.equals("slide"))
            t = this.character.get(2);
        else if(state.equals("changePlan"))
            t = this.character.get(3);

        return t;
    }

    /**
     * Change la texture du personnage à son état
     * @param character la texture
     * @param state (default,jump,slide,changePlan)
     */
    public void setCharacter(Texture character, String state) {
        if(state.equals("default"))
            this.character.set(0, character);
        else if(state.equals("jump"))
            this.character.set(1, character);
        else if(state.equals("slide"))
            this.character.set(2, character);
        else if(state.equals("changePlan"))
            this.character.set(3, character);
    }

    /**
     * Retourne la texture des bats en fonction de sa position z
     * @param z (1,2,3)
     * @return
     */
    public Texture getBats(int z) {
        Texture t = new Texture("images/default.jpg");

        if(z == 1)
            t = this.bats.first();
        else if(z == 2)
            t = this.bats.get(1);
        else if(z == 3)
            t = this.bats.get(2);

        return t;
    }

    /**
     * Change la texture des bats en fonction de sa position z
     * @param bats la texture
     * @param z (1,2,3)
     */
    public void setBats(Texture bats, int z) {
        if(z == 1)
            this.bats.set(0, bats);
        else if(z == 2)
            this.bats.set(1, bats);
        else if(z == 3)
            this.bats.set(2, bats);
    }

    /**
     * Retourne la texture du feu en fonction de sa position z
     * @param z (1,2,3)
     * @return
     */
    public Texture getTrafficLight(int z) {
        Texture t = new Texture("images/default.jpg");

        if(z == 1)
            t = this.trafficLight.first();
        else if(z == 2)
            t = this.trafficLight.get(1);
        else if(z == 3)
            t = this.trafficLight.get(2);

        return t;
    }

    /**
     * Change la texture du feu en fonction de sa position z
     * @param trafficLight la texture
     * @param z (1,2,3)
     */
    public void setTrafficLight(Texture trafficLight, int z) {
        if(z == 1)
            this.trafficLight.set(0, trafficLight);
        else if(z == 2)
            this.trafficLight.set(1, trafficLight);
        else if(z == 3)
            this.trafficLight.set(2, trafficLight);
    }

    public Music getMusic() {
        return music;
    }

    public Sound getDie() {
        return die;
    }

    public Sound getJump() {
        return jump;
    }

    public Sound getSlide() {
        return slide;
    }

    public void setMusic(String musicPath) {
        this.musicPath = musicPath;
        if(!(this.musicPath.equals("null")))
            this.music = Gdx.audio.newMusic(Gdx.files.internal(this.musicPath));
    }

    public void setDie(String diePath) {
        this.diePath = diePath;
        if(!(this.diePath.equals("null")))
            this.die = Gdx.audio.newSound(Gdx.files.internal(this.diePath));
    }

    public void setJump(String jumpPath) {
        this.jumpPath = jumpPath;
        if(!(this.jumpPath.equals("null")))
            this.jump = Gdx.audio.newSound(Gdx.files.internal(this.jumpPath));
    }

    public void setSlide(String slidePath) {
        this.slidePath = slidePath;
        if(!(this.slidePath.equals("null")))
            this.slide = Gdx.audio.newSound(Gdx.files.internal(this.slidePath));
    }

    public String getMusicPath() {
        return musicPath;
    }

    public String getDiePath() {
        return diePath;
    }

    public String getJumpPath() {
        return jumpPath;
    }

    public String getSlidePath() {
        return slidePath;
    }

    /**
     * Retourne les chemin de tous les objets composant le thème
     * @return
     */
    public String getAllPath() {
        String res = "";

        res += this.getName();
        res += ";";

        //Les Textures
        res += this.getTexturePath(this.backgroundSky);
        res += ";";
        res += this.getTexturePath(this.backgroundGround);
        res += ";";
        res += this.getTexturePath(this.train);
        res += ";";
        res += this.getTexturePath(this.train_front);
        res += ";";
        for(int i=0; i<this.character.size; i++) {
            res += this.getTexturePath(this.character.get(i));
            res += ";";
        }
        for(int i=0; i<this.bats.size; i++) {
            res += this.getTexturePath(this.bats.get(i));
            res += ";";
        }
        for(int i=0; i<this.trafficLight.size; i++) {
            res += this.getTexturePath(this.trafficLight.get(i));
            res += ";";
        }

        //Les Sons
        res += this.getMusicPath();
        res += ";";
        res += this.getDiePath();
        res += ";";
        res += this.getJumpPath();
        res += ";";
        res += this.getSlidePath();

        return res;
    }

    public void dispose() {
        this.backgroundSky.dispose();
        this.backgroundGround.dispose();
        this.train.dispose();
        this.train_front.dispose();
        for(int i=0; i<this.character.size; i++)
            this.character.get(i).dispose();
        for(int i=0; i<this.bats.size; i++)
            this.bats.get(i).dispose();
        for(int i=0; i<this.trafficLight.size; i++)
            this.trafficLight.get(i).dispose();
        this.music.dispose();
        this.die.dispose();
        this.jump.dispose();
        this.slide.dispose();
    }
}

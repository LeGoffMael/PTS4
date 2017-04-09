package com.pts4.game.themes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Le Goff Maël on 09/04/2017.
 */

public class DefaultTheme extends Theme {

    public DefaultTheme() {
        super("default");

        this.backgroundSky = new Texture("images/default.jpg");
        this.backgroundGround = new Texture("images/default.jpg");
        this.train = new Texture("images/trains/train.png");
        this.train_front = new Texture("images/trains/train_front.png");

        this.character = new Array<Texture>();
        this.character.add(new Texture("images/character/character_default.png")); //Default
        this.character.add(new Texture("images/character/characterJump.png")); //Jump
        this.character.add(new Texture("images/character/character_default.png")); //Slide
        this.character.add(new Texture("images/character/character_default.png")); //ChangePlan

        this.bats = new Array<Texture>();
        this.bats.add(new Texture("images/obstacles/bats/bats.png"));
        this.bats.add(new Texture("images/obstacles/bats/bats.png"));
        this.bats.add(new Texture("images/obstacles/bats/bats.png"));

        this.trafficLight = new Array<Texture>();
        this.trafficLight.add(new Texture("images/obstacles/fire/fireGreen.png"));
        this.trafficLight.add(new Texture("images/obstacles/fire/fireOrange.png"));
        this.trafficLight.add(new Texture("images/obstacles/fire/fireRed.png"));

        this.music = null;
        this.die = null;
        this.jump = null;
        this.slide = null;
    }

    @Override
    public String getTexturePath(Texture texture) {
        return super.getTexturePath(texture);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public Texture getBackgroundSky() {
        return super.getBackgroundSky();
    }

    @Override
    public void setBackgroundSky(Texture background) {
        super.setBackgroundSky(background);
    }

    @Override
    public Texture getBackgroundGround() {
        return super.getBackgroundGround();
    }

    @Override
    public void setBackgroundGround(Texture background) {
        super.setBackgroundGround(background);
    }

    @Override
    public Texture getTrain() {
        return super.getTrain();
    }

    @Override
    public void setTrain(Texture train) {
        super.setTrain(train);
    }

    @Override
    public Texture getFrontTrain() {
        return super.getFrontTrain();
    }

    @Override
    public void setFrontTrain(Texture train_front) {
        super.setFrontTrain(train_front);
    }

    @Override
    public Texture getCharacter(String state) {
        return super.getCharacter(state);
    }

    @Override
    public void setCharacter(Texture character, String state) {
        super.setCharacter(character, state);
    }

    @Override
    public Texture getBats(int z) {
        return super.getBats(z);
    }

    @Override
    public void setBats(Texture bats, int z) {
        super.setBats(bats, z);
    }

    @Override
    public Texture getTrafficLight(int z) {
        return super.getTrafficLight(z);
    }

    @Override
    public void setTrafficLight(Texture trafficLight, int z) {
        super.setTrafficLight(trafficLight, z);
    }

    @Override
    public Music getMusic() {
        return super.getMusic();
    }

    @Override
    public Sound getDie() {
        return super.getDie();
    }

    @Override
    public Sound getJump() {
        return super.getJump();
    }

    @Override
    public Sound getSlide() {
        return super.getSlide();
    }

    @Override
    public void setMusic(String musicPath) {
        super.setMusic(musicPath);
    }

    @Override
    public void setDie(String diePath) {
        super.setDie(diePath);
    }

    @Override
    public void setJump(String jumpPath) {
        super.setJump(jumpPath);
    }

    @Override
    public void setSlide(String slidePath) {
        super.setSlide(slidePath);
    }

    @Override
    public String getMusicPath() {
        return super.getMusicPath();
    }

    @Override
    public String getDiePath() {
        return super.getDiePath();
    }

    @Override
    public String getJumpPath() {
        return super.getJumpPath();
    }

    @Override
    public String getSlidePath() {
        return super.getSlidePath();
    }

    @Override
    public String getAllPath() {
        return super.getAllPath();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

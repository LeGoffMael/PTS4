package com.pts4.game.themes;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Le Goff Maël on 03/03/2017.
 */

public class Theme {
    protected String name;

    protected Texture backgroundSky, backgroundGround, train, train_front, character, bats, trafficLight;
    protected Sound music, die, jump, slide;

    public Theme(String s) {
        this.name = s;

        this.backgroundSky = null;
        this.backgroundGround = null;
        this.train = new Texture("images/trains/train.png");
        this.train_front = new Texture("images/trains/train_front.png");
        this.character = new Texture("images/character/character_default.png");
        this.bats = new Texture("images/obstacles/bats.png");
        this.trafficLight = null;

        this.music = null;
        this.die = null;
        this.jump = null;
        this.slide = null;
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

    public Texture getCharacter() {
        return character;
    }

    public void setCharacter(Texture character) {
        this.character = character;
    }

    public Texture getBats() {
        return bats;
    }

    public void setBats(Texture bats) {
        this.bats = bats;
    }

    public Texture getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(Texture trafficLight) {
        this.trafficLight = trafficLight;
    }

    public Sound getMusic() {
        return music;
    }

    public void setMusic(Sound music) {
        this.music = music;
    }

    public Sound getDie() {
        return die;
    }

    public void setDie(Sound die) {
        this.die = die;
    }

    public Sound getJump() {
        return jump;
    }

    public void setJump(Sound jump) {
        this.jump = jump;
    }

    public Sound getSlide() {
        return slide;
    }

    public void setSlide(Sound slide) {
        this.slide = slide;
    }

    public void dispose() {
        this.backgroundSky.dispose();
        this.backgroundGround.dispose();
        this.train.dispose();
        this.train_front.dispose();
        this.character.dispose();
        this.bats.dispose();
        this.trafficLight.dispose();
        this.music.dispose();
        this.die.dispose();
        this.jump.dispose();
        this.slide.dispose();
    }
}

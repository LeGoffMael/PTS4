package com.pts4.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Le Goff Maël on 24/02/2017.
 */

public class Animation {
    //Contient les images des animations complètes
    private Array<TextureRegion> frames;
    //La durée de visualisation de l'image avant de passer à l'autre
    private float maxFrameTime;
    //La durée depuis que l'image courante est en vue
    private float currentFrameTime;
    //Le nombre d'image de l'animation
    private int frameCount;
    //L'image courante
    private int frame;

    public Animation(TextureRegion region, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        //La largeur de l'image est égal à la largeur de l'image des animations divisé par le nopbre d'animation
        int frameWidth = region.getRegionWidth() / frameCount;
        //On génére toutes les images qui composent l'animation
        for(int i = 0; i < frameCount; i++) {
            //On crée chaque image par morceau de l'animation complète
            frames.add(new TextureRegion(region, i* frameWidth, 0, frameWidth, region.getRegionHeight()));
        }

        this.frameCount = frameCount;
        this.maxFrameTime = cycleTime / frameCount;
        this.frame = 0;
    }

    /**
     * Met à jour la durée passée et change d'image si nécessaire
     * @param dt
     */
    public void update(float dt) {
        //On augmente la durée passée de l'image courante
        currentFrameTime += dt;

        //si la durée passée de l'image courante est supérieur à la durée max
        if(currentFrameTime > maxFrameTime) {
            //on passe à l'image suivante
            frame++;
            //on réinitialise la durée passée de l'image courante
            currentFrameTime = 0;
        }

        //si le numéro de l'image est supérieur au nombre d'image possible
        if(frame >= frameCount) {
            //on réinitialise l'image
            frame = 0;
        }
    }

    /**
     * Retourne l'image courante de l'animation
     * @return
     */
    public TextureRegion getFrame() {
        return frames.get(frame);
    }
}

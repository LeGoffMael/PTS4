package com.pts4.game;

import com.badlogic.gdx.input.GestureDetector;

/**
 * Created by Le Goff Maël on 26/02/2017.
 */
public class SimpleDirectionGestureDetector extends GestureDetector {

    public interface DirectionListener {
        void onUp();

        void onDown();

        void onTap();

        void onLongPress();
    }

    public SimpleDirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
        //On définit le temps nécessaire pour faire un long press
        super.setLongPressSeconds(0.3f);
    }

    private static class DirectionGestureListener extends GestureAdapter{
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }

        @Override
        public boolean tap(float x, float y, int count, int button) {
            directionListener.onTap();
            return super.tap(x, y, count, button);
        }

        @Override
        public boolean longPress(float x, float y) {
            directionListener.onLongPress();
            return super.longPress(x, y);
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)<=Math.abs(velocityY)){
                if(velocityY>0){
                    directionListener.onDown();
                }else{
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

    }

}

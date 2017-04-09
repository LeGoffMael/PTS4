package com.pts4.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by PC on 28/02/2017.
 */

public class Score {
    private float score;
    private int scoreMax;

    //Constructeur de la classe score
    public Score(){
        this.scoreMax = 0;
        this.scoreMax = this.readScoreMax();
        this.score = 0;
    }


    //Compteur de score
    public void setScore(float time){
        this.score = time;
    }

    public int getScore(){
        int temp = (int)Math.floor(this.score);
        return temp;
    }

    public float getScoreMax(){
        return this.scoreMax;
    }

    //Sauvegarde du meilleur score
    public void saveScoreMax(){
        FileWriter fileWriter = null;
        BufferedWriter tamponWriter = null;

        if(this.score > this.scoreMax){
            this.scoreMax = (int)this.score;

            //Sauvegarde
            try{
                fileWriter = new FileWriter("score.txt");
                tamponWriter = new BufferedWriter(fileWriter);
                tamponWriter.write(String.valueOf(this.scoreMax));
            }
            catch (IOException exception){
                exception.printStackTrace();
            } finally {
                try {
                    if (tamponWriter != null) {
                        tamponWriter.flush();
                        tamponWriter.close();
                    }
                    if (fileWriter != null)
                        fileWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //Lecture du score max dans un fichier
    public int readScoreMax(){
        FileReader file_reader = null;
        BufferedReader tampon_reader = null;

        try {
            file_reader = new FileReader("score.txt");
            tampon_reader = new BufferedReader(file_reader);

            while (true) {
                // Lit une ligne de scores.txt
                String ligne = tampon_reader.readLine();
                // Vérifie la fin de fichier
                if (ligne == null)
                    break;
                //On assigne la valeur trouvée à la variable locale
                this.scoreMax = Integer.parseInt(ligne);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (tampon_reader != null)
                    tampon_reader.close();
                if (file_reader != null)
                    file_reader.close();
            } catch(IOException exception1) {
                exception1.printStackTrace();
            }
        }

        return this.scoreMax;
    }

}

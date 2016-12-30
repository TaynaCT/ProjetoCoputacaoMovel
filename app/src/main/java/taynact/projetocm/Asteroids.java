package taynact.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;

//Classe responsavel pelos Asteroids

public class Asteroids {

    private Bitmap asteroidBitmap;
    private  float x, y;
    private  int maxX, maxY, minX, minY;
    private  float speed = 0;

    //construtor
    public Asteroids(Context context, Point screenLimit){

        //gera um numero aleatorio de 0 - 4
        Random generator = new Random();
        int selectAsteroid = generator.nextInt(4);

        //A partir do numero gerado em generator, seleciona o asteriode a ser desenhado
        switch (selectAsteroid){

            case 0:
                asteroidBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock01);

                break;

            case 1:

                asteroidBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock02);

                break;


            case 2:

                asteroidBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock03);

                break;

            case 3:

                asteroidBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock04);

                break;

            case 4:

                asteroidBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock05);

                break;

        }

        maxX = screenLimit.x;
        maxY = screenLimit.y;
        minX = 0;
        minY = 0;

        speed = generator.nextInt(6) + 10;

        x = screenLimit.x;
        y = generator.nextInt(maxY) - asteroidBitmap.getHeight();

    }

    public  void update(){

    }

    //Getters
    public Bitmap getAsteroidBitmap() {
        return asteroidBitmap;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

}

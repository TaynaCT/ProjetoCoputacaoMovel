package taynact.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;


import java.util.Random;

//Classe responsavel pelos Asteroids

public class Stars {

    private Bitmap starBitmap;
    private  float x, y;
    private  int maxX, maxY, minX, minY;
    private  float speed = 0;

    //construtor
    public Stars(Context context, Point screenLimit){

        //gera um numero aleatorio de 0 - 4
        Random generator = new Random();
        int selectAsteroid = generator.nextInt(2);

        //A partir do numero gerado em generator, seleciona o asteriode a ser desenhado
        switch (selectAsteroid){

            case 0:
                starBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star1);

                break;

            case 1:

                starBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star2);

                break;

            case 2:

                starBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.star3);

                break;
        }

        //limites da tela
        maxX = screenLimit.x;
        maxY = screenLimit.y;
        minX = 0;
        minY = 0;

        speed = generator.nextInt(6);

        //coloca o asteroide na posição inicial, que é o limite direito da tela
        x = screenLimit.x;
        // gera posições aleatorias no eixo de y que vão de 0 - ao tamanha da tela - a altura do bitmap
        y = generator.nextInt(maxY) - starBitmap.getHeight();

    }

    public  void update(){

        //movimenta o asteroide para a esquerda
        x -= speed;

        //verifica se ele saiu da tela
        if(x < minX - starBitmap.getWidth()){

            //caso ele saia volta a a por no outro lado da tela, e gera um novo valor random para a posição em y
            Random generator = new Random();
            speed = generator.nextInt(10)+10;
            x = maxX;
            y = generator.nextInt(maxY) - starBitmap.getHeight();
        }

    }

    //Getters
    public Bitmap getStarBitmap() {
        return starBitmap;
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

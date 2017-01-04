package taynact.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

//Classe responsavel pelas barras


public class Base {

    private Bitmap barBitmap;
    private  float x, y;
    private  float speed = 0;
    private  int selectBar;
    private Rect hitbox;

    //construtor
    public Base(Context context, int bar, Point screenLimit){

        selectBar = bar;

        speed = 1.5f;

        switch (selectBar){

            case 0: // desenha a barra azul no canto superior esquerdo da tela
                barBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.baseblue);
                x = 0;
                y = 0;

                break;

            case 1://desenha a bassa vermelha no canto inferior direito da tela

                barBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basered);
                x = screenLimit.x - barBitmap.getWidth();
                y = screenLimit.y - barBitmap.getHeight() - 5;

                break;

        }

        // Initialize the hit box
        hitbox = new Rect ((int)x, (int)y, barBitmap.getWidth(), barBitmap.getHeight());
    }

    public  void update(){

        //Refresh hit box location
        hitbox.left = (int)x;
        hitbox.top = (int)y;
        hitbox.right = (int)x + barBitmap.getWidth();
        hitbox.bottom = (int)y + barBitmap.getHeight();
    }

    //Getters
    public Bitmap getBarBitmap() {
        return barBitmap;
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

    public Rect getHitbox(){  return hitbox; }

}

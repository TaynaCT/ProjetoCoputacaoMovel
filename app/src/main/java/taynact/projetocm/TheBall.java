package taynact.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

//Classe responsavel por desenhar a bola e guardar as informações relativas ao objeto

public class TheBall {

    private Bitmap ball;
    private  float x, y;
    private  float speed = 0;
    private Rect hitbox;

    //construtor
    public TheBall(Context context){
        x = 10;
        y = 10;
        speed = 1.5f;

        ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);

        // Initialize the hit box
        hitbox = new Rect ((int)x, (int)y, ball.getWidth(), ball.getHeight());
    }

    public  void update(){
        x ++;
        y ++;

        //Refresh hit box location
        hitbox.left = (int)x;
        hitbox.top = (int)y;
        hitbox.right = (int)x + ball.getWidth();
        hitbox.bottom = (int)y + ball.getHeight();
    }

    //Getters
    public Bitmap getBall() {
        return ball;
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

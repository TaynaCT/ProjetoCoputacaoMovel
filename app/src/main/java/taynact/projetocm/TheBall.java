package taynact.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

//Classe responsavel por desenhar a bola e guardar as informações relativas ao objeto

public class TheBall {

    private Bitmap ball;
    private  float x, y;
    private float xVelocity;
    private float yVelocity;

    private Rect hitbox;

    //construtor
    public TheBall(Context context){
        x = 10;
        y = 10;
        xVelocity = 5;
        yVelocity = 5.5f;

        ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);

        // Initialize the hit box
        hitbox = new Rect ((int)x, (int)y, ball.getWidth(), ball.getHeight());
    }

    public  void update(){
        x += xVelocity;
        y += yVelocity;

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

    public void setX(int x) {  this.x = x; }

    public void setY(int y) {  this.y = y; }

    public Rect getHitbox(){  return hitbox; }

    public void reverseXVelocity(){
        xVelocity = -xVelocity;
    }

    public  void  reverseYVelocity(){
        yVelocity = -yVelocity;
    }
    //gera velocidade aleatoria no caso impacto com as barras
    public void setRandomVelocity(){
        Random rand = new Random();
        int newVelocity = rand.nextInt(2);

        if (newVelocity == 0){
            reverseXVelocity();
        }
    }

}

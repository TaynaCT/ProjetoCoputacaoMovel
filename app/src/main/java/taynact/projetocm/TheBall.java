package taynact.projetocm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

//Classe responsavel por desenhar a bola e guardar as informações relativas ao objeto

public class TheBall {

    private Bitmap ball;
    private  float x, y;
    private  float speed;

    //construtor
    public TheBall(Context context){
        x = 50;
        y = 50;
        speed = 1.5f;

        ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
    }

    public  void Update(){
        x += speed;
        y += speed;
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
    
}

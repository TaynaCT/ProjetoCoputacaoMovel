package taynact.projetocm;

/**
 * Created by tayna on 04/12/2016.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.InflateException;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;
    Thread gameThread = null;
    //game object
    private  TheBall ball;

    private  Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //construtor da classe GameView
    public GameView(Context context){
        super(context);

        // Initialize our drawing objects
        ourHolder = getHolder();
        paint = new Paint();

        ball = new TheBall(context);
    }

    @Override
    public void Run(){
        //game loop
        while (playing){
            update();
            draw();
            control();

        }
    }

    public void update(){

        ball.update();

    }
    public void  draw(){

        if(ourHolder.getSurface().isValid()){

            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 0, 0, 0));
            //desenha a sprite da bola
            canvas.drawBitmap(ball.getBall(), ball.getX(),ball.getY(), paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }

    }
    public  void control(){

        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {

        }
    }

    //limpa o thread se o jogo interrompido ou o jogador saia da aplicação
        public  void pause(){
        playing = false;
        try{
            gameThread.join();
        }catch (InterruptedException e){

        }
    }

    //cria um novo tread e o inicia
    public  void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}

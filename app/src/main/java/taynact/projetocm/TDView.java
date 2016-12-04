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

public class  TDView extends SurfaceView implements Runnable{

    volatile boolean playing;
    Thread gameThread = null;
    //game object
    private  TheBall ball;

    private  Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public TDView(Context context){
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

    //jogo em pausa
    public  void pause(){
        playing = false;

        try{
            gameThread.join();
        }catch (InflateException e){

        }
    }

    public  void rsume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

}

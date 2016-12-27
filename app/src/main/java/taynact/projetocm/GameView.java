package taynact.projetocm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;
    Thread gameThread = null;
    //bola
    private  TheBall ball;
    //objetos para desenar o bitmap na tela
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //construtor
    public GameView(Context context) {
        super(context);

        ourHolder = getHolder();
        paint = new Paint();
        ball = new TheBall(context);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update(){

        ball.update();

    }
    private void draw(){

        // o SurfaceHolder é valido?
        if (ourHolder.getSurface().isValid()) {
            //se sim
            // Primeiro guardamos o espaço na memoria onde vai ser usado para fazer o desenho
            canvas = ourHolder.lockCanvas();

            // limpa a ultima frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //desenha a bola
            canvas.drawBitmap(
                    ball.getBall(),
                    ball.getX(),
                    ball.getY(),
                    paint);

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }
    private void control(){

        //controla o frame rate
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
        }

    }

    // Clean up our thread if the game is interrupted or the player quits
    public void pause() {
        playing = false;

        try {
            gameThread.join();

        } catch (InterruptedException e) {
        }
    }

    // Make a new thread and start it
    // Execution moves to our R
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }



}
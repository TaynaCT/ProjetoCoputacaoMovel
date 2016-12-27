package taynact.projetocm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;
    Thread gameThread = null;
    //bola
    private  TheBall ball;
    //barras
    private Base blueBase;
    private Base redBase;
    //objetos para desenar o bitmap na tela
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //construtor
    public GameView(Context context, Point screenLimit) {
        super(context);

        ourHolder = getHolder();
        paint = new Paint();
        ball = new TheBall(context);
        blueBase = new Base(context, 0, screenLimit);
        redBase = new Base(context, 1, screenLimit);
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
        blueBase.update();
        redBase.update();

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

            //desenha a barra azul
            canvas.drawBitmap(
                    blueBase.getBarBitmap(),
                    blueBase.getX(),
                    blueBase.getY(),
                    paint);

            //desenha barra vermelha
            canvas.drawBitmap(
                    redBase.getBarBitmap(),
                    redBase.getX(),
                    redBase.getY(),
                    paint);

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }
    private void control(){

        //controla o frame rate
        //pausamos o thread po 17 milisegundos(1000milisegundos/ 60(fps))
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
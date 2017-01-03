package taynact.projetocm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{

    private Context context;
    private GameView gameView;

    volatile boolean playing;
    Thread gameThread = null;
    //bola
    private  TheBall ball;

    //variaveis de pontuação
    private int pts;
    private int timeStarted;

    //colisões
    Colisions colisions;

    //barras
    private Base blueBase;
    private Base redBase;

    //asteroides
    private Asteroids asteroid1;
    private Asteroids asteroid2;
    private Asteroids asteroid3;

    //strelas background
    private ArrayList<Stars> starList = new ArrayList<Stars>();

    //detector de colisões
    //private Colisions colisions;

    // objetos para desenar o bitmap na tela
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    private Point screenLimit;

    //fim de jogo
    private  boolean gameOver;

    //construtor
    public GameView(Context context, Point screenLimit) {
        super(context);
        this.context = context;
        this.screenLimit = screenLimit;

        startGame();

    }

    //Inicia as variaveis de jogo
    public void  startGame(){

        //numero de estrelas a gerar
        int numStars = 10;

        //cira obijetos da classe Stars e adiciona a lista de estrelas
        for(int i = 0; i < numStars; i++){
            Stars star = new Stars(context, screenLimit);
            starList.add(star);
        }

        ourHolder = getHolder();
        paint = new Paint();
        paint = new Paint();
        ball = new TheBall(context);
        blueBase = new Base(context, 0, screenLimit);
        redBase = new Base(context, 1, screenLimit);
        asteroid1 = new Asteroids(context, screenLimit);
        asteroid2 = new Asteroids(context, screenLimit);
        asteroid3 = new Asteroids(context, screenLimit);

        pts = 0;
        //pega o tempo do sistema em minisegundos
        timeStarted = (int)System.currentTimeMillis();

        gameOver = false;
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

        for (Stars s : starList){
            s.update();
        }

        ball.update();
        blueBase.update();
        redBase.update();
        asteroid1.update();
        asteroid2.update();
        asteroid3.update();

        //teste de colisões
        /*
        ball.setIscolliding(colisions.objectcolision(ball, blueBase));
        ball.setIscolliding(colisions.objectcolision(ball, redBase));
        ball.setIscolliding(colisions.asteroidcolision(ball, asteroid1));
        ball.setIscolliding(colisions.asteroidcolision(ball, asteroid2));
        ball.setIscolliding(colisions.asteroidcolision(ball, asteroid3));
*/

        if(!gameOver){
            pts ++; //(int)System.currentTimeMillis() - timeStarted;
        }

    }
    private void draw(){

        // o SurfaceHolder é valido?
        if (ourHolder.getSurface().isValid()) {
            //se sim
            // Primeiro guardamos o espaço na memoria onde vai ser usado para fazer o desenho
            canvas = ourHolder.lockCanvas();

            // limpa a ultima frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //desenha as estrelas
            for (Stars s : starList){
                canvas.drawBitmap(s.getStarBitmap(),
                        s.getX(),
                        s.getY(),
                        paint);
            }

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

            //desenha os asteroids
            canvas.drawBitmap(
                    asteroid1.getAsteroidBitmap(),
                    asteroid1.getX(),
                    asteroid1.getY(),
                    paint);

            canvas.drawBitmap(
                    asteroid2.getAsteroidBitmap(),
                    asteroid2.getX(),
                    asteroid2.getY(),
                    paint);

            canvas.drawBitmap(
                    asteroid3.getAsteroidBitmap(),
                    asteroid3.getX(),
                    asteroid3.getY(),
                    paint);

            //texto
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(25);
            canvas.drawText("PTS: " + System.currentTimeMillis()/*ball.getIsColliding()*/, 10, 20, paint);

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


    //AQUI É QUE DEVE SER COLOCADO O CODIGO RELACIONADO AO MOVIMENTO
    // SurfaceView allows us to handle the onTouchEvent
   /* @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {


        // There are many different events in MotionEvent
        // We care about just 2 - for now.
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Has the player lifted their finger up?
            case MotionEvent.ACTION_UP:
                // Do something here
                break;
            // Has the player touched the screen?
            case MotionEvent.ACTION_DOWN:
                // Do something here
                break;
        }
        return true;
    }*/
}
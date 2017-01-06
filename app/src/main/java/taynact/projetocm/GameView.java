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
import android.graphics.Rect;
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
    private int touch = 1;

    //barras
    private Base blueBase;
    private Base redBase;

    //asteroides
    private Asteroids[] asteroids;

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

        //inicia os asteroides
        for (int i = 0; i < asteroids.length; i ++){
            asteroids[i] = new Asteroids(context, screenLimit);
        }

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

        Point lastposition = new Point((int)ball.getX(), (int)ball.getY());
        int pts10 = 0;
        // Collision detection on new positions
        // Before move because we are testing last frames
        // position which has just been drawn
        // If you are using images in excess of 100 pixels
        // wide then increase the -100 value accordingly
        if(Rect.intersects(blueBase.getHitbox(), ball.getHitbox()))
        {
            //o que fazer ao bater
            if (touch == 0){
                pts10 = 10;
            }
            ball.setRandomVelocity();
            ball.reverseYVelocity();
            touch = 1;

        }
        if(Rect.intersects  (redBase.getHitbox(), ball.getHitbox()))
        {
            //o que fazer ao bater
            if (touch == 1){
                pts10 = 10;
            }
            ball.setRandomVelocity();
            ball.reverseYVelocity();
            touch = 0;
        }
/*
        if(Rect.intersects  (ball.getHitbox(), asteroid1.getHitbox()))
        {
            //o que fazer ao bater
            if  (ball.getHitbox().right == asteroid1.getHitbox().left);
            {


            }

            if  (ball.getHitbox().left == asteroid1.getHitbox().right);
            {


            }

            if  (ball.getHitbox().top == asteroid1.getHitbox().bottom);
            {


            }

            if  (ball.getHitbox().bottom == asteroid1.getHitbox().top);
            {


            }
        }

        if(Rect.intersects  (ball.getHitbox(), asteroid2.getHitbox()))
        {
            //o que fazer ao bater
            if  (ball.getHitbox().right == asteroid2.getHitbox().left);
            {


            }

            if  (ball.getHitbox().left == asteroid2.getHitbox().right);
            {


            }

            if  (ball.getHitbox().top == asteroid2.getHitbox().bottom);
            {


            }

            if  (ball.getHitbox().bottom == asteroid2.getHitbox().top);
            {


            }

        }

        if(Rect.intersects  (ball.getHitbox(), asteroid3.getHitbox()))
        {
            //o que fazer ao bater
            if  (ball.getHitbox().right == asteroid3.getHitbox().left);
            {


            }

            if  (ball.getHitbox().left == asteroid3.getHitbox().right);
            {


            }

            if  (ball.getHitbox().top == asteroid3.getHitbox().bottom);
            {


            }

            if  (ball.getHitbox().bottom == asteroid3.getHitbox().top);
            {


            }
        }
*/

        //se a bola atingir oas lados da tela
        if(ball.getHitbox().left < 0){
            ball.reverseXVelocity();
        }

        if(ball.getHitbox().right > screenLimit.x){
            ball.reverseXVelocity();
        }

        for (Stars s : starList){
            s.update();
        }
        ball.update();
        blueBase.update();
        redBase.update();

        //por cada asteroide no array de asteroids
        for (Asteroids a : asteroids){
            a.update();
        }

        //Se a bola sai da tela
        if(ball.getHitbox().bottom > screenLimit.y || ball.getHitbox().top < 0){
            //game over
            gameOver = true;
        }

        //enquanto não é game over
        if(!gameOver){
            //faz contagem dos pontos
            pts = (int)System.currentTimeMillis() - timeStarted + pts10;
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
            for(Asteroids a : asteroids){
                canvas.drawBitmap(a.getAsteroidBitmap(),
                        a.getX(),
                        a.getY(),
                        paint);
            }

            //texto
            if(!gameOver) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(25);
                canvas.drawText("PTS: " + pts, 10, 20, paint);
            } else{
                //Quando o jogo acabar
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenLimit.x/2, 100, paint);
                paint.setTextSize(25);
                canvas.drawText("PTS: " + pts, screenLimit.x/2, 160, paint);

                paint.setTextSize(80);
                canvas.drawText("Tap to Replay!", screenLimit.x/2, 350, paint);
            }
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

    //INPUTS
    // SurfaceView allows us to handle the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Diferentes tipos de evento de toque
        // We care about just 2 - for now.
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Has the player lifted their finger up?
            case MotionEvent.ACTION_UP:
                if (!gameOver) {
                    blueBase.setMovementState(blueBase.STOPPED);
                    redBase.setMovementState(redBase.STOPPED);
                }
                break;
            // O jogador tocou a tela¹
            case MotionEvent.ACTION_DOWN:

                //se precionado o lado direito da tela
                if(motionEvent.getX() > screenLimit.x / 2) {
                    blueBase.setMovementState(blueBase.RIGHT);
                    redBase.setMovementState(redBase.LEFT);
                }else {
                    blueBase.setMovementState(blueBase.LEFT);
                    redBase.setMovementState(redBase.RIGHT);
                }
                // caso seja fim de jogo
                if(gameOver){
                    //reinicia o jogo
                    startGame();
                }

                break;

        }
        return true;
    }
}
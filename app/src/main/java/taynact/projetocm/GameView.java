package taynact.projetocm;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;



public class GameView extends SurfaceView implements Runnable{

    private Context context;
    private GameView gameView;


    volatile boolean playing;
    Thread gameThread = null;

    //private Sketch oriente;

    //bola
    private  TheBall ball;

    //variaveis de pontuação
    private int score;
    private int timeStarted;
    private int touch = 1;

    //barras
    private Base blueBase;
    private Base redBase;

    //asteroides
    private Asteroids[] asteroids;

    //strelas background
    private ArrayList<Stars> starList = new ArrayList<Stars>();

    // objetos para desenar o bitmap na tela
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    private Point screenLimit;

    //fim de jogo
    private  boolean gameOver;

    //ranking
    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "RankingFile";

    //score
    String[] savedScores;

    int AUX = -1;

    //construtor
    public GameView(Context context, Point screenLimit) {
        super(context);
        this.context = context;
        this.screenLimit = screenLimit;

        //inicia o objeto do tipo SharedPreferences
        gamePrefs = context.getSharedPreferences(GAME_PREFS, 0);

        startGame();
    }

    //Inicia as variaveis de jogo
    public void  startGame(){

        /*
        //LIMPA SHAREPREFERENCES
        SharedPreferences preferences = context.getSharedPreferences(GAME_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
*/

        //numero de estrelas a gerar
        int numStars = 10;
        asteroids = new Asteroids[3];

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
        //oriente = new Sketch();

        //inicia os asteroides
        for (int i = 0; i < asteroids.length; i ++){
            asteroids[i] = new Asteroids(context, screenLimit);
        }

        score = 0;
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
/*
        if (oriente.getorientez()>0) //esquerda
        {
            blueBase.setMovementState(blueBase.LEFT);
            redBase.setMovementState(redBase.RIGHT);
            if (oriente.getorientey()>0) //aumenta velocidade
            {
                if (touch==1)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() * 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() * 1.5f));
                }
                else if(touch==0)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() / 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() / 1.5f));
                }
            }
            else if (oriente.getorientey()<0) //diminui velocidade
            {
                if (touch==1)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() / 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() / 1.5f));
                }
                else if(touch==0)
                {
                ball.setX(ball.getX() + (ball.getxVelocity() * 1.5f));
                ball.setY(ball.getY() + (ball.getyVelocity() * 1.5f));
                }
            }
        }
        else if (oriente.getorientez()<0) //direita
        {
            blueBase.setMovementState(blueBase.RIGHT);
            redBase.setMovementState(redBase.LEFT);
            if (oriente.getorientey()>0) //aumenta velocidade
            {
                if (touch==1)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() * 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() * 1.5f));
                }
                else if(touch==0)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() / 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() / 1.5f));
                }
            }
            else if (oriente.getorientey()<0) //diminui velocidade
            {
                if (touch==1)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() / 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() / 1.5f));
                }
                else if(touch==0)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() * 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() * 1.5f));
                }
            }

        } else
        { //paralelo ao chão
            blueBase.setMovementState(blueBase.STOPPED);
            redBase.setMovementState(redBase.STOPPED);
            if (oriente.getorientey()>0) //aumenta velocidade
            {
                if (touch==1)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() * 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() * 1.5f));
                }
                else if(touch==0)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() / 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() / 1.5f));
                }
            }
            else if (oriente.getorientey()<0) //diminui velocidade
            {
                if (touch==1)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() / 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() / 1.5f));
                }
                else if(touch==0)
                {
                    ball.setX(ball.getX() + (ball.getxVelocity() * 1.5f));
                    ball.setY(ball.getY() + (ball.getyVelocity() * 1.5f));
                }
            }
        }
*/

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

        if(Rect.intersects  (ball.getHitbox(), asteroids[0].getHitbox()))
        {
            //o que fazer ao bater
            if  (ball.getHitbox().right == asteroids[0].getHitbox().left);
            {
                ball.reverseXVelocity();
            }

            if  (ball.getHitbox().left == asteroids[0].getHitbox().right);
            {
                ball.reverseXVelocity();
            }

            if  (ball.getHitbox().top == asteroids[0].getHitbox().bottom);
            {
                ball.reverseYVelocity();
            }

            if  (ball.getHitbox().bottom == asteroids[0].getHitbox().top);
            {
                ball.reverseYVelocity();
            }
        }

        if(Rect.intersects  (ball.getHitbox(), asteroids[1].getHitbox()))
        {
            //o que fazer ao bater
            if  (ball.getHitbox().right == asteroids[1].getHitbox().left);
            {
                ball.reverseXVelocity();
            }

            if  (ball.getHitbox().left == asteroids[1].getHitbox().right);
            {
                ball.reverseXVelocity();
            }

            if  (ball.getHitbox().top == asteroids[1].getHitbox().bottom);
            {
                ball.reverseYVelocity();
            }

            if  (ball.getHitbox().bottom == asteroids[1].getHitbox().top);
            {
                ball.reverseYVelocity();
            }

        }

        if(Rect.intersects  (ball.getHitbox(), asteroids[2].getHitbox()))
        {
            //o que fazer ao bater
            if  (ball.getHitbox().right == asteroids[2].getHitbox().left);
            {
                ball.reverseXVelocity();
            }

            if  (ball.getHitbox().left == asteroids[2].getHitbox().right);
            {
                ball.reverseXVelocity();
            }

            if  (ball.getHitbox().top == asteroids[2].getHitbox().bottom);
            {
                ball.reverseYVelocity();
            }

            if  (ball.getHitbox().bottom == asteroids[2].getHitbox().top);
            {
                ball.reverseYVelocity();
            }
        }


        //se a bola atingir os lados da tela
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
            score = (int)System.currentTimeMillis() - timeStarted + pts10;
        }

        if (gameOver){

            if(AUX == 0){
                setHightScore();
                SharedPreferences scorePref = context.getSharedPreferences(GAME_PREFS, 0);
                savedScores = scorePref.getString("highScores","").split("\\|");
            }
            AUX = 1;

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
                canvas.drawText("PTS: " + score, 10, 20, paint);
            } else{
                //Quando o jogo acabar
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenLimit.x/2, 100, paint);
                paint.setTextSize(25);
                canvas.drawText("PTS: " + score, screenLimit.x/2, 160, paint);

                paint.setTextSize(80);
                canvas.drawText("Tap to Replay!", screenLimit.x/2, 350, paint);

                paint.setTextSize(50);
                canvas.drawText("HIGH SCORE", screenLimit.x/2, 400, paint);
                //StringBuilder scoreBuild = new StringBuilder("");
                for(int i = 0; i < savedScores.length; i ++){
                    paint.setTextSize(25);
                    canvas.drawText(savedScores[i].toString() + "\n", screenLimit.x/2, 450 + (i*25), paint);
                }

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

    private  void setHightScore(){
        int exScore = score;
        List<Score> scoreStrings = new ArrayList<Score>();

        SharedPreferences.Editor scoreEdit = gamePrefs.edit();
        //para cada score vamor incluir no gamePrefs o score e a data
        android.icu.text.DateFormat date = new android.icu.text.SimpleDateFormat("dd MMMM YYYY");
        //formatação da data
        String dateOutput = date.format(new Date());

        //pega todos dados de score do gamePrefs
        String scores = gamePrefs.getString("highScores", "");

        //verifica se há algum score ja salvo
        if(scores.length() > 0){
            //se houver scores
            String[] exScores = scores.split("\\|");

            //cria um objeto da classse score para cada hight score separando pontuação e data por "-"
            for(String esc : exScores){
                String[] parts = esc.split(" - ");
                scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }
            //adiciona a nova pontuação a lista
            Score newScore = new  Score(dateOutput,exScore);
            scoreStrings.add(newScore);

            //ordena os objetos da lista de acordo com o compareTo metodo da classe Score
            Collections.sort(scoreStrings);

            // Separa os 10 primeiros scores e adiciona em uma pipa-delimited string
            // e então passa para o Shared Preferences
            StringBuilder scoreBuild = new StringBuilder("");
            for(int s=0; s<scoreStrings.size(); s++){
                if(s>=10) break;//se passar de dez, sai do ciclo
                if(s>0) scoreBuild.append("|");//separa os scores pelo "|"
                scoreBuild.append(scoreStrings.get(s).getScoreText());
            }

            //passa para o Shared Preferences
            scoreEdit.putString("highScores", scoreBuild.toString());
            scoreEdit.commit();

        }
        else {
            //se não
            scoreEdit.putString("highScores", "" + dateOutput + " - " + exScore);
            scoreEdit.commit();
        }

    }


}
package taynact.projetocm;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import  android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public  class GameActivity extends  Activity{

    private GameView gameView;

    //ranking
    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "RankingFile";

    //é para este codigo q somos levados quando clicamos em start
    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        //objeto que pega referencias da tela
        Display display = getWindowManager().getDefaultDisplay();

        // é criado um variavel do tipo Point, onde depois recebe a resolução da tela
        //ou seja um ponto com as cordenadas com os limites da tela
        Point size = new Point();
        display.getSize(size);

        //cria instacia do gameView
        //'this' é o Context da aplicação
        gameView = new GameView(this, size);
        //passa o gameView para o view da atividade
        setContentView(gameView);
        //inicia o objeto do tipo SharedPreferences
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
    }

    //se a atividade estiver em pausa, fazemos ter certeza q o thread tambem esta
    @Override
    protected  void onPause(){
        super.onPause();
        gameView.pause();
    }

    //se a atividade for resumida, resume o thread
    @Override
    protected  void onResume(){
        super.onResume();
        gameView.resume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    private  void setHightScore(){
        int exScore = gameView.getScore();
        List<Score> scoreStrings = new ArrayList<Score>();

        SharedPreferences.Editor scoreEdit = gamePrefs.edit();
        //para cada score vamor incluir no gamePrefs o score e a data
        DateFormat date = new SimpleDateFormat("dd MMMM YYYY");
        //formatação da data
        String dateOutput = date.format(new Date());

        //pega todos dados de score do gamePrefs
        String scores = gamePrefs.getString("highScores", "");

        //verifica se há algum score ja salvo
        if(scores.length() > 0){
            //se houver scores
            String[] exScores = scores.split("\\|");
            for(String esc : exScores){
                String[] parts = esc.split(" - ");
                scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }
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
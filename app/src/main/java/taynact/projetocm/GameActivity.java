package taynact.projetocm;

import android.app.Activity;
import android.graphics.Point;
import  android.os.Bundle;
import android.view.Display;

public  class GameActivity extends  Activity{

    private GameView gameView;

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
        //'this' é o Context da aaplicação
        gameView = new GameView(this);
        //passa o gameView para o view da atividade
        setContentView(gameView);
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

}
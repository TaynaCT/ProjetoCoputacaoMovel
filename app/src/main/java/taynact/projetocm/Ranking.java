package taynact.projetocm;

/**
 * Classe controloa o ranking do jogo.
 * Acessa um servidor
 */

import java.util.ArrayList;
import processing.core.PApplet;
import ketai.sensors.*;

public class Ranking {

    String serverULR = "http://heda.000webhostapp.com/teste.php?";
    String updateServer = "update=ranking&values=";
    String getData = "get=ranking";
    String[] rankingLinst;

    //função que envia a pontuação da pontuação para o servidor
    public void updateRanking(String pts){
        String ulr = serverULR + updateServer + pts;
        //String result[] = loadString

    }

}

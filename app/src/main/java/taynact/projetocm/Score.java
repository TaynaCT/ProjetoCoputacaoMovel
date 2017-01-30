package taynact.projetocm;

/**
 *Classe auxiliar que compara objetos dois objeto da classe
 */

public class Score implements Comparable<Score> {

    private  String scoreDate;
    public  int scoreNum;

    public  Score(String date, int num){
        scoreDate = date;
        scoreNum = num;
    }

    @Override
    //metodo q permite comparar os scores
    public int compareTo(Score sc) {
        // return 0 se forem iguais
        // 1 se o numero a comparar for maior que o numero da classe
        //-1 se o numero da classe for maio que o numero a ser comparado
        return sc.scoreNum>scoreNum? 1 : sc.scoreNum<scoreNum? -1 : 0;
    }

    //retorna o score e a data como string
    public  String getScoreText(){
        return scoreDate + " - " + scoreNum;
    }
}

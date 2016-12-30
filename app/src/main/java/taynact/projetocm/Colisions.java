package taynact.projetocm;

/**
 * Created by lobix7 on 30/12/2016.
 */

import android.view.Display;

public class Colisions {

    public boolean objectcolision(TheBall ball, Base base)
    {

        if (ball.getX() < base.getX() + base.getBarBitmap().getWidth() &&
                ball.getX() + ball.getBall().getWidth() > base.getX() &&
                ball.getY() < base.getY() + base.getBarBitmap().getHeight() &&
                ball.getBall().getHeight() + ball.getY() > base.getY())
        { return true;}
        else return false;
    }

    public boolean sidescolision(TheBall ball, GameView gameView)
    {
        if(ball.getX()+(ball.getBall().getWidth()/2)>gameView.getWidth()| ball.getX()-(ball.getBall().getWidth()/2)<0)
        {
            return true;
        }
        else return false;
    }
}

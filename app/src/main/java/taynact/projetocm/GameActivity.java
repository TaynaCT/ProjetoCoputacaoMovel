package taynact.projetocm;

import  android.app.Activity;
import  android.os.Bundle;

public  class GameActivity extends Activity{

    GameView gameView;

    // This is where the "Play" button from HomeActivity sends us
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create an instance of our Tappy Defender View (TDView)
        // Also passing in "this" which is the Context of our app
        gameView = new GameView(this);
        // Make our gameView the view for the Activity
        setContentView(gameView);
    }
    // If the Activity is paused make sure to pause our thread
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
    // If the Activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}
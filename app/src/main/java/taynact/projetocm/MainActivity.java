package taynact.projetocm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ketai.ui.*;

public class MainActivity extends Activity
        implements View.OnClickListener{

    public String namePlayer;
    EditText playerName;

    @Override
    public void onClick(View v) {
        namePlayer = playerName.getText().toString();

        //cria um objeto do tipo intent
        //passa o nome da atividade a iniciar ao clicar o botão 'start'
        Intent i = new Intent(this, GameActivity.class);
        //passa a informação extra para a a atividade GameActivity
        i.putExtra("name", namePlayer);

        //inicia a atividade
        startActivity(i);
        //encerra a atividade atual
        finish();
    }

    // Entrada do jogo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Here we set our UI layout as the view
        setContentView(R.layout.activity_main);

        //cria referencia para o botão "start' na tela inicial
        final Button buttonStart = (Button)findViewById(R.id.buttonStart);
        playerName = (EditText)findViewById(R.id.playerName);

        buttonStart.setOnClickListener(this);
    }

}
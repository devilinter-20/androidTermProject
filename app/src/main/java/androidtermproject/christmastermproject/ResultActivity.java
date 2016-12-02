package androidtermproject.christmastermproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView resultScore;
    Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        resultScore = (TextView)findViewById(R.id.result_score);
        int score = (int) getIntent().getExtras().get("score");
        resultScore.setText(Integer.toString(score));
        ProjectPreferences.setCurrentScore(this,score);
        if(ProjectPreferences.getHighScore(this) < score ){
            ProjectPreferences.setHighScore(this,score);
        }

        okBtn = (Button)findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });
    }
}

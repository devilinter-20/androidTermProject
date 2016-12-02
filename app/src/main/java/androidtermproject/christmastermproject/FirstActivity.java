package androidtermproject.christmastermproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    Button startBtn;
    TextView curr,high;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();

        curr = (TextView)findViewById(R.id.score);
        high = (TextView)findViewById(R.id.high_score);

        curr.setText(Integer.toString(ProjectPreferences.getCurrentScore(this)));
        high.setText(Integer.toString(ProjectPreferences.getHighScore(this)));

        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

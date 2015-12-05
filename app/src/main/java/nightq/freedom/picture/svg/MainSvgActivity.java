package nightq.freedom.picture.svg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import nightq.freedom.picture.R;


public class MainSvgActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_main);

//        findViewById(R.id.tvNext1).setOnClickListener(this);
        findViewById(R.id.tvNextPosition).setOnClickListener(this);
        findViewById(R.id.tvNextCount).setOnClickListener(this);
//
        imageView = ((ImageView) findViewById(R.id.imageView));
    }

    int count = 0;

    @Override
    public void onClick(View view) {
        count ++;
        switch (view.getId()) {
            case R.id.tvNextPosition:
                imageView.setImageResource(
                        count % 2 == 0 ? 0 : R.drawable.bubble);
                break;
            case R.id.tvNextCount:
                imageView.setBackgroundResource(
                        count % 2 == 0 ? 0 : R.drawable.bubble);
                break;
        }
    }

}

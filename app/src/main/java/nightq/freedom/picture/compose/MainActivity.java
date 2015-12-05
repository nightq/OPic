package nightq.freedom.picture.compose;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nightq.freedom.picture.R;
import nightq.freedom.picture.compose.enums.ComposeType;
import nightq.freedom.picture.compose.widgets.ComposeLayout;
import nightq.freedom.picture.compose.widgets.PolygonImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    PolygonImageView image1;
    PolygonImageView image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        findViewById(R.id.tvNext1).setOnClickListener(this);
        findViewById(R.id.tvNextPosition).setOnClickListener(this);
        findViewById(R.id.tvNextCount).setOnClickListener(this);
//
//        image1 = ((PolygonImageView) findViewById(R.id.image1));
//        image2 = ((PolygonImageView) findViewById(R.id.image2));
        findViewById(R.id.tvNextPosition).performClick();
    }

    int defaultPos = -1;
    ComposeType defaultType = ComposeType.ComposeTwoPic;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.tvNext1:
//                break;
//            case R.id.tvNext2:
//                break;
            case R.id.tvNextPosition:
                if (defaultType == ComposeType.ComposeTwoPic) {
                    defaultPos ++;
                    defaultPos = defaultPos % 2;
                } else {
                    defaultPos = 0;
                }
                ((ComposeLayout) findViewById(R.id.composelayout))
                        .setComposeType(defaultType, defaultPos);
                break;
            case R.id.tvNextCount:
                switch (defaultType) {
                    case ComposeFourPic:
                        defaultType = ComposeType.ComposeTwoPic;
                        break;
                    case ComposeThreePic:
                        defaultType = ComposeType.ComposeFourPic;
                        break;
                    case ComposeTwoPic:
                        defaultType = ComposeType.ComposeThreePic;
                        break;
                }
                findViewById(R.id.tvNextPosition).performClick();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

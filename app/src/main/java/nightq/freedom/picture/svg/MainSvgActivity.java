package nightq.freedom.picture.svg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eftimoff.androipathview.PathView;

import nightq.freedom.picture.R;
import nightq.freedom.picture.compose.MainActivity;
import uk.co.senab.photoview.PhotoViewAttacher;


public class MainSvgActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    public EditText editText;
    public EditText editTextX;
    public EditText editTextY;

    public TextView tvCommit;

    public ImageView imageView;

    public PathView pathView;

    public LinearLayout pathViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_main);

        pathViewLayout = (LinearLayout) findViewById(R.id.pathViewLayout);

        pathView = (PathView) findViewById(R.id.pathView);

        pathView.getPathAnimator()
                .delay(100)
                .duration(500)
//                .listenerStart(new AnimationListenerStart())
//                .listenerEnd(new AnimationListenerEnd())
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
        pathView.useNaturalColors();
        pathView.setFillAfter(true);
        pathView.setVisibility(View.GONE);

        imageView = (ImageView) findViewById(R.id.imageView);
        new PhotoViewAttacher(imageView);
        editText = (EditText) findViewById(R.id.editText);
        editTextX = (EditText) findViewById(R.id.editTextX);
        editTextY = (EditText) findViewById(R.id.editTextY);

        tvCommit = (TextView) findViewById(R.id.tvCommit);

        findViewById(R.id.tvCommit).setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        findViewById(R.id.fab).setOnLongClickListener(this);
        findViewById(R.id.tvCommit).setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCommit:
//                try {
//                    LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) editText.getLayoutParams();
//                    ll.width = Integer.valueOf(editTextX.getText().toString());
//                    ll.height = Integer.valueOf(editTextY.getText().toString());
//                    editText.setLayoutParams(ll);
//                } catch (Exception e) {
//                }
//
//                try {
//                    LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//                    ll.width = Integer.valueOf(editTextX.getText().toString());
//                    ll.height = Integer.valueOf(editTextY.getText().toString());
//                    imageView.setLayoutParams(ll);
//                } catch (Exception e) {
//                }

                refreshNewPathView();
                try {
                    LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) pathView.getLayoutParams();
                    ll.width = Integer.valueOf(editTextX.getText().toString());
                    ll.height = Integer.valueOf(editTextY.getText().toString());
                    pathView.setLayoutParams(ll);
                } catch (Exception e) {
                }

                pathView.getPathAnimator()
                        .delay(100)
                        .duration(500)
//                .listenerStart(new AnimationListenerStart())
//                .listenerEnd(new AnimationListenerEnd())
                        .interpolator(new AccelerateDecelerateInterpolator())
                        .start();
                break;
            case R.id.fab:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }

    int count = 0;

    public int[] resIds = {
            R.raw.flag_usa,
            R.raw.ironman,
            R.raw.ironman_white,
            R.raw.issues,
            R.raw.linecap,
            R.raw.logout,
            R.raw.monitor,
            R.raw.settings,
            R.raw.bubbl
    };
    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.fab:

                editTextX.setText("" + 200);
                editTextY.setText("" + 200);
                tvCommit.performClick();
                break;

            case R.id.tvCommit:

                count ++;
                count = count % resIds.length;

                refreshNewPathView();


                pathView.getPathAnimator()
                        .delay(100)
                        .duration(500)
//                .listenerStart(new AnimationListenerStart())
//                .listenerEnd(new AnimationListenerEnd())
                        .interpolator(new AccelerateDecelerateInterpolator())
                        .start();
                break;
        }
        return true;
    }

    public void refreshNewPathView () {
        pathViewLayout.removeAllViews();

        pathView = new PathView(this, null);

        pathView.setFillAfter(true);
        pathView.useNaturalColors();
        pathView.setPathWidth(0.00000001f);
//        pathView.setPathWidth(0);
        pathView.setSvgResource(resIds[count]);

        pathViewLayout.addView(pathView,
                new LinearLayout.LayoutParams(300, 300));
    }


}

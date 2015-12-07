package nightq.freedom.picture.svg;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    public LinearLayout pathViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_main);

        pathViewLayout = (LinearLayout) findViewById(R.id.pathViewLayout);

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

//        VectorDrawable vectorDrawable = (VectorDrawable) getResources().getDrawable(R.drawable.bubble);

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
                try {
                    LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    ll.width = Integer.valueOf(editTextX.getText().toString());
                    ll.height = Integer.valueOf(editTextY.getText().toString());
                    imageView.setLayoutParams(ll);
                } catch (Exception e) {
                }

//                Drawable drawable = imageView.getBackground();
//                if (drawable != null && drawable instanceof VectorDrawable) {
//                    Log.e("nightq", "drawable = " + drawable);
//                }
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

    public int[] resDrawableIds = {
            R.drawable.bubble,
            R.drawable.bubble1,
            R.drawable.bubble2
//            R.drawable.bubble3,
//            R.drawable.bubble4
//            R.drawable.heart
//            R.drawable.reven
    };

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
//
//                editTextX.setText("" + 200);
//                editTextY.setText("" + 200);
                tvCommit.performClick();
                break;

            case R.id.tvCommit:
                count ++;
                count = count % resDrawableIds.length;
//                imageView.setImageResource(resDrawableIds[count]);
                imageView.setBackgroundResource(resDrawableIds[count]);
                Toast.makeText(MainSvgActivity.this, "count = " + count, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void refreshView () {
    }



}

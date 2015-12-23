package nightq.freedom.picture.compose;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import nightq.freedom.picture.R;
import nightq.freedom.picture.compose.enums.ComposeType;
import nightq.freedom.picture.compose.utils.ComposeModelsUtils;
import nightq.freedom.picture.compose.widgets.ComposeLayout;
import nightq.freedom.picture.compose.widgets.ComposeView;
import nightq.freedom.picture.compose.widgets.PolygonImageView;
import nightq.freedom.picture.svg.MainSvgActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    PolygonImageView image1;
    PolygonImageView image2;

    ComposeView composeView;

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        composeView = (ComposeView) findViewById(R.id.composeView);

        imageView = (ImageView) findViewById(R.id.imageView);

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
        findViewById(R.id.fab).setOnClickListener(this);
//
//        image1 = ((PolygonImageView) findViewById(R.id.image1));
//        image2 = ((PolygonImageView) findViewById(R.id.image2));
        findViewById(R.id.tvNextPosition).performClick();

        drawBase();

    }

    int bmpW = 0;
    int bmpH = 0;
    int viewW = 900;
    int viewH = 1200;

    private void getBmp () {
        if (bmp == null || bmp.isRecycled()) {
//            bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.test1);
            bmpW = bmp.getWidth();
            bmpH = bmp.getHeight();
        }
    }

    public Matrix getBase () {
        final float widthScale = viewW / (float) bmpW;
        final float heightScale = viewH / (float) bmpH;

        float current  = Math.max(widthScale, heightScale);
        baseMatrix.setScale(current, current);
        //
//        model.setTransformX((viewWidth - bmpWidth * model.scale) / 2F / viewWidth);
//        model.setTransformY((viewHeight - bmpHeight * model.scale) / 2F /viewHeight);

        baseMatrix.postTranslate(
                (viewW - bmpW * current) / 2F,
                (viewH - bmpH * current) / 2F);
        return baseMatrix;

    }


    public void drawBase () {
        getBmp ();

        Bitmap tmp = Bitmap.createBitmap(viewW, viewH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tmp);
        canvas.drawBitmap(bmp, getBase(), new Paint());

        imageView.setImageBitmap(tmp);
    }

    public Matrix getSupport () {

        int count = getCount();
        float current  = 1 + 1f * count;//1 + 9*count;
        supportMatrix.setScale(
                current, current,
                viewW/2, viewH/2);

        supportMatrix.postTranslate(
                count * viewW/2,
                count * 0);
        return supportMatrix;
    }


    public void drawSupport () {
        getBmp();

        Bitmap tmp = Bitmap.createBitmap(viewW, viewH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tmp);
        canvas.drawBitmap(bmp, getSupport(), new Paint());
        imageView.setImageBitmap(tmp);
    }

    public void drawAll () {

        getBmp();

        resultMatrix.set(getBase());
        resultMatrix.postConcat(getSupport());

        Bitmap tmp = Bitmap.createBitmap(viewW, viewH, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tmp);
        canvas.drawBitmap(bmp, resultMatrix, new Paint());
        imageView.setImageBitmap(tmp);

    }

    int defaultPos = -1;
    ComposeType defaultType = ComposeType.ComposeTwoPic;


    private int getCount () {
        count ++;
        return count % 2;
    }
    int count = 0;
    Matrix baseMatrix = new Matrix();
    Matrix supportMatrix = new Matrix();
    Matrix pathMatrix = new Matrix();
    Matrix resultMatrix = new Matrix();

    Bitmap bmp;
    @Override
    public void onClick(View view) {
        getBmp();
        switch (view.getId()) {
//            case R.id.tvNext1:
//                break;
//            case R.id.tvNext2:
//                break;
            case R.id.tvNextPosition:
                int total = ComposeModelsUtils.getComposeModelsTotal(this, defaultType);
                defaultPos ++;
                defaultPos = defaultPos%total;
//                ((ComposeLayout) findViewById(R.id.composelayout))
//                        .setComposeType(defaultType, defaultPos);
                composeView.setComposeModel(defaultPos);

                drawPicture();

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
                drawPicture();
//                findViewById(R.id.tvNextPosition).performClick();
                break;

            case R.id.fab:
                startActivity(new Intent(this, MainSvgActivity.class));
                finish();
                break;
        }
    }

    public void drawPicture () {
        Log.e("nightq", "defaultType = " + defaultType);
        switch (defaultType) {
            case ComposeFourPic:
                drawAll();
                break;
            case ComposeThreePic:
                drawSupport();
                break;
            case ComposeTwoPic:
                drawBase();
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

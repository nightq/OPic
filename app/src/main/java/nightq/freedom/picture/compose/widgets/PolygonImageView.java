package nightq.freedom.picture.compose.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import nightq.freedom.picture.R;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by Nightq on 15/12/4.
 */
public class PolygonImageView extends ImageView implements View.OnTouchListener{

    ImageView mImageView;
    PhotoViewAttacher mAttacher;

    Paint mPaint;
    Path mPath;
    Region mRegion;

    public PolygonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init (){
        // Set the Drawable displayed
        Drawable bitmap = getResources().getDrawable(R.drawable.brannan);
        setImageDrawable(bitmap);
        // Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
        mAttacher = new PhotoViewAttacher(this);
        // @WARN 重写 setOnTouchListener。为了单独拦截处理touch
        setOnTouchListener(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);

    }

    /**
     *
     */
    public void setPath (Path path) {
        mPath = path;
        RectF rectF = new RectF();
        if (path != null) {
            path.computeBounds(rectF, true);
            mRegion = new Region();
            mRegion.setPath(path,
                    new Region(
                            (int) rectF.left,
                            (int) rectF.top,
                            (int) rectF.right,
                            (int) rectF.bottom));
        } else {
            mRegion = null;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mPath != null) {
            canvas.clipPath(mPath);
            canvas.drawColor(Color.TRANSPARENT);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == ACTION_DOWN) {
            if (mPath != null && mRegion != null
                    && mRegion.contains(
                    (int)motionEvent.getX(),
                    (int)motionEvent.getY())) {
                processTouch(view, motionEvent);
                Log.e("nightq", "id = " + getId() + " down = " + true);
                return true;
            } else {
                Log.e("nightq", "id = " + getId() + " down = " + false);
                return false;
            }
        } else {
            processTouch(view, motionEvent);
            Log.e("nightq", "id = " + getId() + " other = " + true);
            return true;
        }
    }

    public void processTouch (View view, MotionEvent motionEvent) {
        mAttacher.onTouch(view, motionEvent);
    }
}

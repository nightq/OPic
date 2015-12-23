package nightq.freedom.picture.compose.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nightq.freedom.picture.compose.models.ComposeModel;
import nightq.freedom.picture.compose.utils.ComposeModelsUtils;

/**
 * Created by Nightq on 15/12/4.
 */
public class ComposeView extends View {

    Paint mPaint;
    List<Path> mPaths;
    List<Region> mRegions;
    int[] color = {Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED};

    public ComposeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init (){
        // Set the Drawable displayed
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(6);
    }


    /**
     * 设置版式
     */
    public void setComposeModel (int composeModelPosition) {
        List<ComposeModel> list = ComposeModelsUtils
                .getComposeModelsByPicNum(getContext(),
                        ComposeModelsUtils.Compose_Pic_Four);
        if (list == null || composeModelPosition > list.size()-1) {
            return;
        }
        ComposeModel composeModel = list.get(composeModelPosition);

        Log.e("nightq", "view.get = " + getWidth());
        mPaths = composeModel.getPolygonsPath(900, 900);
        invalidate();
    }

    /**
     *
     * @param mPaths
     */
    public void setPaths(List<Path> mPaths) {
        this.mPaths = mPaths;
        mRegions = new ArrayList<>();
        if (mPaths != null) {
            RectF rectF;
            Path path;
            Region region;
            for (int i=0; i<mPaths.size(); i++) {
                path = mPaths.get(i);
                rectF = new RectF();
                path.computeBounds(rectF, true);
                region = new Region();
                region.setPath(path,
                        new Region(
                                (int) rectF.left,
                                (int) rectF.top,
                                (int) rectF.right,
                                (int) rectF.bottom));
                mRegions.add(region);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mPaths != null) {
            Path path;
            for (int i=0; i<mPaths.size(); i++) {
                path = mPaths.get(i);
                canvas.save();
                canvas.clipPath(path);
                canvas.drawColor(color[i]);
                canvas.drawText("position" + i, 10, 10, mPaint);
                canvas.restore();
            }
        }
        super.onDraw(canvas);
    }

}

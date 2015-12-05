package nightq.freedom.picture.compose.widgets;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import nightq.freedom.picture.R;
import nightq.freedom.picture.compose.enums.ComposeType;
import nightq.freedom.picture.compose.models.ComposeModel;
import nightq.freedom.picture.compose.utils.ComposeModelsUtils;

/**
 * Created by Nightq on 15/12/5.
 */
public class ComposeLayout extends RelativeLayout {

    public static final int Width = 900;
    public static final int Height = 900;

    /**
     * 版式 key
     */
    public String composeTypeKey;
    /**
     * 几个view
     */
    public List<PolygonImageView> imageViews;
    /**
     * 当前 版式
     */
    public ComposeModel composeModel;
    /**
     * 当前版式的path
     */
    public List<Path> currentPath;

    public ComposeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置有几张图
     * @param composeType
     * @param defaultComposePosition 默认第几个版式
     */
    public void setComposeType(ComposeType composeType, int defaultComposePosition) {
        if (imageViews == null) {
            imageViews = new ArrayList<>();
            addViewToLayout(imageViews, getViewByPosition(getContext(), 0));
            addViewToLayout(imageViews, getViewByPosition(getContext(), 1));
            addViewToLayout(imageViews, getViewByPosition(getContext(), 2));
            addViewToLayout(imageViews, getViewByPosition(getContext(), 3));
        }
        switch (composeType) {
            case ComposeFourPic:
                imageViews.get(0).setVisibility(View.VISIBLE);
                imageViews.get(1).setVisibility(View.VISIBLE);
                imageViews.get(2).setVisibility(View.VISIBLE);
                imageViews.get(3).setVisibility(View.VISIBLE);
                composeTypeKey = ComposeModelsUtils.Compose_Pic_Four;
                break;
            case ComposeThreePic:
                imageViews.get(0).setVisibility(View.VISIBLE);
                imageViews.get(1).setVisibility(View.VISIBLE);
                imageViews.get(2).setVisibility(View.VISIBLE);
                imageViews.get(3).setVisibility(View.GONE);
                composeTypeKey = ComposeModelsUtils.Compose_Pic_Three;
                break;
            case ComposeTwoPic:
            default:
                imageViews.get(0).setVisibility(View.VISIBLE);
                imageViews.get(1).setVisibility(View.VISIBLE);
                imageViews.get(2).setVisibility(View.GONE);
                imageViews.get(3).setVisibility(View.GONE);
                composeTypeKey = ComposeModelsUtils.Compose_Pic_Two;
                break;
        }
        setComposeModel(defaultComposePosition);
    }

    /**
     * 设置版式
     */
    public void setComposeModel (int composeModelPosition) {
        List<ComposeModel> list = ComposeModelsUtils
                .getComposeModelsByPicNum(getContext(), composeTypeKey);
        if (list == null || composeModelPosition > list.size()-1) {
            return;
        }
        composeModel = list.get(composeModelPosition);

        Log.e("nightq", "view.get = " + getWidth());
        currentPath = composeModel.getPolygonsPath(Width, Height);
        for (int i=0; i<currentPath.size(); i++) {
            imageViews.get(i).setMinimumHeight(Height);
            imageViews.get(i).setMinimumWidth(Width);
            imageViews.get(i).setPath(currentPath.get(i));
        }
    }

    public void addViewToLayout (List<PolygonImageView> list, PolygonImageView view) {
        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(Width, Height);
        addView(view, rl);
        imageViews.add(view);
    }

    /**
     * 通过位置获取view
     * @param position
     * @return
     */
    public static PolygonImageView getViewByPosition (Context context, int position) {
        PolygonImageView view = new PolygonImageView(context, null);
        switch (position) {
            case 0:
                view.setId(R.id.compose_image_0);
                break;
            case 1:
                view.setId(R.id.compose_image_1);
                break;
            case 2:
                view.setId(R.id.compose_image_2);
                break;
            case 3:
                view.setId(R.id.compose_image_3);
                break;
            case 4:
                view.setId(R.id.compose_image_4);
                break;
            case 5:
                view.setId(R.id.compose_image_5);
                break;
        }
        return view;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
}

package nightq.freedom.picture.compose.models;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * 版式
 * Created by Nightq on 15/12/5.
 */
public class ComposeModel {

    public enum PercentUnit {
        // 百分比
        PercentH,
        // 千分比
        PercentT
    }

//      "drawable_name": "",
    public String drawable_name;
    public float[][] polygons;
//      "polygons": [
//              point1      point2      point3      point4
//              [10, 10,    90.5, 10,   80, 85,     15, 95],
//              [15, 7,     70, 4,      95, 95,     4, 75]
//              ]


    private List<Path> polygonsPath;

    /**
     * 获取对应大小的版式框
     * @param width
     * @param height
     * @return
     */
    public synchronized List<Path> getPolygonsPath(float width, float height) {
        if (polygonsPath != null) {
            return polygonsPath;
        }
        if (polygons == null || width == 0 || height == 0) {
            throw new IllegalArgumentException();
        }
        polygonsPath = new ArrayList<>();
        Path tmp;
        float[] polygonArray;
        for (int i = 0; i < polygons.length; i++) {
            tmp = new Path();
            polygonArray = polygons[i];
            tmp.moveTo(
                    sizeFromPercent(polygonArray[0], width),
                    sizeFromPercent(polygonArray[1], height));
            for (int p = 2; p <= polygonArray.length/2; p++) {
                tmp.lineTo(
                        sizeFromPercent(polygonArray[p*2-2], width),
                        sizeFromPercent(polygonArray[p*2-1], height));
            }
            tmp.close();
            polygonsPath.add(tmp);
        }
        return polygonsPath;
    }

    /**
     *
     * @param total
     * @param percent
     * @return
     */
    public float sizeFromPercent (float total, float percent) {
        return sizeFromPercent(PercentUnit.PercentH, total, percent);
    }

    /**
     *
     * @param total
     * @param percent
     * @return
     */
    public float sizeFromPercent (PercentUnit percentUnit, float total, float percent) {
        switch (percentUnit) {
            case PercentH:
                return total * percent / 100;
            case PercentT:
            default:
                return total * percent / 1000;
        }
    }
}

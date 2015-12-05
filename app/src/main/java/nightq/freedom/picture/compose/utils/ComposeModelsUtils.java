package nightq.freedom.picture.compose.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import nightq.freedom.picture.compose.models.ComposeModel;

/**
 * Created by Nightq on 15/12/4.
 */
public class ComposeModelsUtils {

    private static HashMap<String, List<ComposeModel>> composeModelsCache;

    /**
     * 版式 raw 文件名
     */
    public static final String Compose_File = "compose";
    /**
     * 两张图的版式key
     */
    public static final String Compose_Pic_Two = "two_pic";
    /**
     * 三张图的版式key
     */
    public static final String Compose_Pic_Three = "three_pic";
    /**
     * 四张图的版式key
     */
    public static final String Compose_Pic_Four = "four_pic";

    /**
     * 初始化
     * @param context
     */
    public static void init(Context context) {
        getComposeModelsFromRaw(context);
    }

    /**
     * 从raw  获取区号列表
     *
     * @return
     */
    private synchronized static HashMap<String, List<ComposeModel>> getComposeModelsFromRaw(
            Context context) {
        if (composeModelsCache != null) {
            return composeModelsCache;
        }
        try {
            int resId = context.getResources().getIdentifier(
                    Compose_File, "raw",
                    context.getPackageName());
            if (resId <= 0) {
                //NOTHING
            } else {
                Reader reader = new InputStreamReader(context.getResources().openRawResource(resId));
                String json = IoUtils.readAllCharsAndClose(reader);
                composeModelsCache = new Gson().fromJson(json,
                        new TypeToken<HashMap<String, List<ComposeModel>>>() {
                        }.getType());
            }
        } catch (Throwable t) {
            //NOTHING
        }
        return composeModelsCache;
    }


    /**
     * 通过版式图片数量取版式列表
     *
     * @param context
     * @param key
     * @return
     */
    public static List<ComposeModel> getComposeModelsByPicNum(
            Context context, String key) {
        getComposeModelsFromRaw(context);
        if (composeModelsCache == null) {
            throw new IllegalArgumentException();
        }
        return composeModelsCache.get(key);
    }
}

package com.it.one.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.one.R;
import com.it.one.domain.HomeData;
import com.it.one.global.GlobalContants;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */

public class HomeContent extends BaseFragment {

    private TextView homeValue;
    private ImageView homeImage;
    private TextView homeTitle;
    private TextView homeContent;
    private TextView homeLikeCount;
    private Button hlb;
    private TextView dtv;
    private TextView dtv1;
    private String contentId;
    private HomeData mHomeData;
    private BitmapUtils bitmapUtils;
    private ScrollView mScrollView;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View initViews(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.home_content, null);
        homeValue = (TextView) view.findViewById(R.id.value_tv);
        dtv = (TextView) view.findViewById(R.id.date_tv);
        dtv1 = (TextView) view.findViewById(R.id.date1_tv);
        homeImage = (ImageView) view.findViewById(R.id.home_iv);
        homeTitle = (TextView) view.findViewById(R.id.title_tv);
        homeContent = (TextView) view.findViewById(R.id.tv_content);
        homeLikeCount = (TextView) view.findViewById(R.id.like_count_tv);
//        hlb = (Button) view.findViewById(R.id.home_like_bt);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_home);
        contentId = getContentId();
        return view;
    }

    /**
     * 获取data的id
     *
     * @return
     */
    private String getContentId() {
        return getArguments().getString("id") != null ? getArguments().getString("id") : null;
    }

    @Override
    public void initData() {
        getDataFromServer();
    }
    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.PICTUER_CONTENT_URL +
                contentId
                + GlobalContants.SERVER_URL_SUFFIX, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.printf("图片的结果：" + result.toString());
                parseData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
            }
        });
    }


    private void parseData(String result) {
        Gson gson = new Gson();
        mHomeData = gson.fromJson(result, HomeData.class);
        Log.i("home的内容是：" , mHomeData.toString());
        homeValue.setText(mHomeData.data.hp_title);
        homeTitle.setText(mHomeData.data.hp_author);
        homeContent.setText(mHomeData.data.hp_content);
        homeContent.setTextColor(Color.parseColor(mHomeData.data.content_bgcolor));
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(mHomeData.data.maketime);
            Calendar calendar = Calendar.getInstance();calendar.setTime(date);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Log.i("day", day+"");
            dtv.setText(day+"");
            dtv1.setText((calendar.get(Calendar.MONTH)+1)+","+calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        homeLikeCount.setText(mHomeData.data.praisenum);
        initBitmapUtils();
        bitmapUtils.display(homeImage,mHomeData.data.hp_img_url);

    }

    private void initBitmapUtils() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "BitmapUtils";
            int memoryCacheSize = (int) (Runtime.getRuntime().totalMemory() / 8);
            int diskCacheSize = 100 * 1024 * 1024;

            bitmapUtils = new BitmapUtils(getContext(), path, memoryCacheSize, diskCacheSize);
        } else {
            bitmapUtils = new BitmapUtils(getContext());
        }
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_8888);
        bitmapUtils.configDefaultConnectTimeout(5000);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.photo);
        bitmapUtils.configDefaultLoadingImage(R.drawable.photo);
        bitmapUtils.configDefaultCacheExpiry(3600 * 24 * 1000);
        bitmapUtils.configDiskCacheEnabled(true);
        bitmapUtils.configMemoryCacheEnabled(true);
    }
}

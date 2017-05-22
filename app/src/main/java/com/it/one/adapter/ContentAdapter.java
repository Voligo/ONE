package com.it.one.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ContentAdapter extends PagerAdapter {
    private final ArrayList<String> data;
    private final FragmentActivity activity;
    private Map<Integer,View> map = new HashMap<>();
    private TextView homeValue;
    private TextView dtv;
    private TextView dtv1;
    private ImageView homeImage;
    private TextView homeTitle;
    private TextView homeContent;
    private TextView homeLikeCount;
    private ScrollView mScrollView;
    private HomeData mHomeData;
    private BitmapUtils bitmapUtils;
    private boolean []isLoad;
    private View view;
    private int InstaPosition;
    private int CurrePosition;
    private boolean isSelected = false;

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        CurrePosition = position;
        if (!isLoad[position]) {
            view = (View) object;
            findViewById();
            initData(position);
            isLoad[position] = true;
        }
        Log.e("Position","CurrePosition："+CurrePosition+"InstaPosition:"+InstaPosition);
    }

    private void findViewById() {
        homeValue = (TextView) view.findViewById(R.id.value_tv);
        dtv = (TextView) view.findViewById(R.id.date_tv);
        dtv1 = (TextView) view.findViewById(R.id.date1_tv);
        homeImage = (ImageView) view.findViewById(R.id.home_iv);
        homeTitle = (TextView) view.findViewById(R.id.title_tv);
        homeContent = (TextView) view.findViewById(R.id.tv_content);
        homeLikeCount = (TextView) view.findViewById(R.id.like_count_tv);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_home);
        final Button bt = (Button) view.findViewById(R.id.home_like_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected) {
                    bt.setSelected(!isSelected);
                    isSelected = true;
                    homeLikeCount.setText(String.valueOf(mHomeData.data.praisenum + 1));
                } else {
                    bt.setSelected(!isSelected);
                    isSelected = false;
                    homeLikeCount.setText(String.valueOf(mHomeData.data.praisenum));
                }
            }
        });

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ContentAdapter(ArrayList<String> data, FragmentActivity activity) {
        this.data = data;
        this.activity = activity;
        isLoad = new boolean[data.size()];
        for (int i =0 ;i<data.size();i++) {
            isLoad[i] = false;
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        InstaPosition = position;
        View view = null;
        if (isLoad[position]) {
            view = map.get(position);
        } else {
            view = activity.getLayoutInflater().inflate(R.layout.home_content, null);
            isLoad[position] = false;
        }
        container.addView(view);
        return view;
    }

    private void initData(int position) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.PICTUER_CONTENT_URL +
                data.get(position)
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
        homeLikeCount.setText(String.valueOf(mHomeData.data.praisenum));
        initBitmapUtils();
        bitmapUtils.display(homeImage,mHomeData.data.hp_img_url);
    }
    private void initBitmapUtils() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "BitmapUtils";
            int memoryCacheSize = (int) (Runtime.getRuntime().totalMemory() / 8);
            int diskCacheSize = 100 * 1024 * 1024;

            bitmapUtils = new BitmapUtils(activity, path, memoryCacheSize, diskCacheSize);
        } else {
            bitmapUtils = new BitmapUtils(activity);
        }
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.ARGB_8888);
        bitmapUtils.configDefaultConnectTimeout(5000);
//        bitmapUtils.configDefaultLoadFailedImage(R.drawable.photo);
//        bitmapUtils.configDefaultLoadingImage(R.drawable.loader_icon);
        bitmapUtils.configDefaultCacheExpiry(3600 * 24 * 1000);
        bitmapUtils.configDiskCacheEnabled(true);
        bitmapUtils.configMemoryCacheEnabled(true);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        if (isLoad[position])map.put(position, (View) object);
        Log.e("Position", "DestroyP:"+position+"CurrePosition："+CurrePosition+"InstaPosition:"+InstaPosition);
    }
}

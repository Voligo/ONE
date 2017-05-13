package com.it.one;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.it.one.fragment.ContentFragment;
import com.it.one.fragment.HomeFragment;
import com.it.one.global.GlobalContants;
import com.it.one.ui.NoSlidingViewPaper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageButton mShare;
    private NoSlidingViewPaper cvp;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    cvp.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_reading:
                    cvp.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_question:
                    cvp.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_person:
                    cvp.setCurrentItem(3, false);
                    return true;
            }
            return false;
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                mTextMessage.setText("分享");
            }
        });


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        adapter.addFragment(BaseFragment.newInstance("新闻"));
//        adapter.addFragment(BaseFragment.newInstance("图书"));
//        adapter.addFragment(BaseFragment.newInstance("发现"));
//        adapter.addFragment(BaseFragment.newInstance("发现"));
        addContentFragment(adapter);
        cvp.setAdapter(adapter);
//        NetworkPost();
    }

    /**
     * 试验网络链接
     */
    private void NetworkPost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(GlobalContants.PICTURE_SERVER_URl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        StringBuffer buffer = new StringBuffer();
                        int i;
                        while ((i = is.read()) != -1) {
                            buffer.append(i);
                        }
                        String s = buffer.toString();
                        Log.i("AAAA", s);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addContentFragment(ViewPagerAdapter adapter) {
        HomeFragment homeFragment = new HomeFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(setFragmentargument("info", "阅读"));
        adapter.addFragment(setFragmentargument("info", "问题"));
        adapter.addFragment(setFragmentargument("info", "个人"));
    }

    private ContentFragment setFragmentargument(String key, String value) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 初始化UI
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        cvp = (NoSlidingViewPaper) findViewById(R.id.content_vp);
        mShare = (ImageButton) findViewById(R.id.share);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }
}
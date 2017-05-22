package com.it.one.fragment;

import android.util.Log;

import com.google.gson.Gson;
import com.it.one.adapter.ContentAdapter;
import com.it.one.domain.BaseData;
import com.it.one.global.GlobalContants;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 *
 */

public class HomeFragment extends ContentFragment {
    private Gson gson;
    private BaseData idLIst;

    @Override
    public void initData() {
        getDataFromServer();

    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalContants.PICTURE_SERVER_URl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println("返回结果:" + result);
                parseData(result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                System.out.println("网络异常:" + msg);
            }
        });
    }

    /**
     * 解析网络数据
     *
     * @param result
     */
    private void parseData(String result) {
        Gson gson = new Gson();
        idLIst = gson.fromJson(result, BaseData.class);
        Log.i("解析结果：", idLIst.toString());

//        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getFragmentManager());
//        int size = idLIst.data.size();
//        for (int i = 0; i < size; i++) {
//            String id = idLIst.data.get(i);
//            Bundle bundle = new Bundle();
//            bundle.putString("id", id);
//            HomeContent content = new HomeContent();
//            content.setArguments(bundle);
//            adapter.addFragment(content);
//        }
        ContentAdapter adapter = new ContentAdapter(idLIst.data,getActivity());
        vpInfo.setAdapter(adapter);
        vpInfo.setCurrentItem(0);
    }

}

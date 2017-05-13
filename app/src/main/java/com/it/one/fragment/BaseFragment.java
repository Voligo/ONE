package com.it.one.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

//    public static BaseFragment newInstance(String info) {
//        BaseFragment fragment = new BaseFragment();
//        Bundle args = new Bundle();
//        args.putString("info", info);
//        fragment.setArguments(args);
//        return fragment;
//    }

    /**
     * 依附的activity创建完成
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_base, null);
//        TextView tvInfo = (TextView) view.findViewById(R.id.textView);
//        tvInfo.setText(getArguments().getString("info"));
        return initViews(inflater);
    }
//子类必须实现的初始化布局的方法
    public abstract View initViews(LayoutInflater inflater);
//初始化数据
    public void initData() {
    }


}

package com.haohz.search.loading;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.haohz.search.R;


public class LoadingDialog extends BaseDialogFragment {
    private String content;
    private AnimationDrawable mAnimRefresh;

    public static LoadingDialog newInstance() {
        return newInstance("加载中…");
    }


    public static LoadingDialog newInstance(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public void onStart() {
        setDimAmount(0.0f);
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }

        content = bundle.getString("content");

    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void convertView(DialogViewHolder holder, BaseDialogFragment dialog) {
        TextView loading = holder.getView(R.id.loading_iv);
        loading.setText(content);

    }

    public boolean isShowing() {
        if (getDialog() == null) {
            return false;
        }
        return getDialog().isShowing();
    }
}

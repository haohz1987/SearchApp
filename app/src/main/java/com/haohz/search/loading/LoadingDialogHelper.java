package com.haohz.search.loading;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

public class LoadingDialogHelper {
    private static final long ANIM_DURATION = 200;
    private final Context context;
    private int count = 0;
    private FragmentManager fragmentManager;
    private android.app.FragmentManager fragmentManagerActivity;
    private String content;
    private LoadingDialog loadingDialogFragment;
    private LoadingDialogHelper(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager=fragmentManager;
    }

    private LoadingDialogHelper(Context context, android.app.FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManagerActivity=fragmentManager;
    }

    private LoadingDialogHelper(Context context, String content, FragmentManager fragmentManager) {
        this.context = context;
        this.content = content;
        this.fragmentManager=fragmentManager;
    }

    public static LoadingDialogHelper with(Context context, FragmentManager fragmentManager) {
        return new LoadingDialogHelper(context,fragmentManager);
    }

    public static LoadingDialogHelper with(Context context, android.app.FragmentManager fragmentManager) {
        return new LoadingDialogHelper(context,fragmentManager);
    }

    public static LoadingDialogHelper with(Context context, String content, FragmentManager fragmentManager) {
        return new LoadingDialogHelper(context,content,fragmentManager);
    }

    public void show(String content) {
        loadingDialogFragment = (LoadingDialog) LoadingDialog.newInstance(content).setOutCancel(true).show(fragmentManager);
    }


    public void dismiss() {
        clear();
    }

    public void clear() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismissAllowingStateLoss();
        }
        count = 0;
    }
}

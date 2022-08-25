package com.haohz.search.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.haohz.search.R;

public abstract class BaseDialogFragment extends DialogFragment {
    private static final String MARGIN = "margin";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String CANCEL = "out_cancel";
    private static final String THEME = "theme";
    private static final String ANIM = "anim_style";
    private static final String LAYOUT = "layout_id";


    private int margin;//左右边距
    private int width;//宽度
    private int height;//高度
    private float dimAmount = 0.5f;//灰度深浅
    private int gravity = Gravity.CENTER;//显示的位置
    private boolean outCancel = true;//是否点击外部取消
    @StyleRes
    protected int theme = R.style.basic_core_NiceDialogStyle; // dialog主题
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;

    public abstract int intLayoutId();

    public abstract void convertView(DialogViewHolder holder, BaseDialogFragment dialog);

    public int initTheme() {
        return theme;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, initTheme());

        //恢复保存的数据
        if (savedInstanceState != null) {
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM);
            gravity = savedInstanceState.getInt(GRAVITY);
            outCancel = savedInstanceState.getBoolean(CANCEL);
            theme = savedInstanceState.getInt(THEME);
            animStyle = savedInstanceState.getInt(ANIM);
            layoutId = savedInstanceState.getInt(LAYOUT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutId = intLayoutId();
        View view = inflater.inflate(layoutId, container, false);
        convertView(DialogViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initParams();
    }

    public void setDimAmount(Float dim){
        this.dimAmount = dim;
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(GRAVITY, gravity);
        outState.putBoolean(CANCEL, outCancel);
        outState.putInt(THEME, theme);
        outState.putInt(ANIM, animStyle);
        outState.putInt(LAYOUT, layoutId);
    }

    public void initParams() {
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            //调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;
            if (gravity != 0) {
                lp.gravity = gravity;
            }
            switch (gravity) {
                case Gravity.LEFT:
                case (Gravity.LEFT | Gravity.BOTTOM):
                case (Gravity.LEFT | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.basic_core_LeftAnimation;
                    }
                    break;
                case Gravity.TOP:
                    if (animStyle == 0) {
                        animStyle = R.style.basic_core_TopAnimation;
                    }
                    break;
                case Gravity.RIGHT:
                case (Gravity.RIGHT | Gravity.BOTTOM):
                case (Gravity.RIGHT | Gravity.TOP):
                    if (animStyle == 0) {
                        animStyle = R.style.basic_core_RightAnimation;
                    }
                    break;
                case Gravity.BOTTOM:
                    if (animStyle == 0) {
                        animStyle = R.style.basic_core_BottomAnimation;
                    }
                    break;
                default:
                    break;

            }

            //设置dialog宽度
            if (width == 0) {
                lp.width = getScreenWidth(getContext()) - 2 * dip2px(getContext(), margin);
            } else if (width == WindowManager.LayoutParams.WRAP_CONTENT) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else if (width == WindowManager.LayoutParams.MATCH_PARENT) {
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                lp.width = dip2px(getContext(), width);
            }

            //设置dialog高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else if (height == WindowManager.LayoutParams.WRAP_CONTENT) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else if (height == WindowManager.LayoutParams.MATCH_PARENT) {
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                lp.height = dip2px(getContext(), height);
            }

            //设置dialog进入、退出的动画
            if (animStyle!=0){
                window.setWindowAnimations(animStyle);
            }
            window.setAttributes(lp);
        }
        setCancelable(outCancel);
    }

    public BaseDialogFragment setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public BaseDialogFragment setWidth(int width) {
        this.width = width;
        return this;
    }

    public BaseDialogFragment setHeight(int height) {
        this.height = height;
        return this;
    }

    public BaseDialogFragment setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
        return this;
    }

    public BaseDialogFragment setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BaseDialogFragment setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    public BaseDialogFragment setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



    public BaseDialogFragment show(FragmentManager manager) {
        FragmentTransaction ft = manager.beginTransaction();
        if (this.isAdded()) {
            ft.remove(this).commitAllowingStateLoss();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, r.getDisplayMetrics());
        return (int) px;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BaseDialog(requireContext(), getTheme());
    }


}

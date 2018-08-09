package zyf.asos.maventest.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * description: 遍历目标 Activity 下的 View Tree 设置代理类
 *
 * @author zyfasos
 */
public class ViewTreeProcess {

    /**
     * ViewTreeProcess action
     *
     * @param activity 当前 activity
     */
    public static void process(@NonNull Activity activity) {
        View root = activity.getWindow().getDecorView();
        injectDelegateToView(root);
    }

    /**
     * 遍历当前 Activity 下所有的 view
     * 筛选得到符合条件的 view
     *
     * @param view the root view
     */
    private static void injectDelegateToView(@NonNull View view) {
        if (view instanceof ViewGroup) {
            ViewGroup v = (ViewGroup) view;
            if (v.isClickable()) {
                setViewAccessibilityDelegate(view);
            }
            for (int i = 0; i < v.getChildCount(); i++) {
                View sonView = v.getChildAt(i);
                injectDelegateToView(sonView);
            }
        } else {
            if (view instanceof TextView || view instanceof ImageView) {
                if (view.isClickable()) {
                    setViewAccessibilityDelegate(view);
                }
            }
        }

    }

    /**
     * 设置 Accessibility 代理类
     * @param targetView the target view
     * @see View#setAccessibilityDelegate(View.AccessibilityDelegate)
     * @see View.AccessibilityDelegate#sendAccessibilityEvent(View, int)
     */
    private static void setViewAccessibilityDelegate(@NonNull final View targetView) {

        targetView.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void sendAccessibilityEvent(View host, int eventType) {
                super.sendAccessibilityEvent(host, eventType);
                if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {

                }
            }
        });
    }


}

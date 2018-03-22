package common.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.common.R;

import java.util.Collection;


/**
 * Created by cxw on 2016/4/22.
 */
public class DialogUtils {
    private static MaterialDialog materialDialog;

    public static abstract class OnButtonClickListener {
        public abstract void onConfirmButtonClick();

        public void onCancelButtonClick() {

        }
    }

    public static abstract class OnItemClickListener {
        public abstract void onSelection(int which, CharSequence text);
    }

    public static MaterialDialog showSingleButtonDialog(Context context, String message, String positiveText) {
        return showCustomMessageDialog(context, message, null, positiveText, null);
    }

    public static MaterialDialog showSingleButtonDialog(Context context, String message, String positiveText, OnButtonClickListener onButtonClickListener) {
        return showSingleButtonDialog(context, message, positiveText, true, onButtonClickListener);
    }

    public static MaterialDialog showSingleButtonDialog(Context context, String message, String positiveText, boolean cancelabl, OnButtonClickListener onButtonClickListener) {
        return showSingleButtonDialog(context, null, message, positiveText, cancelabl, onButtonClickListener);
    }

    public static MaterialDialog showSingleButtonDialog(Context context, String title, String message, String positiveText, boolean cancelabl, OnButtonClickListener onButtonClickListener) {
        return showCustomMessageDialog(context, title, message, null, positiveText, cancelabl, onButtonClickListener);
    }

    public static MaterialDialog showNormalDialog(Context context, String message, final OnButtonClickListener onButtonClickListener) {
        return showCustomMessageDialog(context, message, "取消", "确定", onButtonClickListener);
    }

    public static MaterialDialog showCustomMessageDialog(Context context, String message, String negativeText, String positiveText, final OnButtonClickListener onButtonClickListener) {
        return showCustomMessageDialog(context, "提示", message, negativeText, positiveText, true, onButtonClickListener);

    }


    public static MaterialDialog showCustomMessageDialog(Context context, String title, String message, String negativeText, String positiveText, boolean cancelable, final OnButtonClickListener onButtonClickListener) {
        materialDialog = new MaterialDialog.Builder(context)

                .title(title)
                .content(message)
                .cancelable(cancelable)
                .negativeText(negativeText)
                .positiveText(positiveText)
                .negativeColor(context.getResources().getColor(R.color.hei_7f))
                .positiveColor(context.getResources().getColor(R.color.main_app))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onButtonClickListener != null) {
                            onButtonClickListener.onCancelButtonClick();
                        }
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onButtonClickListener != null) {
                            onButtonClickListener.onConfirmButtonClick();
                        }
                    }
                }).build();
        try {
            materialDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialDialog;
    }


    public static MaterialDialog showCustomMessageDialog(Context context, String title, String message, String negativeText,
                                                         String positiveText, final OnButtonClickListener onButtonClickListener) {
        materialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .negativeText(negativeText).negativeColor(context.getResources().getColor(R.color.hei_7f))
                .positiveText(positiveText).positiveColor(context.getResources().getColor(R.color.main_app))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onButtonClickListener != null) {
                            onButtonClickListener.onCancelButtonClick();
                        }
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (onButtonClickListener != null) {
                            onButtonClickListener.onConfirmButtonClick();
                        }
                    }
                }).build();
        try {
            materialDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialDialog;
    }

    public static MaterialDialog showSingleDialog(Context context, String title, Collection<String> sList, final OnItemClickListener onItemClickListener) {
        materialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .items(sList)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        onItemClickListener.onSelection(which, text);
                    }
                }).build();
        try {
            materialDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materialDialog;
    }


    public static boolean isShowing() {
        return materialDialog != null && materialDialog.isShowing();
    }

    public static void closeDialog() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
    }

    public static void showEditTextDialog(Context context,String title,String content,String inputHint,MaterialDialog.InputCallback inputCallback) {
        materialDialog=new MaterialDialog.Builder(context)
                .backgroundColor(Color.WHITE)
                .content(content)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(inputHint,"",inputCallback)
                .negativeText("取消")
                .positiveText("确定")
                .negativeColor(context.getResources().getColor(R.color.hui_6))
                .positiveColor(context.getResources().getColor(R.color.main_app))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .widgetColor(Color.parseColor("#d5d5d5"))
                .autoDismiss(false)
                .show();
    }

}

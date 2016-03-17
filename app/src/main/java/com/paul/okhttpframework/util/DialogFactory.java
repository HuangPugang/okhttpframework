package com.paul.okhttpframework.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


public class DialogFactory {

    private static DialogFactory instancts;

    private String defaultHour = "00";
    private String defaultMinute = "00";

    private DialogFactory() {

    }

    public static DialogFactory getInstancts() {
        if (instancts == null) {
            instancts = new DialogFactory();
        }
        return instancts;
    }

//    /**
//     * @param context
//     * @param position
//     * @param listener
//     * @param hourTime 09:00
//     * @return
//     */
//    public Dialog createTimeDialog(Activity context, final int position, final OnFinishClickListener listener, String hourTime) {
//        final boolean isStart = position % 2 == 0 ? true : false;
//        String[] hours;
//        String[] hours1 = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
//        String[] hours2 = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
//        String[] minutes = new String[]{"00", "15", "30", "45"};
//        if (isStart) {
//            hours = hours1;
//        } else {
//            hours = hours2;
//        }
//        defaultHour = "00";
//        defaultMinute = "00";
//        int hourIndex = 0;//选中时的位置
//        int minuteIndex = 0;//选中分的位置
//        if (hourTime != null && hourTime.length() == 5) {
//            defaultHour = hourTime.substring(0, 2);
//            defaultMinute = hourTime.substring(3, 5);
//        }
//
//        final Dialog dialog;
//
//        //设置选中的时
//        for (int i = 0; i < hours.length; i++) {
//            if (defaultHour.equals(hours[i])) {
//                hourIndex = i;
//                break;
//            }
//        }
//        //设置选中的分
//        for (int i = 0; i < minutes.length; i++) {
//            if (defaultMinute.equals(minutes[i])) {
//                minuteIndex = i;
//                break;
//            }
//        }
//        dialog = new Dialog(context, R.style.ActionSheet);
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.dialog_time, null);
//
//        TextView tvFinish = (TextView) rootView.findViewById(R.id.tv_finish);
//        tvFinish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String time = defaultHour + ":" + defaultMinute;
//                listener.onFinishListener(position, time, dialog);
//                dialog.dismiss();
//            }
//        });
//        WheelView wvHour = (WheelView) rootView.findViewById(R.id.wv_hour);
//        wvHour.setSeletion(hourIndex);
//        final WheelView wvMinute = (WheelView) rootView.findViewById(R.id.wv_minute);
//        wvHour.setOffset(2);
//        wvHour.setItems(Arrays.asList(hours));
//        wvHour.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//            @Override
//            public void onSelected(int selectedIndex, String item) {
//                defaultHour = item;
//                if (defaultHour.equals("24")) {
//                    wvMinute.setSeletion(0);
//                    defaultMinute = "00";
//                }
//            }
//        });
//        wvMinute.setOffset(2);
//        wvMinute.setSeletion(minuteIndex);
//        wvMinute.setItems(Arrays.asList(minutes));
//        wvMinute.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
//            @Override
//            public void onSelected(int selectedIndex, String item) {
//                if (defaultHour.equals("24")) {
//                    wvMinute.setSeletion(0);
//                    defaultMinute = "00";
//                    return;
//                }
//                if (!isStart) {
//                    if (defaultHour.equals("00") && item.equals("00")) {
//                        defaultMinute = item = "15";
//                        wvMinute.setSeletion(1);
//                    } else {
//                        defaultMinute = item;
//                    }
//                } else {
//                    defaultMinute = item;
//                }
//            }
//        });
//
//        if (!isStart) {
//            if (defaultHour.equals("00") && defaultMinute.equals("00")) {
//                wvMinute.setSeletion(1);
//                defaultMinute = "15";
//            }
//        }
//
//        final int cFullFillWidth = 10000;
//        rootView.setMinimumWidth(cFullFillWidth);
//
//        Window w = dialog.getWindow();
//        WindowManager.LayoutParams lp = w.getAttributes();
//        lp.x = 0;
//        final int cMakeBottom = -1000;
//        lp.y = cMakeBottom;
//        lp.gravity = Gravity.BOTTOM;
//        dialog.onWindowAttributesChanged(lp);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(rootView);
//        return dialog;
//
//    }
//
//    /**
//     * @param context
//     * @param strs
//     * @param listener
//     * @return
//     */
//    public Dialog createNormalDialog(Activity context, final String[] strs, final OnFinishClickListener listener) {
//        final Dialog dialog;
//        dialog = new Dialog(context, R.style.ActionSheet);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.dialog_normal, null);
//        LinearLayout llContent = (LinearLayout) rootView.findViewById(R.id.ll_content);
//        TextView tvCancel = (TextView) rootView.findViewById(R.id.tv_cancel);
//        llContent.removeAllViews();
//        for (int i = 0; i < strs.length; i++) {
//            View view = inflater.inflate(R.layout.item_dialog, null);
//            final TextView textView = (TextView) view.findViewById(R.id.tv_content);
//            View viewLine = view.findViewById(R.id.view);
//            View iv = view.findViewById(R.id.iv_suggest);
//            if (strs[i].equals("自动开关店")) {
//                iv.setVisibility(View.VISIBLE);
//            } else {
//                iv.setVisibility(View.GONE);
//            }
//            textView.setText(strs[i]);
//            view.setOnClickListener(new ItemClikListener(i, textView.getText().toString(), dialog, listener));
//            if (i == strs.length - 1) {
//                viewLine.setVisibility(View.GONE);
//            }
//            llContent.addView(view);
//        }
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        final int cFullFillWidth = 10000;
//        rootView.setMinimumWidth(cFullFillWidth);
//
//        Window w = dialog.getWindow();
//        WindowManager.LayoutParams lp = w.getAttributes();
//        lp.x = 0;
//        final int cMakeBottom = -1000;
//        lp.y = cMakeBottom;
//        lp.gravity = Gravity.BOTTOM;
//        dialog.onWindowAttributesChanged(lp);
//        dialog.setCanceledOnTouchOutside(true);
//
//        dialog.setContentView(rootView);
//        return dialog;
//
//    }
//
//    class ItemClikListener implements View.OnClickListener {
//        private int position;
//        private String text;
//        private Dialog dialog;
//        private OnFinishClickListener listener;
//
//        public ItemClikListener(int position, String text, Dialog dialog, OnFinishClickListener listener) {
//            this.position = position;
//            this.text = text;
//            this.dialog = dialog;
//            this.listener = listener;
//        }
//
//        @Override
//        public void onClick(View v) {
//            dialog.dismiss();
//            listener.onFinishListener(position, text.toString(), dialog);
//        }
//    }
//
//    public interface OnFinishClickListener {
//        public void onFinishListener(int position, String strs, Dialog dialog);
//    }
//
//
//    private int dp2px(int dp, Context mContext) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                dp, mContext.getResources().getDisplayMetrics());
//    }

    public static void showAlertDialog(Context context, String title, String tips, DialogInterface.OnClickListener positiveClick,DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        AlertDialog dialog ;
        dialog = builder.setMessage(tips)
                .setPositiveButton("确定", positiveClick)
                .setNegativeButton("取消", negativeClick)
                .create();
        //获取按钮对象
        dialog.show();

    }

//
//    public static void showPicDialog(Context context,String url){
//        Dialog dialog = new Dialog(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.view_full_dialog,null);
//        ImageView imageView = (ImageView) view.findViewById(R.id.iv_big_image);
//        ImageUtil.displayImage(url,imageView);
//        dialog.setContentView(view);
//        dialog.show();
//    }


    /**
     * dialog点击回调
     */
    public interface OnDialogClickListener{
        void confirm();
        void cancel();
    }
}

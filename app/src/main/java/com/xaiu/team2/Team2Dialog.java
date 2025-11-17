package com.xaiu.team2;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

public class Team2Dialog {
    public static void showConfirmDialog(Context context, String title, String message,
                                         View.OnClickListener confirmListener,
                                         View.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = View.inflate(context, R.layout.dialog_layout, null);
        builder.setView(dialogView);

        // 获取布局中的控件
        TextView tvTitle = dialogView.findViewById(R.id.tv_dialog_title);
        TextView tvMessage = dialogView.findViewById(R.id.tv_dialog_message);
        TextView tvConfirm = dialogView.findViewById(R.id.tv_dialog_confirm);
        TextView tvCancel = dialogView.findViewById(R.id.tv_dialog_cancel);

        // 设置文本内容
        if (title != null) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE); // 标题为null时隐藏
        }
        tvMessage.setText(message);

        int orangeColor = Color.parseColor("#ffa500");
        tvTitle.setTextColor(orangeColor);
        // tvMessage.setTextColor(orangeColor);
        tvConfirm.setTextColor(orangeColor);
        // tvCancel.setTextColor(orangeColor);

        // 创建弹窗实例
        // 创建弹窗实例
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false); // 点击外部不关闭弹窗

        // 确定按钮
        tvConfirm.setOnClickListener(v -> {
            if (confirmListener != null) {
                confirmListener.onClick(v);
            }
            dialog.dismiss();
        });

        // 取消按钮
        tvCancel.setOnClickListener(v -> {
            if (cancelListener != null) {
                cancelListener.onClick(v);
            } else {
                dialog.dismiss();
            }
        });

        // 显示弹窗
        dialog.show();
    }
}

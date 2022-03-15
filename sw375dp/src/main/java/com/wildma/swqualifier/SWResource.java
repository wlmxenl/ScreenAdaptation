package com.wildma.swqualifier;

import android.content.Context;

/**
 * 最小宽度动态转换
 *
 * @author wangzf
 * @date 2022/3/15
 */
public class SWResource {

    public static float dp2px(Context context, float dpValue) {
        return context.getResources().getDimension(R.dimen.dp_1) * dpValue;
    }

    public static float sp2px(Context context, float spValue) {
        return context.getResources().getDimension(R.dimen.sp_6) / 6f * spValue;
    }
}

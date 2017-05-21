package com.icheung.lyrik.util;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    public static void showError(Context context, int errorResId) {
        Toast.makeText(context, errorResId, Toast.LENGTH_SHORT).show();
    }
}

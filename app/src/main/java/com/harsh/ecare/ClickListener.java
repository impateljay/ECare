package com.harsh.ecare;

import android.view.View;

/**
 * Created by HarsH on 05-03-2017.
 */

public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
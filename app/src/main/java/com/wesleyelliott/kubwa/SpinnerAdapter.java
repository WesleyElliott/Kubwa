package com.wesleyelliott.kubwa;

import android.content.Context;

/**
 * Created by wesley on 2016/07/05.
 */

public class SpinnerAdapter extends HintAdapter<String> {

    public SpinnerAdapter(Context context) {
        super(context);
    }

    @Override
    String getHint() {
        return getContext().getString(R.string.spinner_hint);
    }

    @Override
    String getHintObject() {
        return getHint();
    }

    @Override
    String getValue(int position) {
        return getItem(position);
    }
}

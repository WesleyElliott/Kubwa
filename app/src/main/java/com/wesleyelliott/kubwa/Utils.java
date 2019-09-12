package com.wesleyelliott.kubwa;

import androidx.databinding.BindingAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by wesley on 2016/08/02.
 */

public class Utils {

    @BindingAdapter({"selectionChange"})
    public static void setSelection(Spinner spinner, Spinner.OnItemSelectedListener onItemSelectedListener) {
        spinner.setOnItemSelectedListener(onItemSelectedListener);
    }

    @BindingAdapter({"spinnerError"})
    public static void setSpinnerError(Spinner spinner, String error) {
        if (spinner.getSelectedView() != null)
            ((TextView) spinner.getSelectedView()).setError(error);
    }
}

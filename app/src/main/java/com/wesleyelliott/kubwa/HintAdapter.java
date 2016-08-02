package com.wesleyelliott.kubwa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;

/**
 * Created by wesley on 2016/07/05.
 */

public abstract class HintAdapter<T> extends ArrayAdapter<T> {

    private T hint;
    private LayoutInflater layoutInflater;

    abstract String getHint();
    abstract T getHintObject();
    abstract String getValue(int position);

    public HintAdapter(Context context) {
        super(context, R.layout.layout_spinner);
        hint = getHintObject();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void add(T object) {
        remove(hint);
        insert(hint, 0);
        super.add(object);
    }

    @Override
    public void addAll(Collection<? extends T> collection) {
        remove(hint);
        insert(hint, 0);
        super.addAll(collection);
    }

    @Override
    public void addAll(T... items) {
        remove(hint);
        insert(hint, 0);
        super.addAll(items);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public void clear() {
        super.clear();
        insert(hint, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_spinner, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            viewHolder.textView.setText("");

            viewHolder.textView.setHint(getHint());

        } else {
            viewHolder.textView.setText(getValue(position));
        }

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    private class ViewHolder {
        TextView textView;
    }
}

package com.example.gamematrix;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by tuand on 5/8/2018.
 */

public class GameAdapter extends BaseAdapter {

    Context context;
    List<Integer> numberList;
    int height;


    public GameAdapter(Context context, List<Integer> list, int height) {
        this.context = context;
        this.numberList = list;
        this.height = height;
    }

    @Override
    public int getCount() {
        return numberList.size();
    }

    @Override
    public Object getItem(int i) {
        return numberList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View item = inflater.inflate(R.layout.gridview_item_layout, null);

        ImageView imageView = (ImageView) item.findViewById(R.id.imageView);
        if (numberList.get(i) == 1) {
            imageView.setBackgroundColor(Color.GREEN);
        }
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = height;
        imageView.setLayoutParams(params);
        return item;

    }

    public void setItem(int i) {
        if (numberList.get(i) == 0)
            numberList.set(i, 1);
        else
            numberList.set(i, 0);
    }

    public void resetItem() {
        for (int i = 0; i < numberList.size(); i++) {
            numberList.set(i, 0);
        }
    }
}

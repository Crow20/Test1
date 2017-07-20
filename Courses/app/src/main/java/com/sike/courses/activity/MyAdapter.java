package com.sike.courses.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sike.courses.R;
import com.sike.courses.mvp.model.Course;

import java.util.ArrayList;

/**
 * Created by agritsenko on 04.07.2017.
 */

public class MyAdapter extends BaseAdapter{

    Context ctx;
    LayoutInflater Inflater;
    ArrayList<Course> objects;
    protected static int paddingDp;
    protected static int paddingPixel = 80;
    protected static float density;
    protected static int green;
    protected static int red;


    MyAdapter(Context context, ArrayList<Course> course){
        ctx=context;
        objects = course;
        Inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        density = context.getResources().getDisplayMetrics().density;
        paddingDp = (int)(paddingPixel*density);
        green = context.getResources().getColor(R.color.Green);
        red = context.getResources().getColor(R.color.Red);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    public Course getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){
            view = Inflater.inflate(R.layout.item, parent, false);
        }

        Course course = getItem(position);


        if(course.isSubnameNull(course)){
            ((TextView) view.findViewById(R.id.col1)).setText(course.getName());
            ((TextView) view.findViewById(R.id.col3)).setText(course.getDate());
            ((TextView) view.findViewById(R.id.col4)).setText(course.getValue1());
            if(course.getColor().equals("red")){
                ((TextView) view.findViewById(R.id.col5)).setTextColor(red);
                ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
            }
            else if(course.getColor().equals("green")){
                ((TextView) view.findViewById(R.id.col5)).setTextColor(green);
                ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
            }else if(course.getColor().equals(""))
            ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
        }else{
            if(course.getName().equals("USD")) {
                ((TextView) view.findViewById(R.id.col1)).setText(course.getSubname());
            }
            ((TextView) view.findViewById(R.id.col2)).setText(course.getName());
            ((TextView) view.findViewById(R.id.col3)).setText(course.getDate());
            ((TextView) view.findViewById(R.id.col4)).setText(course.getValue1());
            if(course.getColor().equals("red")&&!(course.getSubname().equals("Нал.")&&!(course.getColor().equals("")))){
                ((TextView) view.findViewById(R.id.col5)).setTextColor(red);
                ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
            }
            else if(course.getColor().equals("green")&&!(course.getSubname().equals("Нал."))&&!(course.getColor().equals(""))){
                ((TextView) view.findViewById(R.id.col5)).setTextColor(green);
                ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
            }else if(course.getColor().equals("")){
                ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
            }
            else if(course.getSubname().equals("Нал.")){
                ((TextView) view.findViewById(R.id.col5)).setText(course.getValue2());
            }
        }
        if(course.getName().equals("EUR/USD")){
            view.setPadding(0, 0, 0, paddingDp);
        }

        return view;
    }
}

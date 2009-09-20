package com.se.browser;

import java.lang.reflect.Field;

import com.se.browser.R;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconListActivity extends ListActivity {
  
  class IconAdapter extends ArrayAdapter<Field> {

    class ViewHolder {
      public ImageView iconImage;
      public TextView iconName;
    }

    public IconAdapter(Context context) {
      super(context, R.layout.icon_list_item);
      populateList();
    }
    
    private void populateList() {
      for(Field field : android.R.drawable.class.getDeclaredFields()) {
        if(field.getName().startsWith("ic_")) {
          add(field);
        }
      }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;

      if(null == convertView) {
        convertView = inflater().inflate(R.layout.icon_list_item, null, false);
        holder = new ViewHolder();
        holder.iconImage = (ImageView)convertView.findViewById(R.id.iconImage);
        holder.iconName =   (TextView)convertView.findViewById(R.id.iconName);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      Field field = getItem(position);
      try {
        holder.iconImage.setImageDrawable(
            getContext().getResources().getDrawable(field.getInt(android.R.drawable.class))
        );
        holder.iconImage.setEnabled(true);
      } catch (Exception e) {
        holder.iconImage.setEnabled(false);
      }
      holder.iconName.setText(field.getName());

      return convertView;
    }

  }

  LayoutInflater mInflater;
  private LayoutInflater inflater() {
    if(null == mInflater) {
      mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    return mInflater;
  }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icon_list_activity);
        setListAdapter(new IconAdapter(this));
    }
}
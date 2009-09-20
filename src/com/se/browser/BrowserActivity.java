package com.se.browser;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BrowserActivity extends ListActivity {
  
  class ActivityAdapter extends ArrayAdapter<Class> {

    class ViewHolder {
      public TextView activityName;
    }

    public ActivityAdapter(Context context) {
      super(context, R.layout.activity_list_item);
      populateList();
    }
    
    private void populateList() {
      add(IconListActivity.class);
      add(SensorListActivity.class);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;

      if(null == convertView) {
        convertView = inflater().inflate(R.layout.activity_list_item, null, false);
        holder = new ViewHolder();
        holder.activityName =  (TextView)convertView.findViewById(R.id.activityName);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      Class item = getItem(position);
      holder.activityName.setText(item.getName());

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
    setContentView(R.layout.browser_activity);
    setListAdapter(new ActivityAdapter(this));
  }
}
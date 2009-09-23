package com.se.browser;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BrowserActivity extends ListActivity {
  
  class ActivityAdapter extends ArrayAdapter<Intent> {

    class ViewHolder {
      public TextView activityName;
    }

    public ActivityAdapter(Context context) {
      super(context, R.layout.activity_list_item);
      populateList();
    }
    
    private void populateList() {
      add(new Intent(getContext(), IconListActivity.class));
      add(new Intent(getContext(), SensorListActivity.class));
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

      Intent item = getItem(position);
      try {
        ActivityInfo info = getPackageManager().getActivityInfo(item.getComponent(), PackageManager.GET_META_DATA);
        holder.activityName.setText(info.labelRes);
      } catch (NameNotFoundException e) {
        holder.activityName.setText(item.getComponent().getClassName());
      }

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
  
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    this.startActivity((Intent)getListAdapter().getItem(position));
  }
}
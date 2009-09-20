package com.se.browser;

import java.util.Comparator;

import android.app.ListActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SensorListActivity extends ListActivity {

  class SensorAdapter extends ArrayAdapter<Sensor> {

    class ViewHolder {
      public TextView sensorName;
      public TextView sensorVendor;
      public TextView sensorClass;
    }

    public SensorAdapter(Context context) {
      super(context, R.layout.sensor_list_item);
      populateList();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;

      if(null == convertView) {
        convertView = inflater().inflate(R.layout.sensor_list_item, null, false);
        holder = new ViewHolder();
        holder.sensorName = (TextView)convertView.findViewById(R.id.sensorName);
        holder.sensorVendor = (TextView)convertView.findViewById(R.id.sensorVendor);
        holder.sensorClass = (TextView)convertView.findViewById(R.id.sensorClass);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }

      Sensor sensor = getItem(position);
      holder.sensorName.setText(sensor.getName());
      holder.sensorVendor.setText(sensor.getVendor());
      holder.sensorClass.setText(sensor.getClass().getName());

      return convertView;
    }

    private void populateList() {
      SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
      Object[] sensors = manager.getSensorList(Sensor.TYPE_ALL).toArray();

      for(int ii=sensors.length - 1 ; ii >= 0 ; ii--) {
        add((Sensor)sensors[ii]);
      }

      sort(getSensorComparator());
    }

    private Comparator<Sensor> mSensorComparator;
    private Comparator<Sensor> getSensorComparator() {
      if(null == mSensorComparator) {
        mSensorComparator = new Comparator<Sensor>() {
          public int compare(Sensor o1, Sensor o2) {
            return o1.getName().compareTo(o2.getName());
          }
        };
      }
      return mSensorComparator;
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
        setContentView(R.layout.sensor_list_activity);

        setListAdapter(new SensorAdapter(this));
    }
}

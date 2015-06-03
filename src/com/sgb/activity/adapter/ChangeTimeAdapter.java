package com.sgb.activity.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.sgb.activity.R;
import com.sgb.clock.InitClock;
import com.sgb.util.ToDoDB;

import android.app.TimePickerDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

public class ChangeTimeAdapter extends BaseAdapter {

	HashMap<Integer, ViewHolder> holders;
	private Context mContext;
	private ArrayList<HashMap<Integer, String>> list = null;

	public ChangeTimeAdapter(Context mContext, ArrayList<HashMap<Integer, String>> list) {
		this.mContext = mContext;
		this.list = list;
		holders = new HashMap<Integer, ChangeTimeAdapter.ViewHolder>();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.change_time_item, null);
			viewHolder.tvStartTime = (TextView) view.findViewById(R.id.my_starttime);
			viewHolder.tvEndTime = (TextView) view.findViewById(R.id.my_endtime);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvStartTime.setText(this.list.get(position).get(0));
		viewHolder.tvEndTime.setText(this.list.get(position).get(1));
		viewHolder.tvStartTime.setTag(position);
		viewHolder.tvEndTime.setTag(position);
		ViewHolder holder = holders.get(position);

		TextView myTextView = (TextView) view.findViewById(R.id.my_list_catalog1);
		String _text = "";
		switch (position + 1) {
		case 0:
			break;
		case 1:
			_text = "1,2节";
			break;
		case 2:
			_text = "3,4节";
			break;
		case 3:
			_text = "5,6节";
			break;
		case 4:
			_text = "7,8节";
			break;
		case 5:
			_text = "9,10节";
			break;
		case 6:
			_text = "选修";
			break;
		default:
			break;
		}
		myTextView.setText(_text);
		if (holder != null) {
			holders.remove(position);
		}
		holders.put(position, viewHolder);
		
		//点击事件
		viewHolder.tvStartTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				final Calendar mCalendar = Calendar.getInstance();
				
				String time = holders.get(position).tvStartTime.getText().toString();
				int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
				int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));
				
				new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
						mCalendar.set(Calendar.MINUTE, minute);
						mCalendar.set(Calendar.SECOND, 0);
						mCalendar.set(Calendar.MILLISECOND, 0);

						if (true) {
							StringBuffer sb = new StringBuffer();
							int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
							int munite = mCalendar.get(Calendar.MINUTE);

							sb.append((hour > 9) ? hour : ("0" + hour));
							sb.append(":").append((munite > 9) ? munite : ("0" + munite));
							holders.get(position).tvStartTime.setText(sb);
							int id = -1;
							if (position == 5) {
								id = 26;
							} else {
								id = position + 1;
							}
							new ToDoDB(mContext).updateStartTime(id, sb.toString());
							list.get(position).put(0, sb.toString());
							notifyDataSetChanged();
							new InitClock().initClock(mContext);
						}
					}
				}, hour, minute, true).show();
//				mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show();
			}
		});
		
		viewHolder.tvEndTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String time = holders.get(position).tvEndTime.getText().toString();
				int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));
				int minute = Integer.parseInt(time.substring(time.indexOf(":") + 1, time.length()));

				final Calendar mCalendar = Calendar.getInstance();
				new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
						mCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
						mCalendar.set(Calendar.MINUTE, minute);
						mCalendar.set(Calendar.SECOND, 0);
						mCalendar.set(Calendar.MILLISECOND, 0);

						if (true) {
							StringBuffer sb = new StringBuffer();
							int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
							int munite = mCalendar.get(Calendar.MINUTE);

							sb.append((hour > 9) ? hour : ("0" + hour));
							sb.append(":").append((munite > 9) ? munite : ("0" + munite));
							holders.get(position).tvEndTime.setText(sb);
							int id = -1;
							if (position == 5) {
								id = 26;
							} else {
								id = position + 1;
							}

							new ToDoDB(mContext).updateEndTime(id,sb.toString());
							list.get(position).put(1, sb.toString());
							notifyDataSetChanged();
							new InitClock().initClock(mContext);
						}
					}
				}, hour, minute, true).show();
//				mCalendar.get(Calendar.HOUR), mCalendar.get(Calendar.MINUTE), true).show();
			}
		});
		
		return view;
	}

	final static class ViewHolder {
		TextView tvStartTime;
		TextView tvEndTime;
	}
}

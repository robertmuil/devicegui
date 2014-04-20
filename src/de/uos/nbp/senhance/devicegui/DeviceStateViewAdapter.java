package de.uos.nbp.senhance.devicegui;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import de.uos.nbp.senhance.datasource.DataSource;
import de.uos.nbp.senhance.datasource.DataSource.DeviceState;

/**
 * 
 * @author rmuil December 20, 2011
 */
public class DeviceStateViewAdapter extends ArrayAdapter<DataSource> {
	private Context mContext;
	private List<DataSource> mList;
	
	public DeviceStateViewAdapter(Context c, List<DataSource> list) {
		super(c, 0, list); //TODO: will this work or need we define the resource id?
		this.mContext = c;
		this.mList = list;
	}

	/**
	 */
	@Override
	public int getCount() {
		return mList.size();
	}

	/**
	 */
	@Override
	public DataSource getItem(int position) {
		return mList.get(position);
	}
	
	/**
	 * An improvement for efficiency, taken from:
	 * {@link http://blog.blackmoonit.com/2010/12/findviewbyid-slow-inside-listview.html}
	 * 
	 * @param aParentView
	 * @param aViewToFind
	 * @return viewHandle
	 */
	protected Object getViewHandle(View aParentView, int aViewToFind) {
		Object v = aParentView.getTag(aViewToFind);
		if (v == null) {
			v = aParentView.findViewById(aViewToFind);
			aParentView.setTag(aViewToFind, v);
		}
		return v;
	}

	/**
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View deviceStateView;
		if (convertView == null) {
			/* if not recycled, initialize some attributes */
			LayoutInflater vi = (LayoutInflater)mContext.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			deviceStateView = vi.inflate(R.layout.device_gui_single_device,
					parent, false);
			
		} else {
			deviceStateView = convertView;
		}

		DataSource src = mList.get(position);
		DeviceState state = src.getSourceDeviceState();
		
		TextView stateTxt = (TextView) getViewHandle(deviceStateView,
				R.id.deviceStateText);
		stateTxt.setText(state.toString());
		
		ImageView icon = (ImageView) getViewHandle(deviceStateView,
				R.id.deviceStateIcon);
		
		/* TODO: use animation for this? */
		int resid = getDeviceStateIconResID(state);
		icon.setImageResource(resid);
		
		ImageView heartOutAttached = (ImageView) getViewHandle(deviceStateView,
				R.id.heartOutAttachedIcon);
		
		if (src.isSinkAttached()) {
			heartOutAttached.setVisibility(View.VISIBLE);
		} else {
			heartOutAttached.setVisibility(View.GONE);
		}
		
		TextView type = (TextView) getViewHandle(deviceStateView,
				R.id.deviceTitle);
		type.setText(src.getTypeString());
		TextView idTxt = (TextView) getViewHandle(deviceStateView,
				R.id.deviceIDText);
		idTxt.setText(src.getDeviceIDString());
		
		TextView valTxt = (TextView) getViewHandle(deviceStateView,
				R.id.deviceCurrentValue);
		valTxt.setText(Integer.toString((int)src.getCurrent()));
		
		return deviceStateView;
	}


	DeviceState getDeviceState(int position) {
		return mList.get(position).getSourceDeviceState();
	}
	
	int getDeviceStateIconResID(DeviceState state) {
		int id = 0; /* TODO: this should be something guaranteed invalid */
		switch (state) {
		case Disconnected:
			id = R.drawable.ic_devicestate_disconnected;
			break;
		case Connecting:
			id = R.drawable.ic_devicestate_connecting;
			break;
		case Connected:
			id = R.drawable.ic_devicestate_connected;
			break;
		case Ready:
			id = R.drawable.ic_devicestate_ready;
			break;
		case Transmitting:
			id = R.drawable.ic_devicestate_transmitting;
			break;
		}
		return id;
	}
}

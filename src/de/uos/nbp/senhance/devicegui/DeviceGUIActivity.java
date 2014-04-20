package de.uos.nbp.senhance.devicegui;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import de.uos.nbp.senhance.datasource.DataSource;
import de.uos.nbp.senhance.datasource.DataSource.DeviceState;

/**
 *
 * @author rmuil
 * December 22, 2011
 */
public abstract class DeviceGUIActivity extends Activity {
	static final String TAG = "DeviceGUIActivity";
	static final boolean D = true;
	
	//private static final int UpdateDelay = 500;

	protected DeviceStateViewAdapter mDeviceListAdapter;
	
	//Handler mTimerHandler = new Handler();

	/**
	 * 
	 */
	public DeviceGUIActivity() {
		super();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	
	}
	
	/**
	 * Expects device list to be initialized.
	 */
	public void initDeviceListView (int listViewResID, ArrayList<DataSource> deviceList) {
		ListView deviceListView = (ListView) findViewById(listViewResID);
		
		mDeviceListAdapter = new DeviceStateViewAdapter(this, deviceList);
		deviceListView.setAdapter(mDeviceListAdapter);
	
		registerForContextMenu(deviceListView);
	
		deviceListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Log.v(TAG, "Item clicked " + position);
			}
		});
	}

	/**
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.device_control_menu, menu);
		int pos = ((AdapterContextMenuInfo)menuInfo).position;
		
		DataSource src = mDeviceListAdapter.getItem(pos);
		DeviceState state = src.getSourceDeviceState();
		
		menu.setGroupEnabled(R.id.device_control_connected_group,
				(state != DeviceState.Disconnected));
		menu.setGroupEnabled(R.id.device_control_disconnected_group,
				(state == DeviceState.Disconnected));
	}

	/**
	 * This function must initiate a connection to a data source, which
	 * may entail first determining an address by, for example,
	 * prompting the user.
	 * 
	 * @param src - the DataSource for which to initiate connection.
	 * @return True if a <em>delayed</em> connection process has been initiated. 
	 *         False if connection already present or immediately conducted.
	 */
	protected abstract boolean initiateDataSourceConnection (DataSource src);
	
	public void refreshDeviceGUI() {
		mDeviceListAdapter.notifyDataSetChanged();
	}
	
	private int mParentContextMenuListIndex;
	
	/**
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		boolean handled = false;
		AdapterContextMenuInfo info;
		try {
			info = (AdapterContextMenuInfo) item.getMenuInfo();
		} catch (ClassCastException e) {
			Log.e(TAG, "bad menuInfo", e);
			return false;
		}
		/*
		 * a null info means we have a sub-menu so use the saved info.position
		 * taken from
		 * http://blog.blackmoonit.com/2010/09/using-submenu-in-listactivitys-context.html
		 */
	    int idxOfList = (info!=null) ? info.position : 
	    	this.mParentContextMenuListIndex;
		
		StringBuilder sb = new StringBuilder("Got context menu item: ");
		sb.append(item.getTitle());
		sb.append(" for item ");
		sb.append(idxOfList);
		if (info != null) {
			sb.append( " (at rowid ").append(info.id).append(")");
		}
		
		if (D) Log.v(TAG,  sb.toString());
		
		DataSource src = mDeviceListAdapter.getItem(idxOfList);
		
		if (item.getItemId() == R.id.device_control_connect) {
			initiateDataSourceConnection(src);
			handled = true;
		} else if (item.getItemId() == R.id.device_control_predisconnect) {

		} else if (item.getItemId() == R.id.device_control_disconnect) {
			src.disconnect();
			handled = true;
		} else if (item.getItemId() == R.id.device_control_start) {
			src.startEvents();
		} else if (item.getItemId() == R.id.device_control_stop) {
			src.stopEvents();
		} else {
			handled = super.onContextItemSelected(item);
		}
		
		if (item.hasSubMenu()) {
			/* handle sub-menus */
			this.mParentContextMenuListIndex = idxOfList;
		}
		
		return handled;
	}

	/**
	 */
	@Override
	protected void onResume() {
		//mTimerHandler.removeCallbacks(mDeviceStatesUpdater);
		//mTimerHandler.postDelayed(mDeviceStatesUpdater, UpdateDelay);
		
		super.onResume();
	}

	/**
	 */
	@Override
	protected void onPause() {
		super.onPause();
		//mTimerHandler.removeCallbacks(mDeviceStatesUpdater);
	}
	
//	Runnable mDeviceStatesUpdater = new Runnable () {
//		public void run() {
//			
//			mDeviceListAdapter.notifyDataSetChanged();
//
//			Log.v(TAG, "updating device states... ");		
//			
//			mTimerHandler.postDelayed(mDeviceStatesUpdater, UpdateDelay);
//		}
//	};
}
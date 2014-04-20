package de.uos.nbp.senhance.devicegui;


import java.util.ArrayList;

import android.os.Bundle;
import de.uos.nbp.senhance.datasource.DataSource;

public class DeviceGUITestActivity extends DeviceGUIActivity {
	static final String TAG = "DeviceTestGUIActivity";
	static final boolean D = true;
	
	static final int NumSources = 4;
	
	ArrayList<DataSource> mDeviceList; 
	
	//private Random randVal = new Random();
	
	public DeviceGUITestActivity() {
		super();
	}

	/**
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.devicegui_test_layout);
		mDeviceList = new ArrayList<DataSource>(NumSources);
		
		for (int id = 0; id < NumSources; id++) {
			mDeviceList.add(new FakeSource());
		}
		
		initDeviceListView(R.id.listView1, mDeviceList);
	}


	void incDeviceState (int position) {
		FakeSource src = (FakeSource) mDeviceList.get(position);
		src.incState();
	}

	/**
	 * Does nothing.
	 */
	@Override
	protected boolean initiateDataSourceConnection(DataSource src) {
		// TODO Auto-generated method stub
		return false;
	}
}
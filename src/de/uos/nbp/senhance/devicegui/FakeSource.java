package de.uos.nbp.senhance.devicegui;

import android.os.Bundle;
import android.os.Handler;
import de.uos.nbp.senhance.DataLogger;
import de.uos.nbp.senhance.datasource.DataSink;
import de.uos.nbp.senhance.datasource.DataSource;
/**
 *
 * This class really does nothing, it is just to be used
 * to unit-test software that needs DataSources, for example
 * the DeviceStateGUI.
 *
 * @author rmuil@UoS.de
 * December 22, 2011
 */
public class FakeSource implements DataSource {
	
	boolean mSinkAttached = false;
	
	DeviceState mState;
	
	public FakeSource () {
		mState = DeviceState.Disconnected;
	}

	public void setState (DeviceState newState) {
		mState = newState;
	}
	
	public void incState () {
		switch (mState) {
		case Disconnected:
			mState = DeviceState.Connecting;
			break;
		case Connecting:
			mState = DeviceState.Connected;
			break;
		case Connected:
			mState = DeviceState.Ready;
			break;
		case Ready:
			mState = DeviceState.Transmitting;
			break;
		case Transmitting:
			mState = DeviceState.Disconnected;
			break;
		}
	}
	/**
	 * Does nothing.
	 */
	@Override
	public void attachLogger(DataLogger eventLogger) {
		
	}

	/**
	 * Simply flicks a switch to say a sink is attached.
	 * Sink is not used.
	 */
	@Override
	public void attachSink(DataSink eventSink) {
		mSinkAttached = true;
	}

	/**
	 * Flicks 'sinkAttached' switch to false.
	 */
	@Override
	public void detachSink(DataSink sinkToDetach) {
		mSinkAttached = false;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean isSinkAttached() {
		return mSinkAttached;
	}


	/**
	 * Does nothing.
	 */
	@Override
	public void attachUIHandler(Handler handler, int sourceID) {
		
	}
	
	/**
	 * Not yet implemented.
	 */
	public int getSourceID() {
		return -1;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void detachUIHandler(Handler handler) {
		
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void connect(String address) {
		
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void connect(String address, boolean autoStart) {
		
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void startEvents() {
		
	}

	/**
	 * @see Does nothing.
	 */
	@Override
	public void configure(Bundle parameters) {
		
	}

	/**
	 * @see Does nothing.
	 */
	@Override
	public boolean needBluetooth() {
		return false;
	}

	/**
	 * 
	 */
	@Override
	public String getTypeString() {
		return "Fake";
	}

	/**
	 */
	@Override
	public String getDeviceIDString() {
		return getTypeString();
	}

	/**
	 * 
	 */
	@Override
	public String getDeviceNameFilterString() {
		return null;
	}

	/**
	 */
	@Override
	public DeviceState getSourceDeviceState() {
		return mState;
	}

	/**
	 */
	@Override
	public void stopEvents() {
		
	}

	/**
	 */
	@Override
	public void disconnect() {
		
	}

	/**
	 * Does Nothing.
	 */
	@Override
	public void setBaseline(float newBaseline) {
		
	}

	/**
	 * Does Nothing.
	 */
	@Override
	public float getBaseline() {
		return -1;
	}

	/**
	 */
	@Override
	public float getCurrent() {
		return -1;
	}

	
	
	
}

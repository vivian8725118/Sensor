package com.example.sensor.compass;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MyCompassActivity extends Activity implements SensorEventListener{
    private MyCompassView compassView;
	private boolean mRegisteredSensor;
	private SensorManager mSensorManager;
	public final int WINDOW_WIDTH=this.getWindow().getWindowManager().getDefaultDisplay().getWidth();
	public final int WINDOW_HEIGHT=this.getWindow().getWindowManager().getDefaultDisplay().getHeight();
	private final Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			 while(!Thread.currentThread().isInterrupted())  
		        {  
		            try  
		            {  
		                Thread.sleep(100);  
		            }  
		            catch(InterruptedException e)  
		            {  
		                Thread.currentThread().interrupt();  
		            }  
		            compassView.postInvalidate();  
		        }  
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		compassView=new MyCompassView(this);
		mRegisteredSensor=false;
		//ȡ��sensorManagerʵ��
		mSensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
		setContentView(compassView);
		new Thread(runnable).start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//����SensorManager��һ���б�
		//��������ָ��Ϊ���򴫸�����TYPE_ORIENTATION��
		List<Sensor> sensors=mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if(sensors.size()>0){
			Sensor sensor=sensors.get(0);
			//ע��SensorManager
			mRegisteredSensor=mSensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_FASTEST);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(mRegisteredSensor){
			mSensorManager.unregisterListener(this);
			mRegisteredSensor=false;
		}
	}
	
	//����׼�ȷ����ı�ʱ
	//sensor->������
	//accuracy->��׼��
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	//�����������ı�ʱ
	@SuppressWarnings("deprecation")
	@Override
	public void onSensorChanged(SensorEvent event) {
		//���շ��򴫸���������
		if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
			float x=event.values[SensorManager.DATA_X];
		    compassView.setDegree(x);
		}
	}

}

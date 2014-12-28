package com.example.sensor.compass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;

import com.example.sensor.R;

public class MyCompassView extends View{

	private Paint _mPaint;
	private String _message = "正北 0°";
	private float _decDegree = 0;
	private Bitmap _compass;
  

	public MyCompassView(Context context) {
		super(context);
		// 载入图片
		Options options=new Options();
		options.inScaled=true;
		_compass = BitmapFactory.decodeResource(getResources(),
				R.drawable.compass,options);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.BLACK);
		_mPaint=new Paint();
		_mPaint.setColor(Color.WHITE);
		_mPaint.setTextSize(30);
		_mPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
		// 实现图像旋转
		Matrix matrix = new Matrix();
		matrix.reset();
		matrix.setTranslate(15, 100);
		matrix.preRotate(-_decDegree, , 145);
		canvas.drawBitmap(_compass, matrix, _mPaint);
	}


	
	public void setDegree(float degree){
		//设置灵敏度
		if(Math.abs(_decDegree-degree)>=2){
			_decDegree=degree;
			int range=22;
			String degreeStr=String.valueOf(_decDegree);
			//指向正北
			if(_decDegree>360-range&&_decDegree<360+range){
				_message="正北"+degreeStr+"°";
			}
			//指向正东
			if(_decDegree>90-range&&_decDegree<90+range){
				_message="正东"+degreeStr+"°";
			}
			//指向正南
			if(_decDegree>180-range&&_decDegree<180+range){
				_message="正南"+degreeStr+"°";
			}
			//指向正西
			if(_decDegree>270-range&&_decDegree<270+range){
				_message="正西"+degreeStr+"°";
			}
			 // 指向东北  
            if(_decDegree > 45 - range && _decDegree < 45 + range)  
            {  
                _message = "东北 " + degreeStr + "°";  
            }  
            // 指向东南  
            if(_decDegree > 135 - range && _decDegree < 135 + range)  
            {  
                _message = "东南 " + degreeStr + "°";  
            }  
            // 指向西南  
            if(_decDegree > 225 - range && _decDegree < 225 + range)  
            {  
                _message = "西南 " + degreeStr + "°";  
            }  
            // 指向西北  
            if(_decDegree > 315 - range && _decDegree < 315 + range)  
            {  
                _message = "西北 " + degreeStr + "°";  
            }  
		}
	}

	public void setMessage(String msg){
		_message=msg;
	}
	

}

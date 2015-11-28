package com.distraction.cm.android;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.distraction.cm.CM;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class AndroidLauncher extends AndroidApplication{
	
	private Handler handler;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CM(), config);
		
		handler = new Handler(Looper.getMainLooper());
		
	}
	
}

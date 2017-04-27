package com.rock.Helper;

import android.app.Application;


public class Applications extends Application {
        private Applications instance;
        public static  String UID ;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
	}
	
}

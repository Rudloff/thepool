package com.ad.thepool.wrapper;

public class Log {

	public static boolean debug;
	
	static
	{
		debug = false;
	}
	
	public static void d(String log)
	{
		if(debug == true)
			android.util.Log.d("pool",log);
	}
	public static void e(String log)
	{
		if(debug == true)
			android.util.Log.e("pool",log);
	}
	public static void w(String log)
	{
		if(debug == true)
			android.util.Log.w("pool",log);
	}
}

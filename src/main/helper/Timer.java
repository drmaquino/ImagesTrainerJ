package main.helper;

public class Timer
{
	private static long _tStart;
	private static long _tEnd;
	
	private Timer()
	{
	}

	public static void start()
	{
		_tStart = System.currentTimeMillis();
	}
	
	public static void stop()
	{
        _tEnd = System.currentTimeMillis();
	}
	
	public static double getTime()
	{
		long tDelta = _tEnd - _tStart;
        double elapsedSeconds = tDelta / 1000.0;
		return elapsedSeconds;
	}
	
	public static void reset()
	{
		_tStart = 0L;
		_tEnd = 0L;
	}

}

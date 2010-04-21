Explanation

Sensor support in Android is fairly simple yet versatile. It is handled via the [code]SensorManager[/code] which is provided from your app context via [code]getSystemService()[/code]. Once you have the sensor manager, you have to register to get its notifications. To do this, we implement the SensorListener interface. 

Notice that we register to get sensor updates in onResume() and unregister in onPause(). This is important. Sensors data come in erratic intervals and can consume a lot of CPU and battery power. To optimize our app for performance, it is the best practice to get the notifications just while your app is in running state.

The notifications come via [code]onSensorChanged()[/code] and [code]onAccuracyChanged()[/code]. Notice that each has a parameter for the sensor reporting. Android sensor API can support a large number of sensors, each one having its own unique integer ID. The current ones are expressed via their constants, such as [code]SensorManager.SENSOR_ORIENTATION[/code].

The sensor values we get in [code]onSensorChanged()[/code] will depend on the specific sensor. For example, orientation sensor reports azimuth as value at index 0, pitch at index 1 and roll at index 2.

[code=src/com.marakana/SensorDemo.java]


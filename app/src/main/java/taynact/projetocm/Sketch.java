package taynact.projetocm;

/**
 * Created by jcsil on 13/10/2016.
 */

import ketai.sensors.KetaiSensor;
import ketai.ui.KetaiVibrate;
import processing.core.PApplet;
import processing.core.PVector;

public class Sketch extends PApplet {


    KetaiSensor sensor;
    PVector magneticField, accelerometer, orient;
    float light, proximity;
    KetaiVibrate vib;

    public void settings() {
        fullScreen();
    }

    public Sketch()
    {
        sensor = new KetaiSensor(this);
        sensor.start();
        sensor.list();

        sensor.enableAllSensors();

        accelerometer = new PVector();

        orient = new PVector();
        orientation(LANDSCAPE);

    }

    //usamos o valor do z para detetar para que lado esta a tombar o dispositivo
    void onOrientationEvent(float x, float y, float z) // x,y,z rotation in degrees
    {orient.set(x,y,z);}



    void onAccelerometerEvent(float x, float y, float z, long time, int accuracy)
    {
        accelerometer.set(x, y, z);
    }

    public float getorientez() {
        return orient.z;
    }

    public float getorientey() {
        return orient.y;
    }

}

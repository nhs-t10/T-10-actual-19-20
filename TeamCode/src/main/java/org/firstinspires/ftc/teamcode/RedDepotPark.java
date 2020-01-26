package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Red Depot Park")
public class RedDepotPark extends Library {

    enum State{
        PARKING, END
    }
    State currentstate;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = {0F, 0F, 0F};

    @Override public void init(){
        hardwareInit();
        currentstate = State.PARKING;
    }
    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        if(currentstate == State.PARKING){
            Parking();
        }
        if(currentstate == State.END){
            Stop();
        }

        Telemetry();
    }

    public void Parking(){
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        if(!moving){
            clock.reset();
            moving = true;
        }else if(distance.getDistance(DistanceUnit.CM)>5){
            drive(.5f,0,0);
        }else if(hsvValues[0] > 100 /*|| clock.seconds() < 1.5*/){
            drive(0,0,.4f);
        }else{
            moving = false;
            drive(0,0,0);
            currentstate = State.END;
        }

        if(hsvValues[0] <= 100){
            moving = false;
            drive(0,0,0);
            currentstate = State.END;
        }
    }

    public void Stop(){
        moving = false;
        drive(0,0,0);
    }

    private void Telemetry(){
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        telemetry.addData("Red: ", color.red());
        telemetry.addData("Green: ", color.green());
        telemetry.addData("Blue: ", color.blue());
        telemetry.addData("Light: ",color.alpha());
        telemetry.addData("Hue: ", hsvValues[0]);
        telemetry.addData("Saturation: ", hsvValues[1]);
        telemetry.addData("Value: ", hsvValues[2]);

        telemetry.addData("Millis since State Start: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
        telemetry.addData("Distamce: ", distance.getDistance(DistanceUnit.CM));
    }
}
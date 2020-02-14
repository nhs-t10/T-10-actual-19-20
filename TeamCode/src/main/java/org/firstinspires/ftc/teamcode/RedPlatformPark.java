package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Red Platform Park")
public class RedPlatformPark extends Library{

    enum State{
        PARKING, END
    }

    State currentState;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = { 0F, 0F, 0F };

    @Override
    public void init(){
        hardwareInit();
        currentState = State.PARKING;
    }

    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        if( currentState == State.PARKING ){
            Parking();
        }
        if( currentState == State.END ){
            Stop();
        }

        Telemetry();
    }

    public void Parking(){
        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( hsvValues[0] <= 100 || clock.seconds() >= 6 ){
            moving = false;
            drive(0, 0, 0);
            currentState = State.END;
        }else if( clock.seconds() >= 5 ){
            drive(0, 0, .3f);
        }else{
            drive(0, 0, -.4f);
        }

        if( distance.getDistance(DistanceUnit.CM) > 8 ){
            drive(.3f, 0, 0);
        }
    }

    public void Stop(){
        moving = false;
        drive(0, 0, 0);
    }

    private void Telemetry(){
        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
        telemetry.addData("Red: ", color.red());
        telemetry.addData("Green: ", color.green());
        telemetry.addData("Blue: ", color.blue());
        telemetry.addData("Light: ", color.alpha());
        telemetry.addData("Hue: ", hsvValues[0]);
        telemetry.addData("Saturation: ", hsvValues[1]);
        telemetry.addData("Value: ", hsvValues[2]);

        telemetry.addData("Millis since State Start: ", clock.seconds());
        telemetry.addData("State: ", currentState);
        telemetry.addData("Distamce: ", distance.getDistance(DistanceUnit.CM));
    }
}
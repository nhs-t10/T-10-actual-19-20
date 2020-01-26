package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Blue Block Auto")
public class BlueBlockAuto extends Library{

    private State currentstate;
    private ElapsedTime clock = new ElapsedTime();
    boolean moving = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = {0F, 0F, 0F};

    @Override
    public void init(){
        hardwareInit();
        vuforiaInit();
        currentstate = State.SCAN;
    }

    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
//        clock.reset();
//        while( clock.milliseconds() < 0.2 ){
//            drive(1.5f, 0, 0);
//        }
        if( currentstate == State.SCAN ){
            scan();
        }
        if( currentstate == State.SLIDE ){
            slide();
        }
        if( currentstate == State.MOVE ){
            move();
        }
        if( currentstate == State.TRAVEL ){
            travel();
        }
        if( currentstate == State.PARK ){
            park();
        }
        if( currentstate == State.END ){
            Stop();
        }

        Telemetry();
    }

    public void scan(){
        //is Sky stone Visible, then either slide left or move forward
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 1){
            if( isSkystoneVisible() ){
                currentstate = State.MOVE;
            }else{
                currentstate = State.SLIDE;
            }
        }

    }

    public void slide(){
        //only do if skystone is not immediately visible
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.milliseconds() < 50){
            drive(0, 0, -.5f);
        }else{
            drive(0,0,0);
            moving = false;
            currentstate = State.SCAN;
        }
    }

    public void move(){
        //move forward to skystone (will need tweaking to make sure that skystone is always visible)
        if(!moving){
            clock.reset();
            moving = true;
        }else if(distance.getDistance(DistanceUnit.INCH)<=18){
            drive(.5f, 0, 0);
        }else{
            rotateFor((float) Math.PI);
            drive(0,0,0);
            moving = false;
            currentstate = State.TRAVEL;
        }
    }

    public void travel(){
        //back up a small amount, then slide left to cross the barrier
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 2){
            drive(0, 0, .5f);
        }else{
            drive(0,0,0);
            moving = false;
            currentstate = State.PARK;
        }
    }

    public void park(){
        //slide right and use color sensor to stop on blue line
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        if(!moving){
            clock.reset();
            moving = true;
        }else if(distance.getDistance(DistanceUnit.CM)>5){
            drive(.5f,0,0);
        }else if(hsvValues[0] < 140 /*|| clock.seconds() < 1.5*/){
            drive(0,0,-.4f);
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.END;
        }
    }

    public void Stop(){
        drive(0, 0, 0);
    }

    enum State{
        SCAN, SLIDE, MOVE, TRAVEL, PARK, END
    }

    private void Telemetry(){
        telemetry.addData("skystone is visible: ", isSkystoneVisible());

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
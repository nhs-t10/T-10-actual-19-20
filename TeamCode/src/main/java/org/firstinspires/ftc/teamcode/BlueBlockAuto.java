package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@SuppressWarnings("all")
//@Autonomous(name = "Blue Block Auto")
public class BlueBlockAuto extends Library{
    Turning turner;

    private final double SCALE_FACTOR = 255;
    private boolean moving = false;
    private boolean started = false;
    private State currentState;
    private ElapsedTime clock = new ElapsedTime();
    private float[] hsvValues = { 0F, 0F, 0F };

    @Override
    public void init(){
        hardwareInit();
        vuforiaInit();
        currentState = State.SCAN;
        turner = new Turning();
        turner.initImuTurning(hardwareMap);
    }

    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        //        clock.reset();
        //        while( clock.milliseconds() < 0.2 ){
        //            drive(1.5f, 0, 0);
        //        }
        if( currentState == State.SCAN ){
            scan();
        }
        if( currentState == State.SLIDE ){
            slide();
        }
        if( currentState == State.MOVE ){
            move();
        }
//        if( currentState == State.TRAVEL ){
//            travel();
//        }
        if( currentState == State.PARK ){
            park();
        }
        if( currentState == State.END ){
            Stop();
        }

        Telemetry();
    }

    private void scan(){
        //is Sky stone Visible, then either slide left or move forward
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 1 ){
            if( isSkystoneVisible() ){
                currentState = State.MOVE;
            }else{
                currentState = State.SLIDE;
            }
        }

    }

    private void slide(){
        //only do if skystone is not immediately visible
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.milliseconds() < 50 ){
            drive(0, 0, -.5f);
        }else{
            drive(0, 0, 0);
            moving = false;
            currentState = State.SCAN;
        }
    }

    private void move(){
        //move forward to skystone (will need tweaking to make sure that skystone is always visible)
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( distance.getDistance(DistanceUnit.INCH) <= 18 ){
            drive(.5f, 0, 0);
        }else{
            turner.turnDegrees(180);
            drive(0, 0, 0);
            //            gripStone(true);
            //            lift.setPower(0.0001);
            drive(1, 0, 0);
            drive(0, 0, 0);
            moving = false;
            currentState = State.PARK;
        }
    }

//    private void travel(){
//        //back up a small amount, then slide left to cross the barrier
//        if( !moving ){
//            clock.reset();
//            moving = true;
//        }else if( clock.seconds() < 2 ){
//            drive(0, 0, .5f);
//        }else{
//            drive(0, 0, 0);
//            //            lift.setPower(0);
//            //            gripStone(false);
//            moving = false;
//            currentState = State.PARK;
//        }
//    }

    private void park(){
        //slide right and use color sensor to stop on blue line
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        if(!moving){
            clock.reset();
            moving = true;
        }else if(hsvValues[0] >= 130 || clock.seconds()>=6){
            moving = false;
            drive(0,0,0);
            //lift.setPower(0);
            //gripStone(false);
            currentState = State.END;
        }else if(clock.seconds()>=5){
            drive(0, 0, .3f);
        }else{
            drive(0,0,-.4f);
        }

        if(distance.getDistance(DistanceUnit.CM)>8){
            drive(.3f,0,0);
        }
    }

    private void Stop(){
        drive(0, 0, 0);
    }

    private void Telemetry(){
        telemetry.addData("skystone is visible: ", isSkystoneVisible());

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

    enum State{
        SCAN, SLIDE, MOVE, TRAVEL, PARK, END
    }

}
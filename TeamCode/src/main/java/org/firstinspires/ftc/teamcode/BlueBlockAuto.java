package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Blue Block Auto")
public class BlueBlockAuto extends Library{

    private State currentstate;
    private int gray, blue;
    private ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override
    public void init(){
        hardwareInit();
        currentstate = State.SCAN;
        blue = color.blue();
        gray = ( color.red() + color.blue() + color.green() ) / 3;
    }

    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        clock.reset();
        while( clock.milliseconds() < 0.2 ){
            drive(-.5f, 0, 0);
        }
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

        telemetry.addData("skystone is visible: ", isSkystoneVisible());
        telemetry.addData("Blue reading: ", color.blue());
        //        telemetry.addData("Gray color: ", gray);
        //        telemetry.addData("Blue color: ", blue);
        //        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void scan(){
        //is Sky stone Visible, then either slide left or move forward
        if( isSkystoneVisible() ){
            currentstate = State.MOVE;
        }else{
            currentstate = State.SLIDE;
        }
    }

    public void slide(){
        //only do if skystone is not immediately visible
        clock.reset();
        while( clock.milliseconds() < 500 ){
            drive(0, 0, -.5f);
        }
        currentstate = State.SCAN;
    }

    public void move(){
        //move forward to skystone (will need tweaking to make sure that skystone is always visible)
        clock.reset();
        while( clock.seconds() > 2 ){
            drive(-.5f, 0, 0);
        }
    }

    public void travel(){
        //back up a small amount, then slide left to cross the barrier
        clock.reset();
        while( clock.seconds() < 3 ){
            drive(0, 0, -.5f);
        }
        currentstate = State.PARK;
    }

    public void park(){
        //slide right and use color sensor to stop on blue line
        clock.reset();
        while( gray > blue ){
            drive(0, 0, .5f);
        }
        currentstate = State.PARK;
    }

    public void Stop(){
        drive(0, 0, 0);
    }

    enum State{
        SCAN, SLIDE, MOVE, TRAVEL, PARK, END
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Blue Block Auto")
public class BlueBlockAuto extends Library {

    enum State{
        SCAN, SLIDE, MOVE, TRAVEL, PARK, END
    }
    State currentstate;
    //int gray, blue;
    ElapsedTime clock = new ElapsedTime();
    //boolean moving = false;

    @Override public void init(){
        hardwareInit();
        currentstate = State.SCAN;
        //gray = color.blue();
        //blue = (int)(gray*1.2);
    }
    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        if(currentstate == State.SCAN){
            scan();
        }
        if(currentstate == State.SLIDE){
            slide();
        }
        if(currentstate == State.MOVE){
            move();
        }
        if(currentstate == State.TRAVEL){
            travel();
        }
        if(currentstate == State.PARK){
            travel();
        }
        if(currentstate == State.END){
            Stop();
        }

        telemetry.addData("Blue reading: ", color.blue());
//        telemetry.addData("Gray color: ", gray);
//        telemetry.addData("Blue color: ", blue);
//        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void scan(){
        //isSkystoneVisible, then either slide left or move forward
    }

    public void slide(){
        //only do if skystone is not immediately visible
    }

    public void move(){
        //move forward to skystone (will need tweaking to make sure that skystone is always visible)
    }

    public void travel(){
        //back up a small amount, then slide left to cross the barrier
    }

    public void park(){
        //slide right and use color sensor to stop on blue line
    }

    public void Stop(){
        drive(0,0,0);
    }
}
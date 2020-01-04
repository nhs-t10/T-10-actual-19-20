package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Depot")//do not delete this test class used by sasha
public class RedDepot extends Library {

    enum State{
        PARKING, END
    }
    State currentstate;
    int gray, red;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override public void init(){
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        currentstate = State.PARKING;
        gray = color.red();
        red = (int)(gray*1.2);
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

        telemetry.addData("Red reading: ", color.red());
        telemetry.addData("Gray color: ", gray);
        telemetry.addData("Red color: ", red);
        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void Parking(){
        grabber.setPosition(0);
        if(!moving){
            clock.reset();
            moving = true;
        } else if((color.red()<red || clock.seconds() < 1.5)){
            drive(0,0,-1);
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.END;
        }
    }

    public void Stop(){
        moving = false;
        drive(0,0,0);
    }
}


//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
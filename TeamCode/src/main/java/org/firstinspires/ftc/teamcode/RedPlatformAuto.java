package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Platform")//do not delete this test class used by sasha
public class RedPlatformAuto extends Library {

    enum State{
        TO_FOUNDATION, FROM_FOUNDATION, PARKING, END
    }
    State currentstate;
    int gray, red;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override public void init(){
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        currentstate = State.TO_FOUNDATION;
        gray = color.red();
        red = (int)(gray*1.2);
    }
    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        if(currentstate == State.TO_FOUNDATION){
            ToFoundation();
        }
        if(currentstate == State.FROM_FOUNDATION){
            FromFoundation();
        }
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

    public void ToFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        } else if(clock.seconds() < 1){
            drive(0,0,-1);
        } else if(clock.seconds() > 1 && clock.seconds() < 2.5){ //color.blue()<blue
            drive(1f,0,0);
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.FROM_FOUNDATION;
        }
    }

    public void FromFoundation(){
        gripFoundation(true);
        if (!moving){
            clock.reset();
            moving = true;
        } else if(clock.seconds() < 2){
            //wait for 2 seconds for grabber
        } else if(clock.seconds() > 2 && clock.seconds() < 3.6){ //(!front1.isPressed()||!front2.isPressed())
            drive(-1f,0,0);//drives until touching wall
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.PARKING;
        }
    }

    public void Parking(){
        gripFoundation(false);
        if(!moving){
            clock.reset();
            moving = true;
        } else if(clock.seconds() < 1){
            drive(0,0,1);
        } else if(clock.seconds() > 1 && (color.red()< red || clock.seconds() < 3)){
            drive(0,0,1);
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
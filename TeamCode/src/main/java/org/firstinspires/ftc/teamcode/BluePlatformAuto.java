package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Blue Platform Auto")//do not delete this test class used by sasha
public class BluePlatformAuto extends Library {

    enum State{
        TO_FOUNDATION, FROM_FOUNDATION, PARKING, END
    }
    State currentstate;
    int gray, blue;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override public void init(){
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        currentstate = State.TO_FOUNDATION;
        gray = color.blue();
        blue = (int)(gray*1.2);
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

        telemetry.addData("Blue reading: ", color.blue());
        telemetry.addData("Gray color: ", gray);
        telemetry.addData("Blue color: ", blue);
        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void ToFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        } else if(clock.seconds() < 1.4){ //color.blue()<blue
            drive(.75f,0,0);
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
        } else if(clock.seconds() > 2 && clock.seconds() < 3.5){ //(!front1.isPressed()||!front2.isPressed())
            drive(-.75f,0,0);//drives until touching wall
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
        } else if(color.blue()< blue || clock.seconds() < 3){
            drive(0,0,.5f);
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
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Distance Sensors")//do not delete this test class used by sasha
public class DistanceSensors extends Library {

    enum State{
        WALL, END
    }
    State currentstate;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override public void init(){
        hardwareInit();
        currentstate = State.WALL;
    }
    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        double distanceRead = distance.getDistance(DistanceUnit.CM);
        if(currentstate == State.WALL){
            ParkingOnWall(distanceRead);
        }
        if(currentstate == State.END){
            Stop();
        }

        telemetry.addData("Distance reading: ", distanceRead);
        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void ParkingOnWall(double distanceRead){
        grabber.setPosition(0);
        if(!moving){
            clock.reset();
            moving = true;
        } else if(distanceRead < 7){
            drive(-.1f,0,0);
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
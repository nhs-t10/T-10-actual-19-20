package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Blue Platform Park")//do not delete this test class used by sasha
public class BluePlatformPark extends Library {

    enum State{
        PARKING, END
    }
    State currentstate;
    int gray, blue;
    ElapsedTime clock = new ElapsedTime();
    boolean moving = false;

    @Override public void init(){
        hardwareInit();
        currentstate = State.PARKING;
        gray = color.blue();
        blue = (int)(gray*1.2);
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

        telemetry.addData("Blue reading: ", color.blue());
        telemetry.addData("Gray color: ", gray);
        telemetry.addData("Blue color: ", blue);
        telemetry.addData("Millis since run: ", clock.seconds());
        telemetry.addData("State: ", currentstate);
    }

    public void Parking(){
        grabber.setPosition(0);
        if(!moving){
            clock.reset();
            moving = true;
        } else if((color.blue()< blue || clock.seconds() < 1.5)){
            drive(0,0,.5f);
        }
        else{
            moving = false;
            drive(0,0,0);
<<<<<<< Updated upstream
            currentstate = State.END;
=======
            currentState = State.END;
        }else if(clock.seconds()>=5){
            drive(0, 0, -.3f);
        }else{
            drive(0,0,.4f);
        }

        if( distanceLeft.getDistance(DistanceUnit.CM)>8 || distanceRight.getDistance(DistanceUnit.CM)>8){
            drive(.3f,0,0);
>>>>>>> Stashed changes
        }
    }

    public void Stop(){
        moving = false;
        drive(0,0,0);
    }
}


<<<<<<< Updated upstream
//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
=======
        telemetry.addData("Millis since State Start: ", clock.seconds());
        telemetry.addData("State: ", currentState);
        telemetry.addData("Distance Left: ", distanceLeft.getDistance(DistanceUnit.CM));
        telemetry.addData("Distance Right: ", distanceRight.getDistance(DistanceUnit.CM));
    }
}
>>>>>>> Stashed changes

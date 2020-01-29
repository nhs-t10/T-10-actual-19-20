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
<<<<<<< Updated upstream
        } else if(clock.seconds() < 1.4){ //color.blue()<blue
            drive(.75f,0,0);
=======
        } else if( distanceLeft.getDistance(DistanceUnit.CM)<=80){
            drive(-.75f,0,0);
>>>>>>> Stashed changes
        }
        else{
            moving = false;
            drive(0,0,0);
            currentstate = State.FROM_FOUNDATION;
        }
<<<<<<< Updated upstream
    }
=======
    }//distanceLeft reading to the foundationLeft is 90cm
>>>>>>> Stashed changes

    public void FromFoundation(){
        gripFoundation(true);
        if (!moving){
            clock.reset();
            moving = true;
        } else if(clock.seconds() < 2){
            //wait for 2 seconds for grabber
<<<<<<< Updated upstream
        } else if(clock.seconds() > 2 && clock.seconds() < 3.5){ //(!front1.isPressed()||!front2.isPressed())
            drive(-.75f,0,0);//drives until touching wall
        }
        else{
=======
        } else if(clock.seconds() > 2 && ( distanceLeft.getDistance(DistanceUnit.CM) >= 30)){ //(!front1.isPressed()||!front2.isPressed())
            drive(.75f,0,0);//drives until touching wall
        }else if( distanceLeft.getDistance(DistanceUnit.CM) >= 5){ //(!front1.isPressed()||!front2.isPressed())
            drive((float)( distanceLeft.getDistance(DistanceUnit.CM)/60+.1),0,0);//drives until touching wall
        }else{
>>>>>>> Stashed changes
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
        } else if(color.blue()< blue || clock.seconds() < 1.5){
            drive(0,0,.5f);
        }
        else{
            moving = false;
            drive(0,0,0);
<<<<<<< Updated upstream
            currentstate = State.END;
        }
    }

    public void Stop(){
=======
            currentState = State.END;
        }else if(clock.seconds()>=5){
            drive(0, 0, -.3f);
        }else{
            drive(0,0,.4f);
        }

        if( distanceLeft.getDistance(DistanceUnit.CM)>8 || distanceRight.getDistance(DistanceUnit.CM)>8){
            drive(.3f,0,0);
        }
    }

    private void Telemetry(){
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        telemetry.addData("Red: ", color.red());
        telemetry.addData("Green: ", color.green());
        telemetry.addData("Blue: ", color.blue());
        telemetry.addData("Light: ",color.alpha());
        telemetry.addData("Hue: ", hsvValues[0]);
        telemetry.addData("Saturation: ", hsvValues[1]);
        telemetry.addData("Value: ", hsvValues[2]);

        telemetry.addData("Millis since State Start: ", clock.seconds());
        telemetry.addData("State: ", currentState);
        telemetry.addData("Distance Left: ", distanceLeft.getDistance(DistanceUnit.CM));
        telemetry.addData("Distance Right: ", distanceRight.getDistance(DistanceUnit.CM));
    }

    private void Stop(){
>>>>>>> Stashed changes
        moving = false;
        drive(0,0,0);
    }
}


//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
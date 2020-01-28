package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.graphics.Color;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="Red Platform Auto")
public class RedPlatformAuto extends Library {

    enum State{
        TO_FOUNDATION, FROM_FOUNDATION, PARKING, END
    }
    private State currentState;
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = {0F, 0F, 0F};

    @Override public void init(){
        hardwareInit();
        currentState = State.TO_FOUNDATION;
    }
    public void init_loop(){
        Telemetry();
    }
    public void loop(){
        /*
        Loop constantly checks state, and then executes a command based on this.
        */
        if( currentState == State.TO_FOUNDATION){
            ToFoundation();
        }
        if( currentState == State.FROM_FOUNDATION){
            FromFoundation();
        }
        if( currentState == State.PARKING){
            Parking();
        }
        if( currentState == State.END){
            Stop();
        }

        Telemetry();
    }

    private void ToFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        } else if(distance.getDistance(DistanceUnit.CM)<=80){
            drive(-.75f,0,0);
        }
        else{
            moving = false;
            drive(0,0,0);
            currentState = State.FROM_FOUNDATION;
        }
    }//distance reading to the platform is 90cm

    private void FromFoundation(){
        //gripFoundation(true);
        if (!moving){
            clock.reset();
            moving = true;
        } else if(clock.seconds() < 2){
            //wait for 2 seconds for grabber
        } else if(clock.seconds() > 2 && (distance.getDistance(DistanceUnit.CM) >= 30)){ //(!front1.isPressed()||!front2.isPressed())
            drive(.75f,0,0);//drives until touching wall
        }else if(distance.getDistance(DistanceUnit.CM) >= 5){ //(!front1.isPressed()||!front2.isPressed())
            drive((float)(distance.getDistance(DistanceUnit.CM)/60+.1),0,0);//drives until touching wall
        }else{
            moving = false;
            drive(0,0,0);
            currentState = State.PARKING;
        }
    }//wall reading is about 1cm

    private void Parking(){
        //        gripFoundation(false);
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        if(!moving){
            clock.reset();
            moving = true;
        }else if(hsvValues[0] <= 100 || clock.seconds()>=4){
            moving = false;
            drive(0,0,0);
            currentState = State.END;
        }else if(clock.seconds()>=3){
            drive(0, 0, .3f);
        }else{
            drive(0,0,-.4f);
        }

        if(distance.getDistance(DistanceUnit.CM)>8){
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
        telemetry.addData("Distamce: ", distance.getDistance(DistanceUnit.CM));
    }

    private void Stop(){
        moving = false;
        drive(0,0,0);
    }
}
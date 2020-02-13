package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class GripFoundation extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;

    @Override
    public void init(){
        hardwareInit();
    }

    public void loop(){
    }

    private void GripFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
            gripFoundation(true);
        }else if( clock.seconds() < 2 ){
            gripFoundation(true);//this grips the foundation
        }else{
            gripFoundation(true);
            moving = false;
            drive(0, 0, 0);
            //            currentState = State.PARKING;
        }
    }//wall reading is about 1cm
}
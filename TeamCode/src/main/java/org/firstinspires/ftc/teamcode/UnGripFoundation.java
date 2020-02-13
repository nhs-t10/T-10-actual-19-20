package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class UnGripFoundation extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;

    @Override
    public void init(){
        hardwareInit();
    }

    public void loop(){
    }

    private void UnGripFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
            gripFoundation(false);
        }else if( clock.seconds() < 2 ){
            gripFoundation(false);//this un grips the foundation
        }else{
            gripFoundation(false);
            moving = false;
            drive(0, 0, 0);
            //            currentState = State.PARKING;
        }
    }//wall reading is about 1cm
}
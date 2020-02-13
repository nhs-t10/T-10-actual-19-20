package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class BackUpFoundation extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;

    @Override
    public void init(){
        hardwareInit();
    }

    public void loop(){
    }

    private void BackUpFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
            gripFoundation(true);
        }else if( clock.seconds() < 2 ){
            gripFoundation(true);//this grips the foundation
        }else if( clock.seconds() > 2 && ( distanceLeft.getDistance(DistanceUnit.CM) >= 30 ) ){ //(!front1.isPressed()||!front2.isPressed())
            gripFoundation(true);
            drive(.75f, 0, 0);//drives until touching wall
        }else if( distanceLeft.getDistance(DistanceUnit.CM) > 10 ){ //(!front1.isPressed()||!front2.isPressed())
            gripFoundation(true);
            drive((float) ( distanceLeft.getDistance(DistanceUnit.CM) / 100 + .1 ), 0, 0);//drives until touching wall
        }else{
            gripFoundation(false);
            moving = false;
            drive(0, 0, 0);
            //            currentState = State.PARKING;
        }
    }//wall reading is about 1cm
}
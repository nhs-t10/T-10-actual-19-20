package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DriveToFoundation extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;

    @Override
    public void init(){
        hardwareInit();
    }

    public void loop(){
    }

    private void DriveToFoundation( boolean isBlue ){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < .75 ){
            if( isBlue ){
                drive(0, 0, -.5f);
            }else{
                drive(0, 0, .5f);
            }
        }else if( distanceLeft.getDistance(DistanceUnit.CM) <= 80 || distanceRight.getDistance(DistanceUnit.CM) <= 80 ){
            drive(-.75f, 0, 0);
        }else{
            moving = false;
            drive(0, 0, 0);
            //            currentState = State.FROM_FOUNDATION;
        }
    }//distanceLeft reading to the platform is 90cm
}
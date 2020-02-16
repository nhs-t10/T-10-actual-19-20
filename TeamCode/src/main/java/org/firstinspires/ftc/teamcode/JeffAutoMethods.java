package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class JeffAutoMethods{

    boolean isBlueSide;
    boolean moving;
    private ElapsedTime clock = new ElapsedTime();

    public JeffAutoMethods(boolean blueSide){
        this.isBlueSide = blueSide;
        moving = false;
    }

    public boolean driveToFoundation(){
        /*if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < .75 ){
            if( isBlueSide ){
                Library.drive(0, 0, -.5f);
            }else{
                Library.drive(0, 0, .5f);
            }
        }else if( Library.distanceLeft.getDistance(DistanceUnit.CM) <= 80 || Library.distanceRight.getDistance(DistanceUnit.CM) <= 80 ){
            Library.drive(-.75f, 0, 0);
        }else{
            moving = false;
            Library.drive(0, 0, 0);
            return true;
            //            currentState = State.FROM_FOUNDATION;
        }
        return false;
         */
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }//distanceLeft reading to the platform is 90cm

    public boolean gripFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
}

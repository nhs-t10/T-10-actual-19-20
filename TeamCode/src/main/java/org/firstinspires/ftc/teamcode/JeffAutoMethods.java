package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

class JeffAutoMethods{

    private boolean isBlueSide;
    private boolean moving, moving2;
    private Turning turner;
    private ElapsedTime clock = new ElapsedTime(), clock2 = new ElapsedTime();
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = { 0F, 0F, 0F };
    private boolean sensedBlock = false;

    JeffAutoMethods( boolean blueSide, HardwareMap hardwareMap ){
        this.isBlueSide = blueSide;
        moving = false; moving2 = false;
        turner = new Turning();
        turner.initImu(hardwareMap);
    }

    boolean driveToFoundation(){
        //this works with grippers on back
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < .75 ){
            if( isBlueSide ){
                Library.drive(0, 0, .5f);
            }else{
                Library.drive(0, 0, -.5f);
            }
        }else if( clock.seconds() < 3.5 ){
            Library.drive(.75f, 0, 0);
        }else{
            Library.drive(0, 0, 0);
            moving = false;
            return true;
        }
        return false;
    }//distanceLeft reading to the platform is 90cm

    boolean gripFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 1 ){
            Library.gripFoundation(true);
        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean backUpFoundation(){
        //this works with grippers on back
        if( !moving ){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 2){
            Library.drive(-.5f, 0, 0);
        }else{
            moving = false;
            Library.drive(0, 0, 0);
            return true;
        }
        return false;
    }

    boolean turnFoundation(){
        //this works with grippers on back
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 2 ){
            if( isBlueSide ){
                turner.turnDegrees(90);
            }else{
                turner.turnDegrees(-90);
            }
        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean pushFoundation(){
        //this works with grippers on back
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 1 ){
            Library.drive(-.5f, 0, 0);
        }else{
            Library.drive(0,0,0);
            moving = false;
            return true;
        }
        return false;
    }

    boolean ungripFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 1 ){
            Library.gripFoundation(false);
        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean moveToParkingPositionFoundation(){
        //this works with grippers on back
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 2 ){
            Library.drive(.75f,0,0);
        }else{
            Library.drive(0,0,0);
            turner.turnDegrees(90);
            moving = false;
            return true;
        }
        return false;
    }

    boolean extendTapeMeasure(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 10 ){
            //this should be 40cm (about 4cm/s)
            Library.tapeMeasure.setPower(1);
        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean driveToQuarry(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( Library.distanceLeft.getDistance(DistanceUnit.INCH) <= 18 ){
            Library.drive(.5f, 0, 0);
        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean getQuarryConfiguration(){
        Color.RGBToHSV((int) ( Library.color.red() * SCALE_FACTOR ), (int) ( Library.color.green() * SCALE_FACTOR ), (int) ( Library.color.blue() * SCALE_FACTOR ), hsvValues);

        if( !moving ){
            clock.reset();
            moving = true;
            sensedBlock = false;
        }else{
            if( Library.isSkystoneVisible() || sensedBlock ){
                if( !moving2 ){
                    clock2.reset();
                    moving2 = true;
                    sensedBlock = true;
                }else if( Library.distanceLeft.getDistance(DistanceUnit.INCH) <= 23 ){
                    Library.drive(.5f, 0, 0);
                }else{
                    turner.turnDegrees(180);
                    moving = false;
                    return true;
                }
            }else{
                if( !moving2 ){
                    clock2.reset();
                    moving2 = true;
                }else if( clock2.milliseconds() < 50 ){
                    Library.drive(0, 0, -.5f);
                }else{
                    Library.drive(0, 0, 0);
                    moving2 = false;
                }
            }
        }
        return false;
    }

    boolean pickUpBlock(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 2 ){
            Library.gripStone(true);
        }else if( clock.seconds() < 5 ){
            Library.gripStone(true);
            Library.liftRight.setPower(0.0001);
            Library.liftLeft.setPower(0.0001);
        }else{
            Library.liftRight.setPower(0);
            Library.liftLeft.setPower(0);
            moving = false;
            return true;
        }
        return false;
    }

    boolean driveToBuildingZone(){
        //NOT FINISHED
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean placeBlock(){
        //NOT FINISHED
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            moving = false;
            return true;
        }
        return false;
    }

    boolean turnToParkingLineBlock(){
        //NOT FINISHED?
        if( !moving ){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){
            turner.turnDegrees(90);
        }else{
            moving = false;
            return true;
        }
        return false;
    }

//    private void park(){
//        //slide right and use color sensor to stop on blue line
//        Color.RGBToHSV((int) ( Library.color.red() * SCALE_FACTOR ), (int) ( Library.color.green() * SCALE_FACTOR ), (int) ( Library.color.blue() * SCALE_FACTOR ), hsvValues);
//        if( !moving ){
//            clock.reset();
//            moving = true;
//        }else if( hsvValues[0] >= 180 || clock.seconds() >= 6 ){
//            moving = false;
//            Library.drive(0, 0, 0);
//            Library.liftRight.setPower(0);
//            Library.liftLeft.setPower(0);
//            Library.gripStone(false);
//        }else if( clock.seconds() >= 5 ){
//            Library.drive(0, 0, .3f);
//        }else{
//            Library.drive(0, 0, -.4f);
//        }
//
//        if( Library.distanceLeft.getDistance(DistanceUnit.CM) > 8 || Library.distanceRight.getDistance(DistanceUnit.CM) > 8 ){
//            Library.drive(.3f, 0, 0);
//        }
//    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class JeffAutoMethods{

    boolean isBlueSide;
    boolean moving;
    Turning turner;
    private ElapsedTime clock = new ElapsedTime();

    public JeffAutoMethods( boolean blueSide, HardwareMap hardwareMap ){
        this.isBlueSide = blueSide;
        moving = false;
        turner = new Turning();
        turner.initImu(hardwareMap);
    }

    public boolean driveToFoundation(){
        if( !moving ){
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
            Library.drive(0, 0, 0);
            return true;
        }
        return false;
    }//distanceLeft reading to the platform is 90cm

    public boolean gripFoundation(){

        Library.gripFoundation(true);

        return true;
    }

    public boolean backUpFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 2 ){
            Library.drive(1, 0, 0);
        }else{
            return true;
        }
        return false;
    }

    public boolean turnFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 2 ){
            if( !isBlueSide ){
                turner.turnDegrees(90);
            }else{
                turner.turnDegrees(-90);
            }
        }else{
            return true;
        }
        return false;
    }

    public boolean pushFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;

        }else if( clock.seconds() < 0.2 ){
            Library.drive(1, 0, 0);

        }else{
            return true;
        }
        return false;
    }

    public boolean ungripFoundation(){

        Library.gripFoundation(false);
        return true;
    }

    public boolean moveToParkingPositionFoundation(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            return true;
        }
        return false;
    }

    public boolean extendTapeMeasure(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){
            Library.tapeMeasure.setPower(1);
        }else{
            return true;
        }
        return false;
    }

    public boolean driveToQuarry(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            return true;
        }
        return false;
    }

    public boolean getQuarryConfiguration(){
        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 1 ){
            if( isSkystoneVisible() ){
                currentState = State.MOVE;
            }else{
                currentState = State.SLIDE;
            }

        }else{
            return true;
        }
        return false;
    }

    private void slide(){
        //only do if skystone is not immediately visible
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.milliseconds() < 50 ){
            drive(0, 0, -.5f);
        }else{
            drive(0, 0, 0);
            moving = false;
            currentState = State.SCAN;
        }
    }

    private void move(){
        //move forward to skystone (will need tweaking to make sure that skystone is always visible)
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( distanceLeft.getDistance(DistanceUnit.INCH) <= 18 ){
            drive(.5f, 0, 0);
        }else{
            turner.turnDegrees(180);
            drive(0, 0, 0);
            gripStone(true);
            liftRight.setPower(0.0001);
            liftLeft.setPower(0.0001);
            drive(1, 0, 0);
            drive(0, 0, 0);
            moving = false;
            currentState = State.PARK;
        }
    }

    private void park(){
        //slide right and use color sensor to stop on blue line
        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( hsvValues[0] >= 180 || clock.seconds() >= 6 ){
            moving = false;
            drive(0, 0, 0);
            liftRight.setPower(0);
            liftLeft.setPower(0);
            gripStone(false);
            currentState = State.END;
        }else if( clock.seconds() >= 5 ){
            drive(0, 0, .3f);
        }else{
            drive(0, 0, -.4f);
        }

        if( distanceLeft.getDistance(DistanceUnit.CM) > 8 || distanceRight.getDistance(DistanceUnit.CM) > 8 ){
            drive(.3f, 0, 0);
        }
    }

    public boolean pickUpBlock(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            return true;
        }
        return false;
    }

    public boolean driveToBuildingZone(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            return true;
        }
        return false;
    }

    public boolean placeBlock(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( clock.seconds() < 5 ){

        }else{
            return true;
        }
        return false;
    }

    public boolean turnToParkingLineBlock(){
        if( !moving ){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){
            turner.turnDegrees(90);
        }else{
            return true;
        }
        return false;
    }
}

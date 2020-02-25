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
        if(!moving){
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
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean backUpFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean turnFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean pushFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean ungripFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean moveToParkingPositionFoundation(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean extendTapeMeasure(float length){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean driveToQuarry(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean getQuarryConfiguration(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean pickUpBlock(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean driveToBuildingZone(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean placeBlock(){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds() < 5){

        }else{
            return true;
        }
        return false;
    }
    public boolean turnToParkingLineBlock(){
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

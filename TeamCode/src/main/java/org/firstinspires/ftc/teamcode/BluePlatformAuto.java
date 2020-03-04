//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import android.graphics.Color;
//
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//
//public class BluePlatformAuto extends Library{
//
//    enum State{
//        TO_FOUNDATION, FROM_FOUNDATION, PARKING, END
//    }
//
//    private State currentState;
//    private ElapsedTime clock = new ElapsedTime();
//    private boolean moving = false;
//    private final double SCALE_FACTOR = 255;
//    private float[] hsvValues = { 0F, 0F, 0F };
//    private Turning turner;
//
//    @Override
//    public void init(){
//        hardwareInit();
//        currentState = State.TO_FOUNDATION;
//        turner = new Turning();
//        turner.initImu(hardwareMap);
//    }
//
//    public void init_loop(){
//        Telemetry();
//    }
//
//    public void loop(){
//        /*
//        Loop constantly checks state, and then executes a command based on this.
//        */
//        if( currentState == State.TO_FOUNDATION ){
//            ToFoundation();
//        }
//        if( currentState == State.FROM_FOUNDATION ){
//            FromFoundation();
//        }
//        if( currentState == State.PARKING ){
//            Parking();
//        }
//        if( currentState == State.END ){
//            Stop();
//        }
//
//        Telemetry();
//    }
//
//    private void ToFoundation(){
//        if( !moving ){
//            clock.reset();
//            moving = true;
//            //        }else if(clock.seconds()<.75){
//            //            drive(0,0,-.5f);
//        }else if( distanceLeft.getDistance(DistanceUnit.CM) <= 80 || distanceRight.getDistance(DistanceUnit.CM) <= 80 ){
//            drive(-.75f, 0, 0);
//        }else{
//            moving = false;
//            drive(0, 0, 0);
//            currentState = State.FROM_FOUNDATION;
//        }
//    }//distanceLeft reading to the platform is 90cm
//
//    private void FromFoundation(){
//        if( !moving ){
//            clock.reset();
//            moving = true;
//            gripFoundation(true);
//        }else if( clock.seconds() < 2 ){
//            gripFoundation(true);
//        }else if( clock.seconds() > 2 && ( distanceLeft.getDistance(DistanceUnit.CM) >= 30 ) ){ //(!front1.isPressed()||!front2.isPressed())
//            gripFoundation(true);
//            drive(.75f, 0, 0);//drives until touching wall
//        }else if( distanceLeft.getDistance(DistanceUnit.CM) > 10 ){ //(!front1.isPressed()||!front2.isPressed())
//            gripFoundation(true);
//            drive((float) ( distanceLeft.getDistance(DistanceUnit.CM) / 100 + .1 ), 0, 0);//drives until touching wall
//        }else{
//            gripFoundation(false);
//            moving = false;
//            drive(0, 0, 0);
//            currentState = State.PARKING;
//        }
//    }//wall reading is about 1cm
//
//    private void Parking(){
//        gripFoundation(false);
//        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
//        if( !moving ){
//            clock.reset();
//            moving = true;
//        }else if( hsvValues[0] >= 180 || clock.seconds() >= 6 ){
//            moving = false;
//            drive(0, 0, 0);
//            currentState = State.END;
//        }else if( clock.seconds() >= 5 ){
//            drive(0, 0, -.3f);
//        }else{
//            drive(0, 0, .4f);
//        }
//
//        if( distanceLeft.getDistance(DistanceUnit.CM) > 8 || distanceRight.getDistance(DistanceUnit.CM) > 8 ){
//            drive(.3f, 0, 0);
//        }
//    }
//
//    private void Telemetry(){
//        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
//        telemetry.addData("Red: ", color.red());
//        telemetry.addData("Green: ", color.green());
//        telemetry.addData("Blue: ", color.blue());
//        telemetry.addData("Light: ", color.alpha());
//        telemetry.addData("Hue: ", hsvValues[0]);
//        telemetry.addData("Saturation: ", hsvValues[1]);
//        telemetry.addData("Value: ", hsvValues[2]);
//
//        telemetry.addData("Millis since State Start: ", clock.seconds());
//        telemetry.addData("State: ", currentState);
//        telemetry.addData("Distamce Left: ", distanceLeft.getDistance(DistanceUnit.CM));
//        telemetry.addData("Distamce Right: ", distanceRight.getDistance(DistanceUnit.CM));
//    }
//
//    private void Stop(){
//        moving = false;
//        drive(0, 0, 0);
//    }
//}
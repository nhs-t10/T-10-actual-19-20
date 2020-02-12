package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Test Jefferson")
public class TestJefferson extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = {0F, 0F, 0F};

    enum State{
        TELEOP, TO_FOUNDATION, FROM_FOUNDATION, PARKING
    }
    private State currentState;

    public void init(){
        hardwareInit();
        driveInit();
    }
    private float[] sums;
    public void loop(){
        if( currentState == State.TELEOP){
            TeleOpDrive();
        }else if( currentState == State.TO_FOUNDATION){
            DriveToFoundation(true);
        }else if( currentState == State.FROM_FOUNDATION){
            BackUpFoundation();
        }else if( currentState == State.PARKING){
            ColorParking(true,true);
        }

        telemetry.addData("Current State: ", currentState);
    }

    private void TeleOpDrive(){
        boolean a = gamepad1.a;
        boolean b = gamepad1.b;
        boolean a2 = gamepad2.a;
        boolean b2 = gamepad2.b;
        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x; //Rotating in place

        //If controller two gives any commands (true) than the robot will use those inputs
        //Otherwise, it will use the inputs of controller one

        if( a2 || b2 ){
            intake(a2, b2);
        }else{
            intake(a, b);
        }

        if(gamepad1.dpad_up){
            currentState = State.TO_FOUNDATION;
        }else if(gamepad1.dpad_right){
            currentState = State.FROM_FOUNDATION;
        }else if(gamepad1.dpad_down){
            currentState = State.PARKING;
        }else if(gamepad1.dpad_left){
            currentState = State.TELEOP;
        }

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if( mode == DRIVING.Slow ){
            sums = drive(linear / 2, rotation / 2, side / 2); // slow driving
        }else if( mode == DRIVING.Fast ){
            sums = drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("Mode: ", sums[0]);
        telemetry.addData("Front Left: ", sums[0]);
        telemetry.addData("Front Right: ", sums[1]);
        telemetry.addData("Back Left: ", sums[2]);
        telemetry.addData("Back Right: ", sums[3]);
        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
    }

    private void BackUpFoundation(){
        if (!moving){
            clock.reset();
            moving = true;
//            gripFoundation(true);
        }else if(clock.seconds() < 2){
//            gripFoundation(true);//this grips the foundation
        }else if(clock.seconds() > 2 && ( distance.getDistance(DistanceUnit.CM) >= 30)){ //(!front1.isPressed()||!front2.isPressed())
//            gripFoundation(true);
            drive(.75f,0,0);//drives until touching wall
        }else if( distance.getDistance(DistanceUnit.CM) > 10){ //(!front1.isPressed()||!front2.isPressed())
//            gripFoundation(true);
            drive((float)( distance.getDistance(DistanceUnit.CM)/100+.1),0,0);//drives until touching wall
        }else{
//            gripFoundation(false);
            moving = false;
            drive(0,0,0);
            //            currentState = State.PARKING;
        }
    }//wall reading is about 1cm
    private void ColorParking(boolean isBlue, boolean isFoundation){
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        boolean isDriveRight = isBlue==isFoundation;
        if(!moving){
            clock.reset();
            moving = true;
        }else if(isBlue&&(hsvValues[0]>=180||clock.seconds()>=6)){
            moving = false;
            drive(0,0,0);
            //            currentState = State.END;
        }else if(!isBlue&&(hsvValues[0]<=60||clock.seconds()>=6)){
            moving = false;
            drive(0,0,0);
            //            currentState = State.END;
        }else if(clock.seconds()>=5){
            if(isDriveRight){
                drive(0, 0, -.3f);
            }else{
                drive(0, 0, .3f);
            }
        }else{
            if(isDriveRight){
                drive(0,0,.4f);
            }else{
                drive(0,0,-.4f);
            }
        }

        if( distance.getDistance(DistanceUnit.CM)>8 || distance.getDistance(DistanceUnit.CM)>8){
            drive(.3f,0,0);
        }
    }
    private void DriveToFoundation(boolean isBlue){
        if(!moving){
            clock.reset();
            moving = true;
        }else if(clock.seconds()<.75){
            if(isBlue){
                drive(0,0,-.5f);
            }else{
                drive(0,0,.5f);
            }
        }else if(distance.getDistance(DistanceUnit.CM)<=80){
            drive(-.75f,0,0);
        }else{
            moving = false;
            drive(0,0,0);
            //            currentState = State.FROM_FOUNDATION;
        }
    }//distanceLeft reading to the platform is 90cm
}
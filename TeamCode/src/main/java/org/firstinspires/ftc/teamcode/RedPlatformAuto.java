package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Red Platform")//do not delete this test class used by sasha
public class RedPlatformAuto extends Library {
    public final int platformDistance = 60;
    public final float driveSpeed = .75f;

    enum State{
        TO_PLATFORM, FROM_PLATFORM, PARKING, END
    }
    State state;
    int i = 0;
    long startTime, startTime2, startTime3, duration, duration2, duration3;

    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        state = State.TO_PLATFORM;
    }
    public void loop()
    {
        telemetry.addData("Red color: ", color.red());
        telemetry.addData("State: ", state);

        if(state == State.TO_PLATFORM)
        {
            
            startTime = System.nanoTime();
            duration = (System.nanoTime() - startTime)/1000000000;
            slideForEncoders(60, -100);
            while(duration<2){
                duration = (System.nanoTime() - startTime)/1000000000;//divide by 1000000000 for seconds
            }

            while(color.red()<20){
                drive(1f,0,0);//drives until touching wall
            }
            drive(0,0,0);

            state = State.FROM_PLATFORM;
        }

        if(state == State.FROM_PLATFORM)
        {
            startTime2 = System.nanoTime();
            duration2 = (System.nanoTime() - startTime2)/1000000000;
//            grabber.setPosition(1);
            while(duration2<=2){
                duration2 = (System.nanoTime() - startTime2)/1000000000;//divide by 1000000000 for seconds
            }

            while(!front1.isPressed()&&!front2.isPressed()){
                drive(-1f,0,0);//drives until touching wall
            }
            drive(0,0,0);

            state = State.PARKING;
        }

        if(state == State.PARKING)
        {
            startTime3 = System.nanoTime();
            duration3 = (System.nanoTime() - startTime3)/1000000000;
            slideForEncoders(60, 100);
            while(duration3<2){
                duration3 = (System.nanoTime() - startTime3)/1000000000;//divide by 1000000000 for seconds
            }

            while(color.red()<20){
                drive(0,0,1);//drives until touching wall
            }
            drive(0,0,0);

            state = State.END;
        }
    }
}


//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
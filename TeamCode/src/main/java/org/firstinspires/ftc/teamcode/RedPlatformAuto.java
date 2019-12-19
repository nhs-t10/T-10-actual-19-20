package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Red Platform")//do not delete this test class used by sasha
public class RedPlatformAuto extends Library {
    public final int platformDistance = 60;
    public final float driveSpeed = .75f;

    enum State{
        TOPLATFORM, FROMPLATFORM, PARKING, END
    }
    State state;
    int i = 0;
    long startTime;
    long endTime;
    long duration;

    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        state = State.TOPLATFORM;
    }
    public void loop()
    {
        telemetry.addData("Red color: ", color.red());
        telemetry.addData("State: ", state);

        if(state == State.TOPLATFORM)
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

            state = State.FROMPLATFORM;
        }

        if(state == State.FROMPLATFORM)
        {
//            startTime = System.nanoTime();
//            duration = (System.nanoTime() - startTime)/1000000000;
//            grabber.setPosition(1);
//            while(duration<2){
//                duration = (System.nanoTime() - startTime)/1000000000;//divide by 1000000000 for seconds
//            }

            while(!front1.isPressed()&&!front2.isPressed()){
                drive(-1f,0,0);//drives until touching wall
            }
            drive(0,0,0);

            state = State.PARKING;
        }

        if(state == State.PARKING)
        {
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
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
        PLATFORM, PARKING, END
    }
    State state;


    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        state = State.PLATFORM;
    }
    public void loop()
    {
        if(state == State.PLATFORM)
        {
//            slideForEncoders(60, -100);
//            driveForEncoders(120,-100);
            //grip(true);
//            driveForEncoders(platformDistance+10,-driveSpeed); //drives to platform with extra
//            while(!front1.isPressed()||!front2.isPressed()){
//                drive(-1f,0,0);//drives until touching wall
//            }
//            drive(0,0,0);
//            grip(false);

            state = State.PARKING;
        }

        if(state == State.PARKING)
        {
//            slideForEncoders(150, 100);
            while(!front1.isPressed()||!front2.isPressed()){
                drive(-1f,0,0);//drives until touching wall
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
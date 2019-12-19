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
    public int step;
    public boolean startPosSet;
    public float startPos;
    public boolean drive;

    enum State{
        PLATFORM, PARKING, END
    }
    State state;


    @Override public void init() {
        hardwareInit();
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
        state = State.PLATFORM;
        step = 1;
        startPosSet = false;
        drive = true;
    }
    public void loop()
    {
        if(state == State.PLATFORM)
        {
            if(step == 1 && startPosSet == false){
                startPos = getStartPos();
            }
            if(step == 1 && startPosSet == true){
                if(drive) {
                    drive = slideForEncoders(60f, 0.5f, startPos);
                }else{
                    step = 2;
                }
            }
            if(step == 2){
                telemetry.addData("Step 2 woot", true);
            }
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
            //driveUntil(ColorSensor,0,0,DRIVE_SPEED);  //commented because I need to define color sensor
            //drive(0,0,0);
            state = State.END;
        }
    }
}


//drive with encoders distance to foundation + overshoot
//move foundation gripper servo(s)
//drive back until touch sensor is pressed
//if it hasn't been pressed by distance to wall + overshoot
//backtrack overshoot
//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//@TeleOp(name = "Leroy's TeleOp Acceleration")
//public class TeleSmoothing extends Library {
//    imuData imu;
//    Turning turner;
//    double angleTurned = 0;
//    ExponentialSmoothing test;
//    double goalAccel = 1.5;
//    double checkAccel = 0.008;
//
//    public void init()
//    {
//        hardwareInit();
//        imu = new imuData(hardwareMap);
//        imu.initImu();
//        turner = new Turning();
//        test = new ExponentialSmoothing();
//    }
//
//    public void loop(){
//        // stuff
//        if(gamepad1){
//
//        }
//        else{
//            test.resetClock();
//        }
//
//        float linear = gamepad1.left_stick_y; //Forward and back
//        float side = gamepad1.left_stick_x; //Right and left
//        float rotation = gamepad1.right_stick_x; //Rotating in place
//    }
//}

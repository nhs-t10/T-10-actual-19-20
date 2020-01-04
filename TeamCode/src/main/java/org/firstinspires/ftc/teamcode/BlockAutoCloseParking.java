//package org.firstinspires.ftc.teamcode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//@Autonomous(name="blue close block")
//public class BlockAutoCloseParking extends Library{
//    public final int platformDistance = 60;
//    public final float driveSpeed = .75f;
//    long startTime;
//    long endTime;
//    long duration;
//    enum State{
//        BLOCK_SEARCHING, BLOCK_GRABBING, PARKING, END
//    }
//    State state;
//    int i = 0;
//
//    @Override public void init() {
//        hardwareInit();
//        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);// we may use more motor encoders but some of the encoders have weird values
//        state = State.PARKING;
//    }
//    public void loop()
//    {
//        telemetry.addData("Red color: ", color.red());
//        telemetry.addData("State: ", state);
//
//        if(state == State.BLOCK_SEARCHING)
//        {
//            //nothing yet
//        }
//
//        if(state == State.BLOCK_GRABBING)
//        {
//            //nothing here lmao
//        }
//
//        if(state == State.PARKING)
//        {
//            startTime = System.nanoTime();
//            duration = (System.nanoTime() - startTime)/1000000000;
//            driveForEncoders(60, -1);
//            while(duration<2){
//                duration = (System.nanoTime() - startTime)/1000000000;//divide by 1000000000 for seconds
//            }
//
//            while(color.red()<20){
//                drive(1f,0,0);//drives until touching wall
//            }
//            drive(0,0,0);
//
//            state = State.END;
//        }
//    }
//}

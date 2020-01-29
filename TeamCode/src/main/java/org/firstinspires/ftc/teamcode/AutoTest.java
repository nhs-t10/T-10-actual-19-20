package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="encoders testing")//do not delete this test class used by sasha
public class AutoTest extends Library {

    EncodersMethod encoderObject = new EncodersMethod();
    EncodersMethod encoderObjectTwo = new EncodersMethod();
    enum State
    {
        VIBIN, ROLLIN, ZOOMIN;
        private static State[] vals = values();
        public State next()
        {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }

    State stat = State.ROLLIN;

    @Override public void init() {
        hardwareInit();

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void loop()
    {

        if(stat == State.ROLLIN&&encoderObject.driveCondition(stat)){
            encoderObject.setGoalAndStart(encoderObject.convertToTicks(100 ),1);
        }
        //        if(stat == State.ZOOMIN&&encoderObject.driveCondition(stat))
        //        {
        //            encoderObject.setGoalAndStart(encoderObject.convertToTicks(-100),2);
        //        }



    }


    //how to use the mini-class:
    //EncodersMethod NAME = new EncodersMethod();     //this creates an object of the class, which allows you to use the methods
    //if(enum == State.STATE && encoderObject.driveCondition(enum)){                  //this is the if statement, which checks if the state is correct and the goal hasn't been reached
    //NAME.setGoalAndStart(getEncoderValue(),NAME.convertToTicks(100),MOVEMENT_NUM); }         //this is where you put your directions in(how far you want to move and shit
    private class EncodersMethod
    {
        float goal;
        float scalar;
        int receipt = 0;
        boolean isDone = false;
        int receiptTest = 0;
        public EncodersMethod()
        {
            goal = 1000000000;//so that the code enters the loop during the first iteration
            receipt = 0;
        }
        public void slowDown()//this will be used later
        {
            scalar = 1;
        }
        public boolean driveCondition(State stateInput)//method that you will use, this is used as a condition, which will move the robot
        {

            telemetry.addData("goal",(goal));
            telemetry.addData("current pos",getEncoderValue());
            telemetry.addData("is done?", isDone);
            telemetry.addData("recapt", receiptTest);
            slowDown();
            if(Math.abs(goal) < Math.abs(getEncoderValue()))
            {
                isDone=true;
                drive(0,0,0);
                goal += 10000000;//this allows the first iteration happen
                stateInput.next();
                return false;
            }else
            {

                isDone=false;
                drive(-1,0,0);
                return true;
            }

        }


        public void setGoalAndStart(float goal,int recieptCheck){ //use this method to set the destination of your travel
            if( receipt != recieptCheck)
            {
                receiptTest++;
                receipt = recieptCheck;
                frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                this.goal = goal;
            }

        }

        public float convertToTicks(float distanceInCm)//converts centimeters to encoders ticks so it can be used in start goal
        {
            return distanceInCm*537.6f/25.5f;
        }
    }
}
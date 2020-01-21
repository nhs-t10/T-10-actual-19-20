package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="encoders testing")//do not delete this test class used by sasha
public class AutoTest extends Library {
    public final int DRIVE_TO_PLATFORM = 100;
    public final float DRIVE_SPEED = .8f;

    EncodersMethod encoderObject = new EncodersMethod();

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
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }




    public void loop()
    {

        if(stat == State.ROLLIN&&encoderObject.driveCondition(stat)){
            encoderObject.setGoalAndStart(getEncoderValue(),encoderObject.convertToTicks(100),1);
        }
        if(stat == State.ZOOMIN&&encoderObject.driveCondition(stat))
        {
            encoderObject.setGoalAndStart(getEncoderValue(),encoderObject.convertToTicks(-100),2);
        }



    }


    //how to use the mini-class:
    //EncodersMethod NAME = new EncodersMethod();     //this creates an object of the class, which allows you to use the methods
    //if(enum == State.STATE && encoderObject.driveCondition(enum)){                  //this is the if statement, which checks if the state is correct and the goal hasn't been reached
    //NAME.setGoalAndStart(getEncoderValue(),NAME.convertToTicks(100),MOVEMENT_NUM); }         //this is where you put your directions in(how far you want to move and shit
    private class EncodersMethod
    {
        float startPos;
        float goal;
        float scalar;
        int receipt;
        public EncodersMethod()
        {
            startPos = 0;
            goal = 1000000000;//so that the code enters the loop during the first iteration
            receipt = 0;
        }
        public void slowDown()//this will be used later
        {
            scalar = 1;
        }
        public boolean driveCondition(State stateInput)//method that you will use, this is used as a condition, which will move the robot
        {

            slowDown();
            if(Math.abs(goal) < Math.abs(getEncoderValue() - startPos))
            {
                drive(0,0,0);
                goal += 10000000;//this allows the first iteration happen
                stateInput.next();
                return false;
            }else
            {
                drive(scalar,0,0);
                return true;
            }

        }


        public void setGoalAndStart(float startPos, float goal,int recieptCheck){ //use this method to set the destination of your travel
            if( receipt != recieptCheck)
            {
                this.startPos = startPos;
                this.goal = goal;
            }

        }

        public float convertToTicks(float distanceInCm)//converts centimeters to encoders ticks so it can be used in start goal
        {
            return distanceInCm*1120/36;//probably is wrong but oh well
        }
    }
}
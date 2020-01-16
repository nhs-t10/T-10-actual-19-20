package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


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
        encodersInit();

    }




    public void loop()
    {

        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;
        boolean a = gamepad1.a;
        boolean b = gamepad1.b;
        boolean liftUp = gamepad1.right_bumper;
        boolean liftDown = gamepad1.left_bumper;
        telemetry.addData("front left:",frontLeft.getCurrentPosition());
        telemetry.addData("front right:",frontRight.getCurrentPosition());
        telemetry.addData("back left:",backLeft.getCurrentPosition());
        telemetry.addData("back right:",backRight.getCurrentPosition());

        if(stat == State.ROLLIN&&encoderObject.driveCondition(stat)){
            encoderObject.setGoalAndStart(getEncoderValue(),encoderObject.convertToTicks(100),1);
        }
        if(stat == State.ZOOMIN&&encoderObject.driveCondition(stat))
        {
            encoderObject.setGoalAndStart(getEncoderValue(),encoderObject.convertToTicks(-100),2);
        }



    }


    //how to use the mini-class:
    //EncodersMethod NAME = new EncodersMethod();
    //if(enum == State.STATE && encoderObject.driveCondition(enum)){
    //NAME.setGoalAndStart(getEncoderValue(),NAME.convertToTicks(100),1); }
    private class EncodersMethod
    {
        float startPos;
        float goal;
        float scalar;
        int receipt;
        public EncodersMethod()
        {
            startPos=0;
            goal=1000000000;
            receipt =0;
        }
        public void slowDown()//this will do more later
        {
            scalar=1;
        }
        public boolean driveCondition(State stateInput)
        {

            slowDown();
            if(Math.abs(goal)<Math.abs(getEncoderValue()-startPos))
            {
                drive(0,0,0);
                goal+=10000000;//temp addition to allow the first iteration to happen
                stateInput.next();
                return true;
            }else
            {
                drive(scalar,0,0);
                return false;
            }

        }


        public void setGoalAndStart(float startPos, float goal,int recieptCheck){
            if( receipt !=recieptCheck)
            {
                this.startPos=startPos;
                this.goal=goal;
            }

        }

        public float convertToTicks(float distanceInCm)//this will do stuff soon
        {
            return distanceInCm*1120/36;//probably is wrong but oh well
        }
    }
}
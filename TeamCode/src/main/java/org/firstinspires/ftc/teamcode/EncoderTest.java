package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "JeffersonAuto")
public class EncoderTest extends Library{
    EncoderClass method;
    boolean isOnBlueSide;

    public void init(){
        hardwareInit();
        method = new EncoderClass();
        isOnBlueSide = true;
    }

    public void loop(){
        float error = method.driveToQuarry(isOnBlueSide);
        telemetry.addData("Error", error);
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "JeffersonAuto")
public class JeffersonAuto extends Library{
    DriveToQuarryExampleClass method;
    boolean isOnBlueSide;

    public void init(){
        hardwareInit();

        method = new DriveToQuarryExampleClass();
        isOnBlueSide = true;
    }

    public void loop(){
        float error = method.driveToQuarry(isOnBlueSide);
        telemetry.addData("Error", error);
    }
}

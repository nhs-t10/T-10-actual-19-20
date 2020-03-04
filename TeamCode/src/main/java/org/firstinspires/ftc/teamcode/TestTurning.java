package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turning Test")
public class TestTurning extends Library{
    PortalTurning turner;

    public void init(){
        hardwareInit();
        turner = new PortalTurning();
        turner.initImu(hardwareMap);
    }

    public void loop(){
        float error = turner.turnDegrees(180);
        telemetry.addData("ERROR: ", error);
    }
}
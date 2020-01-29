package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turning Test")
public class TestTurning extends Library{

    public void init(){
        hardwareInit();
        turner.initImuAndTurning(hardwareMap);
    }

    public void loop(){
        turner.turnDegrees(180);
    }
}
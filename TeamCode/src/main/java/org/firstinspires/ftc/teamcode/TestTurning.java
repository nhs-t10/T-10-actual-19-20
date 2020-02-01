package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Turning Test")
public class TestTurning extends Library {
    Turning turner;

    public void init(){
        hardwareInit();
        turner = new Turning();
        turner.initImu(hardwareMap);
    }

    public void loop(){
        turner.turnDegrees(90);
    }
}
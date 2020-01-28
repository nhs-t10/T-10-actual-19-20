package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Turning Test")
public class TestTurning extends Library{

    Turning turner;
    public void init(){
        hardwareInit();
        turner = new Turning();
        turner.initImuTurning(hardwareMap);
    }

    //ElapsedTime clock = new ElapsedTime();
    //boolean turned;

    public void loop(){
        turner.turnDegrees(180);
    }
}
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Test Turning")
public class TestTurning extends Library{
    Turning turner;
    ElapsedTime clock;
    boolean started;

    public void init(){
        hardwareInit();
        turner = new Turning();
        turner.initImu(hardwareMap);
        clock = new ElapsedTime();
        started = false;
    }

    public void loop(){
        if (!started){
            clock.reset();
            started = true;
        }
        else if (clock.seconds() < 5){
            turner.turnDegrees(90);
        }
        else{
            turner.turnDegrees(-90);
        }
    }
}

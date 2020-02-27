package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Test Encoders")
public class TestEncoders extends Library{
    EncoderDriving driving;
    ElapsedTime clock;

    public void init(){
        hardwareInit();
        driving = new EncoderDriving();
        clock = new ElapsedTime();
    }

    public void loop(){
        if (clock.seconds() < 5){
            driving.encoderDrive(10);
        }
        else{
            driving.encoderStrafe(10);
        }
    }
}

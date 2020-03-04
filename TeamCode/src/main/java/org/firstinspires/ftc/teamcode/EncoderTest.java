package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Test Encoders")
public class EncoderTest extends Library{
    EncoderClass driving;
    ElapsedTime clock;
    boolean started;
    float[] error;
    float errorturn;

    public void init(){
        hardwareInit();
        driving = new EncoderClass();
        clock = new ElapsedTime();
        started = false;
        error = new float[3];
        errorturn = 0;
    }

    public void loop(){
        if(!started){
            started = true;
            clock.reset();
        }else if (clock.seconds() < 5){
            error = driving.encoderStrafe(10);
        }
        telemetry.addData("Encoder Error: ",error[0]);
        telemetry.addData("Destination: ",error[1]);
        telemetry.addData("Current: ",error[2]);
    }
}
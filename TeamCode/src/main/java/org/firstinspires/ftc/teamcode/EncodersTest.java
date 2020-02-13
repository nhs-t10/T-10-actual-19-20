package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "EncodersTest")
public class EncodersTest extends Library{
    float curEncoderValue;
    private static final double ticksPerCm = 194.13;

    public void init(){
        driveInit();
        hardwareInit();

        curEncoderValue = getEncoderValue();
    }

    public void loop(){
        if( getEncoderValue() - ticksPerCm * 100 < curEncoderValue ){
            drive(.5f, 0, 0);
        }else{
            telemetry.addLine("The Girl Next Door");
        }
    }
}

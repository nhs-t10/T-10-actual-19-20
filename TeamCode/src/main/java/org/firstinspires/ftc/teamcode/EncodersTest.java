package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "EnBROders")

public class EncodersTest extends Library
{
    boolean yeet = true;
    public void init() {
        hardwareInit();
        encodersInit();
        telemetry.addData("inited:","yessssssss");
    }
    public void loop()
    {
        yayeet();
    }
    public void yayeet()
    {
        if(yeet)
        {
            driveUntil(100);
            telemetry.addData("driven:","yessssssssss");
            yeet=false;
        }
    }
}
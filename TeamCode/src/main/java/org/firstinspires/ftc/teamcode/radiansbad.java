package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Vuforia;

@TeleOp(name = "skyston")
public class radiansbad extends Library{
    public void init(){
        hardwareInit();
        vuforiaInit();
    }

    public void loop(){
       telemetry.addData("", isSkystoneVisible());
    }
}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "?????")
public class vuforiadoesntwork extends Library{

    public void init(){
        hardwareInit();
    }
    public void loop(){
        telemetry.addData("SKYSTOEN BAD", isSkystoneVisible());
    }
}

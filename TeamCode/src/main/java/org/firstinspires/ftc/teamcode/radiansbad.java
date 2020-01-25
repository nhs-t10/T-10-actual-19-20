package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "180 degrees")
public class radiansbad extends Library{
    public void init(){
        hardwareInit();
    }
    public void loop(){
        rotateFor((float) Math.PI);
    }
}

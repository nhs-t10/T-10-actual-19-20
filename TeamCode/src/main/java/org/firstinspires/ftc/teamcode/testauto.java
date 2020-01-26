package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "wheel tests")//do not delete this test class used by sasha
public class testauto extends Library{
    //    float updatedMM;
    //    final float start = encoderStart();
    public void init(){
        hardwareInit();
    }

    public void loop(){
        boolean a = gamepad1.a;
        if( a ){
            frontLeft.setPower(.3f);
            frontRight.setPower(.3f);
            backLeft.setPower(.3f);
            backRight.setPower(.3f);
        }
    }
}

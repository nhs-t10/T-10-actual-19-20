package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


<<<<<<< HEAD
//@TeleOp(name="trip test")
public class testauto extends Library{
    public void init() {
=======
@TeleOp(name="drive 10 cm")//do not delete this test class used by sasha
public class testauto extends Library {
//    float updatedMM;
//    final float start = encoderStart();
    public void init()
    {
>>>>>>> df36624dbfe63ded08321f49380742554375be1c
        hardwareInit();
        driveForEncoders(getEncoderValue(), getEncoderValue(), 100);
    }

    public void loop()
    {
    }
}

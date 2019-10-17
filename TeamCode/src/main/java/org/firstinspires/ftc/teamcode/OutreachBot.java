package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotor.RunMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@TeleOp(name = "TeleOp")
public class OutreachBot extends OpMode{
    public static DcMotor frontLeft, frontRight;

    public void init() {
        frontLeft = hardwareMap.dcMotor.get("m0");
        frontRight = hardwareMap.dcMotor.get("m1");
    }

    public void loop() {
        float y = gamepad1.left_stick_y;
        float x = gamepad1.left_stick_x;

        float powerLeft = y*x;
        float powerRight = y*(-x);

        frontLeft.setPower(.75*powerLeft);
        frontRight.setPower(.75*powerRight);

        //linear = straight, rotation = turning, side = skating.
        //Linear - rotation will compensate one side to allow the other side to overrotate
        telemetry.addData("x value: ", x);
        telemetry.addData("y value: ", y);
    }

    //public void stop() {

    //}

}
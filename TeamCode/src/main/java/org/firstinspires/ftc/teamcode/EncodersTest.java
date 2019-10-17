package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

@TeleOp(name = "EnBROders")

public class EncodersTest extends Library{
    public void init() {
        hardwareInit();
    }

    public void loop() {
        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;
        omni(linear, rotation, side);
        telemetry.addData("front left", frontLeft.getCurrentPosition());
        telemetry.addData("front right", frontRight.getCurrentPosition());
        telemetry.addData("back left", backLeft.getCurrentPosition());
        telemetry.addData("back right", backRight.getCurrentPosition());

        if(gamepad1.a){
            driveFor(100, .75f, 0, 0);
        
        }
        if(gamepad1.b){
            turnDegrees(90);
        }

        //Every SAMPLES_PER_SECOND
        //Save floatArray values in a file in the format of "l s r"


        //defining the stuff. linear = straight, rotation = turning, side = skating.
        //Linear - rotation will compensate one side to allow the other side to overrate
    }
}
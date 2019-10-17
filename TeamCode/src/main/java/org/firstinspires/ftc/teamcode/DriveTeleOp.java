package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library
{
    double time_millis = 0.0;
    ElapsedTime t = new ElapsedTime();
    public void init() {
        hardwareInit();
    }

    public void loop() { 
        float linear = gamepad1.left_stick_y;
        float side = gamepad1.left_stick_x;
        float rotation = gamepad1.right_stick_x;
        boolean y = gamepad1.y;
        y=!y;
        boolean b = gamepad1.b;

        //linear = straight, rotation = turning, side = skating.
        //Linear - rotation will compensate one side to allow the other side to overrotate

        if(gamepad1.right_stick_button){
            mode = mode.getNext();
        }

        if(mode == DRIVING.Slow){
            omni(linear/2, rotation/2, side/2);} // slow driving
        if(mode == DRIVING.Medium) {
            omni(linear/1.5f, rotation/1.5f, side/1.5f);} // medium driving
        if(mode == DRIVING.Fast) {
            omni(linear, rotation, side);} // fast driving
        platform(y);
        // test Blinkin (LED Strip) by setting it to "Lawn Green"
        setBlinkinPattern(86);
        // change Blinkin (LED Strip) color to "Orange" if B is pressed on gamepad 1
        if (b) {
            setBlinkinPattern(83);
        }
        //omni(linear, rotation, side);
        String vals = String.valueOf(linear) + "\n " +String.valueOf(rotation) + "\n " + String.valueOf(side);
        telemetry.addData("Values:", vals);
        telemetry.addData("Driving Mode:",mode);
        /*if(gamepad1.left_trigger > 0 && scoreMotor.getCurrentPosition() > -6000){
            scoreMotor.setTargetPosition(-6000);
            scoreMotor.setPower(1);
        }
        else if (gamepad1.right_trigger > 0 && scoreMotor.getCurrentPosition() < 6000){
            scoreMotor.setTargetPosition(6000);
            scoreMotor.setPower(-1);
        }
        else{
            scoreMotor.setPower(0f);
        }*/

        /* if(gamepad1.right_stick_button && gamepad1.left_stick_button){
            shutdown();
            telemetry.addData("SLOW DOWN PARTNER", "RESETING...");
        }
        telemetry.addData("Driving Mode:",mode); */
    }

    //public void stop() {

    //}

}
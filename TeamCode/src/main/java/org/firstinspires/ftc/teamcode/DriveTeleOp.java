package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library {
	double time_millis = 0.0;
	ElapsedTime t = new ElapsedTime();

	public void init() {
		hardwareInit();
	}

	public void loop() {
		float linear = gamepad1.left_stick_y;
		float side = gamepad1.left_stick_x;
		float rotation = gamepad1.right_stick_x;

		boolean y = gamepad1.y; //platform hook
		boolean a = gamepad1.a; //positive intake
		boolean b = gamepad1.b; //negative intake
		// float grab = gamepad1.right_trigger;
		float grabberRight = gamepad1.left_trigger; //rotate grabber right
		float grabberLeft = gamepad1.right_trigger; //rotate grabber left
		boolean x = gamepad1.x; //make grabber close
		float intake = 0;

		float liftUp = gamepad1.right_bumper;
		float rightUp = gamepad1.left_bumper;

		boolean grip = false;
		int count = 0;

		if a {
			intake = 1;
		}
		if b {
			intake  = -1;
		}
		
		if liftUp {
			lift(1);
		}
		if liftDown {
			lift(-1);
		}

		if(x.isPressed() && !grip && count == 0{
		
			grip = true;
			grip(grip);
			count = 1;
		}else if (x.isPressed() && grip && count == 1{
		
			grip = false;
			grip(grip);
			count = 0;
		
		}

		y = !y; 

		// linear = straight, rotation = turning, side = skating.
		// Linear - rotation will compensate one side to allow the other side to overrotate

		// if(gamepad1.right_stick_button){
		// mode = mode.getNext();
		// }
		//
		// if(mode == DRIVING.Slow){
		// drive(linear/2, rotation/2, side/2);} // slow driving
		// if(mode == DRIVING.Medium) {
		// drive(linear/1.5f, rotation/1.5f, side/1.5f);} // medium driving
		// if(mode == DRIVING.Fast) {
		// drive(linear, rotation, side);} // fast driving
		

		// test Blinkin (LED Strip) by setting it to "Lawn Green"

		/*
		 * setBlinkinPattern(86); // change Blinkin (LED Strip) color to "Orange" if B
		 * is pressed on gamepad 1 if (b) { setBlinkinPattern(83);r }
		 */
		platform(y);
		drive(linear, rotation, side, intake);
		gRotate(grabberRight, grabberLeft);

		

		String vals = String.valueOf(linear) + "\n " + String.valueOf(rotation) + "\n " + String.valueOf(side);
		telemetry.addData("Values:", vals);
		// telemetry.addData("Driving Mode:",mode);
		/*
		 * if(gamepad1.left_trigger > 0 && scoreMotor.getCurrentPosition() > -6000){
		 * scoreMotor.setTargetPosition(-6000); scoreMotor.setPower(1); } else if
		 * (gamepad1.right_trigger > 0 && scoreMotor.getCurrentPosition() < 6000){
		 * scoreMotor.setTargetPosition(6000); scoreMotor.setPower(-1); } else{
		 * scoreMotor.setPower(0f); }
		 */

		/*
		 * if(gamepad1.right_stick_button && gamepad1.left_stick_button){ shutdown();
		 * telemetry.addData("SLOW DOWN PARTNER", "RESETING..."); }
		 * telemetry.addData("Driving Mode:",mode);
		 */
	}

			

	public void stop() {

	}

}
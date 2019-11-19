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
		
		float grabberRight = gamepad1.left_trigger; //rotate grabber right
		float grabberLeft = gamepad1.right_trigger; //rotate grabber left
		boolean x = gamepad1.x; //make grabber open/close
		float intake = 0;

		boolean liftUp = gamepad1.right_bumper;
		boolean liftDown = gamepad1.left_bumper;

		boolean grip = false;
		int count = 0;

		if (a) {
			intake = 1;
		}else{
			intake = 0;
		}
		if (b) {
			intake  = -1;
		}else{
			intake = 0;
		}

		if (liftUp) {
			lift(1);
		}else{
			lift(0);
		}
		if (liftDown) {
			lift(-1);
		}else{
			lift(0);
		}

		if(x && !grip && count == 0){
			grip = true;
			grip(grip);
			count = 1;
		}else if(x && grip && count == 1){
			grip = false;
			grip(grip);
			count = 0;
		}

		y = !y; //inverts platform hook for ease of use


		// test Blinkin (LED Strip) by setting it to "Lawn Green"

		/*
		 * setBlinkinPattern(86); // change Blinkin (LED Strip) color to "Orange" if B
		 * is pressed on gamepad 1 if (b) { setBlinkinPattern(83);r }
		 */
		platform(y);
		drive(linear, rotation, side, intake);
		//grotate(grabberLeft, grabberRight);

		

		String vals = String.valueOf(linear) + "\n " + String.valueOf(rotation) + "\n " + String.valueOf(side);
		telemetry.addData("Values:", vals);
		
	}

			

	public void stop() {

	}

}
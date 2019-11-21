package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library {
	private boolean grip = false;
	private int count = 0;

	public void init() {
		hardwareInit();
	}

	public void loop() {
		float linear = gamepad1.left_stick_y;
		float side = gamepad1.left_stick_x;
		float rotation = gamepad1.right_stick_x;

		boolean y = gamepad1.y; //platform hook
		boolean y2 = gamepad2.y;

		boolean a = gamepad1.a; //positive intake
		boolean b = gamepad1.b; //negative intake
		boolean a2 = gamepad2.a;
		boolean b2 = gamepad2.b;

//
//		float grabberRight = gamepad1.left_trigger; //rotate grabber right
//		float grabberLeft = gamepad1.right_trigger; //rotate grabber left
//		float grabberRight2 = gamepad2.left_trigger; //rotate grabber right
//		float grabberLeft2 = gamepad2.right_trigger; //rotate grabber left

		boolean x = gamepad1.x; //make grabber open/close
		boolean x2 = gamepad2.x; //make grabber open/close


		boolean liftUp = gamepad1.right_bumper; //Lift control
		boolean liftDown = gamepad1.left_bumper;
		boolean liftUp2 = gamepad2.right_bumper;
		boolean liftDown2 = gamepad2.left_bumper;

		if (a2 || b2) {
			if (a2) {
				intake(1);
			} else {
				intake(0);
			}

			if (b2) {
				intake(-1);
			} else {
				intake(0);
			}
		} else {
			if (a) {
				intake(1);
			} else {
				intake(0);
			}

			if (b) {
				intake(-1);
			} else {
				intake(0);
			}

		}

		if (liftUp2 || liftDown2) {
			if (liftUp2) {
				lift(-1);
			} else {
				lift(0);
			}

			if (liftDown2) {
				lift(1);
			} else {
				lift(0);
			}
		} else {
			if (liftUp) {
				lift(-1);
			} else {
				lift(0);
			}

			if (liftDown) {
				lift(1);
			} else {
				lift(0);
			}
		}

			if (x && !grip && count == 0 || x2 && !grip && count == 0) {
				grip = true;
				grip(grip);
				count = 1;
			} else if (x && grip && count == 1 || x2 && grip && count == 1) {
				grip = false;
				grip(grip);
				count = 0;
			}
			y = !y;
			y2 = !y2; //inverts platform hook for ease of use


			// test Blinkin (LED Strip) by setting it to "Lawn Green"

			/*
			 * setBlinkinPattern(86); // change Blinkin (LED Strip) color to "Orange" if B
			 * is pressed on gamepad1 if (b) { setBlinkinPattern(83);r }
			 */
			platform(y2);
			platform(y);


			drive(linear, rotation, side);
			//gRotate(grabberLeft2, grabberRight2);
			//gRotate(grabberLeft, grabberRight);

			String vals = String.valueOf(linear) + "\n " + String.valueOf(rotation) + "\n " + String.valueOf(side);
			telemetry.addData("Values:", vals);

		}
		public void stop(){
	}
}
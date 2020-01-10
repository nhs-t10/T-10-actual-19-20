package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library {
	public void init(){
		hardwareInit();
		backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}

	public void loop(){
		//Intake for gripFoundation, liftGivenControllerValues, and grabber values
		boolean a = gamepad1.a;
		boolean b = gamepad1.b;
		boolean a2 = gamepad2.a;
		boolean b2 = gamepad2.b;
		boolean x = gamepad1.x;
		boolean x2 = gamepad2.x;
		boolean y = gamepad1.y;
		boolean y2 = gamepad2.y;
		boolean liftUp = gamepad1.right_bumper;
		boolean liftDown = gamepad1.left_bumper;
		boolean liftUp2 = gamepad2.right_bumper;
		boolean liftDown2 = gamepad2.left_bumper;
		boolean skystone = gamepad1.dpad_up;

		//Intake for movement and rotation values
		float linear = gamepad1.left_stick_y;
		float side = gamepad1.left_stick_x;
		float rotation = gamepad1.right_stick_x;
//		float grabberLeft = gamepad1.right_trigger;
//		float grabberRight = gamepad1.left_trigger;
//		float grabberRight2 = gamepad2.left_trigger;
//		float grabberLeft2 = gamepad2.right_trigger;

		//If controller two gives any commands (true) than the robot will use those inputs
		//Otherwise, it will use the inputs of controller one
		if(a2 || b2){
			intake(a2, b2);
		}else{
			intake(a, b);
		}//this is proper bracket etiquette

		if(x2){
			gripStone(true);
		}else{
			gripStone(x);
		}

		if(y2){
			gripFoundation(true);
		}else{
			gripFoundation(y);
		}

		if(liftUp2 || liftDown2){
			liftGivenControllerValues(liftUp2, liftDown2);
		}else{
			liftGivenControllerValues(liftUp, liftDown);
		}


//		if(grabberRight2 != 0 || grabberLeft2 != 0){
//			gRotate(grabberLeft2, grabberRight2);
//		}else{
//			gRotate(grabberLeft, grabberRight);
//		}

		if(gamepad1.right_stick_button){
			mode = mode.getNext();
		}

		if(mode == DRIVING.Slow){
			drive(linear/2, rotation/2, side/2); // slow driving
		}

		if(mode == DRIVING.Medium){
			drive(linear/1.5f, rotation/1.5f, side/1.5f); // medium driving
		}

		if(mode == DRIVING.Fast){
			drive(linear, rotation, side); // fast driving
		}

		telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
	}
}
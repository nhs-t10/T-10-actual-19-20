package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp")
public class DriveTeleOp extends Library
{
	boolean subroutine;

	public void init()
	{
		hardwareInit();
	}

	public void loop()
    {
    	if (isSkystoneVisible() && distance.getDistance(DistanceUnit.CM) > 5)
		{
			subroutine = true;
			drive(.5f, 0, 0);

			return;
		}

    	if (subroutine)
		{
			gripStone(false);
			driveForEncoders(getEncoderValue(), getEncoderValue(), -50);

			subroutine = true;
		}

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

		//Intake for movement and rotation values
		float linear = gamepad1.left_stick_y;
		float side = gamepad1.left_stick_x;
		float rotation = gamepad1.right_stick_x;

		if(a2 || b2)
			intake(a2, b2);
		else
			intake(a, b);

		if(x2)
			gripStone(true);
		else
			gripStone(x);

		if(y2)
			gripFoundation(true);

		else
			gripFoundation(y);

		if(liftUp2 || liftDown2)
			liftGivenControllerValues(liftUp2, liftDown2);
		else
			liftGivenControllerValues(liftUp, liftDown);

		telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
	}
}
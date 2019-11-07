package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "PlatfromAuto")
public class PlatformAuto extends Library {
	enum state {
		MOVING, PUSHING;
	}

	state currentState = null;

	public void init() {
		hardwareInit();
		currentState = state.MOVING;
	}

	public void loop() {
		// Loop constantly checks state, and then executes a command based on this.
		if (currentState == state.MOVING) {
			MoveToPlatform();
		}
		telemetry.addData("Current State: ", currentState);
	}

	public void MoveToPlatform() {
		platform(false);// sets platform grabber up
		driveFor(75, .75f, 0, 0);// moves to platform
		platform(true);// sets platform grabber down
		driveForNeg(-75, -.75f, 0, 0);// moves back
		platform(false);// sets platform grabber up
		currentState = state.PUSHING;
	}
}
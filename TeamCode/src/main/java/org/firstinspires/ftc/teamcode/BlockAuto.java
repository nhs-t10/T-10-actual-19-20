package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BlockAuto")
public class BlockAuto extends Library {
	/*
		T-10 Preliminary Autonomous
		This is based on the assumption that we are:
            - Starting on the Block side (just outside tape)
            - Doing all 4 possible autonomous tasksks
	 */

	//constants and state declaration
	enum state {
		DETECTING_SKYSTONE, GRABBING_SKYSTONE, DELIVERING, PLACING, REPOSITIONING, NAVIGATING
	}

	state currentState = null;

	public void init() {
		hardwareInit();
		currentState = state.DELIVERING;
	}

	public void loop() {
		//Loop constantly checks state, and then executes a command based on this.
		if (currentState == state.DETECTING_SKYSTONE){
			detectSkystone();
		}
		if (currentState == state.GRABBING_SKYSTONE){
			grabSkystone();
		}
		if (currentState == state.DELIVERING) {
			deliverSkystone();
		}
		if (currentState == state.PLACING) {
			placeStone();
		}
		if (currentState == state.REPOSITIONING) {
			reposition();
		}
		if (currentState == state.NAVIGATING) {
			navigate();
		}
		telemetry.addData("Current State: ", currentState);
	}

	//The first state of autonomous: Robot detects which stone is a skystone
	public void detectSkystone() {
		//boolean aligned = skystone.isAligned();   <-- Change skystone and check method name
		//if(aligned) {
		//	currentState = state.GRABBING_SKYSTONE;
		//}
		//else {
		//	skystone.skystoneAlign();  <-- Change skystone and check method name
		//}
	}

	public void grabSkystone() {
		//driveFor(29.75f, 1, 0, 0, 0)
		//Grab the block <-- Talk with hardware about how this will be done
		currentState = state.DELIVERING;
	}

	public void deliverSkystone() {
		//might need to back up a bit to avoid the middle structure
		//turnDegrees(90);
		//if(skystoneLocation == 4)
		//	driveFor(DISTANCE, 1, 0, 0, 0)  <-- Fill in distance with the actual distance, same for below
		//if(skystoneLocation == 5)
		//	driveFor(DISTANCE, 1, 0, 0, 0)
		//if(skystoneLocation == 5)
		//	driveFor(DISTANCE, 1, 0, 0, 0)
		currentState = state.PLACING;
	}

	public void placeStone() {
		currentState = state.REPOSITIONING;
	}

	public void reposition() {
		// if aligned with the building platform, push it into the corner
		// talk with hardware about which part will be used to push
		// if not aligned, use CV to drive and turn until aligned with the building
		// platform
		// then push it into the corner
		currentState = state.NAVIGATING;
	}

	public void navigate() {
		// Do navigating, parking under bridge
	}
}
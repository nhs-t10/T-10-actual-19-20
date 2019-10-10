package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.opencv.core.Rect;

@Autonomous(name= "BlockAuto")
public class BlockAuto extends Library {
	/*
		T-10 Preliminary Autonomous
		This is based on the assumption that we are:
            - Starting on the Block side
            - Doing all 4 possible autonomus tasks
	 */

	// constants and state declaration
	enum state {
		DELIVERING, PLACING, REPOSITIONING, NAVIGATING;
	}
	state currentState = null;

	public void init() {
		initialize_robot();
		currentState = state.DELIVERING;
	}

	public void loop() {
		//Loop constantly checks state, and then executes a command based on this.
		if(currentState == state.DELEVERING){
			deliverSkystone();
        }
        if(currentState == state.PLACING){
			place();
		}
		if(currentState == state.REPOSITIONING){
			reposition();
        }
		if(currentState == state.NAVIGATING){
			navigate();
		}
		telemetry.addData("Current State: ", currentState);
	}

	public void deliverSkystone() {
        //if aligned with a skystone, drive foreward and get it to ghe other side
        //if not aligned, use CV to align <-- likely a seperate method
		currentState = state.PLACING;
    }
    
    public void place() {
		currentState = state.REPOSITIONING;
	}

	public void reposition() {
        // if aligned with the building platform, push it into the corner
                //talk with hardware about which part will be used to push
        //if not aligned, use CV to drive and turn until aligned with the building platform
        //then push it into the corner
		currentState = state.NAVIGATING;
	}

	public void navigate() {
		// Do navigating, parking under bridge
    }
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")
public class AutoSelection extends Library{
    public enum Auto {STONE, FOUNDATION, PARKING}

    public void init() {
        hardwareInit();
        Auto[] autos = Auto.values();
        int stones = 0;
        boolean foundation = false;
        boolean parking = false;
        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;
        boolean right = gamepad1.dpad_right;
        boolean left = gamepad1.dpad_left;

        for (Auto types : autos){
            switch (types) {
                case STONE:
                    telemetry.addLine("up for 0 Stone");
                    telemetry.addLine("right for 1 Stone");
                    telemetry.addLine("down for 2 Stone");
                    telemetry.addLine("left for 3 Stone");

                    if (up) { //change to up arrow for robot
                        stones = 0;
                    }

                    else if (right) { //change to right arrow
                        stones = 1;
                    }

                    else if (down) { //change to down arrow
                        stones = 2;
                    }

                    else if (left) { //change to left arrow
                        stones = 3;
                    }
                    break;
                case FOUNDATION:
                    telemetry.addLine("up for yes foundation");
                    telemetry.addLine("down for no foundation");

                    if (up) { //change to up arrow
                        foundation = true;
                    }
                    else if (down){ //change to down arrow
                        foundation = false;
                    }
                    break;
                case PARKING:
                    telemetry.addLine("up for yes parking");
                    telemetry.addLine("down for no parking");

                    if (up) { //change to up arrow
                        parking = true;
                    }
                    else if (down) { //change to down arrow
                        parking = false;
                    }
                    break;
            }
        }
        telemetry.addLine(stones + " Stones"); //change to telemetry
        telemetry.addLine(foundation + " : will do foundation");
        telemetry.addLine(parking + " : will do parking");
    }
    public void loop(){}
}
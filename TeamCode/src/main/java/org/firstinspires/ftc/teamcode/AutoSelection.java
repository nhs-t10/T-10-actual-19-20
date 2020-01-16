package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")
public class AutoSelection extends Library{
    public enum Auto {STONE, FOUNDATION, PARKING, FINAL, VIBE}

    Auto auto = Auto.STONE;
    public void init(){}

    private Auto[] autos = Auto.values();
    int stones = -1; boolean foundation = Boolean.valueOf(null); boolean park = Boolean.valueOf(null);

    public void init_loop(){
        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;
        boolean right = gamepad1.dpad_right;
        boolean left = gamepad1.dpad_left;

            switch (auto) {
                case STONE:
                    telemetry.addLine("up for 0 Stone");
                    telemetry.addLine("right for 1 Stone");
                    telemetry.addLine("down for 2 Stone");
                    telemetry.addLine("left for 3 Stone");

                    if (up) {
                        stones = 0;
                    }

                    else if (right) {
                        stones = 1;
                    }

                    else if (down) {
                        stones = 2;
                    }

                    else if (left) {
                        stones = 3;
                    }
                    if (stones > -1)
                        auto = Auto.FOUNDATION;
                    break;
                case FOUNDATION:
                    telemetry.addLine("up for yes foundation");
                    telemetry.addLine("down for no foundation");

                    if (up) {
                        foundation = true;
                    }
                    else if (down){
                        foundation = false;
                    }
                    if (foundation != Boolean.valueOf(null))
                        auto = Auto.PARKING;
                    break;
                case PARKING:
                    telemetry.addLine("up for yes parking");
                    telemetry.addLine("down for no parking");

                    if (up) {
                        park = true;
                    }
                    else if (down) {
                        park = false;
                    }
                    if (park != Boolean.valueOf(null))
                        auto = Auto.FINAL;
                    break;
                case FINAL:
                    telemetry.addLine(stones + " Stones");
                    telemetry.addLine(foundation + " : will do foundation");
                    telemetry.addLine(park + " : will do parking");
                    auto = Auto.VIBE;
                    break;


        }

    }
    public void loop(){}
}
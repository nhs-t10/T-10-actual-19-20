package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")
public class AutoSelection extends Library{
    public enum Auto {STONE, FOUNDATION, PARKING, FINAL, VIBE}

    Auto auto = Auto.STONE;
    public void init(){}

    private Auto[] autos = Auto.values();
    int stones = -1; int foundation = -1; int park = -1; boolean foundationB; boolean parkB;

    public void init_loop(){


            switch (auto) {
                case STONE:
                    telemetry.addLine("up for 0 Stone");
                    telemetry.addLine("right for 1 Stone");
                    telemetry.addLine("down for 2 Stone");
                    telemetry.addLine("left for 3 Stone");
                    boolean up = gamepad1.dpad_up;
                    boolean down = gamepad1.dpad_down;
                    boolean right = gamepad1.dpad_right;
                    boolean left = gamepad1.dpad_left;

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
                    if (stones >= 0)
                        auto = Auto.FOUNDATION;
                    break;
                case FOUNDATION:
                    telemetry.addLine("up for yes foundation");
                    telemetry.addLine("down for no foundation");
                    up = gamepad1.dpad_up;
                    down = gamepad1.dpad_down;

                    if (up) {
                        foundation = 1;
                        foundationB = true;
                    }
                    else if (down){
                        foundation = 0;
                        foundationB = false;
                    }
                    if (foundation >= 0)
                        auto = Auto.PARKING;
                    break;
                case PARKING:
                    telemetry.addLine("up for yes parking");
                    telemetry.addLine("down for no parking");
                    up = gamepad1.dpad_up;
                    down = gamepad1.dpad_down;

                    if (up) {
                        park = 1;
                        parkB = true;
                    }
                    else if (down) {
                        park = 0;
                        parkB = false;
                    }
                    if (park >= 0)
                        auto = Auto.FINAL;
                    break;
                case FINAL:
                    telemetry.addLine(stones + " Stones");
                    telemetry.addLine(foundationB + " : will do foundation");
                    telemetry.addLine(parkB + " : will do parking");
                    auto = Auto.VIBE;
                    break;


        }

    }
    public void loop(){}
}
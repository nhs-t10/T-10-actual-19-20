package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")
public class AutoSelection extends Library{
    public enum Auto {STONE, FOUNDATION, PARKING, FINAL}

    public void init(){}

    private Auto[] autos = Auto.values();
    int stones = 0; boolean foundation = false; boolean park = false;

    public void init_loop(){
        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;
        boolean right = gamepad1.dpad_right;
        boolean left = gamepad1.dpad_left;
        boolean a = gamepad1.a;

        for (Auto types : autos){
            switch (types) {
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
                    if (a)
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
                    if (a)
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
                    if (a){
                        break;
                    }
                case FINAL:
                    telemetry.addLine(stones + " Stones");
                    telemetry.addLine(foundation + " : will do foundation");
                    telemetry.addLine(park + " : will do parking");
                    stop();

            }
        }

    }
    public void loop(){}
}
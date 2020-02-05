package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")

/*
blue platform auto
blue platform park
blue depot park
blue block auto

red platform auto
red platform park
red depot park
 */
public class AutoSelection extends Library{
    //public enum Auto {STONE, FOUNDATION, PARKING, FINAL, VIBE}
    public enum Auto {COLOR, RED, BLUE, VIBE}
    int stones = -1; int foundation = -1; int park = -1; int type = -1;
    private String Color, Type;
    private boolean up, down, left, right, foundationB, parkB;
    //Auto auto = Auto.STONE;
    Auto auto = Auto.COLOR;
    BlueBlockAuto BB = new BlueBlockAuto();

    public void init(){}

    public void init_loop()
    {
        up = gamepad1.dpad_up;
        down = gamepad1.dpad_down;
        right = gamepad1.dpad_right;
        left = gamepad1.dpad_left;

        switch (auto){
            case COLOR:{
                telemetry.addLine("UP for BLUE side");
                telemetry.addLine("DOWN for RED side");

                if( up ){
                    Color = "red";
                }

                else if( down ){
                    Color = "blue";
                }

                if( Color.equals("blue") ){
                    auto = Auto.BLUE;

                    try{
                        Thread.sleep(150);
                    }catch(Exception ie){
                    }
                }

                else if( Color.equals("red") ){
                    auto = Auto.RED;

                    try{
                        Thread.sleep(150);
                    }catch(Exception ie){
                    }
                }
                break;
            }

            case BLUE:
            {
                telemetry.addLine("UP for BLUE PLATFORM AUTO");
                telemetry.addLine("DOWN for BLUE PLATFORM PARK");
                telemetry.addLine("LEFT for BLUE DEPOT PARK");
                telemetry.addLine("RIGHT for BLUE BLOCK AUTO");

                if( up ){
                    Type = "blue platform auto";
                    type++;
                }

                else if( down ){
                    Type = "blue platform park";
                    type++;
                }
                else if( left ){
                    Type = "blue depot park";
                    type++;
                }
                else if( right ){
                    Type = "blue block auto";
                    type++;
                }

                if( type > -1 ){
                    auto = Auto.VIBE;
                }

                break;
            }

            case RED:
            {
                telemetry.addLine("UP for RED PLATFORM AUTO");
                telemetry.addLine("DOWN for RED PLATFORM PARK");
                telemetry.addLine("LEFT for RED DEPOT PARK");
                //telemetry.addLine("RIGHT for BLUE BLOCK AUTO");

                if( up ){
                    Type = "red platform auto";
                    type++;
                }

                else if( down ){
                    Type = "red platform park";
                    type++;
                }
                else if( left ){
                    Type = "red depot park";
                    type++;
                }
//                else if( right ){
//                    Type = "red block auto";
//                    type++;
//                }

                if( type > -1 ){
                    auto = Auto.VIBE;
                }
                break;
            }
        }
    }
    public void loop(){
        if (Color.equals("red") && Type.equals("foundation")){
            //            redFoundation();
        }

        else if (Color.equals("blue") && Type.equals("foundation")){
            //            blueFoundation();
        }

        else if (Color.equals("red") && Type.equals("block")){
            //            redBlock();
        }
        else if (Color.equals("blue") && Type.equals("block")){
            BB.start();
        }
    }
}
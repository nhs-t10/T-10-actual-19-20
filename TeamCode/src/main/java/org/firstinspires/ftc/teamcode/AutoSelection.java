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

    BluePlatformAuto BPA = new BluePlatformAuto();
    BluePlatformPark BPP = new BluePlatformPark();
    BlueDepotPark BDP = new BlueDepotPark();
    BlueBlockAuto BBA = new BlueBlockAuto();

    RedPlatformAuto RPA = new RedPlatformAuto();
    RedPlatformPark RPP = new RedPlatformPark();
    RedDepotPark RDP = new RedDepotPark();

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
                }

                else if( down ){
                    Type = "blue platform park";
                }
                else if( left ){
                    Type = "blue depot park";
                }
                else if( right ){
                    Type = "blue block auto";
                }

                if( !Type.equals("") ){
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
                }

                else if( down ){
                    Type = "red platform park";
                }
                else if( left ){
                    Type = "red depot park";
                }
//                else if( right ){
//                    Type = "red block auto";
//                    type++;
//                }

                if( !Type.equals("") ){
                    auto = Auto.VIBE;
                }
                break;
            }
        }
    }
    public void loop(){
        if (Type.equals("blue platform auto")){
            BPA.start();
        }

        else if (Type.equals("blue platform park")){
            BPP.start();
        }

        else if (Type.equals("blue depot park")){
            BDP.start();
        }
        else if (Type.equals("blue block auto")){
            BBA.start();
        }

        if (Type.equals("red platform auto")){
            RPA.start();
        }

        else if (Type.equals("red platform park")){
            RPP.start();
        }

        else if (Type.equals("red depot park")){
            RDP.start();
        }
    }
}
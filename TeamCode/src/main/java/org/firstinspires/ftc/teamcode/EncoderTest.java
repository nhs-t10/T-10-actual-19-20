package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Encoder Test")//do not delete this test class used by sasha
public class EncoderTest extends Library
{
    float startEncoderValue;
    final double CMPerRotation = 194.13;

    public void init()
    {
        startEncoderValue = Library.getEncoderValue();
    }

    public void loop()
    {
        if (Library.getEncoderValue() + 100 * CMPerRotation > startEncoderValue)
            Library.drive(.5f, 0, 0);
    }
}
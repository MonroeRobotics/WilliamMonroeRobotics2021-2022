package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous

public class TeestShootAuto extends LinearOpMode
{

    // todo: write your code here
    private DcMotor motorL;
    private DcMotor motorR;
    private DcMotor motorA;
    private DcMotor motorS;
    private Servo servoL;
    private Servo servoF;

    public void runOpMode() {
        motorL = hardwareMap.get(DcMotor.class, "motorL");
        motorR = hardwareMap.get(DcMotor.class, "motorR");
        motorA = hardwareMap.get(DcMotor.class, "motorA");
        motorS = hardwareMap.get(DcMotor.class, "motorS");
        servoL = hardwareMap.get(Servo.class, "servoL");
        servoF = hardwareMap.get(Servo.class, "servoF");
        
        
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        servoF.setPosition(.38);
        servoL.setPosition(.93);
        waitForStart();
        
        motorL.setPower(-1);
        motorR.setPower(1);
        sleep(1900);
        motorL.setPower(.25);
        motorR.setPower(-.25);
        sleep(200);
        motorL.setPower(0);
        motorR.setPower(0);
        
        
        motorS.setPower(-1);
        sleep(4000);
        servoF.setPosition(.8);
        sleep(500);
        motorS.setPower(0);
        servoF.setPosition(.38);
    }
    
    /*
    //Press Letter button move feeding servo
            if (this.gamepad2.a){ 
                servoF.setPosition(.8);
            }
            if (this.gamepad2.b){ 
                servoF.setPosition(.38);
            }
            
            //Press Letter button move feeding servo
            if (this.gamepad2.x){ 
                servoL.setPosition(.38);
            }
            if (this.gamepad2.y){ 
                servoL.setPosition(.88);
            }
    */
}

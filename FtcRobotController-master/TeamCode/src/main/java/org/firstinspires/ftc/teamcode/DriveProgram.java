package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp
// Wheel Movement
public class DriveProgram extends LinearOpMode{
    //region Declare Hardware Objects
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor lineMotor;
    private DcMotor lineMotor2;
    private Servo dropServo;
    private DcMotor intakeMotor = null;
    //endregion
    
    public void runOpMode(){
        //region Variables Setup
        double motorPower = 1;
        double leftstickX;
        double leftstickY;
        double rightstickX;
        double rightstickY;
        double direction;
        double magnitude;
        double fRight;
        double bRight;
        double bLeft;
        double fLeft;
        double fLeftBRight;
        double turn;

        //region Set Up Variables for Timer
        long lastTestTime = 0;
        long lastTime = 0;
        int stage = 0;
        long time;
        long checkTime;
        long checkTimeEnd = 0;
        double servoPos = 0.97;
        //endregion
        //endregion

        //region Hardware Map
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        intakeMotor  = hardwareMap.get(DcMotor.class, "intakeMotor");
        lineMotor = hardwareMap.get(DcMotor.class, "LeverMotor");
        lineMotor2 = hardwareMap.get(DcMotor.class, "backMotor");
        dropServo = hardwareMap.servo.get("dropServo");
        //endregion
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){
            
            leftstickX = this.gamepad1.left_stick_x;
            leftstickY = -this.gamepad1.left_stick_y;
            
            turn = -this.gamepad1.right_stick_x;

            //region Math For Wheel Movement
            direction = Math.atan2(leftstickY, leftstickX);
            magnitude = Math.sqrt(Math.pow(leftstickX, 2) + Math.pow(leftstickY, 2)) * 1.5;
            
            fRight = (Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude + turn);
            bLeft = (-Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude + turn);
            bRight = (Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude + turn);
            fLeft = (-Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude + turn);
            
            if (fRight > 1 || fRight < -1){
                fLeft = (fLeft / Math.abs(fRight));
                fRight = (fRight / Math.abs(fRight));
                bRight = (bRight / Math.abs(fRight));
                bLeft = (bLeft / Math.abs(fRight));
            }
        
            if (fLeft > 1 || fLeft < -1){
                fLeft = (fLeft / Math.abs(fLeft));
                fRight = (fRight / Math.abs(fLeft));
                bLeft = (bLeft / Math.abs(fLeft));
                bRight = (bRight / Math.abs(fLeft));
            }
            //endregion


            //region Intake Movement
            if (this.gamepad1.a){
                
                intakeMotor.setPower(motorPower);
            }
            else if (this.gamepad1.y){
                intakeMotor.setPower(-motorPower);
            }
            else{
                intakeMotor.setPower(0);
            }
            //endregion

            //region Manual Servo Movement
            if(this.gamepad1.right_trigger > 0.5){
                servoPos = .8;
            }

            if(this.gamepad1.left_trigger > 0.5){
                servoPos = .97;
            }

            //endregion

            //region Stage Movement of arm
            //when X is clicked start timer and switch
            if (this.gamepad1.x && stage == 0){
                time = 1000;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                stage = 1;
            }

            //while timer is active and stage 1 is active move arm up
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 1){
                time = 4900;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.8;
                stage = 2;
            }

            //while timer is active and stage 2 is active move arm up
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 2) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
            }

            /* when time exceeds limit, start new time, switch stage, and move servo
            (holds in same spot for a while) */
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 2){
                time = 2000;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.0;
                stage = 3;
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 3){
                time = 3400;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.8;
                sleep(20);
                stage = 4;
            }

            //while timer is active and stage 4 is active move arm down
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 4) {
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 4){
                time = 600;
                checkTime =  System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.97;
                sleep(20);
                stage = 5;
            }

            //while timer is active and stage 5 is active move arm down
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 5) {
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }

            //when timer is up and stage 5 is active stop motors and reset stage
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 5){
                stage = 0;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                telemetry.addData("Running", "False");
            }

            //endregion

            dropServo.setPosition(servoPos);

            //region Manual Motor And Arm Movement

            //region Manual Motor Movement for adjustment
            if (this.gamepad1.dpad_left){
                lineMotor2.setPower(-1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.dpad_right){
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.right_bumper){
                lineMotor.setPower(1);
                telemetry.addData("Running", "True");
            }
            if (this.gamepad1.left_bumper){
                lineMotor.setPower(-1);
                telemetry.addData("Running", "True");
            }

            //endregion

            //region Arm Movement
            if (this.gamepad1.dpad_up){
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");

            }
            if (this.gamepad1.dpad_down){
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");

            }

            //endregion

            else {
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                telemetry.addData("Running", "False");

            }

            //endregion

            //region Setting Motors
            backLeft.setPower(bLeft);
            frontRight.setPower(fRight);
            backRight.setPower(bRight);
            frontLeft.setPower(fLeft);
            //endregion

            //region Telemetry Data
            telemetry.addData("Status", "Initialized");
            telemetry.addData("leftX", leftstickX);
            telemetry.addData("leftY", leftstickY);
            telemetry.addData("Direction", direction);
            telemetry.addData("magnitude", magnitude);
            telemetry.addData("fRight", fRight);
            telemetry.addData("bLeft", bLeft);
            telemetry.update();
            //endregion
        }
    }
    
}

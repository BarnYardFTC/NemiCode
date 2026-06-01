package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class NemiTeleop extends LinearOpMode {
    DcMotor motor;
    Servo servo;

    public static double SERVO_MAX_LEFT = 0;
    public static double SERVO_MAX_RIGHT = 1;

    public static double SERVO_POS_JUMPS_VAL = 0.1;
    public static long SERVO_POS_JUMPS_INTERVALS = 100;

    @Override
    public void runOpMode() throws InterruptedException {

        motor = hardwareMap.get(DcMotor.class, "motor");
        servo = hardwareMap.get(Servo.class, "servo");

        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        servo.scaleRange(SERVO_MAX_LEFT, SERVO_MAX_RIGHT);

        servo.setPosition(0.5);

        double servoPos = 0.5;
        long lastServoUpdate = 0;

        waitForStart();
        while (opModeIsActive()){
            double motorPower = gamepad1.right_trigger - gamepad1.left_trigger;
            motor.setPower(motorPower);

            long currentTime = System.currentTimeMillis();

            if (currentTime - lastServoUpdate >= SERVO_POS_JUMPS_INTERVALS) {

                if (gamepad1.right_bumper) {
                    servoPos += SERVO_POS_JUMPS_VAL;
                    lastServoUpdate = currentTime;
                }

                if (gamepad1.left_bumper) {
                    servoPos -= SERVO_POS_JUMPS_VAL;
                    lastServoUpdate = currentTime;
                }

                // Keep servo position between 0 and 1
                servoPos = Math.max(0.0, Math.min(1.0, servoPos));

                servo.setPosition(servoPos);
            }

            telemetry.addData("motorPower: ", motorPower);
            telemetry.addData("Actual motorPower: ", motor.getPower());
            telemetry.update();
        }
    }
}


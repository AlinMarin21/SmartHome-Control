#include <Servo.h>
#include "DHT.h"
#include "MQ135.h"
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

/*** Type of used DHT sensor ***/
#define DHTTYPE DHT22

/*** Display dimensions ***/
#define SCREEN_WIDTH 128 // OLED display width,  in pixels
#define SCREEN_HEIGHT 64 // OLED display height, in pixels

#define OLED_RESET -1

#define RX_ELEMENTS 49
#define TX_ELEMENTS 49

#define SOB 255
#define EOB 255

#define LED_OFF 0
#define LED_ON 1

#define MANUAL_CONTROL 0
#define MOTION_CONTROL 1
#define DARKNESS_CONTROL 2

#define MOTION_DETECTED 1
#define NO_MOTION 0

#define DOOR_OPEN 1
#define DOOR_CLOSE 0

#define DOOR_LOCKED 1
#define DOOR_UNLOCKED 0

#define AUTO_CLOSE_ON 1
#define AUTO_CLOSE_OFF 0

#define TIME_EXPIRED 1
#define TIME_NOT_EXPIRED 0

#define AC_ON 1
#define AC_OFF 0

#define AUTO_AC_OFF 0
#define AUTO_AC_ON 1

#define DISPLAY_ON 1
#define DISPLAY_OFF 0

#define TEMPERATURE_INSIDE_DISPLAY 0
#define TEMPERATURE_OUTSIDE_DISPLAY 1
#define HUMIDITY_INSIDE_DISPLAY 2
#define HUMIDITY_OUTSIDE_DISPLAY 3
#define TEMPERATURE_INSIDE_OUTSIDE_DISPLAY 4
#define HUMIDITY_INSIDE_OUTSIDE_DISPLAY 5
#define AIR_QUALITY_DISPLAY 6
#define GAS_DISPLAY 7
#define RAINFALL_DISPLAY 8
#define CUSTOM_DISPLAY 9

#define CELSIUS_UNIT 0
#define FAHRENHEIT_UNIT 1

#define ALARM_ON 1
#define ALARM_OFF 0

#define DISPLAYED_ON_CUSTOM 1
#define NOT_DISPLAYED_ON_CUSTOM 0

#define YELLOW 0
#define RED 1
#define ORANGE 2
#define AQUA 3
#define BLUE 4
#define GREEN 5
#define PINK 6
#define PURPLE 7

#define SYSTEM_ON 1
#define SYSTEM_OFF 0

#define FIRST_TRANSMISSION_AFTER_RESTART 1
#define NORMAL_TRANSMISSION 0

const int room1_led_red_color_pin = 2;
const int room1_led_green_color_pin = 3;
const int room1_led_blue_color_pin = 4;
const int room2_led_red_color_pin = 5;
const int room2_led_green_color_pin = 6;
const int room2_led_blue_color_pin = 7;
const int living_room_led_pin = 8;
const int kitchen_led1_pin = 28;
const int kitchen_led2_pin = 29;
const int bathroom_led_pin = 30;
const int terrace_left_led1_pin = 31;
const int terrace_left_led2_pin = 32;
const int terrace_left_led3_pin = 33;
const int terrace_left_led4_pin = 34;
const int terrace_right_led1_pin = 35;
const int terrace_right_led2_pin = 36;
const int terrace_right_led3_pin = 37;
const int terrace_right_led4_pin = 38;

const int photorestior_pin = A0;
const int PIR_sensor_pin = 53;
const int inside_dht_pin = 50;
const int outside_dht_pin = 51;
const int mq135_pin = A1;
const int mq2_pin = A2;
const int rainfall_sensor_pin = A3;

const int ac_pin = 9;
const int left_gate_servo_pin = 10;
const int right_gate_servo_pin = 11;
const int door_locker_servo_pin = 12;
const int door_servo_pin = 13;

const int alarm_pin = 48;

byte RxBuffer[RX_ELEMENTS] = {0};
/* Rx Buffer
* 0: SOB (Start Of Buffer)
* 1: Living Room LED State
* 2: Living Room LED Brightness
* 3: Kitchen LED 1 State
* 4: Kitchen LED 2 State
* 5: Room 1 LED State
* 6: Room 1 LED Red (RGB) Value
* 7: Room 1 LED Green (RGB) Value
* 8: Room 1 LED Blue (RGB) Value
* 9: Room 2 LED State
* 10: Room 2 LED Red (RGB) Value
* 11: Room 2 LED Green (RGB) Value
* 12: Room 2 LED Blue (RGB) Value
* 13: Terrace LED State For Manual Control
* 14: Terrace LED Mode Control
* 15: Bathroom LED State
* 16: Front Door State
* 17: Front Door Automatic Closing State
* 18: Front Door Locking State
* 19: Gates State
* 20: Gates Automatic Closing State
* 21: AC State
* 22: Auto AC State
* 23: AC Speed
* 24: Display State
* 25: Display Mode
* 26: Temperature Threshold Value
* 27: Brightness Threshold Value
* 28: CO2 Threshold Value
* 29: Air Quality Threshold Value
* 30: Temperature Unit
* 31: Air Quality Alarm State
* 32: CO2 Alarm State
* 33: Humidity Alarm State
* 34: Motion Alarm State
* 35: Custom Display Inside Temperature State
* 36: Custom Display Outside Temperature State
* 37: Custom Display Inside Humidity State
* 38: Custom Display Outside Humidity State
* 39: Custom Display Air Quality State
* 40: Custom Display CO2 State
* 41: Custom Display Rainfall State
* 42: Custom Display Brightness State
* 43: Custom Display Motion State
* 44: Room 1 Led Color
* 45: Room 2 Led Color
* 46: CO2 Threshold Value
* 47: Air Quality Threshold Value
* 48: EOB (End Of Buffer) */

byte TxBuffer[TX_ELEMENTS] = {0};
/* Tx Buffer
* 0: SOB (Start Of Buffer)
* 1: Motion Detection
* 2: Brightness Value
* 3: Inside Temperature Integer Value
* 4: Inside Temperature Floating Value
* 5: Inside Humidity Value
* 6: Outside Temperature Integer Value
* 7: Outside Temperature Floating Value
* 8: Outside Humidity Value
* 9: Air Quality Value
* 10: CO2 Value
* 11: Door Time Expired
* 12: Gate Time Expired
* 13: Air Quality Value
* 14: CO2 Value
* 48: EOB (End Of Buffer) */

byte ReceivedData[RX_ELEMENTS] = {0};
byte RecoveryBuffer[TX_ELEMENTS] = {0};
byte LastValidReceivedData[RX_ELEMENTS] = {0};

int serial_availability = 0;
int recovery_debounce = 0;

DHT inside_dht_sensor(inside_dht_pin, DHTTYPE);
DHT outside_dht_sensor(outside_dht_pin, DHTTYPE);
MQ135 mq135_sensor(mq135_pin);
Adafruit_SSD1306 myDisplay(128, 64, &Wire, OLED_RESET);

Servo left_gate_servo;
Servo right_gate_servo;
Servo door_locker_servo;
Servo door_servo;

int living_room_led_state = LED_OFF;
int living_room_led_brightness = 5;
int kitchen_led_1_state = LED_OFF;
int kitchen_led_2_state = LED_OFF;
int room_1_led_state = LED_OFF;
int room_1_rgb_red_color = 0;
int room_1_rgb_green_color = 0;
int room_1_rgb_blue_color = 0;
int room_2_led_state = LED_OFF;
int room_2_rgb_red_color = 0;
int room_2_rgb_green_color = 0;
int room_2_rgb_blue_color = 0;
int bathroom_led_state = LED_OFF;
int terrace_led_state = LED_OFF;
int terrace_led_mode_control = MANUAL_CONTROL;
int front_door_state = DOOR_CLOSE;
int front_door_automatic_closing = AUTO_CLOSE_OFF;
int front_door_locking_state = DOOR_UNLOCKED;
int gates_state = DOOR_CLOSE;
int gates_automatic_closing = AUTO_CLOSE_OFF;
int ac_state = AC_OFF;
int auto_ac = AUTO_AC_OFF;
int ac_speed = 5;
int display_state = DISPLAY_OFF;
int display_mode = TEMPERATURE_INSIDE_DISPLAY;
int temperature_threshold = 22;
int brightness_threshold = 40;
int air_quality_threshold = 3000;
int co2_threshold = 300;
int temperature_unit = CELSIUS_UNIT;
int air_quality_alarm = ALARM_OFF;
int co2_alarm = ALARM_OFF;
int humidity_alarm = ALARM_OFF;
int motion_alarm = ALARM_OFF;
int custom_display_inside_temperature = DISPLAYED_ON_CUSTOM;
int custom_display_outside_temperature = DISPLAYED_ON_CUSTOM;
int custom_display_inside_humidity = DISPLAYED_ON_CUSTOM;
int custom_display_outside_humidity = DISPLAYED_ON_CUSTOM;
int custom_display_air_quality = DISPLAYED_ON_CUSTOM;
int custom_display_co2 = NOT_DISPLAYED_ON_CUSTOM;
int custom_display_rainfall = NOT_DISPLAYED_ON_CUSTOM;
int custom_display_brightness = NOT_DISPLAYED_ON_CUSTOM;
int custom_display_motion = NOT_DISPLAYED_ON_CUSTOM;
int room_1_color = YELLOW;
int room_2_color = YELLOW;

int motion_state = NO_MOTION;
int brightness = 0;
float inside_temperature = 0.0;
float outside_temperature = 0.0;
int inside_humidity = 0;
int outside_humidity = 0;
int air_quality_ppm = 0;
int co2_ppm = 0;
int auto_door_time_expired = TIME_NOT_EXPIRED;
int auto_gate_time_expired = TIME_NOT_EXPIRED;

int rainfall = 0;

int alley_lights_last_state = LED_OFF;
int alley_lights_counter = 0;
int start_alley_lights_counter = 0;

int system_state = SYSTEM_OFF;
int system_transmission = NORMAL_TRANSMISSION;

int door_counter = 0;
int gate_counter = 0;
int start_door_counter = 0;
int start_gate_counter = 0;
int last_door_state = 0;
int last_gate_state = 0;


/*** Graphics for OLED ***/
const unsigned char epd_bitmap_humidity [] PROGMEM = {
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x0f, 0x80, 0x40, 0x00, 0x00, 0x00, 0x00, 0x1f, 0x80, 0xc0, 0x00, 0x00, 0x00, 0x00, 0x1f, 0xc1, 
	0xe0, 0x00, 0x00, 0x00, 0x00, 0x3f, 0xe1, 0xe0, 0x00, 0x00, 0x00, 0x00, 0x3f, 0xe3, 0xf0, 0x00, 
	0x00, 0x00, 0x00, 0x7f, 0xe7, 0xf8, 0x00, 0x00, 0x00, 0x00, 0xff, 0xe7, 0xf8, 0x00, 0x00, 0x00, 
	0x00, 0xff, 0xcf, 0xfc, 0x00, 0x00, 0x00, 0x01, 0xff, 0x8f, 0xfe, 0x00, 0x00, 0x00, 0x01, 0xff, 
	0x9f, 0xfe, 0x00, 0x00, 0x00, 0x01, 0xff, 0x3f, 0xff, 0x00, 0x00, 0x00, 0x01, 0xfe, 0x3f, 0xff, 
	0x80, 0x00, 0x00, 0x01, 0xfe, 0x7f, 0xff, 0x80, 0x00, 0x00, 0x01, 0xfc, 0x7f, 0xff, 0xc0, 0x00, 
	0x00, 0x00, 0xfc, 0xff, 0xf8, 0xc0, 0x00, 0x00, 0x00, 0xf9, 0xff, 0xe0, 0x20, 0x00, 0x00, 0x00, 
	0x79, 0xff, 0xc0, 0x00, 0x00, 0x00, 0x00, 0x11, 0xff, 0x87, 0x00, 0x00, 0x00, 0x00, 0x03, 0xff, 
	0x88, 0x80, 0x00, 0x00, 0x00, 0x03, 0xff, 0x98, 0x8e, 0x00, 0x00, 0x00, 0x03, 0xff, 0x98, 0x9c, 
	0x00, 0x00, 0x00, 0x03, 0xff, 0x88, 0x98, 0x00, 0x00, 0x00, 0x03, 0xff, 0xcf, 0x38, 0x00, 0x00, 
	0x00, 0x01, 0xff, 0xc0, 0x70, 0x00, 0x00, 0x00, 0x01, 0xff, 0xf0, 0xe0, 0x00, 0x00, 0x00, 0x00, 
	0xff, 0xf8, 0xc0, 0x00, 0x00, 0x00, 0x00, 0xff, 0xf1, 0x9b, 0x00, 0x00, 0x00, 0x00, 0x7f, 0xf3, 
	0x91, 0x00, 0x00, 0x00, 0x00, 0x3f, 0xe7, 0x21, 0x00, 0x00, 0x00, 0x00, 0x0f, 0xc6, 0x31, 0x00, 
	0x00, 0x00, 0x00, 0x03, 0x8c, 0x11, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x0c, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
}; // 'humidity', 50x50px

const unsigned char epd_bitmap_temperature [] PROGMEM = {
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x1f, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38, 0xc0, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x30, 0xe0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x60, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x20, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x63, 0xf8, 0x00, 0x00, 0x00, 0x00, 0x20, 
	0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x63, 0x80, 
	0x00, 0x00, 0x00, 0x00, 0x26, 0x63, 0xc0, 0x00, 0x00, 0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x63, 0xf8, 0x00, 0x00, 0x00, 0x00, 
	0x26, 0x63, 0xf8, 0x00, 0x00, 0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x60, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x63, 0xc0, 0x00, 0x00, 0x00, 0x00, 0x26, 0x63, 0xc0, 0x00, 
	0x00, 0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x26, 0x63, 0xf8, 0x00, 0x00, 0x00, 0x00, 0x26, 0x63, 0xf8, 0x00, 0x00, 0x00, 0x00, 0x26, 
	0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x26, 0x60, 0x00, 0x00, 0x00, 0x00, 0x00, 0x66, 0x60, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0xe6, 0x30, 0x00, 0x00, 0x00, 0x00, 0x01, 0x8f, 0x18, 0x00, 0x00, 0x00, 
	0x00, 0x03, 0x13, 0xcc, 0x00, 0x00, 0x00, 0x00, 0x03, 0x63, 0xec, 0x00, 0x00, 0x00, 0x00, 0x06, 
	0x47, 0xe6, 0x00, 0x00, 0x00, 0x00, 0x06, 0x0f, 0xf6, 0x00, 0x00, 0x00, 0x00, 0x06, 0x8f, 0xf6, 
	0x00, 0x00, 0x00, 0x00, 0x06, 0xdf, 0xf6, 0x00, 0x00, 0x00, 0x00, 0x06, 0x7f, 0xe6, 0x00, 0x00, 
	0x00, 0x00, 0x03, 0x7f, 0xe6, 0x00, 0x00, 0x00, 0x00, 0x03, 0x3f, 0xcc, 0x00, 0x00, 0x00, 0x00, 
	0x01, 0x9f, 0x9c, 0x00, 0x00, 0x00, 0x00, 0x01, 0xc0, 0x38, 0x00, 0x00, 0x00, 0x00, 0x00, 0xf0, 
	0x70, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3f, 0xe0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0f, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
}; // 'temperature', 50x50px

const unsigned char epd_bitmap_airquality [] PROGMEM = {
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x7c, 0x00, 0x70, 0x00, 0x00, 0x00, 0x01, 0xff, 0x83, 0xfe, 0x00, 0x00, 0x00, 0x07, 0xff, 0xc7, 
	0xff, 0x00, 0x00, 0x00, 0x0f, 0xff, 0xff, 0xff, 0x80, 0x00, 0x00, 0x1f, 0xff, 0xff, 0xff, 0xc0, 
	0x00, 0x00, 0x3f, 0x8e, 0x1f, 0xff, 0xc0, 0x00, 0x00, 0x7f, 0x0c, 0x0f, 0xff, 0xe0, 0x00, 0x00, 
	0x7f, 0x7d, 0xef, 0xff, 0xe0, 0x00, 0x00, 0xff, 0x7d, 0xec, 0x3f, 0xe0, 0x00, 0x00, 0xff, 0x7d, 
	0xec, 0x3f, 0xe0, 0x00, 0x00, 0xff, 0x7d, 0xef, 0xbf, 0xc0, 0x00, 0x00, 0x3f, 0x0c, 0x0e, 0x3f, 
	0x00, 0x00, 0x07, 0x8f, 0x8e, 0x1c, 0x3c, 0x78, 0x00, 0x0f, 0xcf, 0xff, 0xfd, 0xfc, 0xfc, 0x00, 
	0x1f, 0xe7, 0xff, 0xfc, 0x39, 0xfe, 0x00, 0x3f, 0xf7, 0xfe, 0x1c, 0x3b, 0xff, 0x00, 0x3f, 0xf7, 
	0xfc, 0x0f, 0xfb, 0xff, 0x00, 0x3f, 0xff, 0xf9, 0xe7, 0xff, 0xff, 0x00, 0x1f, 0xff, 0xf3, 0xf3, 
	0xff, 0xfe, 0x00, 0x1f, 0xff, 0xe7, 0xf9, 0xff, 0xfe, 0x00, 0x0f, 0xff, 0xcf, 0x3c, 0xff, 0xfc, 
	0x00, 0x00, 0x00, 0x1e, 0x1e, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3c, 0xcf, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x79, 0xe7, 0x80, 0x00, 0x00, 0x00, 0x00, 0xf3, 0xf3, 0xc0, 0x00, 0x00, 0x00, 0x01, 0xe7, 
	0x39, 0xe0, 0x00, 0x00, 0x00, 0x03, 0xce, 0x1c, 0xf0, 0x00, 0x00, 0x00, 0x07, 0x9c, 0xce, 0x78, 
	0x00, 0x00, 0x00, 0x0f, 0x39, 0xe7, 0x3c, 0x00, 0x00, 0x00, 0x1e, 0x73, 0x33, 0x9e, 0x00, 0x00, 
	0x00, 0x3c, 0xe3, 0x39, 0xcf, 0x00, 0x00, 0x00, 0x79, 0xe7, 0x39, 0xe7, 0x80, 0x00, 0x00, 0x73, 
	0xcd, 0x3c, 0xf3, 0x80, 0x00, 0x00, 0x67, 0xcc, 0x3c, 0xf9, 0x80, 0x00, 0x00, 0x07, 0xce, 0x3c, 
	0xf8, 0x00, 0x00, 0x00, 0x07, 0xcf, 0x0c, 0xf8, 0x00, 0x00, 0x00, 0x07, 0xcf, 0x1c, 0xf8, 0x00, 
	0x00, 0x00, 0x07, 0xef, 0x1d, 0xf8, 0x00, 0x00, 0x00, 0x07, 0xe7, 0x39, 0xf8, 0x00, 0x00, 0x00, 
	0x07, 0xf1, 0x23, 0xf8, 0x00, 0x00, 0x00, 0x07, 0xfc, 0x0f, 0xf8, 0x00, 0x00, 0x00, 0x07, 0xff, 
	0x3f, 0xf8, 0x00, 0x00, 0x00, 0x03, 0xff, 0x3f, 0xf0, 0x00, 0x00, 0x00, 0x03, 0xff, 0x3f, 0xf0, 
	0x00, 0x00, 0x00, 0x03, 0xff, 0xff, 0xf0, 0x00, 0x00, 0x00, 0x03, 0xff, 0xff, 0xf0, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
}; // 'airquality', 50x50px

const unsigned char epd_bitmap_gas [] PROGMEM = {
	0x00, 0x00, 0x1f, 0xfe, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3f, 0xff, 0x00, 0x00, 0x00, 0x00, 0x00, 
	0x60, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x63, 0xf1, 0x00, 0x00, 0x00, 0x00, 0x00, 0x67, 0xf9, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x64, 0x19, 0x00, 0x00, 0x00, 0x00, 0x00, 0x66, 0x19, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x67, 0xf9, 0x00, 0x00, 0x00, 0x00, 0x00, 0x61, 0xe1, 0x00, 0x00, 0x00, 0x00, 
	0x00, 0x60, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0xff, 0xff, 0xc0, 0x00, 0x00, 0x00, 0x03, 0xff, 
	0xff, 0xf0, 0x00, 0x00, 0x00, 0x07, 0x00, 0x00, 0x78, 0x00, 0x00, 0x00, 0x0e, 0x00, 0x00, 0x1c, 
	0x00, 0x00, 0x00, 0x1c, 0x00, 0x00, 0x0e, 0x00, 0x00, 0x00, 0x18, 0x00, 0x00, 0x06, 0x00, 0x00, 
	0x00, 0x30, 0x00, 0x00, 0x07, 0x00, 0x00, 0x00, 0x30, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x30, 
	0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x30, 0x01, 0xc0, 0x03, 0x00, 0x00, 0x00, 0x30, 0x03, 0xc0, 
	0x03, 0x00, 0x00, 0x00, 0x30, 0x07, 0x80, 0x03, 0x00, 0x00, 0x00, 0x30, 0x07, 0x80, 0x03, 0x00, 
	0x00, 0x00, 0x30, 0x0f, 0xc0, 0x03, 0x00, 0x00, 0x00, 0x30, 0x0c, 0xe0, 0x03, 0x00, 0x00, 0x00, 
	0x30, 0x0c, 0x70, 0x03, 0x00, 0x00, 0x00, 0x30, 0x0c, 0x38, 0x03, 0x00, 0x00, 0x00, 0x30, 0x06, 
	0x1c, 0x03, 0x00, 0x00, 0x00, 0x30, 0x1f, 0x0e, 0x03, 0x00, 0x00, 0x00, 0x30, 0x1b, 0x07, 0x03, 
	0x00, 0x00, 0x00, 0x30, 0x3f, 0x03, 0x03, 0x00, 0x00, 0x00, 0x30, 0x3e, 0x03, 0x83, 0x00, 0x00, 
	0x00, 0x30, 0x60, 0x01, 0x83, 0x00, 0x00, 0x00, 0x30, 0x60, 0x01, 0x83, 0x00, 0x00, 0x00, 0x30, 
	0x60, 0x01, 0x83, 0x00, 0x00, 0x00, 0x30, 0x30, 0x03, 0x03, 0x00, 0x00, 0x00, 0x30, 0x38, 0x07, 
	0x03, 0x00, 0x00, 0x00, 0x30, 0x1f, 0xfe, 0x03, 0x00, 0x00, 0x00, 0x30, 0x0f, 0xf8, 0x03, 0x00, 
	0x00, 0x00, 0x30, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x38, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 
	0x18, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x1c, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x0e, 0x00, 
	0x00, 0x1c, 0x00, 0x00, 0x00, 0x07, 0x80, 0x00, 0xf8, 0x00, 0x00, 0x00, 0x03, 0xff, 0xff, 0xf0, 
	0x00, 0x00, 0x00, 0x03, 0xff, 0xff, 0xf0, 0x00, 0x00, 0x00, 0x03, 0x80, 0x00, 0xf0, 0x00, 0x00, 
	0x00, 0x01, 0xff, 0xff, 0xe0, 0x00, 0x00, 0x00, 0x00, 0xff, 0xff, 0xc0, 0x00, 0x00
}; // 'gas', 50x50px

const unsigned char epd_bitmap_rainfall [] PROGMEM = {
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x70, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 
	0xfe, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0f, 0xff, 0x0f, 0xf8, 0x00, 0x00, 0x00, 0x1f, 0xff, 0xbf, 
	0xfe, 0x00, 0x00, 0x00, 0x3f, 0xff, 0xff, 0xff, 0x00, 0x00, 0x00, 0x3f, 0xff, 0xff, 0xff, 0x80, 
	0x00, 0x00, 0x7f, 0xff, 0xff, 0xff, 0x80, 0x00, 0x00, 0x7f, 0xff, 0xff, 0xff, 0xc0, 0x00, 0x03, 
	0xff, 0xff, 0xff, 0xff, 0xe0, 0x00, 0x0f, 0xff, 0xff, 0xff, 0xff, 0xe0, 0x00, 0x1f, 0xff, 0xff, 
	0xff, 0xff, 0xe0, 0x00, 0x3f, 0xff, 0xff, 0xff, 0xff, 0xf8, 0x00, 0x7f, 0xff, 0xff, 0xff, 0xff, 
	0xfe, 0x00, 0x7f, 0xff, 0xff, 0xff, 0xff, 0xff, 0x00, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0x80, 
	0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0x80, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xc0, 0xff, 0xff, 
	0xff, 0xff, 0xff, 0xff, 0xc0, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xc0, 0xff, 0xff, 0xff, 0xff, 
	0xff, 0xff, 0xc0, 0x7f, 0xff, 0xff, 0xff, 0xff, 0xff, 0xc0, 0x7f, 0xff, 0xff, 0xff, 0xff, 0xff, 
	0x80, 0x7f, 0xff, 0xff, 0xff, 0xff, 0xff, 0x80, 0x3f, 0xff, 0xff, 0xff, 0xff, 0xff, 0x00, 0x1f, 
	0xff, 0xff, 0xff, 0xff, 0xfe, 0x00, 0x07, 0xff, 0xff, 0xff, 0xff, 0xf8, 0x00, 0x01, 0xf7, 0xff, 
	0xff, 0xff, 0x80, 0x00, 0x00, 0x03, 0xff, 0xff, 0xff, 0x00, 0x00, 0x00, 0x01, 0xff, 0xff, 0xfe, 
	0x00, 0x00, 0x00, 0x00, 0x7f, 0x1f, 0xfc, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0f, 0xf0, 0x00, 0x00, 
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0xc0, 0x00, 0x80, 0x00, 0x00, 0x60, 
	0x00, 0xc0, 0x01, 0x80, 0x00, 0x00, 0xe0, 0x01, 0xe0, 0x03, 0xc0, 0x00, 0x00, 0xf0, 0x01, 0xe0, 
	0x03, 0xc0, 0x00, 0x01, 0xf0, 0x03, 0xf0, 0x03, 0xe0, 0x00, 0x01, 0xf8, 0x03, 0xf0, 0x07, 0xe0, 
	0x00, 0x01, 0xf8, 0x03, 0xf0, 0x07, 0xe0, 0x00, 0x01, 0xf0, 0x41, 0xe0, 0x83, 0xe0, 0x00, 0x00, 
	0x60, 0xe0, 0xc1, 0xc1, 0x80, 0x00, 0x00, 0x00, 0xe0, 0x01, 0xc0, 0x00, 0x00, 0x00, 0x01, 0xf0, 
	0x03, 0xe0, 0x00, 0x00, 0x00, 0x01, 0xf0, 0x03, 0xe0, 0x00, 0x00, 0x00, 0x01, 0xf0, 0x03, 0xf0, 
	0x00, 0x00, 0x00, 0x01, 0xf0, 0x03, 0xe0, 0x00, 0x00, 0x00, 0x01, 0xf0, 0x03, 0xe0, 0x00, 0x00, 
	0x00, 0x00, 0xc0, 0x00, 0xc0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
}; // 'rainfall', 50x50px

void setup() {
  Serial.begin(9600);

  TxBuffer[0] = SOB;
  TxBuffer[48] = EOB;

  inside_dht_sensor.begin();
  outside_dht_sensor.begin();

  pinMode(room1_led_red_color_pin, OUTPUT);
  pinMode(room1_led_green_color_pin, OUTPUT);
  pinMode(room1_led_blue_color_pin, OUTPUT);
  pinMode(room2_led_red_color_pin, OUTPUT);
  pinMode(room2_led_green_color_pin, OUTPUT);
  pinMode(room2_led_blue_color_pin, OUTPUT);

  pinMode(living_room_led_pin, OUTPUT);
  pinMode(kitchen_led1_pin, OUTPUT);
  pinMode(kitchen_led2_pin, OUTPUT);
  pinMode(bathroom_led_pin, OUTPUT);

  pinMode(terrace_left_led1_pin, OUTPUT);
  pinMode(terrace_left_led2_pin, OUTPUT);
  pinMode(terrace_left_led3_pin, OUTPUT);
  pinMode(terrace_left_led4_pin, OUTPUT);
  pinMode(terrace_right_led1_pin, OUTPUT);
  pinMode(terrace_right_led2_pin, OUTPUT);
  pinMode(terrace_right_led3_pin, OUTPUT);
  pinMode(terrace_right_led4_pin, OUTPUT);

  pinMode(photorestior_pin, INPUT);
  pinMode(PIR_sensor_pin, INPUT);
  pinMode(mq2_pin, INPUT);
  pinMode(rainfall_sensor_pin, INPUT);

  pinMode(ac_pin, OUTPUT);
  pinMode(alarm_pin, OUTPUT);

  left_gate_servo.attach(left_gate_servo_pin);
  right_gate_servo.attach(right_gate_servo_pin);
  door_locker_servo.attach(door_locker_servo_pin);
  door_servo.attach(door_servo_pin);

  left_gate_servo.write(90);
  right_gate_servo.write(90);
  door_locker_servo.write(0);
  door_servo.write(90);

  //internel charge pump cct enable. or DC-to-DC converter
  myDisplay.begin(SSD1306_SWITCHCAPVCC, 0x3C);
  myDisplay.clearDisplay(); 
}

void loop() {

  if(SYSTEM_OFF == system_state) {
    system_state = SYSTEM_ON;
    system_transmission = FIRST_TRANSMISSION_AFTER_RESTART;
  }

  readSensors();
  TxUpdate();

  if(Serial.available() > 0) {
    recovery_debounce = 0;
    
    if(serial_availability == 0) {
      serial_availability = 1;
      if(FIRST_TRANSMISSION_AFTER_RESTART == system_transmission) {
        setDefaultData();
        system_transmission = NORMAL_TRANSMISSION;
      }
      writeRecoveryData();
    }
    else {
      communicationWithBluetoothModule();
    }
  }
  else {
    recovery_debounce++;
    if(recovery_debounce == 6) {
      serial_availability = 0;
      recoverLastReceivedData();
    }
  }

  RxUpdate();
  lightsControl();
  ACControl();
  doorsControl();
  alarmControl();
  displayControl();

  delay(250);
}

void writeRecoveryData() {
  for(int i = 0; i < TX_ELEMENTS; i++) {
    Serial.write(RecoveryBuffer[i]);
  }
}

void readReceiverBuffer() {
  for(int i = 0; i < RX_ELEMENTS; i++) {
    ReceivedData[i] = Serial.read();
  }
  if(SOB != ReceivedData[0] || EOB != ReceivedData[48]) {
    for (int j = 0; j < 14; j++) {
      int first_element = ReceivedData[0] & 0xFF;
      for (int k = 0; k < RX_ELEMENTS - 1; k++) {
        ReceivedData[k] = ReceivedData[k + 1];
      }
      ReceivedData[48] = (byte) first_element;
    }
    if(SOB != ReceivedData[0] || EOB != ReceivedData[48]) {
      //do nothing  
    }             
    else {
      for(int i = 0; i < RX_ELEMENTS; i++) {
        RxBuffer[i] = ReceivedData[i];
      }
    }    
  }
  else {
    for(int i = 0; i < RX_ELEMENTS; i++) {
      RxBuffer[i] = ReceivedData[i];
    }
  }
  // Serial.print("Rx: ");
  // for(int i = 0; i < RX_ELEMENTS; i++) {
  //   Serial.print(RxBuffer[i]);
  //   Serial.print(" ");
  // }
  // Serial.println();
}

void writeTransmitterBuffer() {
  for(int i = 0; i < TX_ELEMENTS; i++) {
    Serial.write(TxBuffer[i]);
  }
}

void communicationWithBluetoothModule() {
  validateRxData();
  readReceiverBuffer();
  writeTransmitterBuffer();
}

void recoverLastReceivedData() {
  for(int i = 0; i < RX_ELEMENTS; i++) {
    RecoveryBuffer[i] = LastValidReceivedData[i];
  }
  // Serial.print("Recovery: ");
  // for(int i = 0; i < RX_ELEMENTS; i++) {
  //   Serial.print(RecoveryBuffer[i]);
  //   Serial.print(" ");
  // }
  // Serial.println();
}

void validateRxData() {
  for(int i = 0; i < RX_ELEMENTS; i++) {
    LastValidReceivedData[i] = RxBuffer[i];
  }
  // Serial.print("Last: ");
  // for(int i = 0; i < RX_ELEMENTS; i++) {
  //   Serial.print(LastValidReceivedData[i]);
  //   Serial.print(" ");
  // }
  // Serial.println();
}

void RxUpdate() {
  living_room_led_state = RxBuffer[1] & 0xFF;
  living_room_led_brightness = RxBuffer[2] & 0xFF;
  kitchen_led_1_state = RxBuffer[3] & 0xFF;
  kitchen_led_2_state = RxBuffer[4] & 0xFF;
  room_1_led_state = RxBuffer[5] & 0xFF;
  room_1_rgb_red_color = RxBuffer[6] & 0xFF;
  room_1_rgb_green_color = RxBuffer[7] & 0xFF;
  room_1_rgb_blue_color = RxBuffer[8] & 0xFF;
  room_2_led_state = RxBuffer[9] & 0xFF;
  room_2_rgb_red_color = RxBuffer[10] & 0xFF;
  room_2_rgb_green_color = RxBuffer[11] & 0xFF;
  room_2_rgb_blue_color = RxBuffer[12] & 0xFF;
  bathroom_led_state = RxBuffer[15] & 0xFF;
  terrace_led_state = RxBuffer[13] & 0xFF;
  terrace_led_mode_control = RxBuffer[14] & 0xFF;
  front_door_state = RxBuffer[16] & 0xFF;
  front_door_automatic_closing = RxBuffer[17] & 0xFF;
  front_door_locking_state = RxBuffer[18] & 0xFF;
  gates_state = RxBuffer[19] & 0xFF;
  gates_automatic_closing = RxBuffer[20] & 0xFF;
  ac_state = RxBuffer[21] & 0xFF;
  auto_ac = RxBuffer[22] & 0xFF;
  ac_speed = RxBuffer[23] & 0xFF;
  display_state = RxBuffer[24] & 0xFF;
  display_mode = RxBuffer[25] & 0xFF;
  temperature_threshold = RxBuffer[26] & 0xFF;
  brightness_threshold = RxBuffer[27] & 0xFF;
  air_quality_threshold = ((RxBuffer[29] & 0xFF) * 256) + (RxBuffer[47] & 0xFF);
  co2_threshold = ((RxBuffer[28] & 0xFF) * 256) + (RxBuffer[46] & 0xFF);
  temperature_unit = RxBuffer[30] & 0xFF;
  air_quality_alarm = RxBuffer[31] & 0xFF;
  co2_alarm = RxBuffer[32] & 0xFF;
  humidity_alarm = RxBuffer[33] & 0xFF;
  motion_alarm = RxBuffer[34] & 0xFF;
  custom_display_inside_temperature = RxBuffer[35] & 0xFF;
  custom_display_outside_temperature = RxBuffer[36] & 0xFF;
  custom_display_inside_humidity = RxBuffer[37] & 0xFF;
  custom_display_outside_humidity = RxBuffer[38] & 0xFF;
  custom_display_air_quality = RxBuffer[39] & 0xFF;
  custom_display_co2 = RxBuffer[40] & 0xFF;
  custom_display_rainfall = RxBuffer[41] & 0xFF;
  custom_display_brightness = RxBuffer[42] & 0xFF;
  custom_display_motion = RxBuffer[43] & 0xFF;
  room_1_color = RxBuffer[44] & 0xFF;
  room_2_color = RxBuffer[45] & 0xFF;
}

void TxUpdate() {
  TxBuffer[1] = motion_state;
  TxBuffer[2] = brightness;
  TxBuffer[3] = (inside_temperature * 100) / 100;
  TxBuffer[4] = (int)((inside_temperature * 100) / 10) % 10;
  TxBuffer[5] = inside_humidity;
  TxBuffer[6] = (outside_temperature * 100) / 100;
  TxBuffer[7] = (int)((outside_temperature * 100) / 10) % 10;
  TxBuffer[8] = outside_humidity;
  TxBuffer[9] = air_quality_ppm / 256;
  TxBuffer[10] = co2_ppm / 256;
  TxBuffer[11] = auto_door_time_expired;
  TxBuffer[12] = auto_gate_time_expired;
  TxBuffer[13] = air_quality_ppm % 256;
  TxBuffer[14] = co2_ppm % 256;
}

void setDefaultData() {

  RecoveryBuffer[2] = 5;
  RecoveryBuffer[6] = 254;
  RecoveryBuffer[7] = 254;
  RecoveryBuffer[8] = 0;
  RecoveryBuffer[10] = 254;
  RecoveryBuffer[11] = 254;
  RecoveryBuffer[12] = 0;
  RecoveryBuffer[23] = 150;
  RecoveryBuffer[26] = 24;
  RecoveryBuffer[27] = 35;
  RecoveryBuffer[28] = 15;
  RecoveryBuffer[46] = 160;
  RecoveryBuffer[29] = 11;
  RecoveryBuffer[47] = 184;
  RecoveryBuffer[35] = DISPLAYED_ON_CUSTOM;
  RecoveryBuffer[36] = DISPLAYED_ON_CUSTOM;
  RecoveryBuffer[37] = DISPLAYED_ON_CUSTOM;
  RecoveryBuffer[38] = DISPLAYED_ON_CUSTOM;
  RecoveryBuffer[39] = DISPLAYED_ON_CUSTOM;
  RecoveryBuffer[44] = YELLOW;
  RecoveryBuffer[45] = YELLOW;
}

void lightsControl() {
  updateLivingRoomLight();
  updateKitchenLight();
  updateBathroomLight(); 
  updateRoom1Light();
  updateRoom2Light();
  updateAlleyLights();
}

void readSensors() {
  readBrightnessValue();
  readMotionDetection();
  readTemperatureValues();
  readHumidityValues();
  readAQIValue();
  readCO2Value();
}

void ACControl() {
  if(AUTO_AC_OFF == auto_ac) {
    if(AC_ON == ac_state) {
      analogWrite(ac_pin, ac_speed);
    }
    else if(AC_OFF == ac_state) {
      analogWrite(ac_pin, AC_OFF);
    }
  }
  else if(AUTO_AC_ON == auto_ac) {
    if(inside_temperature > temperature_threshold) {
      analogWrite(ac_pin, 254);
    }
  }
}

void doorsControl() {
  controlGates();
  controlFrontDoor();
}

void updateLivingRoomLight() {
  if(LED_ON == living_room_led_state) {
    analogWrite(living_room_led_pin, living_room_led_brightness);
  }
  else if(LED_OFF == living_room_led_state) {
    analogWrite(living_room_led_pin, LED_OFF);
  }
}

void updateBathroomLight() {
  if(LED_ON == bathroom_led_state) {
    digitalWrite(bathroom_led_pin, LED_ON);
  }
  else if(LED_OFF == bathroom_led_state) {
    digitalWrite(bathroom_led_pin, LED_OFF);
  }

}

void updateKitchenLight() {
  if(LED_ON == kitchen_led_1_state) {
    digitalWrite(kitchen_led1_pin, LED_ON);
  }
  else if(LED_OFF == kitchen_led_1_state) {
    digitalWrite(kitchen_led1_pin, LED_OFF);
  }

  if(LED_ON == kitchen_led_2_state) {
    digitalWrite(kitchen_led2_pin, LED_ON);
  }
  else if(LED_OFF == kitchen_led_2_state) {
    digitalWrite(kitchen_led2_pin, LED_OFF);
  }
}

void updateRoom1Light() {
  if(LED_ON == room_1_led_state) {
    analogWrite(room1_led_red_color_pin, room_1_rgb_red_color);
    analogWrite(room1_led_green_color_pin, room_1_rgb_green_color);
    analogWrite(room1_led_blue_color_pin, room_1_rgb_blue_color);
  }
  else if(LED_OFF == room_1_led_state) {
    analogWrite(room1_led_red_color_pin, 0);
    analogWrite(room1_led_green_color_pin, 0);
    analogWrite(room1_led_blue_color_pin, 0);    
  }
}

void updateRoom2Light() {
  if(LED_ON == room_2_led_state) {
    analogWrite(room2_led_red_color_pin, room_2_rgb_red_color);
    analogWrite(room2_led_green_color_pin, room_2_rgb_green_color);
    analogWrite(room2_led_blue_color_pin, room_2_rgb_blue_color);
  }
  else if(LED_OFF == room_2_led_state) {
    analogWrite(room2_led_red_color_pin, 0);
    analogWrite(room2_led_green_color_pin, 0);
    analogWrite(room2_led_blue_color_pin, 0);    
  }
}

void updateAlleyLights() {

  if(MOTION_CONTROL == terrace_led_mode_control && MOTION_DETECTED == motion_state) {
    terrace_led_state = LED_ON;
  }
  else if(MOTION_CONTROL == terrace_led_mode_control && NO_MOTION == motion_state) {
    terrace_led_state = LED_OFF;
  }

  if(DARKNESS_CONTROL == terrace_led_mode_control && brightness_threshold >= brightness) {
    terrace_led_state = LED_ON;
  }
  else if(DARKNESS_CONTROL == terrace_led_mode_control && brightness_threshold < brightness) {
    terrace_led_state = LED_OFF;
  }

  if(terrace_led_state == LED_ON && alley_lights_last_state == LED_OFF)
  {
    start_alley_lights_counter = 1;
  }

  if(start_alley_lights_counter == 1) {
    alley_lights_counter++;
  }

  if(terrace_led_state == LED_OFF) {
    start_alley_lights_counter = 0;
    alley_lights_counter = 0;

    digitalWrite(terrace_left_led1_pin, LED_OFF);
    digitalWrite(terrace_right_led1_pin, LED_OFF);
    digitalWrite(terrace_left_led2_pin, LED_OFF);
    digitalWrite(terrace_right_led2_pin, LED_OFF);
    digitalWrite(terrace_left_led3_pin, LED_OFF);
    digitalWrite(terrace_right_led3_pin, LED_OFF);
    digitalWrite(terrace_left_led4_pin, LED_OFF);
    digitalWrite(terrace_right_led4_pin, LED_OFF);
  }

  if(alley_lights_counter == 1 || alley_lights_counter == 7 || alley_lights_counter == 13) {
    digitalWrite(terrace_left_led1_pin, LED_ON);
    digitalWrite(terrace_right_led1_pin, LED_ON);
    digitalWrite(terrace_left_led2_pin, LED_OFF);
    digitalWrite(terrace_right_led2_pin, LED_OFF);
    digitalWrite(terrace_left_led3_pin, LED_OFF);
    digitalWrite(terrace_right_led3_pin, LED_OFF);
    digitalWrite(terrace_left_led4_pin, LED_OFF);
    digitalWrite(terrace_right_led4_pin, LED_OFF);
  }
  if(alley_lights_counter == 2 || alley_lights_counter == 6 || alley_lights_counter == 8 || alley_lights_counter == 12) {
    digitalWrite(terrace_left_led1_pin, LED_OFF);
    digitalWrite(terrace_right_led1_pin, LED_OFF);
    digitalWrite(terrace_left_led2_pin, LED_ON);
    digitalWrite(terrace_right_led2_pin, LED_ON);
    digitalWrite(terrace_left_led3_pin, LED_OFF);
    digitalWrite(terrace_right_led3_pin, LED_OFF);
    digitalWrite(terrace_left_led4_pin, LED_OFF);
    digitalWrite(terrace_right_led4_pin, LED_OFF);
  }
  if(alley_lights_counter == 3 || alley_lights_counter == 5 || alley_lights_counter == 9 || alley_lights_counter == 11) {
    digitalWrite(terrace_left_led1_pin, LED_OFF);
    digitalWrite(terrace_right_led1_pin, LED_OFF);
    digitalWrite(terrace_left_led2_pin, LED_OFF);
    digitalWrite(terrace_right_led2_pin, LED_OFF);
    digitalWrite(terrace_left_led3_pin, LED_ON);
    digitalWrite(terrace_right_led3_pin, LED_ON);
    digitalWrite(terrace_left_led4_pin, LED_OFF);
    digitalWrite(terrace_right_led4_pin, LED_OFF);
  }
  if(alley_lights_counter == 4 || alley_lights_counter == 10) {
    digitalWrite(terrace_left_led1_pin, LED_OFF);
    digitalWrite(terrace_right_led1_pin, LED_OFF);
    digitalWrite(terrace_left_led2_pin, LED_OFF);
    digitalWrite(terrace_right_led2_pin, LED_OFF);
    digitalWrite(terrace_left_led3_pin, LED_OFF);
    digitalWrite(terrace_right_led3_pin, LED_OFF);
    digitalWrite(terrace_left_led4_pin, LED_ON);
    digitalWrite(terrace_right_led4_pin, LED_ON);
  }

  if(alley_lights_counter == 14) {
    digitalWrite(terrace_left_led1_pin, LED_ON);
    digitalWrite(terrace_right_led1_pin, LED_ON);
    digitalWrite(terrace_left_led2_pin, LED_ON);
    digitalWrite(terrace_right_led2_pin, LED_ON);
    digitalWrite(terrace_left_led3_pin, LED_ON);
    digitalWrite(terrace_right_led3_pin, LED_ON);
    digitalWrite(terrace_left_led4_pin, LED_ON);
    digitalWrite(terrace_right_led4_pin, LED_ON);

    start_alley_lights_counter = 0;
    alley_lights_counter = 0;
  }

  alley_lights_last_state = terrace_led_state;
}

void readBrightnessValue() {
  int photoresistor_value = analogRead(photorestior_pin);
  brightness = map(photoresistor_value, 0, 1023, 0, 100);
}

void readMotionDetection() {
  int pir_sensor_value = digitalRead(PIR_sensor_pin);
  if(pir_sensor_value == HIGH) {
    if(NO_MOTION == motion_state) {
      motion_state = MOTION_DETECTED;
    }
  } 
  else {
    if(MOTION_DETECTED == motion_state) {
      motion_state = NO_MOTION;
    }
  }
}

void readTemperatureValues() {
  inside_temperature = inside_dht_sensor.readTemperature();
  outside_temperature = outside_dht_sensor.readTemperature();

  if(FAHRENHEIT_UNIT == temperature_unit) {
    transformCelsiusToFahrenheit();
  }
}

void transformCelsiusToFahrenheit() {
  inside_temperature = (inside_temperature * 9 / 5) + 32;
  outside_temperature = (outside_temperature * 9 / 5) + 32;
}

void readHumidityValues() {
  inside_humidity = inside_dht_sensor.readHumidity();
  outside_humidity = outside_dht_sensor.readHumidity();
}

void readAQIValue() {
  air_quality_ppm = mq135_sensor.getCorrectedPPM(inside_temperature, inside_humidity);
}

void readCO2Value() {
  int mq2_value = analogRead(mq2_pin);
  co2_ppm = map(mq2_value, 0, 1023, 200, 10000);
}

void readRainfallValue() {
  int rainfall_sensor_value = analogRead(rainfall_sensor_pin);
  rainfall = map(rainfall_sensor_value, 0, 1023, 0, 100);
}

void controlGates() {

  if(DOOR_OPEN == gates_state) {
      left_gate_servo.write(0);
      right_gate_servo.write(180);
  }

  if(AUTO_CLOSE_OFF == gates_automatic_closing) {
    if(DOOR_CLOSE == gates_state) {
      left_gate_servo.write(90);
      right_gate_servo.write(90);
    }
    auto_gate_time_expired = TIME_NOT_EXPIRED;
  }
  else if(AUTO_CLOSE_ON == gates_automatic_closing) {
    if(DOOR_CLOSE == gates_state) {
      left_gate_servo.write(90);
      right_gate_servo.write(90);

      start_gate_counter = 0;
      gate_counter = 0;
    }

    if(DOOR_CLOSE == last_gate_state && DOOR_OPEN == gates_state) {
      start_gate_counter = 1;
      gate_counter = 0;
      auto_gate_time_expired = TIME_NOT_EXPIRED;
    }

    if(start_gate_counter == 1) {
      gate_counter++;
    }

    if(gate_counter == 40) {
      auto_gate_time_expired = TIME_EXPIRED;
    }

  }

  last_gate_state = gates_state;
}

void controlFrontDoor() {

  if(DOOR_OPEN == front_door_state) {
    door_servo.write(180);
  }

  if(AUTO_CLOSE_OFF == front_door_automatic_closing) {
    if(DOOR_CLOSE == front_door_state) {
      door_servo.write(90);
    }
    auto_door_time_expired = TIME_NOT_EXPIRED;
  }
  else if(AUTO_CLOSE_ON == front_door_automatic_closing) {
    if(DOOR_CLOSE == front_door_state) {
      door_servo.write(90);

      start_door_counter = 0;
      door_counter = 0;
    }

    if(DOOR_CLOSE == last_door_state && DOOR_OPEN == front_door_state) {
      start_door_counter = 1;
      door_counter = 0;
      auto_door_time_expired = TIME_NOT_EXPIRED;
    }

    if(start_door_counter == 1) {
      door_counter++;
    }

    if(door_counter == 40) {
      auto_door_time_expired = TIME_EXPIRED;
    }

  }

  last_door_state = front_door_state;

  if(DOOR_LOCKED == front_door_locking_state) {
    door_locker_servo.write(90);
  }
  else if(DOOR_UNLOCKED == front_door_locking_state) {
    door_locker_servo.write(0);
  }
}

void alarmControl() {

  if(ALARM_ON == air_quality_alarm && air_quality_threshold < air_quality_ppm) {
    digitalWrite(alarm_pin, LOW);
  }
  else {
    digitalWrite(alarm_pin, HIGH);
  }

  if(ALARM_ON == co2_alarm && co2_threshold < co2_ppm) {
    digitalWrite(alarm_pin, LOW);
  }
  else {
    digitalWrite(alarm_pin, HIGH);
  }

  if(ALARM_ON == humidity_alarm && 90 < inside_humidity) {
    digitalWrite(alarm_pin, LOW);
  }
  else {
    digitalWrite(alarm_pin, HIGH);
  }

  if(ALARM_ON == motion_alarm && MOTION_DETECTED == motion_state) {
    digitalWrite(alarm_pin, LOW);
  }
  else {
    digitalWrite(alarm_pin, HIGH);
  }
}

void displayControl() {
  if(TEMPERATURE_INSIDE_DISPLAY == display_mode) {
    oledDisplayInsideTemperature();
  }
  else if(TEMPERATURE_OUTSIDE_DISPLAY == display_mode) {
    oledDisplayOutsideTemperature();
  }
  else if(HUMIDITY_INSIDE_DISPLAY == display_mode) {
    oledDisplayInsideHumidity();
  }
  else if(HUMIDITY_OUTSIDE_DISPLAY == display_mode) {
    oledDisplayOutsideHumidity();
  }
  else if(TEMPERATURE_INSIDE_OUTSIDE_DISPLAY == display_mode) {
    oledDisplayInsideOutsideTemperature();
  }
  else if(HUMIDITY_INSIDE_OUTSIDE_DISPLAY == display_mode) {
    oledDisplayInsideOutsideHumidity();
  }
  else if(AIR_QUALITY_DISPLAY == display_mode) {
    oledDisplayAirQuality();
  }
  else if(GAS_DISPLAY == display_mode) {
    oledDisplayGas();
  }
  else if(RAINFALL_DISPLAY == display_mode) {
    oledDisplayRainfall();
  }
  else if(CUSTOM_DISPLAY == display_mode) {
    oledDisplayCustom();
  }
}

void oledDisplayInsideTemperature()
{
  long integer_temperature = (inside_temperature * 100) / 100;
  String string_temperature = String(integer_temperature);
  
  myDisplay.clearDisplay();

  myDisplay.drawBitmap(0, 10, epd_bitmap_temperature, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(70, 10);
  myDisplay.println("Inisde");

  myDisplay.setCursor(55, 20);
  myDisplay.println("Temperature");

  myDisplay.setTextSize(3);
  myDisplay.setCursor(60, 40);
  myDisplay.println(string_temperature);

  myDisplay.drawCircle(100, 40, 3, WHITE);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(106, 40);
  if(CELSIUS_UNIT == temperature_unit) {
    myDisplay.println("C");
  }
  else if(FAHRENHEIT_UNIT == temperature_unit) {
    myDisplay.println("F");
  }

  myDisplay.display();
}

void oledDisplayOutsideTemperature()
{
  long integer_temperature = (outside_temperature * 100) / 100;
  String string_temperature = String(integer_temperature);
  
  myDisplay.clearDisplay();

  myDisplay.drawBitmap(0, 10, epd_bitmap_temperature, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(67, 10);
  myDisplay.println("Outside");

  myDisplay.setCursor(55, 20);
  myDisplay.println("Temperature");

  myDisplay.setTextSize(3);
  myDisplay.setCursor(60, 40);
  myDisplay.println(string_temperature);

  myDisplay.drawCircle(100, 40, 3, WHITE);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(106, 40);
  if(CELSIUS_UNIT == temperature_unit) {
    myDisplay.println("C");
  }
  else if(FAHRENHEIT_UNIT == temperature_unit) {
    myDisplay.println("F");
  }

  myDisplay.display();
}

void oledDisplayInsideHumidity()
{
  String string_humidity = String(inside_humidity);

  myDisplay.clearDisplay();

  myDisplay.drawBitmap(5, 10, epd_bitmap_humidity, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(66, 10);
  myDisplay.println("Inside");

  myDisplay.setCursor(60, 20);
  myDisplay.println("Humidity");

  myDisplay.setTextSize(3);
  myDisplay.setCursor(65, 40);
  myDisplay.println(string_humidity);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(105, 48);
  myDisplay.println("%");

  myDisplay.display();  
}

void oledDisplayOutsideHumidity()
{
  String string_humidity = String(outside_humidity);

  myDisplay.clearDisplay();

  myDisplay.drawBitmap(5, 10, epd_bitmap_humidity, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(63, 10);
  myDisplay.println("Outside");

  myDisplay.setCursor(60, 20);
  myDisplay.println("Humidity");

  myDisplay.setTextSize(3);
  myDisplay.setCursor(65, 40);
  myDisplay.println(string_humidity);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(105, 48);
  myDisplay.println("%");

  myDisplay.display();  
}

void oledDisplayAirQuality()
{

  String string_aqi_ppm = String(air_quality_ppm);

  myDisplay.clearDisplay();

  myDisplay.drawBitmap(0, 10, epd_bitmap_airquality, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(55, 10);
  myDisplay.println("Air Quality");

  myDisplay.setTextSize(2);
  myDisplay.setCursor(65, 30);
  myDisplay.println(string_aqi_ppm);

  myDisplay.setTextSize(1);
  myDisplay.setCursor(95, 50);
  myDisplay.println("ppm");

  myDisplay.display();
}

void oledDisplayGas()
{

  String string_co2_ppm = String(co2_ppm);

  myDisplay.clearDisplay();

  myDisplay.drawBitmap(0, 10, epd_bitmap_gas, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(78, 8);
  myDisplay.println("Gas");

  myDisplay.setCursor(50, 18);
  myDisplay.println("Concentration");

  myDisplay.setTextSize(2);
  myDisplay.setCursor(65, 35);
  myDisplay.println(string_co2_ppm);

  myDisplay.setTextSize(1);
  myDisplay.setCursor(95, 55);
  myDisplay.println("ppm");

  myDisplay.display();
}

void oledDisplayRainfall()
{
  String string_rainfall = String(rainfall);

  myDisplay.clearDisplay();

  myDisplay.drawBitmap(5, 10, epd_bitmap_rainfall, 50, 50, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(70, 10);
  myDisplay.println("Rainfall");

  myDisplay.setTextSize(3);
  if(rainfall < 10) {
    myDisplay.setCursor(75, 30);
  }
  else {
    myDisplay.setCursor(65, 30);
  }
  myDisplay.println(string_rainfall);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(105, 38);
  myDisplay.println("%");

  myDisplay.display();  
}

void oledDisplayInsideOutsideTemperature() {

  long integer_inside_temperature = (inside_temperature * 100) / 100;
  String string_inside_temperature = String(integer_inside_temperature);

  long integer_outside_temperature = (outside_temperature * 100) / 100;
  String string_outside_temperature = String(integer_outside_temperature);
  
  myDisplay.clearDisplay();

  myDisplay.drawRect(0, 0, 128, 64, WHITE);
  myDisplay.drawRect(0, 0, 128, 15, WHITE);
  myDisplay.drawRect(0, 14, 64, 49, WHITE);
  myDisplay.drawRect(0, 14, 64, 15, WHITE);
  myDisplay.drawRect(64, 14, 64, 15, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(32, 3);
  myDisplay.println("TEMPERATURE");

  myDisplay.setCursor(15, 18);
  myDisplay.println("INSIDE");

  myDisplay.setCursor(76, 18);
  myDisplay.println("OUTSIDE");

  myDisplay.setTextSize(2);
  myDisplay.setCursor(10, 40);
  myDisplay.println(string_inside_temperature);

  myDisplay.drawCircle(38, 40, 2, WHITE);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(44, 40);
  if(CELSIUS_UNIT == temperature_unit) {
    myDisplay.println("C");
  }
  else if(FAHRENHEIT_UNIT == temperature_unit) {
    myDisplay.println("F");
  }

  myDisplay.setTextSize(2);
  myDisplay.setCursor(74, 40);
  myDisplay.println(string_outside_temperature);

  myDisplay.drawCircle(102, 40, 2, WHITE);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(110, 40);
  if(CELSIUS_UNIT == temperature_unit) {
    myDisplay.println("C");
  }
  else if(FAHRENHEIT_UNIT == temperature_unit) {
    myDisplay.println("F");
  }

  myDisplay.display();  
}

void oledDisplayInsideOutsideHumidity() {

  String string_inside_humidity = String(inside_humidity);
  String string_outside_humidity = String(outside_humidity);
  
  myDisplay.clearDisplay();

  myDisplay.drawRect(0, 0, 128, 64, WHITE);
  myDisplay.drawRect(0, 0, 128, 15, WHITE);
  myDisplay.drawRect(0, 14, 64, 49, WHITE);
  myDisplay.drawRect(0, 14, 64, 15, WHITE);
  myDisplay.drawRect(64, 14, 64, 15, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(38, 3);
  myDisplay.println("HUMIDITY");

  myDisplay.setCursor(15, 18);
  myDisplay.println("INSIDE");

  myDisplay.setCursor(76, 18);
  myDisplay.println("OUTSIDE");

  myDisplay.setTextSize(2);
  myDisplay.setCursor(12, 40);
  myDisplay.println(string_inside_humidity);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(42, 40);
  myDisplay.println("%");

  myDisplay.setTextSize(2);
  myDisplay.setCursor(76, 40);
  myDisplay.println(string_outside_humidity);

  myDisplay.setTextSize(2);
  myDisplay.setCursor(108, 40);
  myDisplay.println("%");

  myDisplay.display();  
}

void oledDisplayCustom() {

  int column = 9;

  long integer_inside_temperature = (inside_temperature * 100) / 10;
  long integer_outside_temperature = (outside_temperature * 100) / 10;

  String string_temperature_inside = String(integer_inside_temperature / 10) + "." + String(integer_inside_temperature % 10);
  String string_temperature_outside = String(integer_outside_temperature / 10) + "." + String(integer_outside_temperature % 10);
  String string_humidity_inside = String(inside_humidity);
  String string_humidity_outside = String(outside_humidity);
  String string_aq = String(air_quality_ppm);
  String string_co2 = String(co2_ppm);
  String string_rainfall = String(rainfall);
  String string_brightness = String(brightness);

  myDisplay.clearDisplay();

  myDisplay.drawRect(0, 0, 128, 64, WHITE);
  myDisplay.drawRect(0, 0, 128, 15, WHITE);

  myDisplay.setTextSize(1);
  myDisplay.setTextColor(WHITE);

  myDisplay.setCursor(5, 3);
  myDisplay.println("MEASURE");

  myDisplay.setCursor(73, 3);
  myDisplay.println("VAL");

  myDisplay.setCursor(100, 3);
  myDisplay.println("UNIT");

  if(DISPLAYED_ON_CUSTOM == custom_display_inside_temperature) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Temp In");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_temperature_inside);

    myDisplay.drawCircle(102, column, 2, WHITE);
    myDisplay.setCursor(106, column);
    if(CELSIUS_UNIT == temperature_unit) {
      myDisplay.println("C");
    }
    else if(FAHRENHEIT_UNIT == temperature_unit) {
      myDisplay.println("F");
    }
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_outside_temperature) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Temp Out");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_temperature_outside);

    myDisplay.drawCircle(102, column, 2, WHITE);
    myDisplay.setCursor(106, column);
    if(CELSIUS_UNIT == temperature_unit) {
      myDisplay.println("C");
    }
    else if(FAHRENHEIT_UNIT == temperature_unit) {
      myDisplay.println("F");
    }
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_inside_humidity) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Hum In");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_humidity_inside);

    myDisplay.setCursor(102, column);
    myDisplay.println("%");
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_outside_humidity) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Hum Out");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_humidity_outside);

    myDisplay.setCursor(102, column);
    myDisplay.println("%");
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_air_quality) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Air Q");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_aq);

    myDisplay.setCursor(102, column);
    myDisplay.println("ppm");
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_co2) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Gas");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_co2);

    myDisplay.setCursor(102, column);
    myDisplay.println("ppm");
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_rainfall) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Rain");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_rainfall);

    myDisplay.setCursor(102, column);
    myDisplay.println("%");
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_brightness) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Bright");

    myDisplay.setCursor(73, column);
    myDisplay.println(string_brightness);

    myDisplay.setCursor(102, column);
    myDisplay.println("%");
  }

  if(DISPLAYED_ON_CUSTOM == custom_display_motion) {
    column = column + 9;

    myDisplay.setCursor(5, column);
    myDisplay.println("Motion");

    myDisplay.setCursor(73, column);
    if(MOTION_DETECTED == motion_state) {
      myDisplay.println("ON");
    }
    else {
      myDisplay.println("OFF");
    }

    myDisplay.setCursor(102, column);
    myDisplay.println("");
  }

  myDisplay.display();
}
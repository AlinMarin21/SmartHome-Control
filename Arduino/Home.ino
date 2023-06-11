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
#define TEMPERATURE_HUMIDITY_INSIDE_DISPLAY 4
#define TEMPERATURE_HUMIDITY_OUTSIDE_DISPLAY 5
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

int serial_availability = 0;
int recovery_debounce = 0;

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
float outside_temprature = 0.0;
int inside_humidity = 0;
int outside_humidity = 0;
int air_quality_ppm = 0;
int co2_ppm = 0;
int auto_door_time_expired = TIME_NOT_EXPIRED;
int auto_gate_time_expired = TIME_NOT_EXPIRED;

int i = 0;
float temp = 22.7;
int hum = 0;
int aq = 240;
int co2 = 1340;

int door_cnt = 0;
int gate_cnt = 0;
int start_door_cnt = 0;
int start_gate_cnt = 0;
int last_door_state = 0;
int last_gate_state = 0;

void setup() {
  Serial.begin(9600);
  TxBuffer[0] = SOB;
  TxBuffer[48] = EOB;
}

void loop() {

  if(Serial.available() > 0) {
    recovery_debounce = 0;
    
    if(serial_availability == 0) {
      serial_availability = 1;
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

  tx1Update();

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
  if(255 == ReceivedData[0] || 255 == ReceivedData[44]) {
    //do nothing
  }
  else {
    for(int i = 0; i < RX_ELEMENTS; i++) {
      RxBuffer[i] = ReceivedData[i];
    }
  }
}

void writeTransmitterBuffer() {
  for(int i = 0; i < TX_ELEMENTS; i++) {
    Serial.write(TxBuffer[i]);
  }
}

void communicationWithBluetoothModule() {
  readReceiverBuffer();
  writeTransmitterBuffer();
}

void recoverLastReceivedData() {
  for(int i = 0; i < RX_ELEMENTS; i++) {
    RecoveryBuffer[i] = RxBuffer[i];
  }
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
  TxBuffer[6] = (outside_temprature * 100) / 100;
  TxBuffer[7] = (int)((outside_temprature * 100) / 10) % 10;
  TxBuffer[8] = outside_humidity;
  TxBuffer[9] = air_quality_ppm / 256;
  TxBuffer[10] = co2_ppm / 256;
  TxBuffer[11] = auto_door_time_expired;
  TxBuffer[12] = auto_gate_time_expired;
  TxBuffer[13] = air_quality_ppm % 256;
  TxBuffer[14] = co2_ppm % 256;
}

void tx1Update() {
  hum++;
   i++;
   aq = aq + 3;
   co2 = co2 + 2;

   if(i==100)
   {
     TxBuffer[1] = 1;
   }
   if(i == 200) {
     TxBuffer[1] = 0;
   }

   if(i==50) {
     TxBuffer[2] = 25;
   }
   if(i==150) {
     TxBuffer[2] = 60;
   }

  if(i==100) {
    hum = 0;
  }

  if(i%50 == 0) {
    temp = temp + 0.3;
  }

  if(RxBuffer[16] == 1 && last_door_state == 0) {
    start_door_cnt = 1;
    TxBuffer[11] = 0;
  }
  if(RxBuffer[19] == 1 && last_gate_state == 0) {
    start_gate_cnt = 1;
    TxBuffer[12] = 0;
  }

  if(start_door_cnt == 1) {
    door_cnt++;
  }
  if(start_gate_cnt == 1) {
    gate_cnt++;
  }

  if(door_cnt == 40 && RxBuffer[17] == 1) {
    TxBuffer[11] = 1;
  }
  if(gate_cnt == 40 && RxBuffer[20] == 1) {
    TxBuffer[12] = 1;
  }

  if(RxBuffer[16] == 0) {
    start_door_cnt = 0;
    door_cnt = 0;
    TxBuffer[11] = 0;
  }
  if(RxBuffer[19] == 0) {
    start_gate_cnt = 0;
    gate_cnt = 0;
    TxBuffer[12] = 0;
  }


  int t = (int)((temp * 100) / 10) % 10;

  TxBuffer[3] = (temp * 100) / 100;
  TxBuffer[4] = t;
  TxBuffer[5] = hum;
  TxBuffer[8] = hum;
  TxBuffer[6] = (temp * 100) / 100;
  TxBuffer[7] = t;
  TxBuffer[9] = aq/256;
  TxBuffer[13] = aq%256;
  TxBuffer[10] = co2/256;
  TxBuffer[14] = co2%256;

   if(i==200) {
     i = 0;
     hum = 0;
     aq = 189;
     co2 = 1142;
   }

   last_door_state = RxBuffer[16];
   last_gate_state = RxBuffer[19];
   
}


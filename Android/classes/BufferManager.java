package com.example.home;

public class BufferManager {

    public static final int RX_ELEMENTS = 49;
    public static final int TX_ELEMENTS = 49;

    public static final int SOB = 255;
    public static final int EOB = 255;

    public static byte[] RxBuffer = new byte[RX_ELEMENTS];
    /* Rx Buffer
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
    public static byte[] TxBuffer = new byte[TX_ELEMENTS];
    /* Tx Buffer
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

    public static String currentLanguage;
}

package com.github.xingshuangs.iot.protocol.modbus.service;

import com.github.xingshuangs.iot.protocol.common.buff.EByteBuffFormat;
import com.github.xingshuangs.iot.utils.HexUtil;
import com.github.xingshuangs.iot.utils.ShortUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
@Ignore
public class ModbusRtuOverTcpTest {

    private final ModbusRtuOverTcp plc = new ModbusRtuOverTcp("127.0.0.1");

    @Before
    public void before() {
        this.plc.setComCallback(x -> log.debug("长度[{}]，内容：{}", x.length, HexUtil.toHexString(x)));
    }

    @Test
    public void readCoil() {
        List<Boolean> booleans = plc.readCoil(0, 1);
        assertEquals(1, booleans.size());
        assertArrayEquals(new Boolean[]{true}, booleans.toArray(new Boolean[0]));

        booleans = plc.readCoil(0, 4);
        assertArrayEquals(new Boolean[]{true, false, true, false}, booleans.toArray(new Boolean[0]));
    }

    @Test
    public void writeCoil() {
        plc.writeCoil(0, true);

        List<Boolean> list = Arrays.asList(true, false, true, false);
        plc.writeCoil(0, list);
    }

    @Test
    public void writeCoil1() {
        plc.writeCoil(0, true);
        List<Boolean> booleans = plc.readCoil(0, 1);
        assertArrayEquals(new Boolean[]{true}, booleans.toArray(new Boolean[0]));
        List<Boolean> list = Arrays.asList(true, false, true, false);
        plc.writeCoil(0, list);
        booleans = plc.readCoil(0, 4);
        assertArrayEquals(new Boolean[]{true, false, true, false}, booleans.toArray(new Boolean[0]));

        plc.writeCoil(2,0, true);
        booleans = plc.readCoil(2,0, 1);
        assertArrayEquals(new Boolean[]{true}, booleans.toArray(new Boolean[0]));
        list = Arrays.asList(true, false, true, false);
        plc.writeCoil(2,0, list);
        booleans = plc.readCoil(2,0, 4);
        assertArrayEquals(new Boolean[]{true, false, true, false}, booleans.toArray(new Boolean[0]));
    }

    @Test
    public void readDiscreteInput() {
        List<Boolean> booleans = plc.readDiscreteInput(2,0, 4);
        assertEquals(4, booleans.size());
        assertArrayEquals(new Boolean[]{true, true, true, false}, booleans.toArray(new Boolean[0]));
    }

    @Test
    public void readInputRegister() {
        byte[] bytes = plc.readInputRegister(2,0, 2);
        assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0x21, (byte) 0x00, (byte) 0x00}, bytes);
    }

    @Test
    public void readHoldRegister() {
        byte[] bytes = plc.readHoldRegister(0, 1);
        assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0x33}, bytes);
        int actual = ShortUtil.toUInt16(bytes);
        assertEquals(51, actual);

        bytes = plc.readHoldRegister(0, 2);
        assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0x33, (byte) 0x00, (byte) 0x21}, bytes);

        bytes = plc.readHoldRegister(0, 4);
        assertArrayEquals(new byte[]{(byte) 0x00, (byte) 0x33, (byte) 0x00, (byte) 0x21,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00}, bytes);
    }

    @Test
    public void writeHoldRegister() {
        plc.writeHoldRegister(2, 123);

        List<Integer> list = Arrays.asList(11, 12, 13, 14);
        plc.writeHoldRegister(3, list);
    }

    @Test
    public void readBoolean() {
        byte[] expect = new byte[]{(byte) 0x22, (byte) 0xF0};
        plc.writeHoldRegister(0, expect);
        boolean b = this.plc.readBoolean(0, 1);
        assertTrue(b);
        b = this.plc.readBoolean(0, 5);
        assertTrue(b);
        b = this.plc.readBoolean(0, 6);
        assertFalse(b);
        b = this.plc.readBoolean(0, 9);
        assertFalse(b);
        b = this.plc.readBoolean(0, 12);
        assertTrue(b);
        b = this.plc.readBoolean(0, 15);
        assertTrue(b);
    }

    @Test
    public void readWriteData() {
        plc.writeInt16(2, (short) 10);
        short data = plc.readInt16(2);
        assertEquals(10, data);

        plc.writeUInt16(3, 20);
        int i = plc.readUInt16(3);
        assertEquals(20, i);

        plc.writeInt32(4, 32);
        int i1 = plc.readInt32(4);
        assertEquals(32, i1);

        plc.writeUInt32(6, 32L);
        long l = plc.readUInt32(6);
        assertEquals(32L, l);

        plc.writeFloat32(8, 12.12f);
        float v = plc.readFloat32(8);
        assertEquals(12.12f, v, 0.0001);

        plc.writeFloat64(10, 33.21);
        double v1 = plc.readFloat64(10);
        assertEquals(33.21, v1, 0.0001);

        plc.writeString(14, "pppp");
        String s = plc.readString(14, 4);
        assertEquals("pppp", s);
    }

    @Test
    public void readWriteData1() {
        plc.writeInt16(2, (short) 10, true);
        short data = plc.readInt16(2, true);
        assertEquals(10, data);

        plc.writeUInt16(3, 20, true);
        int i = plc.readUInt16(3, true);
        assertEquals(20, i);

        plc.writeInt32(4, 32, EByteBuffFormat.DC_BA);
        int i1 = plc.readInt32(4, EByteBuffFormat.DC_BA);
        assertEquals(32, i1);

        plc.writeUInt32(6, 32L, EByteBuffFormat.DC_BA);
        long l = plc.readUInt32(6, EByteBuffFormat.DC_BA);
        assertEquals(32L, l);

        plc.writeFloat32(8, 12.12f, EByteBuffFormat.DC_BA);
        float v = plc.readFloat32(8, EByteBuffFormat.DC_BA);
        assertEquals(12.12f, v, 0.0001);

        plc.writeFloat64(10, 33.21, EByteBuffFormat.DC_BA);
        double v1 = plc.readFloat64(10, EByteBuffFormat.DC_BA);
        assertEquals(33.21, v1, 0.0001);

        plc.writeString(14, "pppp", StandardCharsets.UTF_8);
        String s = plc.readString(14, 4, StandardCharsets.UTF_8);
        assertEquals("pppp", s);
    }

    @Test
    public void readWriteData2() {
        plc.writeString(2, "1234");
        String s = plc.readString(2, 4);
        assertEquals("1234", s);
    }

    @Test
    public void readWriteData3() {
        plc.writeInt16(1,2, (short) 10);
        short data = plc.readInt16(1,2);
        assertEquals(10, data);

        plc.writeUInt16(2,3, 20);
        int i = plc.readUInt16(2,3);
        assertEquals(20, i);

        plc.writeInt32(1,4, 32);
        int i1 = plc.readInt32(1,4);
        assertEquals(32, i1);

        plc.writeUInt32(2,6, 32L);
        long l = plc.readUInt32(2,6);
        assertEquals(32L, l);

        plc.writeFloat32(1,8, 12.12f);
        float v = plc.readFloat32(1,8);
        assertEquals(12.12f, v, 0.0001);

        plc.writeFloat64(2,10, 33.21);
        double v1 = plc.readFloat64(2,10);
        assertEquals(33.21, v1, 0.0001);

        plc.writeString(1,14, "pppp");
        String s = plc.readString(1,14, 4);
        assertEquals("pppp", s);
    }

}
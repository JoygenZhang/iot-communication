package com.github.xingshuangs.iot.protocol.s7.model;

import com.github.xingshuangs.iot.protocol.s7.enums.EDataVariableType;
import com.github.xingshuangs.iot.protocol.s7.enums.EReturnCode;
import org.junit.Test;

import static org.junit.Assert.*;


public class DatumTest {

    @Test
    public void byteArrayLength() {
        DataItem dataItem = new DataItem();
        ReadWriteDatum datum = new ReadWriteDatum();
        datum.getReturnItems().add(dataItem);
        assertEquals(4, datum.byteArrayLength());
    }

    @Test
    public void toByteArray() {
        DataItem dataItem = new DataItem();
        dataItem.setReturnCode(EReturnCode.from((byte) 0xFF));
        dataItem.setVariableType(EDataVariableType.BYTE_WORD_DWORD);
        dataItem.setCount(1);
        dataItem.setData(new byte[1]);
        ReadWriteDatum datum = new ReadWriteDatum();
        datum.getReturnItems().add(dataItem);
        byte[] actual = datum.toByteArray();
        byte[] expect = {(byte) 0xFF, (byte) 0x04, (byte) 0x00, (byte) 0x08, (byte) 0x00};
        assertArrayEquals(expect, actual);
    }
}
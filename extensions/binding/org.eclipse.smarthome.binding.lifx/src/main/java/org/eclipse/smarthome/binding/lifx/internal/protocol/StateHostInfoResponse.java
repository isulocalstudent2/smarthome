/**
 * Copyright (c) 2014,2019 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.binding.lifx.internal.protocol;

import java.nio.ByteBuffer;

import org.eclipse.smarthome.binding.lifx.internal.fields.ByteField;
import org.eclipse.smarthome.binding.lifx.internal.fields.Field;
import org.eclipse.smarthome.binding.lifx.internal.fields.FloatField;
import org.eclipse.smarthome.binding.lifx.internal.fields.UInt32Field;

/**
 * @author Tim Buckley - Initial Contribution
 * @author Karel Goderis - Enhancement for the V2 LIFX Firmware and LAN Protocol Specification
 */
public class StateHostInfoResponse extends Packet {

    public static final int TYPE = 0x0D;

    public static final Field<Float> FIELD_SIGNAL = new FloatField().getLittleField();
    public static final Field<Long> FIELD_TX = new UInt32Field().getLittleField();
    public static final Field<Long> FIELD_RX = new UInt32Field().getLittleField();
    public static final Field<ByteBuffer> FIELD_RESERVED_5 = new ByteField(2);

    private float signal;
    private long tx;
    private long rx;

    public SignalStrength getSignalStrength() {
        return new SignalStrength(signal);
    }

    public long getTx() {
        return tx;
    }

    public long getRx() {
        return rx;
    }

    public StateHostInfoResponse() {
        setTagged(false);
        setAddressable(true);
        setResponseRequired(true);
    }

    @Override
    public int packetType() {
        return TYPE;
    }

    @Override
    protected int packetLength() {
        return 14;
    }

    @Override
    protected void parsePacket(ByteBuffer bytes) {
        signal = FIELD_SIGNAL.value(bytes);
        tx = FIELD_TX.value(bytes);
        rx = FIELD_RX.value(bytes);
    }

    @Override
    protected ByteBuffer packetBytes() {
        return ByteBuffer.allocate(packetLength()).put(FIELD_SIGNAL.bytes(signal)).put(FIELD_TX.bytes(tx))
                .put(FIELD_RX.bytes(rx));
    }

    @Override
    public int[] expectedResponses() {
        return new int[] {};
    }

}

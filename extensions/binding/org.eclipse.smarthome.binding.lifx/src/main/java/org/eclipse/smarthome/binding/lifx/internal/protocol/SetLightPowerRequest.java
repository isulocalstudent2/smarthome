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

import org.eclipse.smarthome.binding.lifx.internal.fields.Field;
import org.eclipse.smarthome.binding.lifx.internal.fields.UInt16Field;
import org.eclipse.smarthome.binding.lifx.internal.fields.UInt32Field;

/**
 * @author Tim Buckley - Initial Contribution
 * @author Karel Goderis - Enhancement for the V2 LIFX Firmware and LAN Protocol Specification
 */
public class SetLightPowerRequest extends Packet {

    public static final int TYPE = 0x75;

    public static final Field<Integer> FIELD_STATE = new UInt16Field();
    public static final Field<Long> FIELD_DURATION = new UInt32Field().getLittleField();

    private PowerState state;
    private long duration;

    public PowerState getState() {
        return state;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public SetLightPowerRequest() {
        state = PowerState.OFF;
        this.duration = 0;
        setTagged(false);
        setAddressable(true);
        setResponseRequired(true);
    }

    public SetLightPowerRequest(PowerState state) {
        this.state = state;
        this.duration = 0;
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
        return 6;
    }

    @Override
    protected void parsePacket(ByteBuffer bytes) {
        state = PowerState.fromValue(FIELD_STATE.value(bytes));
        duration = FIELD_DURATION.value(bytes);
    }

    @Override
    protected ByteBuffer packetBytes() {
        return ByteBuffer.allocate(packetLength()).put(FIELD_STATE.bytes(state.getValue()))
                .put(FIELD_DURATION.bytes(duration));
    }

    @Override
    public int[] expectedResponses() {
        return new int[] { StateLightPowerResponse.TYPE };
    }

}

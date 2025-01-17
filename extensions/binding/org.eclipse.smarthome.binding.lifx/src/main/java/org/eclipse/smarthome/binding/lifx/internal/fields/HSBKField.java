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
package org.eclipse.smarthome.binding.lifx.internal.fields;

import java.nio.ByteBuffer;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * @author Wouter Born - Add support for MultiZone light control
 */
@NonNullByDefault
public class HSBKField extends Field<HSBK> {

    public static final Field<Integer> FIELD_HUE = new UInt16Field().getLittleField();
    public static final Field<Integer> FIELD_SATURATION = new UInt16Field().getLittleField();
    public static final Field<Integer> FIELD_BRIGHTNESS = new UInt16Field().getLittleField();
    public static final Field<Integer> FIELD_KELVIN = new UInt16Field().getLittleField();

    @Override
    public int defaultLength() {
        return 8;
    }

    @Override
    public HSBK value(ByteBuffer bytes) {
        int hue = FIELD_HUE.value(bytes);
        int saturation = FIELD_SATURATION.value(bytes);
        int brightness = FIELD_BRIGHTNESS.value(bytes);
        int kelvin = FIELD_KELVIN.value(bytes);

        return new HSBK(hue, saturation, brightness, kelvin);
    }

    @Override
    protected ByteBuffer bytesInternal(HSBK value) {
        return ByteBuffer.allocate(defaultLength()).put(FIELD_HUE.bytes(value.getHue()))
                .put(FIELD_SATURATION.bytes(value.getSaturation())).put(FIELD_BRIGHTNESS.bytes(value.getBrightness()))
                .put(FIELD_KELVIN.bytes(value.getKelvin()));
    }

}

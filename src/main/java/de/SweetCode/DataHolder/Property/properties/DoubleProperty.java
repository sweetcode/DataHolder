/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Jan Kr�ger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 *
 */

package de.SweetCode.DataHolder.Property.properties;

import de.SweetCode.DataHolder.Property.Property;
import de.SweetCode.DataHolder.utils.Optional;

/**
 * Created by Yonas on 18.09.2015.
 */
public class DoubleProperty<K> implements Property<K, Double> {

    private K key;
    private Optional<Double> value;

    public DoubleProperty(K key, Optional<Double> value) {
        this.key = key;
        this.value = value;
    }

    public DoubleProperty(K key, double value) {
        this(key, Optional.of(value));
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public Optional<Double> getValue() {
        return this.value;
    }

    @Override
    public Property<K, Double> update(Double value) {

        this.value = Optional.of(value);

        return this;

    }

}

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

package de.SweetCode.DataHolder.DataCarriers;

import com.google.common.collect.ArrayListMultimap;
import de.SweetCode.DataHolder.DataCarrier;
import de.SweetCode.DataHolder.Property.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Yonas on 15.09.2015.
 */
public class DataHolder implements DataCarrier {

    private ArrayListMultimap<Class<?>, Property<?, ?>> datas;

    public DataHolder() {
        this.datas = ArrayListMultimap.create();
    }

    public DataHolder(int expectedPropertyTypes, int expectedPropertyPerTypes) {
        this.datas = ArrayListMultimap.create(expectedPropertyTypes, expectedPropertyPerTypes);
    }

    /**
     * Stores a Property in the DataHolder
     * @param property
     * @return Returns true if it was successfully and false if a Property with the same key already exists in the DataHolder object.
     */
    @Override
    public boolean store(Property<?, ?> property) {

        if(this.contains(property.getClass(), property.getKey())) {
            return false;
        }

        this.datas.put(property.getClass(), property);

        return true;

    }

    /**
     * Returns all stored Properties for this DataHolder.
     * @return
     */
    @Override
    public Collection<Property<?, ?>> getProperties() {

        return this.datas.values();

    }

    /**
     * Returns all stored Property types.
     * @return
     */
    @Override
    public Collection<Class<?>> getPropertyTypes() {

        return this.datas.keySet();

    }

    /**
     * Returns the first stored Property.
     * @param propertyClass The property class which implements the {@see de.SweetCode.DataHolder.Property.Property} interface.
     * @param <T>
     * @return T the result, if no property is stored for the class the function will return null.
     */
    public <T extends Property<?, ?>> T getFirstProperty(Class<T> propertyClass) {

        if(!(this.datas.containsKey(propertyClass))) {
            return null;
        }

        return ((T) this.datas.get(propertyClass).get(0));

    }

    /**
     * Returns the Property by key.
     * @param <T>
     * @param propertyClass The property class which implements the {@see de.SweetCode.DataHolder.Property.Property} interface.
     * @return T the result, if no property is stored for the class the function will return null.
     */
    @Override
    public <T extends Property> T getProperty(Class<T> propertyClass, Object key) {

        List<T> list = this.getProperties(propertyClass);

        if(list.isEmpty()) {
            return null;
        }

        for(T entry : this.getProperties(propertyClass)) {

            if(entry.getKey().equals(key)) {
                return entry;
            }

        }

        return null;

    }

    /**
     * Returns all stored Properties.
     * @param propertyClass The property class which implements the {@see de.SweetCode.DataHolder.Property.Property} interface.
     * @param <T>
     * @return Collection<T> A collection of all stored Properties of the same type if no Property is stored for the given class the function will return a empty ArrayList.
     */
    public <T extends Property<?, ?>> List<T> getProperties(Class<T> propertyClass) {

        if(!(this.datas.containsKey(propertyClass))) {
            return new ArrayList<>();
        }

        return (List<T>) this.datas.get(propertyClass);

    }

    /**
     * Returns the amount of the stored Properties
     * @return
     */
    @Override
    public int size() {
        return this.datas.size();
    }

    /**
     * Checks if the DataHolder contains the given Property class.
     * @param propertyClass The Property class
     * @param <T>
     * @return true if the DataHolder contains the Property class.
     */
    public <T extends Property<?, ?>> boolean contains(Class<T> propertyClass) {

        return (!(this.datas.get(propertyClass) == null));

    }

    /**
     * Checks if the DataHolder contains the given Property class and if the class is related with the given key.
     * @param propertyClass The Property class
     * @param <T>
     * @return true if the DataHolder contains the Property class with the related key.
     */
    @Override
    public <T extends Property<?, ?>> boolean contains(Class<T> propertyClass, Object key) {

        return (!(this.getProperty(propertyClass, key) == null));

    }

    /**
     * Delets a Property that is related with a key.
     * @param propertyClass The Property class.
     * @param key The related key.
     * @param <T>
     * @return returns the deleted Property object if the DataHolder contains the given pair of Property class and key otherwise it returns null.
     */
    @Override
    public <T extends Property<?, ?>> T deleteProperty(Class<T> propertyClass, Object key) {

        if(!(this.contains(propertyClass, key))) {
            return null;
        }

        T property = this.getProperty(propertyClass, key);
        this.deleteProperty(property);
        return property;

    }

    /**
     * Delets a Property.
     * @param property The Property object.
     * @param <T>
     * @return returns the deleted Property object if the DataHolder contains the given pair of Property class and key otherwise it returns null.
     */
    public <T extends Property<?, ?>> T deleteProperty(T property) {

        if(!(this.contains(property.getClass(), property.getKey()))) {
            return null;
        }

        this.datas.remove(property.getClass(), property);
        return property;

    }

    /**
     * Delets all Properties related to this Property class.
     * @param <T>
     * @param propertyClass The Property class.
     * @return returns a list of all deleted Properties.
     */
    public <T extends Property<?, ?>> List<T> deleteProperties(Class<T> propertyClass) {

        if(!(this.contains(propertyClass))) {
            return null;
        }

        List<T> properties = this.getProperties(propertyClass);
        this.datas.removeAll(propertyClass);
        return properties;

    }

    /**
     * Deletes all stored Properties.
     */
    @Override
    public void clear() {
        this.datas.clear();
    }

}
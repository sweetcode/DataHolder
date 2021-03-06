# DataHolder ![https://travis-ci.org/sweetcode/DataHolder](https://api.travis-ci.org/sweetcode/DataHolder.svg)
---
`DataHolder` is a simple project to provide a easy-to-use data storage solution.
You will find some examples of the usage below.

Please keep in mind that this version isn't ready for a production environment. Every
release can contain basic changes.

#### [Latest Release](https://github.com/sweetcode/DataHolder/releases/latest) | [All Releases](https://github.com/sweetcode/DataHolder/releases/)

---
#### Basic Usage
```java
import de.SweetCode.DataHolder.DataCarrier.DataHolder;

public class PlayerData extends DataHolder {

    public PlayerData() {}

}
```

Thats it. You have a working `DataHolder` now. `DataHolder` is a basic class that implements
the `DataCarrier`. I will explain the `DataCarrier` interface below.

---
### DataCarrier
The `DataCarrier` interface provides all required methods for a basic data carrier.
Each method in the interface has a really descriptive name and Java doc. The project
provides some basic DataCarriers such as

- `DataHolder` It's the basic DataHolder that provides a simple multi-value-by-key storage. That means you can store different `Property objects` with the same key (`Property class`).
- `DataUniqueKeyHolder` only allows to store one-value-per-key so you can't store different values with the same key.
- `DataTreeHolder` This one is also one-value-per-key but it works like the `TreeMap`. Its automatically sorts the stored `Properties`.

```java
public interface DataCarrier {

    /**
     * Stores a Property in the DataHolder
     * @param property
     * @return Returns true if it was successfully and false if a Property with the same key already exists in the DataHolder object.
     */
    boolean store(Property<?, ?> property);

    /**
     * Returns all stored Properties for this DataHolder.
     * @return
     */
    Collection<Property<?, ?>> getProperties();

    /**
     * Returns all stored Property types.
     * @return
     */
    Collection<Class<?>> getPropertyTypes();

    /**
     * Returns the Property by key.
     * @param <T>
     * @param propertyClass The property class which implements the {@see de.SweetCode.DataHolder.Property.Property} interface.
     * @return T the result, if no property is stored for the class the function will return null.
     */
    <T extends Property> Property<?, ?> getProperty(Class<T> propertyClass, Object key);

    /**
     * Checks if the DataHolder contains the given Property class and if the class is related with the given key.
     * @param propertyClass The Property class
     * @param <T>
     * @return true if the DataHolder contains the Property class with the related key.
     */
    <T extends Property<?, ?>> boolean contains(Class<T> propertyClass, Object key);

    /**
     * Deletes a Property that is related with a key.
     * @param propertyClass The Property class.
     * @param key The related key.
     * @param <T>
     * @return returns the deleted Property object if the DataHolder contains the given pair of Property class and key otherwise it returns null.
     */
    <T extends Property<?, ?>> Optional<T> deleteProperty(Class<T> propertyClass, Object key);

    /**
     * Updates the value of a property.
     * @param propertyClass The property class.
     * @param key The related key.
     * @param value The value to set.
     * @param <K>
     * @param <V>
     * @param <T>
     * @return boolean true if it was successfully, otherwise false
     */
    <K, V, T extends Property<K, V>> boolean update(Class<T> propertyClass, K key, V value);

    /**
     * Returns the size.
     * @return
     */
    int size();

    /**
     * Deletes all stored Properties.
     */
    void clear();

}
```

---
### Property Class
To create your own `Property` class you just have to implement the `Property interface`.
The interface contains three methods `K getKey()`, `Optional<V> get()` and `Property<K, V> update(Object value)`.

Below you can find a simple example how it works:

```java
import de.SweetCode.DataHolder.Property.Property;

public class ExampleProperty implements Property<String, Double> {

    private String key;
    private Optional<Double> value;

    public ExampleProperty(String key, double value) {

        this.key = key;
        this.double = Optional.of(value);

    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Optional<Double> getValue() {
        return this.value;
    }

    @Override
    public Property<String, Double> update(Double value) {

        this.value = Optional.of(value);

        return this;
    }

}
```
---
### Serializable Property
It's basically the same but you also implement a method that allows you to return
the `Property` in a serialized version.
```java
import de.SweetCode.DataHolder.Property.PropertySerializable;

public class ExampleSerializableProperty implements PropertySerializable<String, Double, String> {

    private String key;
    private Optional<Double> value;

    public ExampleProperty(String key, double value) {

        this.key = key;
        this.double = Optional.of(value);

    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Optional<Double> getValue() {
        return this.value;
    }

    @Override
    public Property<String, Double> update(Double value) {

        this.value = Optional.of(value);

        return this;
        
        // Well, instead you also can return a new instance.
        // return new ExampleProperty(this.key, this.value.isPresent() ? this.value.get() : null);
    }

    @Override
    public String serialize() {

        return String.format(
                "{\"key\":%s,\"value\":%d}",
                this.getKey(),
                this.get().get()
        );

    }

}
```
---
#### Store Method
This method allows you to store a `Property`. The project already contains for all
java primitive data types (`String`, `Integer`, `Boolean` etc.) a basic `Property` class.
```java
PlayerData player = new PlayerData();

player.store(new ExampleProperty("Money", 1200.5));
player.store(new ExampleProperty("Health", 12));
```

#### Get Property/Properties
This method returns the first `Property`, all `Properties` of the same kind or a `Property` related to a key.

```java
ExampleProperty property = player.getProperty(ExampleProperty.class, "Money").get();
System.out.println(property.getKey() + " => " + property.get().get());
```
This code writes into your console `Money => 1200.5`.
And this is how you get all of the stored Properties of the same kind.
```java
List<ExampleProperty> properties = player.getProperties(ExampleProperty.class);

for(ExampleProperty entry : properties) {
    System.out.println(entry.getKey() + " => " + entry.get().get());
}

```

This code writes into your console.
```
Money => 1200.5
Health => 12
```
---
### Delete Property/Properties
To delete all `Properties` of the same kind is really easy.
```java
player.deleteProperties(ExampleProperty.class);
```
This would delete all `Properties` of the kind `ExampleProperty`.

It's also really easy to delete a `Property` based on the type and key.
```java
player.deleteProperty(ExampleProperty.class, "A");
```
---
### Store all kind of Properties
This is the main advantage. You can store all kind of `Property` in the `DataCarrier` it just has to be a `Property` everything else is up to you.

```java
player.store(new BooleanProperty<String>("online", true));
player.store(new StringProperty<String>("homepage", "http://example.org"));
```

### Update Properties
This is really simple but at least you have to ways to do this.
```java
player.getProperty(BooleanProperty.class, "online").update(false); //Changes the value to false
```
or
```java
player.updateProperty(BooleanProperty.class, "online", false); //Changes the value to false
```

package ro.rasel.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapBuilder<K, V> {
    private final Supplier<Map<K, V>> supplier;
    private final Map<K, V> map;

    private MapBuilder(Supplier<Map<K, V>> supplier) {
        this.supplier = supplier;
        this.map = supplier.get();
    }

    public static <K, V> MapBuilder<K, V> ofMap() {
        return new MapBuilder<>(HashMap::new);
    }

    public static <K, V> MapBuilder<K, V> ofOrderedMap() {
        return new MapBuilder<>(LinkedHashMap::new);
    }

    public MapBuilder<K, V> put(K k, V v) {
        map.put(k, v);
        return this;
    }

    public MapBuilder<K, V> putAll(Map<? extends K, ? extends V> map) {
        this.map.putAll(map);
        return this;
    }

    public MapBuilder<K, V> remove(K k) {
        map.remove(k);
        return this;
    }

    /**
     * When you want any map {@link Map} or an ordered one you can simply use this method instead of {@link MapBuilder#build(Function)} .
     *
     * @return an immutable map containing the elements provided to the {@link MapBuilder}
     */
    public Map<K, V> build() {
        return build(map -> {
            Map<K, V> newMap = supplier.get();
            newMap.putAll(map);
            return Collections.unmodifiableMap(newMap);
        });
    }

    /**
     * While {@link MapBuilder} has the purpose to help creating a new {@link Map} or any subtype by using the builder
     * pattern the client of the method will have to actually create it himself by providing a function that should be
     * used for bot creation of the new {@link Map} and copying the content.
     * This allows for both creation of any type of {@link Map} and a way to create an immutable {@link Map}.<br>
     *
     * <pre> {@code mapBuilder.build(HashMap::new);
     *  mapBuilder.build(m -> Collections.unmodifiableNavigableMap(new TreeMap<>(m))); } </pre>
     * <p>
     * While the client can use the identity function this is not normally a good approach from the perspective of builder parent.
     *
     * <pre> {@code mapBuilder.build(Function.identity()); } </pre>
     * <p>
     * From the perspective of correctness of the code this is does not pose any risk as long as an unordered or
     * ordered map is needed, the build method is called only once and none of the modify methods are called for the
     * builder after that. <br>
     * In addition to that the builder can be used to build any Object based on that map like in the following example:
     *
     * <pre> {@code Stream<Integer> stream = mapBuilder.build(map -> map.keySet().stream());
     *  stream.map(Object::toString).collect(Collectors.joining(", "));} </pre>
     *
     * @param function used for generating the build map
     * @param <S>      type of map
     * @return new object
     */
    public <S> S build(Function<Map<K, V>, ? extends S> function) {
        return function.apply(Collections.unmodifiableMap(map));
    }
}

package ro.rasel.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MapBuilderTest {

    @Test
    public void mapTest() {
        MapBuilder<Integer, String> mapBuilder =
                MapBuilder.<Integer, String>ofMap().put(2, "2").put(1, "One").put(2, "Two").put(3, "Three");

        Map<Integer, String> hashMap = mapBuilder.build(HashMap::new);
        Map<Integer, String> linkedHashMap = mapBuilder.build(LinkedHashMap::new);
        Map<Integer, String> treeMap = mapBuilder.build(TreeMap::new);
        Map<Integer, String> immutableMap = mapBuilder.build(m -> Collections.unmodifiableMap(new HashMap<>(m)));
        Map<Integer, String> immutableNavigableMap =
                mapBuilder.build(m -> Collections.unmodifiableNavigableMap(new TreeMap<>(m)));

        assertMapContentAndType(hashMap, HashMap.class);
        assertMapContentAndType(linkedHashMap, LinkedHashMap.class);
        assertMapContentAndType(treeMap, TreeMap.class);
        assertMapContentAndType(immutableMap, Map.class);
        assertMapContentAndType(immutableNavigableMap, NavigableMap.class);
    }

    @Test
    public void orderedMapTest() {
        MapBuilder<Integer, String> orderedMapBuilder =
                MapBuilder.<Integer, String>ofOrderedMap()
                        .put(2, "2")
                        .put(1, "One")
                        .put(5, "Five")
                        .put(2, "Two")
                        .put(4, "Four")
                        .put(3, "Three");

        Map<Integer, String> linkedHashMap = orderedMapBuilder.build(LinkedHashMap::new);
        ArrayList<Integer> integers = new ArrayList<>(linkedHashMap.keySet());
        assertThat(integers.get(0), is(2));
        assertThat(integers.get(1), is(1));
        assertThat(integers.get(2), is(5));
        assertThat(integers.get(3), is(4));
        assertThat(integers.get(4), is(3));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testImmutable() {
        MapBuilder<Integer, String> mapBuilder =
                MapBuilder.<Integer, String>ofMap().put(2, "2").put(1, "One").put(2, "Two").put(3, "Three");
        Map<Integer, String> immutableMap = mapBuilder.build(m -> Collections.unmodifiableMap(new HashMap<>(m)));
        immutableMap.put(4, "4");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testImmutableMapProvidedInFunction() {
        MapBuilder<Integer, String> mapBuilder =
                MapBuilder.<Integer, String>ofMap().put(2, "2").put(1, "One").put(2, "Two").put(3, "Three");
        mapBuilder.build(m -> {
            m.put(4, "4");
            return Collections.unmodifiableMap(new HashMap<>(m));
        });
    }

    @Test
    public void testBuilderForNonMapObjects() {
        MapBuilder<Integer, String> mapBuilder =
                MapBuilder.<Integer, String>ofOrderedMap().put(2, "2").put(1, "One").put(2, "Two").put(3, "Three");
        String join =
                mapBuilder.build(map -> map.keySet().stream()).map(Object::toString).collect(Collectors.joining(", "));
        assertThat(join, is("2, 1, 3"));
    }

    private static <R extends Map<?, ?>> void assertMapContentAndType(R map, Class<? extends R> clazz) {
        assertThat(map.get(1), is("One"));
        assertThat(map.get(2), is("Two"));
        assertThat(map.get(3), is("Three"));
        assertThat(map.size(), is(3));
        assertTrue(clazz.isAssignableFrom(map.getClass()));
    }

}
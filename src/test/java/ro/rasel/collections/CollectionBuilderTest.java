package ro.rasel.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CollectionBuilderTest {

    @Test
    public void ofCollection() {
        CollectionBuilder<Integer> collectionBuilder =
                CollectionBuilder.<Integer>ofCollection().add(2).add(1).add(2).add(3);

        Collection<Integer> arrayList = collectionBuilder.build(ArrayList::new);
        Collection<Integer> linkedList = collectionBuilder.build(LinkedList::new);
        Collection<Integer> hashSet = collectionBuilder.build(HashSet::new);
        Collection<Integer> linkedHashSet = collectionBuilder.build(LinkedHashSet::new);
        Collection<Integer> treeSet = collectionBuilder.build(TreeSet::new);
        Collection<Integer> immutableSet = collectionBuilder.build(s -> Collections.unmodifiableSet(new HashSet<>(s)));
        Collection<Integer> immutableNavigableSet =
                collectionBuilder.build(s -> Collections.unmodifiableNavigableSet(new TreeSet<>(s)));

        assertMapContentAndType(arrayList, ArrayList.class, 4);
        assertMapContentAndType(linkedList, LinkedList.class, 4);
        assertMapContentAndType(hashSet, HashSet.class, 3);
        assertMapContentAndType(linkedHashSet, LinkedHashSet.class, 3);
        assertMapContentAndType(treeSet, TreeSet.class, 3);
        assertMapContentAndType(immutableSet, Set.class, 3);
        assertMapContentAndType(immutableNavigableSet, NavigableSet.class, 3);
    }

    @Test
    public void ofOrderedCollection() {
        CollectionBuilder<Integer> orderedSetBuilder =
                CollectionBuilder.<Integer>ofOrderedCollection().add(2).add(1).add(5).add(2).add(4).add(3);

        Stream.of(orderedSetBuilder.build(), orderedSetBuilder.build(ArrayList::new))
                .forEach(collection -> {
                            ArrayList<Integer> integers = new ArrayList<>(collection);
                            assertThat(integers.get(0), is(2));
                            assertThat(integers.get(1), is(1));
                            assertThat(integers.get(2), is(5));
                            assertThat(integers.get(3), is(2));
                            assertThat(integers.get(4), is(4));
                            assertThat(integers.get(5), is(3));
                        }
                );
    }

    @Test
    public void ofSet() {
        CollectionBuilder<Integer> orderedSetBuilder =
                CollectionBuilder.<Integer>ofSet().add(2).add(1).add(5).add(2).add(4).add(3);

        Stream.of(orderedSetBuilder.build(), orderedSetBuilder.build(ArrayList::new))
                .forEach(collection -> {
                            assertThat(collection.contains(2), is(true));
                            assertThat(collection.contains(1), is(true));
                            assertThat(collection.contains(5), is(true));
                            assertThat(collection.contains(2), is(true));
                            assertThat(collection.contains(4), is(true));
                            assertThat(collection.size(), is(5));
                        }
                );
    }

    @Test
    public void ofOrderedSet() {
        CollectionBuilder<Integer> orderedSetBuilder =
                CollectionBuilder.<Integer>ofOrderedSet().add(2).add(1).add(5).add(2).add(4).add(3);

        Stream.of(orderedSetBuilder.build(), orderedSetBuilder.build(ArrayList::new))
                .forEach(collection -> {
                            ArrayList<Integer> integers = new ArrayList<>(collection);
                            assertThat(integers.get(0), is(2));
                            assertThat(integers.get(1), is(1));
                            assertThat(integers.get(2), is(5));
                            assertThat(integers.get(3), is(4));
                            assertThat(integers.get(4), is(3));
                            assertThat(collection.size(), is(5));
                        }
                );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void assertImmutable() {
        CollectionBuilder<Integer> collectionBuilder =
                CollectionBuilder.<Integer>ofCollection().add(2).add(1).add(2).add(3);
        Collection<Integer> immutableMap =
                collectionBuilder.build(c -> Collections.unmodifiableCollection(new ArrayList<>(c)));
        immutableMap.add(4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void assertImmutableCollectionProvidedInFunction() {
        CollectionBuilder<Integer> collectionBuilder =
                CollectionBuilder.<Integer>ofCollection().add(2).add(1).add(2).add(3);
        collectionBuilder.build(c -> {
            c.add(4);
            return Collections.unmodifiableCollection(new ArrayList<>(c));
        });
    }

    @Test
    public void builderForNonCollectionObjects() {
        CollectionBuilder<Integer> collectionBuilder =
                CollectionBuilder.<Integer>ofCollection().add(2).add(1).add(2).add(3);
        String join = collectionBuilder.build(Collection::stream).map(Object::toString)
                .collect(Collectors.joining(", "));
        assertThat(join, is("2, 1, 2, 3"));
    }

    private static <R extends Collection<?>> void assertMapContentAndType(R collection, Class<? extends R> clazz,
            int size) {
        assertThat(collection.contains(1), is(true));
        assertThat(collection.contains(2), is(true));
        assertThat(collection.contains(3), is(true));
        assertThat(collection.size(), is(size));
        assertTrue(clazz.isAssignableFrom(collection.getClass()));
    }

}
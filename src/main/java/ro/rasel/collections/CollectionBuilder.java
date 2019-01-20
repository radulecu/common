package ro.rasel.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.function.Supplier;

public class CollectionBuilder<E> {
    private final Supplier<Collection<E>> supplier;
    private final Collection<E> collection;

    private CollectionBuilder(Supplier<Collection<E>> supplier) {
        this.supplier = supplier;
        this.collection = supplier.get();
    }

    public static <K> CollectionBuilder<K> ofCollection() {
        return ofOrderedCollection();
    }

    public static <K> CollectionBuilder<K> ofSet() {
        return new CollectionBuilder<>(HashSet::new);
    }

    public static <K> CollectionBuilder<K> ofOrderedCollection() {
        return new CollectionBuilder<>(ArrayList::new);
    }

    public static <K> CollectionBuilder<K> ofOrderedSet() {
        return new CollectionBuilder<>(LinkedHashSet::new);
    }

    public CollectionBuilder<E> add(E e) {
        collection.add(e);
        return this;
    }

    public CollectionBuilder<E> addAll(Collection<E> collection) {
        this.collection.addAll(collection);
        return this;
    }

    public CollectionBuilder<E> remove(E e) {
        collection.remove(e);
        return this;
    }

    /**
     * When you want any {@link Collection}, or one that respects the builder type (e.g. ordered based on
     * {@link CollectionBuilder#ofOrderedCollection()}) you can simply use this method instead of {@link CollectionBuilder#build(Function)} .
     *
     * @return an immutable map containing the elements provided to the {@link CollectionBuilder}
     */
    public Collection<E> build() {
        return build(collection -> {
            Collection<E> newCollection = supplier.get();
            newCollection.addAll(collection);
            return Collections.unmodifiableCollection(newCollection);
        });
    }

    /**
     * While {@link CollectionBuilder} has the purpose to help creating a new {@link Collection} or any subtype
     * by using the builder pattern, the client of the method will have to actually create it himself by providing a
     * function that should be used for both creation of the new {@link Collection} and copying the content.
     * This allows for both creation of any type of {@link Collection} and a way to create an immutable one.<br>
     *
     * <pre> {@code collectionBuilder.build(ArrayList::new);
     *  collectionBuilder.build(m -> Collections.unmodifiableNavigableSet(new TreeSet<>(m))); } </pre>
     * <p>
     * While the client can use the identity function this is not normally a good approach from the perspective of builder parent.
     *
     * <pre> {@code collectionBuilder.build(Function.identity()); } </pre>
     * <p>
     * From the perspective of correctness of the code this does not pose any risk as long as the build method is called
     * only once and none of the modify methods are called for the builder after that. <br>
     * In addition to that the builder can be used to build any Object based on that collection like in the following example:
     *
     * <pre> {@code Stream<Integer> stream = collectionBuilder.build(Collection::stream);
     *  stream.collection(Object::toString).collect(Collectors.joining(", "));} </pre>
     *
     * @param function used for generating the build map
     * @param <S>      type of collection
     * @return new object
     */
    public <S> S build(Function<Collection<E>, ? extends S> function) {
        return function.apply(Collections.unmodifiableCollection(collection));
    }
}

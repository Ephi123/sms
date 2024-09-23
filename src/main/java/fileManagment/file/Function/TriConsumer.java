package fileManagment.file.Function;

@FunctionalInterface
public interface TriConsumer<U,T,V> {
     void accept(U u, T t, V v);

}

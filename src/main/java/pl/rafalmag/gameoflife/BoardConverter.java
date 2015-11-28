package pl.rafalmag.gameoflife;

/**
 * Bidirectional converter between {@link Board} and some object.
 * @param <T> type of the object to convert to/from
 */
public interface BoardConverter<T> {

    Board convert(T board);
    T convert(Board board);
}

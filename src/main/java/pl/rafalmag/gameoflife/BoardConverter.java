package pl.rafalmag.gameoflife;

/**
 * Converter from some object to {@link Board}.
 *
 * @param <T> type of the object to from
 */
public interface BoardConverter<T> {

    Board convertFrom(T board);

}

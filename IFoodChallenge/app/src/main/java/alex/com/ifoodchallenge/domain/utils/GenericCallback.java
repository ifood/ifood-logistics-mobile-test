package alex.com.ifoodchallenge.domain.utils;

public abstract class GenericCallback<T> {
    public abstract void call(String result);

    public boolean success;
    public T object;
    public GenericCallback innerCallback;
}

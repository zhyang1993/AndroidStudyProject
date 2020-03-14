// IMyAidlInterface.aidl
package com.example.myapplication.aidldemo;
import com.example.myapplication.aidldemo.Book;
import com.example.myapplication.aidldemo.IOnNewBookListener;
interface IMyAidlInterface {
    List<Book> getAllBooks();
    void addBook(in Book book);
    void resigerListener(IOnNewBookListener iOnNewBookListener);
    void unresigerListener(IOnNewBookListener iOnNewBookListener);
}

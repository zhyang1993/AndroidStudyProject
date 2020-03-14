// IOnNewBookListener.aidl
package com.example.myapplication.aidldemo;
import com.example.myapplication.aidldemo.Book;
// Declare any non-default types here with import statements

interface IOnNewBookListener {
 void onNewBook(in Book book);
}

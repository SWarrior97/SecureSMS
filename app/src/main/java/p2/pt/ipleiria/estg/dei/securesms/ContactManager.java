package p2.pt.ipleiria.estg.dei.securesms;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.support.v4.app.ListFragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactManager{
    private List<Contact> contacts;


    public ContactManager() {
        contacts = new ArrayList<>();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

   
    }
}

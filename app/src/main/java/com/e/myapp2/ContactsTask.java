package com.e.myapp2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.e.myapp2.data.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsTask extends AsyncTask<String, String, String> {

    private Context context;
    private List<Contact> contacts;
    private TasklListener listener;

    public ContactsTask(Context context, TasklListener listener) {
        this.context = context;
        this.listener = listener;
        contacts = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... strings) {
        getContactList();

        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        listener.onContactsReady(contacts);
    }

    private void getContactList() {
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("contact", "Name: " + name);
                        Log.i("contact", "Phone Number: " + phoneNo);
                        contacts.add(new Contact(name, phoneNo));
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

    public interface TasklListener{
        void onContactsReady(List<Contact> contacts);
    }
}

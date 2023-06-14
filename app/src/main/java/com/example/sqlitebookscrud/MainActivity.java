package com.example.sqlitebookscrud;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText Title, Author ,PubDate, updateold, updatenew, delete;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Title= (EditText) findViewById(R.id.edit_book);
        Author= (EditText) findViewById(R.id.edit_author);
        PubDate= (EditText) findViewById(R.id.edit_pub_date);
        updateold= (EditText) findViewById(R.id.cbn);
        updatenew= (EditText) findViewById(R.id.nbn);
        delete = (EditText) findViewById(R.id.del_book);

        helper = new myDbAdapter(this);
    }
    public void addBooks(View view)
    {
        String t1 = Title.getText().toString();
        String t2 = Author.getText().toString();
        String t3 = PubDate.getText().toString();
        if(t1.isEmpty() || t2.isEmpty() || t3.isEmpty()) //Validation
        {
            Message.message(getApplicationContext(),"Enter all Details");
        }
        else
        {
            long id = helper.insertData(t1,t2,t3);
            if(id<=0)
            {
                Message.message(getApplicationContext(),"Insertion Unsuccessful");
                Title.setText("");
                Author.setText("");
                PubDate.setText("");
            } else
            {
                Message.message(getApplicationContext(),"Insertion Successful");
                Title.setText("");
                Author.setText("");
                PubDate.setText("");
            }
        }
    }

    public void viewBooks(View view)
    {
        String data = helper.getData();
        Message.message(this,data);
    }

    public void updateBooks(View view)
    {
        String b1 = updateold.getText().toString();
        String b2 = updatenew.getText().toString();
        if(b1.isEmpty() || b2.isEmpty())
        {
            Message.message(getApplicationContext(),"Enter Data");
        }
        else
        {
            int a= helper.updateName(b1, b2);
            if(a<=0)
            {
                Message.message(getApplicationContext(),"Unsuccessful");
                updateold.setText("");
                updatenew.setText("");
            } else {
                Message.message(getApplicationContext(),"Updated");
                updateold.setText("");
                updatenew.setText("");
            }
        }

    }
    public void deleteBook( View view)
    {
        String bname = delete.getText().toString();
        if(bname.isEmpty())
        {
            Message.message(getApplicationContext(),"Enter Data");
        }
        else{
            int a= helper.delete(bname);
            if(a<=0)
            {
                Message.message(getApplicationContext(),"Unsuccessful");
                delete.setText("");
            }
            else
            {
                Message.message(this, "DELETED");
                delete.setText("");
            }
        }
    }
}
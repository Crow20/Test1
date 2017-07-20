package com.sike.courses.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.sike.courses.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity {

    private String[] mGroupsArray = new String[] { "Информация о разработчике", "Контакты", "О приложении"};

    private String[] mContacts = new String[] { "Март", "Апрель"};
    private String[] mApplication = new String[] { "Июнь", "Июль"};


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setTitle("О программе");

        Map<String, String> map;
        // коллекция для групп
        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
        // заполняем коллекцию групп из массива с названиями групп

        for (String group : mGroupsArray) {
            // заполняем список атрибутов для каждой группы
            map = new HashMap<>();
            map.put("groupName", group); // время года
            groupDataList.add(map);
        }

        // список атрибутов групп для чтения
        String groupFrom[] = new String[] { "groupName" };
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] { android.R.id.text1 };

        //Создаём коллекцию для вложений в пункты
        ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>

        // создаем коллекцию элементов для первой группы
        ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
        // заполняем список атрибутов для каждого элемента


//        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
//        tvTitle.setText(getResources().getString(R.string.info_title));
        String[] mInfo = new String[] {getResources().getString(R.string.info_paragraph), getResources().getString(R.string.info_title)};
        map = new HashMap<>();
        map.put("text", mInfo[0]);
        map.put("title", mInfo[1]);
        сhildDataItemList.add(map);

        // добавляем в коллекцию коллекций
        сhildDataList.add(сhildDataItemList);

        //сhildDataItemList.clear();

        ArrayList<Map<String, String>> сhildDataItemList1 = new ArrayList<>();

        String[] mContacts = new String[] { getResources().getString(R.string.contacts_text), getResources().getString(R.string.contacts_title)};
        map = new HashMap<>();
        map.put("text", mContacts[0]);
        map.put("title", mContacts[1]);
        сhildDataItemList1.add(map);

        // добавляем в коллекцию коллекций
        сhildDataList.add(сhildDataItemList1);

        //сhildDataItemList1.clear();

        ArrayList<Map<String, String>> сhildDataItemList2 = new ArrayList<>();

        String[] mApplication = new String[] { getResources().getString(R.string.application_text), getResources().getString(R.string.application_title)};
        map = new HashMap<>();
        map.put("text", mApplication[0]);
        map.put("title", mApplication[1]);
        сhildDataItemList2.add(map);

        // добавляем в коллекцию коллекций
        сhildDataList.add(сhildDataItemList2);

        // список атрибутов элементов для чтения
        String childFrom[] = new String[] { "text", "title" };
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        int childTo[] = new int[] { R.id.tvText, R.id.tvTitle };

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, R.layout.expandable_list_item,
                childFrom, childTo);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expListView);
        expandableListView.setAdapter(adapter);
    }
}
